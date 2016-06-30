package com.sinosafe.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosafe.common.PointsType;
import com.sinosafe.common.ResponseResult;
import com.sinosafe.common.ReturnCode;
import com.sinosafe.common.SystemCode;
import com.sinosafe.dao.mapper.PointProductDetailMapper;
import com.sinosafe.dao.mapper.PointSettingMapper;
import com.sinosafe.dao.mapper.UserPointsMapper;
import com.sinosafe.entity.CommonParaService;
import com.sinosafe.entity.PointProductionDetail;
import com.sinosafe.entity.UserPoints;
import com.sinosafe.excepiton.BusinessException;
import com.sinosafe.service.*;
import com.sinosafe.vo.PointVo;
import com.sinosafe.vo.PointsConsumptionVo;
import com.sinosafe.vo.PointsSettingVO;
import com.sinosafe.vo.UserPointDetailsVo;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created with base.
 * User: anguszhu
 * Date: Apr,06 2016
 * Time: 8:19 PM
 * description:
 */
@Service
public class PointsServiceImpl implements PointsService {
    private final static Logger logger = LoggerFactory.getLogger(PointsServiceImpl.class);
    @Autowired
    PointProductDetailMapper pointDetail;
    @Autowired
    LogService logService;
    @Autowired
    PointsProductService productService;
    @Autowired
    PointsConSumService conSumService;
    @Autowired
    PrimarykeyService primarykeyService;
    @Autowired
    PointSettingMapper pointSettingMapper;
    @Autowired
    UserPointsMapper userPointsMapper;

    /**
     * 查询用户积分明细信息
     *
     * @return json参数
     * @throws BusinessException 按类型查看积分
     */
    @Override
    public String queryPointsDetails(String json) throws BusinessException {

        /*接口返回消息*/
        Long seqNo = primarykeyService.getSeqence();
        logger.info("查询积分详情请求参数:" + json);
        JSONObject jsonObject = JSON.parseObject(json);
        String userId = jsonObject.getString("userId");
        String type = jsonObject.getString("type");
        logService.saveRequestLog(seqNo, type, "queryPointsDetails", userId, json);
        try {
            if (StringUtils.isEmpty(json)) {
                new ResponseResult("990100", "传入的参数为空", null).toString();
            }
            if (StringUtils.isEmpty(userId)) {
                return new ResponseResult("990101", "用户标识不能为空", null).toString();
            }
            if (StringUtils.isEmpty(type)) {
                return new ResponseResult("990102", "参数type必填", null).toString();
            }
            List<UserPointDetailsVo> result = null;
            Map<String, Object> info = new HashMap<>();
            Map<String,Object> responseMap=new ConcurrentHashMap<>();
            info.put("userId", userId);
            /**查用户消费的积分详细*/
            if ("CONSUME".equals(type)) {
                result = conSumService.findUserConsumPoint(info);
                /**查用户获得的积分*/
            } else if ("PRODUCE".equals(type)) {
                result = productService.findUserPointsProduct(info);
                /**查询所有的积分详细*/
            } else {
                result = conSumService.findUserAllPints(info);
                int totalPoints =0;
                for(UserPointDetailsVo vo:result){
                    totalPoints=vo.getTotalPoints();
                    break;
                }
                responseMap.put("totalPoints",totalPoints);
                responseMap.put("records",result );
            }
            String resposne = new ResponseResult(ResponseResult.SUCESS, "查询成功", responseMap).toString();
            logService.updateResponseLog(seqNo, type, "queryPointsDetails", userId, resposne);
            logger.info("查询积分详情接口返回参数：" + resposne);
            return resposne;
        } catch (Exception e) {
            logger.error("查询信息失败："+e.getMessage());
            logService.saveExceptionLog("queryPointsDetails", e);
            e.printStackTrace();
            return new ResponseResult("990000", "查询失败", null).toString();
        }
    }



    @Override
    public Long findPointsByUserId(String userId) throws BusinessException {
        return pointSettingMapper.findPointByUserId(userId);
    }

    /**
     * 扣除积分接口
     *
     * @param consumeVO
     * @return
     */
    @Transactional
    @Override
    public boolean consumePoints(PointsConsumptionVo consumeVO) {

        logger.info(consumeVO!=null?consumeVO.toString():"consumeVo is null");
        Long userPoints = pointSettingMapper.findPointByUserId(consumeVO.getLoginId());
        Double cousumePoints = Double.valueOf(consumeVO.getPoints());
        if (userPoints == null ||(userPoints != null && cousumePoints - userPoints > 0)) {
            consumeVO.errorCode = "1005";
            consumeVO.setErrorMsg("积分不足,请充值!");
            return false;
        }
        Long seqence = primarykeyService.getSeqence();

        consumeVO.setLeftPoints(String.valueOf(userPoints - cousumePoints));
        // fuiouInterfaceMapper.saveConsumptionRecord(consumeVO);
        //1.插入明细表
        consumeVO.setThirdPartyId(SystemCode.FUIOU.toString());
        consumeVO.setId(seqence);
        userPointsMapper.saveUserConsumePoints(consumeVO);
        //2.查询用户积分对象
        String loginId = consumeVO.getLoginId();
        UserPoints userPointInfo = userPointsMapper.findUserPoints(loginId); // 查询用户积分
        //3.移到用户积分历史表,插入用户积分
        Long date = new Date().getTime();
        logger.info("------开始移到用户积分历史表,插入用户积分，处理编号：" + date);
        updateExistUserPoints(SystemCode.FUIOU.toString(), loginId, -cousumePoints.intValue(), userPointInfo, seqence, date);
        logger.info("------结束移到用户积分历史表,插入用户积分，处理编号：" + date);
        consumeVO.setOrderNoDest(String.valueOf(seqence));
        //4. 根据积分的生成时间,扣减积分
        //4.1 查找用户有效的所有生成记录,按时间倒序排列
       List<PointProductionDetail> records = userPointsMapper.getUserAllProductionDetail(loginId);
       List<PointProductionDetail> newRecords = new ArrayList<>();
        double leftPoints=cousumePoints;
        for(PointProductionDetail detail:records){
            detail.setCreatedBy(consumeVO.getThirdPartyId());
            detail.setUpdateBy(consumeVO.getThirdPartyId());
            detail.setCmpMasterId(seqence);
            newRecords.add(detail);
            if(leftPoints - detail.getPoints()>=0){
                leftPoints-=detail.getPoints();
                detail.setPoints(-detail.getPoints());
                continue;
            }
            detail.setPoints(-Double.valueOf(leftPoints).intValue());
            break;
       }
        pointDetail.saveConsumedDetailsToProdction(newRecords);
        return true;
    }

    /**
     * 提供对外接口方法
     * 增加用户积分
     * 积分类型处理pointType，注册：REGIST，签到：LOGIN，下单：ORDER，升级APP：DOWNLOAD_APP...
     * 积分来源系统pointSource：互联平台：B2C， 微信：WEIXIN，移动展业：YDZY
     * @param pointInfo
     * @return
     */
    @Transactional
    @Override
    public String addPoints(String pointInfo) {
        Long date = new Date().getTime();
        logger.info("调用积分生成接口时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "|| 处理编号：" + date + " || 参数：" + pointInfo);
        Map<String, Object> resultMsg = new HashMap<>();
        Long squen = primarykeyService.getSeqence();
        try {
            CommonParaService cp = new CommonParaService(pointInfo, resultMsg).parsePara();
            if (cp.isParaMiss()) {
                return JSON.toJSONString(cp.getResultMsg());
            }
            String orderNo = cp.getOrderNo();
            String pointType = cp.getPointType();
            JSONObject pointObject = cp.getPointObject();
            String comCode = cp.getComCode();
            String pointSource = cp.getPointSource();
            String userId = cp.getUserId();
            String filterId = "";
            if(StringUtils.isEmpty(orderNo)){
                filterId = cp.getUserId(); //目的：不为订单时，防止插入日志报错
            }
            logService.saveRequestLog(squen, pointSource, "addPoints", StringUtils.isEmpty(orderNo) ? filterId : orderNo, pointInfo);
            if (StringUtil.isNotBlank(orderNo) && PointsType.ORDER.toString().equalsIgnoreCase(pointType)) {  //积分类型：订单
                Object jsonArr = pointObject.get("orderDetails");
                logger.info("------进入生成订单积分类型，处理编号：" + date);
                if (jsonArr == null || comCode == null) {
                    return new ResponseResult( ResponseResult.PARA_MISS,"订单参数缺失",null).toString();
                }

                List<PointProductionDetail> list = parseJsonArray(jsonArr);
                //2016年5月4日Modify，增加 保单号，产品代码，状态 重复判断，明细表有该保单号,产品代码则不生成积分。
                List<PointProductionDetail> detail = pointDetail.findProductCodeByOrderNo(orderNo);
//                logger.info("----------查询明细表保单号是否有重复PointProductionDetail："+detail.toString());
                Iterator<PointProductionDetail> iter = list.iterator();
                while (iter.hasNext()) {
                    PointProductionDetail pro = iter.next();
                    for (PointProductionDetail p : detail) { //取出明细表是否已有该orderNo and productCode
                        // 取出传入要生成积分的参数
                        if (StringUtils.equalsIgnoreCase(orderNo, p.getOrderNo()) && StringUtils.equalsIgnoreCase(pro.getProductCode(), p.getProductCode())) { // 对比
                            iter.remove(); // 去掉已生成积分的productCo
                            break;
                        }
                    }
                }
                if(list.size() == 0){
                    return  new ResponseResult( ResponseResult.PRODUCT_POINT_PAPA_REPEAT,"生成积分参数重复",null).toString();
                }
                logger.info("----------查询明细表保单号是否有重复处理后的数据list："+list.toString());
                String subMechanism=comCode;
                comCode = comCode.substring(0, 2);
                List<PointsSettingVO> pVo = productPoints(comCode, pointType, pointSource, list, date); //调用积分生成方法
                if (pVo == null || pVo.size() == 0) {
                    return new ResponseResult(ResponseResult.PRODUCT_POINT_RULE_ERROR,"无此积分配置规则",null).toString();
                }

                List<PointProductionDetail> prop = new ArrayList<>();
                Integer orderPoints = 0; // 当前订单的总积分
                for (PointsSettingVO pv : pVo) {
                    PointProductionDetail pd = new PointProductionDetail();
                    pd.setUserId(userId);
                    pd.setPointType(StringUtils.upperCase(pointType));
                    pd.setPointSource(StringUtils.upperCase(pointSource));
                    pd.setPoints(pv.getPoints());
                    orderPoints += pv.getPoints();
                    pd.setOrderNo(orderNo);
                    pd.setMechanism(comCode);
                    pd.setSubMechanism(subMechanism);
                    for (PointProductionDetail ppd : list) {
                        if (StringUtils.equalsIgnoreCase(ppd.getProductCode(), pv.getProductCode())) {
                            pd.setProposalNo(ppd.getProposalNo());
                            pd.setProductCode(ppd.getProductCode().toUpperCase());
                            pd.setFee(ppd.getFee());
                            break;
                        }
                    }
                    pd.setDeadLine(getDeadline()); //积分有效的时间（下单成功开始） + 一年时间
                    pd.setCreatedBy(pointType);
                    pd.setUpdateBy(pointType);
                    prop.add(pd);
                }
                logger.info("-----------开始生成积分明细，处理编号：" + date);
                int r = pointDetail.savePointsProductDetail(prop); //插入积分明细表
                logger.info("-----------结束生成积分明细，处理编号：" + date);

//                List<UserPoints> newUserPointInfo = new ArrayList<UserPoints>();
                UserPoints userPointInfo = userPointsMapper.findUserPoints(userId); // 查询用户积分
                Long newId = primarykeyService.getSeqence();
                if (userPointInfo != null) {
                    // 已有该用户的积分，需增加积分再删除原来的积分
                    updateExistUserPoints(pointType, userId, orderPoints, userPointInfo, newId, date);
                } else {
                    logger.info("-----------新增用户积分，处理编号：" + date);
                    UserPoints up = new UserPoints();
                    up.setId(newId);
                    up.setUserId(userId);
                    up.setTotalPoints(Double.valueOf(orderPoints)); //总积分
                    up.setSumPoints(Double.valueOf(orderPoints)); // 累计积分
                    up.setStatus(1);
                    up.setCreatedBy(pointType);
                    up.setUpdatedBy(pointType);
                    logger.info("-----------开始保存新用户积分，处理编号：" + date);
                    userPointsMapper.saveUserPoint(up);
                    logger.info("-----------结束保存新用户积分，处理编号：" + date);
                }
                logger.info("--------积分生成成功，处理编号：" + date);

                String response = new ResponseResult(ResponseResult.SUCESS, "积分生成成功", parseListToMap(pVo)).toString();
                logService.updateResponseLog(squen, pointSource, "addPoints", orderNo, response);
                return response;
            } else {
                List<PointsSettingVO> pVo = productPoints(pointType, pointSource); //调用积分生成方法
                if (pVo == null || pVo.size() == 0) {
                    return new ResponseResult( ResponseResult.PRODUCT_POINT_RULE_ERROR,"无此积分配置规则",null).toString();
                }
                List<PointProductionDetail> prop = new ArrayList<>();
                Integer orderPoints = 0; // 当前订单的总积分
                for (PointsSettingVO pv : pVo) {
                    PointProductionDetail pd = new PointProductionDetail();
                    pd.setUserId(userId);
                    pd.setPointType(StringUtils.upperCase(pointType));
                    pd.setPointSource(StringUtils.upperCase(pointSource));
                    pd.setPoints(pv.getPoints());
                    orderPoints += pv.getPoints();
                    pd.setOrderNo(orderNo);
                    pd.setSubMechanism("");
                    pd.setMechanism("");
                    pd.setProposalNo("");
                    pd.setProductCode("");
                    pd.setFee(0.0);
                    pd.setDeadLine(getDeadline()); //积分有效的时间（下单成功开始） + 一年时间
                    pd.setCreatedBy(pointType);
                    pd.setUpdateBy(pointType);
                    prop.add(pd);
                }

                logger.info("-----------开始生成积分明细，处理编号：" + date);
                int r = pointDetail.savePointsProductDetail(prop); //插入积分明细表
                logger.info("-----------结束生成积分明细，处理编号：" + date);
                UserPoints userPointInfo = userPointsMapper.findUserPoints(userId); // 查询用户积分
                Long newId = primarykeyService.getSeqence();
                if (userPointInfo != null) {
                    // 已有该用户的积分，需增加积分再删除原来的积分
                    updateExistUserPoints(pointType, userId, orderPoints, userPointInfo, newId, date);
                } else {
                    logger.info("-----------新增用户积分，处理编号：" + date);
                    UserPoints up = new UserPoints();
                    up.setId(newId);
                    up.setUserId(userId);
                    up.setTotalPoints(Double.valueOf(orderPoints)); //总积分
                    up.setSumPoints(Double.valueOf(orderPoints)); // 累计积分
                    up.setStatus(1);
                    up.setCreatedBy(pointType);
                    up.setUpdatedBy(pointType);
                    logger.info("-----------开始保存新用户积分，处理编号：" + date);
                    userPointsMapper.saveUserPoint(up);
                    logger.info("-----------结束保存新用户积分，处理编号：" + date);
                }
                logger.info("--------积分生成成功，处理编号：" + date);
                String res = new ResponseResult(ResponseResult.SUCESS, "积分生成成功", parseListToMap(pVo)).toString();
                logService.updateResponseLog(squen, pointSource, "addPoints", StringUtils.isEmpty(orderNo) ? filterId : orderNo, res);
                return res;
            }
        } catch (Exception e) {
            logger.error("getPointsInfo", e.getMessage());
//            logService.saveExceptionLog("getPointsInfo",e);
            e.printStackTrace();
            logger.info("--------积分生成系统异常，处理编号：" + date);
            return new ResponseResult( ResponseResult.PRODUCT_POINT_ERROR,"积分生成系统异常",null).toString();
        }
    }

    /**
     * 重载匹配规则
     *
     * @param pointType
     * @param pointSource
     * @return
     */
    private List<PointsSettingVO> productPoints(String pointType, String pointSource) {
        List<PointsSettingVO> point = new ArrayList<>();
        PointsSettingVO ps = new PointsSettingVO();
        ps.setPointType(StringUtils.upperCase(pointType));
        ps.setPointSource(StringUtils.upperCase(pointSource));
        ps.setStatus(1); // 查询积分规则，该区域的配置规则必须有效
        point.add(ps);
        logger.info("----查询规则设置前积分：" + point);
        List<PointsSettingVO> pVo = pointSettingMapper.findProductPointSetting(point); // 查询数据库生成的积分信息（积分生成在SQL表达式里）
        logger.info("返回积分Vo：" + pVo);
        return pVo;
    }

    /**
     * 预计用户可获得的积分
     * 积分类型处理，注册：REGIST，签到：LOGIN，下单：ORDER，升级APP：DOWNLOAD_APP...
     *
     * @param pointInfo
     * @return
     */
    @Override
    public String getPointsInfo(String pointInfo) {
        Long date = new Date().getTime();
        logger.info("调用预计用户可获得积分接口时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "|| 处理编号：" + date + " || 参数：" + pointInfo);
        Map<String, Object> resultMsg = new HashMap<>();
        Long squen = primarykeyService.getSeqence();
        try {
            CommonParaService cp = new CommonParaService(pointInfo, resultMsg).parsePara();
            if (cp.isParaMiss()) {
                return JSON.toJSONString(cp.getResultMsg());
            }
            String orderNo = cp.getOrderNo();
            String pointType = cp.getPointType();
            JSONObject pointObject = cp.getPointObject();
            String comCode = cp.getComCode();
            String pointSource = cp.getPointSource();
            String filterId = "";
            if(StringUtils.isEmpty(orderNo)){
               filterId = cp.getUserId(); //目的：不为订单时，防止插入日志报错
            }
            logService.saveRequestLog(squen, pointSource, "getPointsInfo", StringUtils.isEmpty(orderNo) ? filterId : orderNo , pointInfo);
            if (StringUtil.isNotBlank(orderNo) && PointsType.ORDER.toString().equalsIgnoreCase(pointType)) {  //积分类型：订单
                Object jsonArr = pointObject.get("orderDetails");
                logger.info("------进入生成订单积分类型，处理编号：" + date);
                if (jsonArr == null || comCode == null) {
                    return new ResponseResult( ResponseResult.PARA_MISS,"订单参数缺失",null).toString();
                }
                comCode = comCode.substring(0, 2);
                List<PointProductionDetail> list = parseJsonArray(jsonArr);  //产品代码，可能有多个
                List<PointsSettingVO> pVo = productPoints(comCode, pointType, pointSource, list, date); //调用积分生成方法
                if (pVo == null || pVo.size() == 0) {
                    return new ResponseResult( ResponseResult.PRODUCT_POINT_RULE_ERROR,"无此积分配置规则",null).toString();
                }
                logger.info("--------预计积分成功生成，处理编号：" + date);
                String res = new ResponseResult(ResponseResult.SUCESS, "预计积分成功生成", parseListToMap(pVo)).toString();
                logService.updateResponseLog(squen, pointSource, "getPointsInfo", orderNo, res);
                return res;
            }
            List<PointsSettingVO> pVo = productPoints(pointType, pointSource); //调用积分生成方法
            if (pVo == null || pVo.size() == 0) {
                return new ResponseResult(ResponseResult.PRODUCT_POINT_RULE_ERROR, "无此积分配置规则", parseListToMap(pVo)).toString();
            }
            logger.info("--------预计积分成功生成，处理编号：" + date);
            String res = new ResponseResult(ResponseResult.SUCESS, "预计积分成功生成", parseListToMap(pVo)).toString();
            logService.updateResponseLog(squen, pointSource, "getPointsInfo", StringUtils.isEmpty(orderNo) ? filterId : orderNo, res);
            return res;

        } catch (Exception e) {
            logger.error("getPointsInfo", e.getMessage());
//            logService.saveExceptionLog("getPointsInfo",e);
            e.printStackTrace();
            logger.info("--------积分生成系统异常，处理编号：" + date);
            return new ResponseResult(ResponseResult.PRODUCT_POINT_ERROR, "积分生成系统异常",null).toString();
        }
    }

    private double updateExistUserPoints(String pointType, String userId, Integer orderPoints, UserPoints userPointInfo, Long newId, Long date) {
        double validPoints = 0; // 用户有效积分
        userPointInfo.setMasterId(newId);
        userPointInfo.setUpdatedBy(pointType);
        userPointInfo.setUpdatedDate(new Date());
//                  userPointInfo.add(up);
        logger.info("move to history record is:" + userPointInfo);
        userPointsMapper.saveUserHistoryPoint(userPointInfo); // 保存当前用户积分信息入积分历史库
        logger.info("-----------结束保存用户积分历史表，处理编号：" + date);
        logger.info("------------用户原有积分： " + userPointInfo.getTotalPoints() + " ，新增积分： " + orderPoints);
        int row = userPointsMapper.deleteUserPonitById(userId); //删除当前用户积分信息
        if (row > 0) {
            userPointInfo.setId(newId);
            userPointInfo.setUserId(userPointInfo.getUserId());
            userPointInfo.setTotalPoints(userPointInfo.getTotalPoints() + orderPoints); //总积分
            validPoints = userPointInfo.getTotalPoints();
            if (orderPoints > 0) {
                userPointInfo.setSumPoints(userPointInfo.getSumPoints() + orderPoints); // 累计积分
            }
            userPointInfo.setStatus(1);
            userPointInfo.setCreatedBy(pointType);
            userPointInfo.setUpdatedBy(pointType);
            logger.info("-----------开始保存已有用户最新积分，处理编号：" + date);
            logger.info("new insert to user_potins record is:" + userPointInfo);
            userPointsMapper.saveUserPoint(userPointInfo);
            logger.info("-----------结束保存已有用户最新积分，处理编号：" + date);
        }
        logger.info("------------用户最新积分： " + validPoints);
        return validPoints;
    }

    private List<PointsSettingVO> productPoints(String comCode, String pointType, String pointSource, List<PointProductionDetail> list, long date) {

//        List<PointProductionDetail> list = parseJsonArray(jsonArr);  //产品代码，可能有多个
        List<PointsSettingVO> point = new ArrayList<>();
        for (PointProductionDetail p : list) {   //寻找匹配规则 pointType productCode pointSource mechanism status
            PointsSettingVO ps = new PointsSettingVO();
            ps.setPointType(StringUtils.upperCase(pointType));
            ps.setProductCode(StringUtils.upperCase(p.getProductCode()));
            ps.setPointSource(StringUtils.upperCase(pointSource));
            ps.setMechanism(comCode);
            ps.setStatus(1); // 查询积分规则，该区域的配置规则必须有效
            ps.setFee(p.getFee());
            point.add(ps);
        }
        logger.info("----查询积分规则前，处理编号：" + date);
        List<PointsSettingVO> pVo = pointSettingMapper.findProductPointSetting(point); // 查询数据库生成的积分信息（积分生成在SQL表达式里）
        logger.info("处理编号：" + date + ",返回积分Vo：" + pVo);

        return pVo;
    }

    /**
     * 将返回的积分信息list转为map出参
     *
     * @param v
     * @return
     */
    private Map<String, Object> parseListToMap(List<PointsSettingVO> v) {
        Map<String, Object> map = new HashMap<>();
        List<Object> listPara = new ArrayList<>();
        Integer orderPoints = 0;
        for (PointsSettingVO psVO : v) {
//            logger.info("解析parseListToMap：" + psVO.toString());
            orderPoints += psVO.getPoints();
            Map<String, Object> subMap = new HashMap<>();
            subMap.put("productCode", psVO.getProductCode());
            subMap.put("points", psVO.getPoints());
            listPara.add(subMap);
        }
        map.put("orderPoints", orderPoints);
        map.put("details", listPara);
        logger.error(JSON.toJSONString(map));
        return map;
    }

    private List<PointProductionDetail> parseJsonArray(Object jsonArr) {
        List<PointProductionDetail> list = JSON.parseArray(jsonArr + "", PointProductionDetail.class);  //产品代码，可能有多个
        return list;
    }

    private Date getDeadline() {
        Calendar calendar = Calendar.getInstance();
//      积分有效期：下单成功的时间开始 + 下一年的最后一天
        int year = calendar.get(Calendar.YEAR) + 1;
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND,999);
        Date date = calendar.getTime();
        System.out.println("----积分有效期是：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        return date;
    }

}
