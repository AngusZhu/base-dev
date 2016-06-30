package com.sinosafe.service.impl;

import com.sinosafe.dao.mapper.PointProductDetailMapper;
import com.sinosafe.entity.PointProductionDetail;
import com.sinosafe.service.PointsProductService;
import com.sinosafe.vo.UserPointDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wudaibo on 2016/4/23.
 * 用户收入积分服务类
 */
@Service
public class PointsProductServiceImpl implements PointsProductService {
    @Autowired
    PointProductDetailMapper pointProductMapper;
    @Override
    /**根据用户的userId查询*/
    public List<UserPointDetailsVo> findUserPointsProduct(Map<String, Object> map) {
        List<PointProductionDetail> allPointsByparams = pointProductMapper.findAllPointsByparams(map);
        List<UserPointDetailsVo> userPoints = new ArrayList<UserPointDetailsVo>();
        if(allPointsByparams.size()>0){
            for (PointProductionDetail p:allPointsByparams){
                UserPointDetailsVo detailsVo = new UserPointDetailsVo();
                detailsVo.setProcessTime(p.getCreatedDate());
                detailsVo.setSource(p.getPointSource());
                detailsVo.setPoint(p.getPoints());
                detailsVo.setStatus(p.getStatus());
                detailsVo.setPointType(p.getPointType());
                userPoints.add(detailsVo);
            }
        }
        return userPoints;
    }
}
