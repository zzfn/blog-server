package com.api.jello.dao;

import com.api.jello.entity.Article;
import com.api.jello.entity.Keep;
import com.api.jello.vo.BillVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author cc
 */
@Repository
public interface KeepDao extends BaseMapper<Keep> {
    BigDecimal getBalance();

    List<BillVO> getBillTime(@Param("time") String time);

    List<Keep> getOutlay();

    List<BillVO> listKeeps(@Param("time") String time,@Param("type") String type);
}
