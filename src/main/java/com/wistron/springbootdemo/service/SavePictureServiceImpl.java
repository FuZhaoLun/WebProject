package com.wistron.springbootdemo.service;

import com.wistron.springbootdemo.util.TimeConversionUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;

@Service
public class SavePictureServiceImpl implements SavePictureService {
    @Override
    public String savePicture(HttpServletRequest httpServletRequest) {
        try {
            MultipartHttpServletRequest multipartHttpServletRequest =(MultipartHttpServletRequest)httpServletRequest;
            MultipartFile multipartFile =  multipartHttpServletRequest.getFile("file");
            String savePath = "E:\\网盘下载\\Picture";
            File dir = new File(savePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(savePath, TimeConversionUtil.dateToString(new Date())+ ".jpg");
            multipartFile.transferTo(file);
            return "/Upload/File";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
