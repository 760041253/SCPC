package top.hcode.hoj.controller.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.hcode.hoj.common.exception.StatusFailException;
import top.hcode.hoj.common.exception.StatusForbiddenException;
import top.hcode.hoj.common.result.CommonResult;
import top.hcode.hoj.pojo.entity.signup.SignUpContest;
import top.hcode.hoj.pojo.dto.SignUpContestDTO;
import top.hcode.hoj.service.signup.SignUpContestService;

import java.util.List;

/**
 * @author 76004
 * @apiNote 报名收集表
 * @date 2023/4/21 20:52
 */
@RestController
@RequestMapping("/api/signup")
public class SignUpContestController {
    @Autowired
    private SignUpContestService signUpListService;

    @GetMapping("/get-sign-up-page")
    public CommonResult<List<SignUpContest>> getSIgnUpContestPage( Integer pageNum, Integer pageSize) throws StatusForbiddenException {
        return signUpListService.getContestSignList(pageNum,pageSize);
    }

    @DeleteMapping("/delete-sign-up-contest")
    public CommonResult<Void> deleteSignUpContest(Long sid) throws StatusForbiddenException, StatusFailException {
        return signUpListService.deleteContestSignUp(sid);
    }

    @PutMapping("/update-sign-up-contest")
    public CommonResult<Void> updateSignUpContest(@RequestBody SignUpContestDTO signUpContest) throws StatusForbiddenException, StatusFailException {
        return signUpListService.updateContestSignUp(signUpContest);
    }

    @PostMapping("/add-new-sign-up-contest")
    public CommonResult<Void> addNewSignUpContest(@RequestBody SignUpContestDTO signUpContestDTO) throws StatusForbiddenException, StatusFailException {
        return signUpListService.addNewContestSignUp(signUpContestDTO);
    }


}
