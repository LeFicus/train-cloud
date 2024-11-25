package ous.train.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    /**
    * id
    */
    private Long id;

    /**
    * 手机号
    */
    private String mobile;
}