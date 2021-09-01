package com.changgou.file.controller;

import com.changgou.file.FastDFSFile;
import com.changgou.util.FastDFSClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Logger;

@RestController
public class FileController {


    @PostMapping("/upload")
    public String upload(MultipartFile file){ // MultipartFile接收文件流
        if (!file.isEmpty()){
            try {
                byte[] bytes = file.getBytes();
                // 将数据存储到fastdfs，返回文件存储路径
                String originalFilename = file.getOriginalFilename();
                String[] upload = FastDFSClient.upload(new FastDFSFile(originalFilename, bytes, StringUtils.getFilenameExtension(originalFilename)));
                // 文件实际路径
                String realpath = FastDFSClient.getTrackerUrl()+"/" + upload[0] + "/" + upload[1];
                return realpath;
            } catch (Exception e) {
                e.printStackTrace(); // 注意实际开发中不要这样写
            }
        }
        return null;
    }
}
