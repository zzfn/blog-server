package org.owoto.service;


import org.owoto.entity.Keep;
import com.baomidou.mybatisplus.extension.service.IService;

public interface KeepService extends IService<Keep> {

    Object getBillTime(String string);
}
