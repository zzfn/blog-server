package com.zzf.service;

import com.zzf.entity.Trace;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzf.vo.Tags;

import java.util.List;

/**
 *
 */
public interface TraceService extends IService<Trace> {
    Object removeExpiredTrace();

    Object getPerformance();

    Object getUserCount();

    Object getAnyCount(Integer num);
}
