package com.sinosafe.controller.menu;

import com.sinosafe.common.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with base.
 * User: anguszhu
 * Date: Dec,05 2015
 * Time: 6:42 PM
 * description:
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @RequestMapping(value="/list",method= RequestMethod.GET)
    public ResponseResult getAllMenu(){

        return null;
    }

}
