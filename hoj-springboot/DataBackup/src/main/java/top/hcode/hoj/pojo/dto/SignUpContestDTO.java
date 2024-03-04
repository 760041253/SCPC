package top.hcode.hoj.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author 76004
 * @apiNote
 * @date 2023/4/21 20:19
 */

@Data
public class SignUpContestDTO {
    Long id;

    Long contestId;

    String title;

    Date createTime;

    Date startTime;

    Date endTime;

    // ACM/OI
    String contestType;

    /***
     * 从低位到高位
     */
    Integer maxParticipants;
}
