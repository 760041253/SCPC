package top.hcode.hoj.pojo.entity.signup;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 76004
 * @apiNote
 * @date 2023/4/7 16:33
 */

@TableName(value = "team_info")
@ApiModel(value = "组队信息")
@Data
public class TeamInfo {

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "每场比赛中的队伍id")
    private Integer teamId;

    @ApiModelProperty(value = "队伍登录名")
    private String teamUsername;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty(value = "成员名",notes = "用-隔开")
    private String teammatesName;

    @ApiModelProperty(value = "队伍类型",notes = "1:女队")
    private Integer teamType;

    @ApiModelProperty(value = "队伍名")
    private String teamName;

    @ApiModelProperty(value = "队伍英文名")
    private String englishTeamName;

    @ApiModelProperty(value = "成员尺码",notes = "-分割")
    private String size;

    @ApiModelProperty(value = "成员学校",notes = "-分割")
    private String school;

    @ApiModelProperty(value = "学号",notes = "-分割")
    private String studentId;

    @ApiModelProperty(value = "班级", notes = "-分割")
    private String studentClass;

    @ApiModelProperty(value = "性别,1(男),0(女)", notes = "-分割")
    private String gender;

    @ApiModelProperty(value = "电话号码", notes = "-分割")
    private String phoneNumber;

    @ApiModelProperty(value = "昵称", notes = "-分割")
    private String nickname;

    private Date createTime;

    @ApiModelProperty(value = "比赛Id")
    private Long signUpContestId;

    @ApiModelProperty(value = "成员用户名", notes = "-分割")
    private String usersName;

    @ApiModelProperty(value = "ac(200)、pending(500)、rej(400)、applyForUpdate(300)")
    private Integer status;

    @ApiModelProperty(value = "打星 1 or 0")
    private Integer star;
}
