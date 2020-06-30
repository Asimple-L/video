package com.asimple.controller;

import com.asimple.util.FileOperate;
import com.asimple.util.JSONUtil;
import com.asimple.util.ResponseReturnUtil;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @ProjectName video
 * @description 工具访问，文件上传，邮件发送等等
 * @author Asimple
 */
@RestController
public class Util implements ServletContextAware {
    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * @author Asimple
     * @description 文件上传(多文件上传处理)
     **/
    @RequestMapping(value = "/upload", produces = "application/json;charset=UTF-8")
    public String upload(String childPath, HttpServletRequest request) throws IOException{
        // 文件上传处理
        Properties pro = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("user.properties");
        try { // 加载本地存贮路径
            pro.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 初始化返回对象
        List<Map<String, String>> list = new ArrayList<>();
        // 判断request是否有文件上传(多部分请求)
        if ( multipartResolver.isMultipart(request) ) {
            // 将请求转化成多部分request请求
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 获取request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();

            // 文件保存路径
            String desPath = File.separator+childPath;
            String path = this.servletContext.getRealPath(pro.getProperty("upload")+desPath);
            // 记录文件上传数目
            int count = 1;
            while ( iter.hasNext() ) {
                // 记录上传过程起始时间，用来计算上传时间
                int pre = (int)System.currentTimeMillis();
                // 获取上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if( file != null ) {
                    // 获得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    // 打印文件大小
                    System.err.println("文件大小：" + file.getSize());
                    // 如果文件名不为空，说明文件存在
                    if( myFileName!=null && !"".equals(myFileName.trim()) ) {
                        System.err.println("上传文件" + count + ": " + myFileName + "用时：");
                        // 重命名上传后的文件名
                        String fileName = file.getOriginalFilename();
                        String fileType = fileName.substring(fileName.lastIndexOf("."));
                            // 文件后缀名
                        String fileSuffix = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
                            // 文件名（除后缀）
                        String fileNameSuffix = fileName.substring(0, fileName.lastIndexOf("."));
                            // 防止上传无后缀名的文件，所以不用 "."+fileSuffix
                        String newFileName = System.currentTimeMillis() + fileType;
                        // 新建一个文件
                        File file2 = new File(path, newFileName);
                        if( !file2.exists() ) {
                            file2.createNewFile();
                        }
                        file.transferTo(file2);
                        count ++;

                        // 设置返回数据
                        Map<String, String> map = new HashMap<>(4);
                        map.put("fileSuffix", fileSuffix);
                        map.put("fileName", fileName);
                        map.put("name", fileNameSuffix);
                        // 文件路径
                        map.put("filePath", pro.getProperty("upload")+childPath+"/"+newFileName);
                        list.add(map);
                    }
                }
                // 记录上传该文件后的时间
                int finaltime = (int) System.currentTimeMillis();
                System.err.println((finaltime-pre) + "ms");
            }
        }
        return JSONUtil.toJSONString(list);
    }

    /**
     * @author Asimple
     * @description 根据文件路径删除系统下的文件
     **/
    @RequestMapping(value = "/delFile", produces = "application/json;charset=UTF-8")
    public Object delFile(String picsPath) {
        JSONObject jsonObject = new JSONObject();
        if( picsPath.startsWith("/video/") ) {
            picsPath = picsPath.substring(picsPath.lastIndexOf("/video/")+"/video/".length());
        }
        if(FileOperate.delFile(this.servletContext.getRealPath("/"+picsPath))) {
            return ResponseReturnUtil.returnSuccessWithoutMsgAndData();
        }
        return ResponseReturnUtil.returnErrorWithMsg("删除失败,请稍后重试!");
    }



}