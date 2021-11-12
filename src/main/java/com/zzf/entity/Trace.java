package com.zzf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;



/**
 * @author cc
 */
@Data
public class Trace {
    /**
     * 主键
     */
    private String id;
    /**
     * 埋点类型
     */
    private String name;
    /**
     * 值
     */
    private Double value;
    /**
     * 浏览器类型
     */
    private String browser;
    /**
     * 浏览器版本
     */
    private String browserVersion;
    /**
     * ip地址
     */
    private String ip;

    private String os;

    private String osVersion;

    private Date time;

    private String url;

    private String visitorId;

    private String referrer;

}