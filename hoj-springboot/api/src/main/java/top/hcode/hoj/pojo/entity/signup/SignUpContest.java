package top.hcode.hoj.pojo.entity.signup;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 76004
 * @apiNote
 * @date 2023/4/21 20:07
 */
@Data
@TableName("sign_up_contest")
public class SignUpContest {
    @TableId(value = "id",type = IdType.AUTO)
    Long id;

    Long contestId;

    String title;

    Date createTime;

    Date startTime;

    Date endTime;

    //ACM/OI
    String contestType;

    /***
     * 从低位到高位
     */
    Integer maxParticipants;
}
