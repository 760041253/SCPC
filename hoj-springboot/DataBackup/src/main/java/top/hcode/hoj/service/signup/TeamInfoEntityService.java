package top.hcode.hoj.service.signup;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.hcode.hoj.common.exception.StatusFailException;
import top.hcode.hoj.common.exception.StatusForbiddenException;
import top.hcode.hoj.common.result.CommonResult;
import top.hcode.hoj.pojo.dto.TeamInfoDTO;
import top.hcode.hoj.pojo.entity.signup.TeamInfo;
import top.hcode.hoj.pojo.vo.TeamInfoVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 76004
 * @apiNote
 * @date 2023/4/10 14:11
 */
public interface TeamInfoEntityService {

    public CommonResult<List<TeamInfoVO>> selectWaitingReviewTeamInfoPage(Long cid, Integer pageSize, Integer pageNum)
            throws StatusForbiddenException, StatusFailException;

    public CommonResult<List<TeamInfoVO>> getTeamInfoByUsernameFromContestId(Long cid) throws StatusFailException;

    public CommonResult<IPage<TeamInfo>> getTeamInfoPageByContestId(Long cid, Integer pageSize, Integer pageNum,
            Integer status) throws StatusFailException, StatusForbiddenException;

    public CommonResult<TeamInfoVO> getTeamInfoById(Long teamId) throws StatusFailException;

    public CommonResult<Void> updateSignUpTable(TeamInfoDTO teamInfoDTO) throws StatusForbiddenException;

    public CommonResult<Integer> getACTeamCount(Long contestId);

    public CommonResult<Void> signUp(TeamInfoDTO teamInfoDto) throws StatusFailException, StatusForbiddenException;

    public void generateCsvByContestId(Long cid, Long choose, HttpServletResponse response)
            throws IOException, StatusFailException, StatusForbiddenException;

    public CommonResult<Void> checkApply(Long tid, Boolean isOk, Long cid)
            throws StatusFailException, StatusForbiddenException;

    public CommonResult<Void> withdrawApply(Long tid) throws StatusForbiddenException, StatusFailException;

    public CommonResult<List<String>> getChooseUserList(String usernameLike);
}
