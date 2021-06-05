package org.owoto.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zzfn
 * @date 2021-06-06 2:01 上午
 */
@Setter
@Getter
public class TalkBot extends BaseEntity{
    private String accessToken;
    private String secret;
    private String name;
}
