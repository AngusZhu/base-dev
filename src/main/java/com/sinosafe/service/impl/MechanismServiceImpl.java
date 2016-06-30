package com.sinosafe.service.impl;

import com.sinosafe.dao.mapper.MechanismMapper;
import com.sinosafe.entity.Mechanism;
import com.sinosafe.service.LogService;
import com.sinosafe.service.MechanismService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tb160316 on 2016/4/23.
 */
@Service
public class MechanismServiceImpl implements MechanismService {
    private final static Logger logger = LoggerFactory.getLogger(MechanismServiceImpl.class);
    @Autowired
    public LogService log;

    @Autowired
    public MechanismMapper mechanismMapper;

    @Override
    public List<Mechanism> findMechanism() {
        try {
            return mechanismMapper.findMechanism();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
