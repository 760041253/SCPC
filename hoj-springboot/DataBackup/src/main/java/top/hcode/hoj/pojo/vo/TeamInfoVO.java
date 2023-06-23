package top.hcode.hoj.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 76004
 * @apiNote
 * @date 2023/4/12 17:49
 */
@Data
public class TeamInfoVO {
    Long id;

    @ApiModelProperty(value = "每场比赛中的队伍id")
    Integer teamId;

    @ApiModelProperty(value = "队伍登录名")
    String teamUsername;

    @ApiModelProperty(value = "成员名",notes = "用-隔开")
    String teammatesName;

    @ApiModelProperty(value = "队伍类型",notes = "1:女队")
    Integer teamType;

    @ApiModelProperty(value = "队伍名")
    String teamName;

    @ApiModelProperty(value = "队伍英文名")
    String englishTeamName;

    @ApiModelProperty(value = "成员尺码",notes = "-分割")
    String size;

    @ApiModelProperty(value = "成员学校",notes = "-分割")
    String school;

    @ApiModelProperty(value = "学号",notes = "-分割")
    String studentId;

    @ApiModelProperty(value = "班级", notes = "-分割")
    String studentClass;

    @ApiModelProperty(value = "性别,1(男),0(女)", notes = "-分割")
    String gender;

    @ApiModelProperty(value = "电话号码", notes = "-分割")
    String phoneNumber;

    @ApiModelProperty(value = "昵称", notes = "-分割")
    String nickname;

    Date creatTime;

    @ApiModelProperty(value = "比赛Id")
    Long contestId;

    @ApiModelProperty(value = "成员用户名", notes = "-分割")
    String usersName;

    @ApiModelProperty(value = "ac(200)、pending(500)、rej(400)、applyForUpdate(300)")
    Integer status;

    @ApiModelProperty(value = "打星 1 or 0")
    Integer star;

    //是否是队长，是（1），否（0）
    Integer captain;
}
