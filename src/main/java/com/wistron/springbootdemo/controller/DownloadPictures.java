package com.wistron.springbootdemo.controller;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 下载图片类
 */
public final class DownloadPictures implements Runnable {
    private URL url = null;//URL
    private URLConnection urlConn =  null; //url连接
    private BufferedReader bufIn = null;//缓冲读取器，读取网页信息
    private static final String IMG_REG = "<img.*src\\s*=\\s*(.*?)[^>]*?>";//img标签正则
    private static final String IMG_SRC_REG = "src\\s*=\\s*\"?(.*?)(\"|>|\\s+)";//img src属性正则
    private String downloadPath = null;//保存路径
    //构造，参数：想要下载图片的网址、下载到的图片存放的文件路径
    public DownloadPictures(String urlStr, String downloadPath) {
        createFolder(downloadPath);//创建文件夹
        try {
            url = new URL(urlStr);
            urlConn = url.openConnection();
            //设置请求属性，有部分网站不加这句话会抛出IOException: Server returned HTTP response code: 403 for URL异常
            //如：b站
            urlConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            bufIn = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        this.downloadPath = downloadPath;
    }

    //检测路径是否存在，不存在则创建
    private void createFolder(String path) {
        File myPath = new File(path);
        if (!myPath.exists()) myPath.mkdirs();//不存在则创建文件夹
    }

    //下载函数
    public void Download() {
        final int N = 20;//每一次处理的文本行数，这个数越小越容易遗漏图片链接，越大效率越低 (理论上)
        String line = "";
        String text = "";
        while (line != null){//网页内容被读完时结束循环
            for(int i = 0; i < N; i++)//读取N行网页信息存入到text当中，因为src内容可能分为多行，所以使用这种方法
                try {
                    line = bufIn.readLine();//从网页信息中获取一行文本

                    if(line != null)//判断防止把null也累加到text中
                        text += line;
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            //将img标签正则封装对象再调用matcher方法获取一个Matcher对象
            final Matcher imgM = Pattern.compile(IMG_REG).matcher(text);
            if(!imgM.find())//如果在当前text中没有找到img标签则结束本次循环
                continue;
            //将img src正则封装对象再调用matcher方法获取一个Matcher对象
            //用于匹配的文本为找到的整个img标签
            final Matcher imgSrcM = Pattern.compile(IMG_SRC_REG).matcher(imgM.group());
            while (imgSrcM.find()) {
                String imageLink = imgSrcM.group(1);//从正则中的第一个组中得到图片链接
                print(imageLink);//打印一遍链接
                //如果得到的src内容没有写协议，则添加上
//                if(!imageLink.matches("https://[\\s\\S]*"))       //这里有问题
//                    imageLink = "https://" + imageLink;
                print(imageLink);//打印一遍链接
                try {
                    //缓冲输入流对象，用于读取图片链接的图片数据
                    //在链接的图片不存在时会抛出未找到文件异常
                    final BufferedInputStream in = new BufferedInputStream(new URL(imageLink).openStream());
                    //文件输出流对象用于将从url中读取到的图片数据写入到本地
                    //保存的路径为downloadPath，保存的图片名为时间戳+".png"
                    final FileOutputStream file = new FileOutputStream(new File(downloadPath + System.currentTimeMillis() + ".png"));
                    int temp;//用于保存in从图片连接中获取到的数据
                    while ((temp = in.read()) != -1) file.write(temp);//将数据写入到本地路径中
                    //关闭流
                    file.close();
                    in.close();
                    //下载完一张图片后休息一会
                    try {
                        Thread.sleep(800);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //将text中的文本清空
            text = "";
        }
    }

    //run
    @Override
    public void run() {
        Download(); //下载函数
    }

    //打印语句
    public void print(Object obj) {
        System.out.println(obj);
    }
}