package com.sinosafe.controller.login;

import com.alibaba.fastjson.JSONObject;
import com.sinosafe.common.AesUtil;
import com.sinosafe.common.MD5;
import com.sinosafe.common.ResponseResult;
import com.sinosafe.common.SystemCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with base.
 * User: anguszhu
 * Date: Apr,09 2016
 * Time: 8:23 AM
 * description:
 */

@RestController
@RequestMapping("/login")
public class LoginController {
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Value("${fuiou.weixin.login}")
    private String  weixinJumpUrl;

    @Value("${fuiou.pc.login}")
    private String  pcJumpUrl;

    @RequestMapping(value = "/encrypt", method = {RequestMethod.POST,RequestMethod.GET})
    public String encrypt(@RequestBody String loginStr) throws Exception {
        logger.info("---enter LoginController.encrypt ,inParam: "+loginStr);
        try{
            String jumpUrl=pcJumpUrl;
            JSONObject loginJson = JSONObject.parseObject(loginStr);
            String source = loginJson.getString("source");
            String loginId = loginJson.getString("loginId");
            String userName = loginJson.getString("userName");
            if(StringUtils.isNotEmpty(loginId)
                    && StringUtils.isNotEmpty(userName)
                    && StringUtils.isNotEmpty(source)){
                String sinosafe = SystemCode.SINOSAFE.toString();
                String token = MD5.getToken(MD5.MD5_KEY,loginId,userName, sinosafe) ;
                if(SystemCode.WEIXIN.toString().equals(source)){
                    jumpUrl=weixinJumpUrl;
                }
                jumpUrl+="token="+token+ "&loginId="+AesUtil.defaultUrlEncrypt(loginId)+"&userNm="+AesUtil.defaultUrlEncrypt(userName)+"&firmNo="+ sinosafe;
            }else{
               return new ResponseResult("990300","缺少参数",null).toString();
            }
            Map<String,String>  jumpMap=new ConcurrentHashMap<>();
            jumpMap.put("jumpUrl",jumpUrl);
            String response = new ResponseResult(ResponseResult.SUCESS, "加密成功", jumpMap).toString();
            logger.info("---left LoginController.encrypt ,outParam: "+response);
            return response;
        }catch(Exception e){
            String response = new ResponseResult("990301", "解析失败", null).toString();
            logger.info("---left LoginController.encrypt ,outParam: "+response);
            return response;
        }
    }



}
