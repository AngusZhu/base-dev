package com.sinosafe.service;

import com.sinosafe.common.SystemCode;
import com.sinosafe.vo.InterfaceLogVo;

/**
 * Created by zhuhuanmin on 2016/4/21.
 */
public interface LogService {

     public void saveExceptionLog(String method,Exception e);
     public void saveRequestLog(Long logId,SystemCode systemCode,String interfaceCode,String orderNo,String inParam);
     public void saveRequestLog(Long logId,String systemCode,String interfaceCode,String orderNo,String inParam);
     public void updateResponseLog(Long logId,SystemCode systemCode,String interfaceCode,String orderNo,String outParam);
     public void updateResponseLog(Long logId,String systemCode,String interfaceCode,String orderNo,String outParam);
     public void saveReqeustLog(InterfaceLogVo requestDto);
     public void updateResponseLog(InterfaceLogVo requestDto);


}
