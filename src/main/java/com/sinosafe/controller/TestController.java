package com.sinosafe.controller;

import com.alibaba.fastjson.JSON;
import com.sinosafe.common.FileUtils;
import com.sinosafe.common.ResponseResult;
import com.sinosafe.controller.fuintegral.PointsController;
import com.sinosafe.entity.CommonParaService;
import com.sinosafe.service.PointsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * 测试类
 * Created by LKH on 2016/4/23.
 */
@RestController
@RequestMapping
public class TestController {
    private final static Logger log = LoggerFactory.getLogger(PointsController.class);

    @Autowired
    private PointsService pointsServiceImpl;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String addPoints(@RequestBody String orderDetails){
        log.info("-----------start test--------------------------");
        String result = pointsServiceImpl.addPoints(orderDetails);
        log.info("--------------end test-------------------------");
        return result;
    }

    @RequestMapping(value = "/newTest", method = RequestMethod.POST)
    public String testPoints(@RequestBody String orderDetails){
        log.info("-----------start newTest--------------------------");
        double userValidPoints = 9999;
        Map<String, Object> resultMsg = new HashMap<>();
        CommonParaService cp = new CommonParaService(orderDetails,resultMsg).parsePara();
        if(cp.isParaMiss()){
            log.info("--------------end newTest-------------------------");
            return JSON.toJSONString(resultMsg);
        }
        log.info("--------------end newTest-------------------------");
        return new ResponseResult(ResponseResult.SUCESS,"积分生成成功",userValidPoints).toString();
    }

    @RequestMapping(value = "/getPoint", method = RequestMethod.POST)
    public String testGetPoint(@RequestBody String pointsInfo){
        log.info("-----------start testGetPoint--------------------------");
        String result = pointsServiceImpl.getPointsInfo(pointsInfo);
        log.info("--------------end testGetPoint-------------------------");
        return result;
    }
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String testDetail(@RequestBody String pointsInfo){
        log.info("-----------start testGetPoint--------------------------");
        String result = pointsServiceImpl.queryPointsDetails(pointsInfo);
        log.info("--------------end testGetPoint-------------------------");
        return result;
    }
//    public static void main(String[] args) {
//        Calendar calendar = Calendar.getInstance();
////      积分有效期：下单成功的时间开始 + 下一年的最后一天
//        int year = calendar.get(Calendar.YEAR)+1;
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH,11);
//        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//        calendar.set(Calendar.HOUR,calendar.getActualMaximum(Calendar.HOUR));
//        calendar.set(Calendar.MINUTE,59);
//        calendar.set(Calendar.SECOND,59);
////        calendar.roll(Calendar.DAY_OF_MONTH, true);
//        Date date = calendar.getTime();
//        System.out.println("----积分有效的时间（下单成功开始） + 一年时间是："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
//    }

    @RequestMapping(value = "/md5", method = RequestMethod.GET)
    public String getMD5(){
        return FileUtils.getMD5Key();
    }
    @RequestMapping(value = "/aes", method = RequestMethod.GET)
    public String getAES(){
        return FileUtils.getAESKey();
    }
}
