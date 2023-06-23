package top.hcode.hoj.service.signup;

import top.hcode.hoj.common.exception.StatusFailException;
import top.hcode.hoj.common.exception.StatusForbiddenException;
import top.hcode.hoj.common.result.CommonResult;
import top.hcode.hoj.pojo.entity.signup.SignUpContest;
import top.hcode.hoj.pojo.dto.SignUpContestDTO;

import java.util.List;

/**
 * @author 76004
 * @apiNote
 * @date 2023/4/21 20:22
 */
public interface SignUpContestService {
    public CommonResult<List<SignUpContest>> getContestSignList(Integer pageSize,Integer pageNum) throws StatusForbiddenException;

    public CommonResult<Void>addNewContestSignUp(SignUpContestDTO signUpContestDTO) throws StatusForbiddenException, StatusFailException;

    public CommonResult<Void>deleteContestSignUp(Long id) throws StatusForbiddenException, StatusFailException;

    public CommonResult<Void>updateContestSignUp(SignUpContestDTO signUpContestDTO) throws StatusForbiddenException, StatusFailException;
}
