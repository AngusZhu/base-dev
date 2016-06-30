package com.sinosafe.controller.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zhuhuanmin on 2016/4/22.
 */
@RestController
@RequestMapping("/login")
public class SecurityController {


    @RequestMapping(value = "/params", method = RequestMethod.POST)
    public String queryPoints(@RequestBody String jumpparam) {
        JSONObject jsonObject = JSON.parseObject(jumpparam);

        return null;
    }
}
