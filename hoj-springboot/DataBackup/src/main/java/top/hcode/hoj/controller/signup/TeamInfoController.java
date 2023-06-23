package top.hcode.hoj.controller.signup;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.hcode.hoj.common.exception.StatusFailException;
import top.hcode.hoj.common.exception.StatusForbiddenException;
import top.hcode.hoj.common.result.CommonResult;
import top.hcode.hoj.pojo.dto.TeamInfoDTO;
import top.hcode.hoj.pojo.entity.signup.TeamInfo;
import top.hcode.hoj.pojo.vo.TeamInfoVO;
import top.hcode.hoj.service.signup.TeamInfoEntityService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author 76004
 * @apiNote 报名队伍信息
 * @date 2023/4/11 8:07
 */
@RestController
@RequestMapping("/api/team/")
public class TeamInfoController {
    @Autowired
    private TeamInfoEntityService teamInfoEntityService;

    @GetMapping("/select-waiting-review-team-info-page")
    @ApiOperation("查询ac后申请修改的队伍信息")
    public CommonResult<List<TeamInfoVO>> selectWaitingReviewTeamInfoPage(Long cid, Integer pageSize, Integer pageNum) throws StatusForbiddenException, StatusFailException {
        return teamInfoEntityService.selectWaitingReviewTeamInfoPage(cid,pageSize,pageNum);
    }

    @GetMapping("/get-team-info-by-username-from-contest-id")
    @ApiOperation("查询当前登录的用户在某场比赛中的队伍信息")
    public CommonResult<List<TeamInfoVO>> getTeamInfoByUsernameFromContestId(Long cid) throws StatusFailException {
        return teamInfoEntityService.getTeamInfoByUsernameFromContestId(cid);
    }


    @GetMapping("/get-team-info-page-by-contest-id")
    @ApiOperation("获得指定比赛的申请报名队伍名单,可以根据status查询，status为0则查询当前比赛所有报名队伍")
    public CommonResult<IPage<TeamInfo>> getTeamInfoPageByContestId(Long cid, Integer pageSize, Integer pageNum, Integer status) throws StatusForbiddenException, StatusFailException {
        return teamInfoEntityService.getTeamInfoPageByContestId(cid,pageSize,pageNum,status);
    }

    @GetMapping("/get-team-info-by-id")
    @ApiOperation("根据队伍id获取队伍信息")
    public CommonResult<TeamInfoVO> getTeamInfoById(Long teamId) throws StatusFailException{
        return teamInfoEntityService.getTeamInfoById(teamId);
    }

    @PutMapping("/update-sign-up-table")
    @ApiOperation("更新队伍信息")
    public CommonResult<Void> updateSignUpTable(@RequestBody TeamInfoDTO teamInfoDTO) throws StatusForbiddenException{
        return teamInfoEntityService.updateSignUpTable(teamInfoDTO);
    }

    @GetMapping("/get-ac-team-count")
    public CommonResult<Integer> getACCount(Long contestId){
        return teamInfoEntityService.getACTeamCount(contestId);
    }

    @PostMapping("/sign-up")
    public CommonResult<Void> signUp(@Valid @RequestBody TeamInfoDTO teamInfoDTO) throws StatusFailException, StatusForbiddenException{
        return teamInfoEntityService.signUp(teamInfoDTO);
    }

    @PutMapping("/check-apply")
    public CommonResult<Void> checkApply(Long tid, Boolean isOk, Long cid) throws StatusFailException, StatusForbiddenException {
        return teamInfoEntityService.checkApply(tid,isOk,cid);
    }

    @PutMapping("/withdraw-apply")
    public CommonResult<Void>withdrawApply(Long tid) throws StatusForbiddenException, StatusFailException {
        return teamInfoEntityService.withdrawApply(tid);
    }

    /***
     * 生成报名信息csv
     * @param cid 比赛id
     * @param choose 选择要生成的个人信息，按位与生成从低位到高位以此对应（
     *               1.队员姓名
     *               2.队员学号
     *               3.队员班级
     *               4.队员衣服尺码
     *               5.队员电话号码
     *               6.队员scpc用户名
     *               ）
     * @param response
     * @return
     * @throws IOException
     * @throws StatusFailException
     * @throws StatusForbiddenException
     */
    @GetMapping("/getSignUpCsv")
    public void generateCsvByContestId(Long cid,Long choose, HttpServletResponse response) throws IOException, StatusFailException, StatusForbiddenException{
        teamInfoEntityService.generateCsvByContestId(cid,choose,response);
    }

    @GetMapping("/test")
    public String test(){
        return "test success";
    }


}
