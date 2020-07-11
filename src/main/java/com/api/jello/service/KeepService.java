package com.api.jello.service;


import com.api.jello.entity.Keep;
import com.baomidou.mybatisplus.extension.service.IService;

public interface KeepService extends IService<Keep> {

    Object getBillTime(String string);
}
