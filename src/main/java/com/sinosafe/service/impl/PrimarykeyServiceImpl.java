package com.sinosafe.service.impl;

import com.sinosafe.dao.mapper.PrimarykeyMapper;
import com.sinosafe.service.PrimarykeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhuhuanmin on 2016/4/21.
 */
@Service
public class PrimarykeyServiceImpl implements PrimarykeyService{

    @Autowired
    public  PrimarykeyMapper primarykeyMapper;


    public Long getSeqence(){
       return  primarykeyMapper.getSeqence();
    }


}
