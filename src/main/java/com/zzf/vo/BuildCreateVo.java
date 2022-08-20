package com.zzf.vo;

import lombok.Data;

/**
 * @author cc
 */
@Data
public class BuildCreateVo {
    private String owner;
    private String repo;
    private String branch = "main";
}
