package top.hcode.hoj.service.signup.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.hcode.hoj.common.exception.StatusFailException;
import top.hcode.hoj.common.exception.StatusForbiddenException;
import top.hcode.hoj.common.result.CommonResult;
import top.hcode.hoj.mapper.ContestMapper;
import top.hcode.hoj.mapper.SignUpContestMapper;
import top.hcode.hoj.mapper.TeamInfoMapper;
import top.hcode.hoj.pojo.entity.contest.Contest;
import top.hcode.hoj.pojo.entity.signup.SignUpContest;
import top.hcode.hoj.pojo.entity.signup.TeamInfo;
import top.hcode.hoj.pojo.dto.SignUpContestDTO;
import top.hcode.hoj.service.signup.SignUpContestService;

import java.util.Date;
import java.util.List;

/**
 * @author 76004
 * @apiNote
 * @date 2023/4/21 20:33
 */
@Service
public class SignUpContestServiceImpl implements SignUpContestService {
    @Autowired
    private SignUpContestMapper signUpContestMapper;

    @Autowired
    private TeamInfoMapper teamInfoMapper;

    @Autowired
    private ContestMapper contestMapper;

    @Override
    public CommonResult<List<SignUpContest>> getContestSignList(Integer pageSize, Integer pageNum) throws StatusForbiddenException {
        List<SignUpContest> signUpContests = signUpContestMapper.selectPage(new Page<SignUpContest>(pageSize,pageNum),
                null).getRecords();
        return CommonResult.successResponse(signUpContests);
    }

    @Override
    public CommonResult<Void> addNewContestSignUp(SignUpContestDTO signUpContestDTO) throws StatusForbiddenException, StatusFailException {
        boolean isRoot = SecurityUtils.getSubject().hasRole("root");
        if(!isRoot){
            throw new StatusForbiddenException("您没有权限创建比赛报名!");
        }

        Long cid = signUpContestDTO.getContestId();
        if(cid != null){
            Contest contest = contestMapper.selectById(cid);
            if(contest == null){
                throw new StatusFailException("比赛不存在");
            }
            signUpContestDTO.setStartTime(contest.getStartTime());
            signUpContestDTO.setEndTime(contest.getEndTime());
        }
        signUpContestDTO.setCreateTime(new Date());
        SignUpContest signUpContest = new SignUpContest();
        BeanUtil.copyProperties(signUpContestDTO,signUpContest);
        signUpContestMapper.insert(signUpContest);
        return CommonResult.successResponse();
    }

    @Override
    public CommonResult<Void> deleteContestSignUp(Long id) throws StatusForbiddenException, StatusFailException {

        boolean isRoot = SecurityUtils.getSubject().hasRole("root");
        if(!isRoot){
            throw new StatusForbiddenException("您没有权限删除比赛报名!");
        }
        SignUpContest signUpContest = signUpContestMapper.selectById(id);
        if(signUpContest == null){
            throw new StatusFailException("比赛不存在");
        }
        signUpContestMapper.deleteById(id);
        //删除报名内容
        teamInfoMapper.delete(new QueryWrapper<TeamInfo>().eq("sign_up_contest_id",signUpContest.getId()));


        return CommonResult.successResponse();
    }

    @Override
    public CommonResult<Void> updateContestSignUp(SignUpContestDTO signUpContestDTO) throws StatusForbiddenException, StatusFailException {

        boolean isRoot = SecurityUtils.getSubject().hasRole("root");
        if(!isRoot){
            throw new StatusForbiddenException("您没有权限删除比赛报名!");
        }
        SignUpContest signUpContest = signUpContestMapper.selectById(signUpContestDTO.getId());
        if(signUpContest == null){
            throw new StatusFailException("该报名收集表不存在");
        }
        BeanUtil.copyProperties(signUpContestDTO, signUpContest);
        signUpContestMapper.updateById(signUpContest);
        return CommonResult.successResponse();
    }
}
