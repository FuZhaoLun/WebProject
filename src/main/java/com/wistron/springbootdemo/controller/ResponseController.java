package com.wistron.springbootdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@RestController
@RequestMapping("/springboot")
public class ResponseController {
    @RequestMapping(value = "/result",method = RequestMethod.POST)
    public void uploadPicture(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        try {
            PrintWriter writer = httpServletResponse.getWriter();
            //获取参数
            String password = (String) httpServletRequest.getAttribute("password");
            //返回响应
            if("111".equals(password)){
                writer.println("Login Successful!");
            }
            else {
                writer.println("Login Failed!");
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
