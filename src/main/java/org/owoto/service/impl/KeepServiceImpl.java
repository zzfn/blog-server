package org.owoto.service.impl;


import org.owoto.dao.KeepDao;
import org.owoto.entity.Keep;
import org.owoto.service.KeepService;
import org.owoto.vo.BillVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class KeepServiceImpl extends ServiceImpl<KeepDao, Keep> implements KeepService {
    @Autowired
    KeepDao keepDao;

    @Override
    public Object getBillTime(String time) {
        Map<String,Object> map=new HashMap<>();
        List<BillVO> billVOList=keepDao.getBillTime(time);
        billVOList.forEach(billVO -> {
            map.put(billVO.getKeepType(),billVO.getValue());
            map.put("time",billVO.getTime());
        });
        return map;
    }
}
