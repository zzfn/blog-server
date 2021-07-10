package com.zzf.vo;

import lombok.Data;

/**
 * @author cc
 * @date 2020/8/10 0:25
 */
@Data
public class TokenVO {
    private String token;
    private String refreshToken;
    private Long expired;
}
