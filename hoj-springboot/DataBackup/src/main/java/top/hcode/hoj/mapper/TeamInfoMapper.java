package top.hcode.hoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.hcode.hoj.pojo.entity.signup.TeamInfo;

/**
 * @author 76004
 * @apiNote
 * @date 2023/4/8 13:25
 */
@Mapper
public interface TeamInfoMapper extends BaseMapper<TeamInfo> {
}
