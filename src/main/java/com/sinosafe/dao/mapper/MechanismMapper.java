package com.sinosafe.dao.mapper;

import com.sinosafe.entity.Mechanism;

import java.util.List;

/**
 * Created by wangjian on 2016/4/23.
 */
public interface MechanismMapper {
    /**
     * 查询机构
     * @return
     */
    public List<Mechanism> findMechanism();
}
