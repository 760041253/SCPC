package top.hcode.hoj.service.signup.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.hcode.hoj.common.exception.StatusFailException;
import top.hcode.hoj.common.exception.StatusForbiddenException;
import top.hcode.hoj.common.result.CommonResult;
import top.hcode.hoj.common.result.ResultStatus;
import top.hcode.hoj.mapper.SignUpContestMapper;
import top.hcode.hoj.mapper.TeamInfoMapper;
import top.hcode.hoj.mapper.UserInfoMapper;
import top.hcode.hoj.pojo.dto.TeamInfoDTO;
import top.hcode.hoj.pojo.entity.signup.SignUpContest;
import top.hcode.hoj.pojo.entity.signup.TeamInfo;
import top.hcode.hoj.pojo.entity.user.UserInfo;
import top.hcode.hoj.pojo.vo.TeamInfoVO;
import top.hcode.hoj.service.signup.TeamInfoEntityService;
import top.hcode.hoj.shiro.AccountProfile;
import top.hcode.hoj.utils.Constants;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 76004
 * @apiNote
 * @date 2023/4/10 14:12
 */
@Service
public class TeamInfoEntityServiceImpl implements TeamInfoEntityService {

    @Autowired
    private SignUpContestMapper signUpContestMapper;

    @Autowired
    private TeamInfoMapper teamInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public CommonResult<List<TeamInfoVO>> selectWaitingReviewTeamInfoPage(Long cid, Integer pageSize, Integer pageNum)
            throws StatusForbiddenException, StatusFailException {
        SignUpContest contest = signUpContestMapper.selectById(cid);

        if (contest == null) {
            throw new StatusFailException("没有这场比赛");
        }

        AccountProfile accountProfile = (AccountProfile) SecurityUtils.getSubject().getPrincipal();

        boolean isRoot = SecurityUtils.getSubject().hasRole("root");

        if (!isRoot) {
            throw new StatusForbiddenException("您没有权限获取该名单!");
        }
        List<TeamInfo> teamInfos = teamInfoMapper.selectList(new QueryWrapper<TeamInfo>().eq("sign_up_contest_id", cid)
                .eq("status", Constants.Team.APPLY_FOR_UPDATE));
        List<TeamInfoVO> teamInfoVos = new ArrayList<>();
        BeanUtil.copyProperties(teamInfos, teamInfoVos, "password");
        return CommonResult.successResponse(teamInfoVos);
    }

    /***
     * 查询用户在当前比赛的组队信息
     *
     * @param cid
     * @return
     */
    @Override
    public CommonResult<List<TeamInfoVO>> getTeamInfoByUsernameFromContestId(Long cid) throws StatusFailException {
        // 获取当前登录的用户
        AccountProfile userRolesVo = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        // 获取本场比赛的状态
        SignUpContest contest = signUpContestMapper.selectById(cid);
        if (contest == null) {
            throw new StatusFailException("报名列表不存在");
        }

        List<TeamInfoVO> teamInfoVOS = teamInfoMapper
                .selectList(new QueryWrapper<TeamInfo>().eq("sign_up_contest_id", contest.getId())
                        .like("users_name", userRolesVo.getUsername() + "-%"))
                .stream().filter(entity -> {
                    // 是不是队长
                    if (userRolesVo.getUsername().equals(entity.getUsersName().split("-")[0])) {
                        return true;
                    } else {
                        return false;
                    }
                }).map(entity -> {
                    return BeanUtil.copyProperties(entity, TeamInfoVO.class, "password");
                }).collect(Collectors.toList());

        return CommonResult.successResponse(teamInfoVOS);
    }

    /***
     * 获取待审核的报名信息
     *
     * @param cid
     * @param pageSize
     * @param pageNum
     * @return
     * @throws StatusFailException
     * @throws StatusForbiddenException
     */
    @Override
    public CommonResult<IPage<TeamInfo>> getTeamInfoPageByContestId(Long cid, Integer pageSize, Integer pageNum,
            Integer status) throws StatusFailException, StatusForbiddenException {

        SignUpContest contest = signUpContestMapper.selectById(cid);
        if (contest == null) {
            throw new StatusFailException("报名收集表不存在");
        }
        List<TeamInfo> teamInfos = null;
        List<TeamInfoVO> teamInfoVOS = new ArrayList<>();
        if (status == 0) {
            return CommonResult.successResponse(teamInfoMapper.selectPage(new Page<TeamInfo>(pageNum, pageSize),
                    new QueryWrapper<TeamInfo>().eq("sign_up_contest_id", cid).orderByAsc("star")
                            .orderByDesc("create_time")));
        } else if (status.equals(Constants.Team.PENDING.getStatus())
                || status.equals(Constants.Team.REJECT.getStatus())
                || status.equals(Constants.Team.ACCEPT.getStatus())
                || status.equals(Constants.Team.APPLY_FOR_UPDATE.getStatus())) {
            return CommonResult.successResponse(teamInfoMapper.selectPage(new Page<TeamInfo>(pageNum, pageSize),
                    new QueryWrapper<TeamInfo>().eq("sign_up_contest_id", cid).eq("status", status).orderByAsc("star")
                            .orderByDesc("create_time")));
        } else {
            throw new StatusFailException("状态码不正确");
        }

        // for(TeamInfo teamInfo:teamInfos){
        // TeamInfoVO teamInfoVO = new TeamInfoVO();
        // BeanUtil.copyProperties(teamInfo,teamInfoVO);
        // teamInfoVO.setCreatTime(teamInfo.getCreateTime());
        // teamInfoVOS.add(teamInfoVO);
        // }
        //
        //
        // return CommonResult.successResponse(teamInfoVOS);
    }

    @Override
    public CommonResult<TeamInfoVO> getTeamInfoById(Long teamId) throws StatusFailException {
        TeamInfo teamInfo = teamInfoMapper.selectById(teamId);
        if (teamInfo == null) {
            throw new StatusFailException("队伍不存在");
        }
        TeamInfoVO teamInfoVO = new TeamInfoVO();
        BeanUtil.copyProperties(teamInfo, teamInfoVO, "password");
        return CommonResult.successResponse(teamInfoVO);
    }

    @Override
    public CommonResult<Void> updateSignUpTable(TeamInfoDTO teamInfoDTO) throws StatusForbiddenException {
        // 获取当前登录的用户
        AccountProfile userRolesVo = (AccountProfile) SecurityUtils.getSubject().getPrincipal();

        String[] users = teamInfoDTO.getUsersName().split("-");

        boolean isRoot = SecurityUtils.getSubject().hasRole("root");
        if (!userRolesVo.getUsername().equals(users[0]) && !isRoot) {
            throw new StatusForbiddenException("不是你的报名信息");
        }

        SignUpContest contest = signUpContestMapper.selectById(teamInfoDTO.getSignUpContestId());

        // 比赛是否结束
        if (new Date().after(contest.getEndTime()) && !isRoot) {
            throw new StatusForbiddenException("比赛已结束，不能更改");
        }
        if (teamInfoDTO.getStatus().equals(Constants.Team.ACCEPT.getStatus())) {
            throw new StatusForbiddenException("您的申请已通过，请提交更改申请");
        }
        TeamInfo teamInfo = new TeamInfo();
        BeanUtil.copyProperties(teamInfoDTO, teamInfo, "password");

        // 判断是否为女队
        String[] genders = teamInfo.getGender().split("-");
        teamInfo.setTeamType(1);
        for (String i : genders) {
            if (i.equals("0")) {
                teamInfo.setTeamType(0);
                break;
            }
        }

        // 队伍名判重
        int count = teamInfoMapper.selectCount(new QueryWrapper<TeamInfo>()
                .eq("sign_up_contest_id", teamInfo.getSignUpContestId()).eq("team_name", teamInfo.getTeamName()));
        if (count > 1) {
            throw new StatusForbiddenException("队伍名重复");
        }

        // 将比赛与实际用户绑定（如果填写了的话）
        String[] username = teamInfo.getUsersName().split("-");
        // 队长一定是当前发起报名的用户
        username[0] = userRolesVo.getUsername();
        if (username[0].equals(username[1]) || (!Objects.equals(username[2], "*") && username[1].equals(username[2]))
                || username[0].equals(username[2])) {
            throw new StatusForbiddenException("队员重复");
        }
        for (String i : username) {
            if (!i.equals("*")) {
                // 用户填写了用户名
                UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("username", i));
                if (userInfo != null) {
                    // 增加用户比赛记录(待添加)
                } else {
                    throw new StatusForbiddenException("用户不存在，请检查用户名是否填写正确");
                }
            }
        }
        teamInfo.setStatus(Constants.Team.APPLY_FOR_UPDATE.getStatus());
        teamInfoMapper.updateById(teamInfo);
        return CommonResult.successResponse();
    }

    public CommonResult<Integer> getACTeamCount(Long contestId) {
        return CommonResult.successResponse(teamInfoMapper.selectCount(new QueryWrapper<TeamInfo>()
                .eq("sign_up_contest_id", contestId).eq("status", Constants.Team.ACCEPT.getStatus())));
    }

    /***
     * 报名
     */
    @Override
    public CommonResult<Void> signUp(TeamInfoDTO teamInfoDto) throws StatusFailException {
        // 获取当前登录的用户
        AccountProfile userRolesVo = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        // 获取本场比赛报名的状态
        SignUpContest signUpContest = signUpContestMapper.selectById(teamInfoDto.getSignUpContestId());

        TeamInfo teamInfo = new TeamInfo();
        // 将传输的属性赋值给entity
        BeanUtil.copyProperties(teamInfoDto, teamInfo);

        if (signUpContest == null) {
            throw new StatusFailException("错误：该报名列表不存在！");
        }
        if (new Date().after(signUpContest.getEndTime()) || signUpContest.getStartTime().after(new Date())) {
            throw new StatusFailException("不能在报名时间之外报名");
        }

        teamInfo.setStatus(Constants.Team.PENDING.getStatus());

        if (teamInfoMapper.selectCount(new QueryWrapper<TeamInfo>().eq("sign_up_contest_id", signUpContest.getId())
                .eq("users_name", userRolesVo.getUsername() + "%")) == 1) {
            throw new StatusFailException("您在该比赛已有报名记录，请修改报名信息");
        }

        // 队伍名判重
        int count = teamInfoMapper.selectCount(new QueryWrapper<TeamInfo>()
                .eq("sign_up_contest_id", teamInfo.getSignUpContestId()).eq("team_name", teamInfo.getTeamName()));
        if (count > 0) {
            throw new StatusFailException("队伍名重复");
        }

        // 队伍英文名判重(x)

        // 将比赛与实际用户绑定（如果填写了的话）
        String[] username = teamInfo.getUsersName().split("-");
        // 队长一定是当前发起报名的用户
        username[0] = userRolesVo.getUsername();
        if (username[0].equals(username[1]) || (!Objects.equals(username[2], "*") && username[1].equals(username[2]))
                || username[0].equals(username[2])) {
            throw new StatusFailException("队员重复");
        }
        for (String i : username) {
            if (!i.equals("*")) {
                // 用户填写了用户名
                UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("username", i));
                if (userInfo != null) {
                    // 增加用户比赛记录(待添加)
                } else {
                    throw new StatusFailException("用户不存在，请检查用户名是否填写正确");
                }
            }
        }

        // 判断是否为女队
        String[] genders = teamInfo.getGender().split("-");
        teamInfo.setTeamType(1);
        for (String i : genders) {
            if (i.equals("0")) {
                teamInfo.setTeamType(0);
                break;
            }
        }

        teamInfo.setCreateTime(new Date());

        teamInfoMapper.insert(teamInfo);
        return CommonResult.successResponse();
    }

    @Override
    public void generateCsvByContestId(Long cid, Long choose, HttpServletResponse response)
            throws StatusFailException, StatusForbiddenException, IOException {
        SignUpContest signUpContest = signUpContestMapper.selectById(cid);
        if (signUpContest == null) {
            throw new StatusFailException("该报名列表不存在");
        }

        List<TeamInfo> teamInfos = teamInfoMapper.selectList(new QueryWrapper<TeamInfo>().eq("sign_up_contest_id", cid)
                .eq("status", Constants.Team.ACCEPT.getStatus()));

        boolean isRoot = SecurityUtils.getSubject().hasRole("root");
        boolean isAdmin = SecurityUtils.getSubject().hasRole("admin");
        if (!isRoot && !isAdmin) {
            throw new StatusForbiddenException("您无权操作!");
        }
        // 导出列表
        List<String[]> csvData = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();
        List<String> title = new ArrayList<>();
        title.add("账号");
        title.add("密码");
        title.add("空行");
        title.add("姓名");
        title.add("女队?");
        title.add("队伍名称");
        title.add("队伍英文名");
        data.add(title);
        for (int i = 0; i < teamInfos.size(); i++) {
            TeamInfo teamInfo = teamInfos.get(i);
            String teamUsername = "CT-" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-" + (101 + i);
            String password = RandomUtil.randomString(8);
            String[] teammatesName = teamInfo.getTeammatesName().split("-");
            String[] studentsId = teamInfo.getStudentId().split("-");
            String[] studentClass = teamInfo.getStudentClass().split("-");
            String[] size = teamInfo.getSize().split("-");
            String[] phoneNumber = teamInfo.getPhoneNumber().split("-");
            String[] usersName = teamInfo.getUsersName().split("-");
            List<String> nowData = new ArrayList<>();
            // CT-(含零年月日)-(顺序数字)
            nowData.add(teamUsername);
            // 默认八位随机密码
            nowData.add(password);
            // 空行
            nowData.add(" ");
            // 队员a名字(-队员b名字)(-队员c名字)
            nowData.add(teamInfo.getTeammatesName());
            nowData.add(String.valueOf(teamInfo.getTeamType()));
            nowData.add(teamInfo.getTeamName());
            nowData.add(teamInfo.getEnglishTeamName());
            for (int index = 0; index < 3; index++) {
                // 1.队员姓名
                if ((choose & (1 << 1)) == (1 << 1)) {
                    nowData.add(teammatesName[index]);
                    if (i == 0)
                        title.add(" 姓名");
                }
                // 2.队员学号
                if ((choose & (1 << 2)) == (1 << 2)) {
                    nowData.add(studentsId[index]);
                    if (i == 0)
                        title.add("学号");
                }
                // 3.队员班级
                if ((choose & (1 << 3)) == (1 << 3)) {
                    nowData.add(studentClass[index]);
                    if (i == 0) {
                        title.add("班级");
                    }
                }
                // 4.队员衣服尺码
                if ((choose & (1 << 4)) == (1 << 4)) {
                    nowData.add(size[index]);
                    if (i == 0)
                        title.add("尺码");
                }
                // 5.队员电话号码
                if ((choose & (1 << 5)) == (1 << 5)) {
                    nowData.add(phoneNumber[index]);
                    if (i == 0)
                        title.add("电话");
                }
                // 6.队员scpc用户名
                if ((choose & (1 << 6)) == (1 << 6)) {
                    nowData.add(usersName[index]);
                    if (i == 0)
                        title.add("用户名");
                }
            }
            data.add(nowData);
            teamInfo.setPassword(password);
            teamInfo.setTeamUsername(teamUsername);
        }
        int count = 6;
        for (int i = 1; i <= 6; i++) {
            if (((1 << i) & choose) == (1 << i))
                count++;
        }
        for (List<String> i : data) {
            String[] tmp = new String[i.size()];
            for (int j = 0; j < i.size(); j++) {
                tmp[j] = i.get(j);
            }
            csvData.add(tmp);
        }

        String fileName = "contest_" + cid + "sign_up_+" + System.currentTimeMillis() + ".csv";

        try {
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/ms-txt.numberformat:@");
            response.setContentType("application/x-download");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".csv");
            StringBuffer buf = new StringBuffer();

            for (String[] row : csvData) {
                buf.append(String.join(",", row)).append("\n");
            }
            response.getOutputStream().write(buf.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, Object> map = new HashMap<>();
            map.put("status", ResultStatus.SYSTEM_ERROR);
            map.put("msg", "下载文件失败，请重新尝试！");
            map.put("data", null);
        } finally {
            // log.info("[{}],[{}],pid:[{}],operatorUid:[{}],operatorUsername:[{}]",
            // "Sign_Up", "Download", cid, userRolesVo.getUid(), userRolesVo.getUsername());
            response.getOutputStream().close();
        }

    }

    @Override
    public CommonResult<Void> checkApply(Long tid, Boolean isOk, Long cid)
            throws StatusFailException, StatusForbiddenException {
        boolean isRoot = SecurityUtils.getSubject().hasRole("root");

        SignUpContest contest = signUpContestMapper.selectById(cid);

        if (contest == null) {
            throw new StatusFailException("没有该比赛");
        }

        if (!isRoot) {
            throw new StatusForbiddenException("您没有权限进行此操作");
        }
        TeamInfo teamInfo = teamInfoMapper.selectById(tid);

        if (isOk) {
            teamInfo.setStatus(Constants.Team.ACCEPT.getStatus());
        } else {
            teamInfo.setStatus(Constants.Team.REJECT.getStatus());
        }
        teamInfoMapper.updateById(teamInfo);
        return CommonResult.successResponse();
    }

    @Override
    public CommonResult<Void> withdrawApply(Long tid) throws StatusForbiddenException, StatusFailException {
        AccountProfile userRolesVo = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        TeamInfo teamInfo = teamInfoMapper.selectById(tid);
        if (teamInfo == null) {
            throw new StatusFailException("队伍不存在");
        }
        String[] usernames = teamInfo.getUsersName().split("-");
        if (!userRolesVo.getUsername().equals(usernames[0])) {
            throw new StatusForbiddenException("你无权修改");
        }
        Long cid = teamInfo.getSignUpContestId();
        SignUpContest contest = signUpContestMapper.selectById(cid);

        if (contest == null) {
            throw new StatusFailException("没有该比赛");
        }

        // 比赛是否结束
        if (new Date().after(contest.getEndTime()) || contest.getStartTime().after(new Date())) {
            throw new StatusFailException("不能在报名时间之外修改报名");
        }

        teamInfoMapper.deleteById(tid);
        return CommonResult.successResponse();
    }

    /**
     * 模糊查询获取用户名
     */
    @Override
    public CommonResult<List<String>> getChooseUserList(String usernameLike) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper();
        IPage<UserInfo> userInfoIPage = userInfoMapper.selectPage(new Page<>(1, 10),
                queryWrapper.like("username", usernameLike));
        return CommonResult.successResponse(
                userInfoIPage.getRecords().stream().map(UserInfo::getUsername).collect(Collectors.toList()));
    }
}
