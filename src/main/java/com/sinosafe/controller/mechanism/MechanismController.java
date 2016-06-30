package com.sinosafe.controller.mechanism;

import com.sinosafe.common.ResponseResult;
import com.sinosafe.entity.Mechanism;
import com.sinosafe.service.MechanismService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wangjian on 2016/4/25.
 */
@RestController
@RequestMapping
public class MechanismController {
    private final static Logger logger = LoggerFactory.getLogger(MechanismController.class);
    @Autowired
    MechanismService mechanismServiceImpl;

    @RequestMapping(value = "/mechanism", method = RequestMethod.GET)
    public String findMechanism(){
        return  new ResponseResult(ResponseResult.SUCESS,"查询成功",mechanismServiceImpl.findMechanism()).toString();
    }
}
