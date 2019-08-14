package com.example.demo.controller;

import cn.com.gmmedicare.common.model.ResultMsg;
import cn.com.gmmedicare.common.model.ResultStatusCodeEnum;
import com.example.demo.service.ImageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @package: com.example.demo.controller
 * @author: 陈明磊<minglei.chen @ gm-medicare.com>
 * @date: 2018/2/24 17:15
 * @ModificarionHistory who     when   what
 * --------------|------------------|--------------
 */
@Controller
//@RequestMapping("/test/")
public class TestController {


    @Autowired
    private ImageDataService imageDataService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(String param){

        System.out.println(param);
        //int i=1/0;
      /*  ImageData imageData= imageDataService.selectByPrimaryKey(Long.parseLong("12"));

        System.out.println("------=====----"+imageData);*/
        return "hello";
    }

    @RequestMapping(value = "/test2",method = RequestMethod.GET)
    @ResponseBody
    public ResultMsg test2(@RequestParam("cname") String cname
    ){
        ResultMsg resultMsg = new ResultMsg(ResultStatusCodeEnum.OK.getErrcode(),
                ResultStatusCodeEnum.OK.getErrmsg(),cname);

        return resultMsg;
    }
    @RequestMapping(value = "/test3/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String test3(@PathVariable(value ="id") String id
    ){
        return id;
    }

}
