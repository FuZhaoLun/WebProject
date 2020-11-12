package com.wistron.springbootdemo.controller;

import com.wistron.springbootdemo.util.TimeConversionUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

@RestController
@RequestMapping("/springboot")
public class TestController {
    @RequestMapping("/downloadPicture")
    public String download(@RequestParam("url") String url) throws Exception{
        HttpClient httpclient = new HttpClient();
        GetMethod method = new GetMethod(url);
        httpclient.executeMethod(method);
        byte[] bytes = method.getResponseBody();
        String savePath = "E:\\网盘下载\\Picture";
        File dir = new File(savePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(savePath,TimeConversionUtil.dateToString(new Date())+ ".jpg");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes);
        fileOutputStream.close();
        method.releaseConnection();
        return "下载图片成功！";
    }
}
