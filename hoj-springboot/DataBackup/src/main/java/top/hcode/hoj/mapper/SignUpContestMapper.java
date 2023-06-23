package top.hcode.hoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.hcode.hoj.pojo.entity.signup.SignUpContest;

/**
 * @author 76004
 * @apiNote
 * @date 2023/4/21 20:34
 */
@Mapper
public interface SignUpContestMapper extends BaseMapper<SignUpContest> {
}
