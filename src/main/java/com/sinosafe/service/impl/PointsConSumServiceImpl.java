package com.sinosafe.service.impl;

import com.sinosafe.dao.mapper.PointConSumDetailsMapper;
import com.sinosafe.entity.PointsConsumDetails;
import com.sinosafe.service.PointsConSumService;
import com.sinosafe.vo.UserPointDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/4/23.
 */
@Service
public class PointsConSumServiceImpl implements PointsConSumService {
    @Autowired
    PointConSumDetailsMapper pointConsumDao;
    /**查询用户所有的积分*/
    @Override
    public List<UserPointDetailsVo> findUserAllPints(Map<String, Object> map) {

        return pointConsumDao.findUserAllPointsDetails(map);
    }

    @Override
    /**
     * 查询用户积分消费数据
     * @params map 查询条件
     */
    public List<UserPointDetailsVo> findUserConsumPoint(Map<String, Object> map)  {
        List<PointsConsumDetails> allConsumByParams = pointConsumDao.findConsumPointsByparams(map);
        /**处理查询结果*/
        List<UserPointDetailsVo> resultvo = new ArrayList<UserPointDetailsVo>();
        if(allConsumByParams.size()>0){
            for(PointsConsumDetails p:allConsumByParams){
                UserPointDetailsVo detailsVo = new UserPointDetailsVo();
                detailsVo.setPoint(p.getPoints());
                detailsVo.setPointType(p.getThirdPartId());
                detailsVo.setSource(p.getOrderNo());
                try {
                    detailsVo.setProcessTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p.getCreatedDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                detailsVo.setStatus(p.getType());
                resultvo.add(detailsVo);
            }
        }
        return resultvo;
    }
}
