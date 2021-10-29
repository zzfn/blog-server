package com.zzf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;


@TableName(value ="T_TRACE")
@Data
public class Trace {
    private String name;

    private String value;

    private String browser;

    private String browserVersion;

    private String ip;

    private String os;

    private String osVersion;

    private Date time;

    private String url;

    private String visitorId;

    private String referrer;

}