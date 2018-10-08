package org.mintleaf.modules.core.controller;

import org.mintleaf.vo.ResultMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

import static org.mintleaf.vo.ResultMsg.fail;
import static org.mintleaf.vo.ResultMsg.ok;

/**
 * @Author: MengchuZhang
 * @Date: 2018/8/9 15:48
 * @Version 1.0
 */
@Controller
@RequestMapping("upload")
public class UploadController {

    /**
     * 初始化上传文件界面，跳转到index.html
     * @return
     */
    @RequestMapping(value = "index.html",method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView view =new ModelAndView("modules/core/upload/uploads.html");
        return view;
    }

    /**
     * 提取上传方法为公共方法
     * @param uploadDir 上传文件目录
     * @param file 上传对象
     * @throws Exception
     */
    private void executeUpload(String uploadDir,MultipartFile file) throws Exception
    {
        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //上传文件名
        String filename = UUID.randomUUID() + suffix;
        //服务器端保存的文件对象
        File serverFile = new File(uploadDir + filename);
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);
    }

    /**
     * 上传文件方法
     * @param file 前台上传的文件对象
     * @return
     */
    @RequestMapping(value = "/upload.do",method = RequestMethod.POST)
    public @ResponseBody
    ResultMsg upload(HttpServletRequest request, MultipartFile file)
    {
        try {
            //上传目录地址
            String uploadDir = "E:\\uploadfile\\";
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if(!dir.exists())
            {
                dir.mkdir();
            }
            //调用上传方法
            executeUpload(uploadDir,file);
        }catch (Exception e)
        {
            //打印错误堆栈信息
            e.printStackTrace();
            return fail("上传失败！");
        }

        return ok();
    }

    /**
     * 上传多个文件
     * @param request 请求对象
     * @param file 上传文件集合
     * @return
     */
    @RequestMapping(value = "/uploads.do",method = RequestMethod.POST)
    public @ResponseBody String uploads(HttpServletRequest request,MultipartFile[] file)
    {
        try {
            //上传目录地址
            String uploadDir = "E:\\uploadfile\\";
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if(!dir.exists())
            {
                dir.mkdir();
            }
            //遍历文件数组执行上传
            for (int i =0;i<file.length;i++) {
                if(file[i] != null) {
                    //调用上传方法
                    executeUpload(uploadDir, file[i]);
                }
            }
        }catch (Exception e)
        {
            //打印错误堆栈信息
            e.printStackTrace();
            return "上传失败";
        }
        return "上传成功";
    }
}