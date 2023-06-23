package top.hcode.hoj.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author 76004
 * @apiNote
 * @date 2023/4/12 17:12
 */
@Data
public class TeamInfoDTO {
    Long id;

    @Pattern(regexp = "^[^-\\n]+-[^-\\n]+-[^-\\n]+$", message = "属性格式必须为 **-**-**")
    String teammatesName;

    Integer teamType;

    @NotBlank(message = "队伍名不能为空")
    String teamName;

    @NotBlank(message = "队伍英文名不能为空")
    String englishTeamName;

    @Pattern(regexp = "^[^-\\n]+-[^-\\n]+-[^-\\n]+$", message = "属性格式必须为 **-**-**")
    String size;

    String school;

    @Pattern(regexp = "^[^-\\n]+-[^-\\n]+-[^-\\n]+$", message = "属性格式必须为 **-**-**")
    String studentId;

    @Pattern(regexp = "^[^-\\n]+-[^-\\n]+-[^-\\n]+$", message = "属性格式必须为 **-**-**")
    String studentClass;

    @Pattern(regexp = "^[^-\\n]+-[^-\\n]+-[^-\\n]+$", message = "属性格式必须为 **-**-**")
    String gender;

    @Pattern(regexp = "^[^-\\n]+-[^-\\n]+-[^-\\n]+$", message = "属性格式必须为 **-**-**")
    String phoneNumber;

    String nickname;

    @NotNull(message = "报名列表id不能为空")
    Long signUpContestId;

    @Pattern(regexp = "^[^-\\n]+-[^-\\n]+-[^-\\n]+$", message = "属性格式必须为 **-**-**")
    String usersName;

    @NotNull(message = "请选择是否打星")
    Integer star;

    Integer status;
}
