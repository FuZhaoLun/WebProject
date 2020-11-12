package com.wistron.springbootdemo.controller;

import com.wistron.springbootdemo.service.SavePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/springboot")
public class LoginController {
    @Autowired
    SavePictureService savePictureService;

    @RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET})
    public void savePicture(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Map<String,Object> map = new HashMap<>();
        try {
            httpServletRequest.setCharacterEncoding("UTF-8");
            httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter writer = httpServletResponse.getWriter();
            //调用业务
//            String result = savePictureService.savePicture(httpServletRequest);
            //获取参数
            String userName = httpServletRequest.getParameter("userName");
            String password = httpServletRequest.getParameter("password");
            //存储参数
            httpServletRequest.setAttribute("userName", userName);
            httpServletRequest.setAttribute("password", password);
            if("1".equals(userName)){
                writer.println("用户名正确！");
                if(userName!=""&&password!="") {
                    //请求转发
                    httpServletRequest.getRequestDispatcher("/springboot/result").forward(httpServletRequest,httpServletResponse);
                }
            }
            else {
                writer.println("用户名不正确！");
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}