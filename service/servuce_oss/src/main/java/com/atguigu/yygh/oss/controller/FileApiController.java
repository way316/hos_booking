package com.atguigu.yygh.oss.controller;

import com.atguigu.yygh.cmn.common.result.Result;
import com.atguigu.yygh.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/oss/file")
public class FileApiController {

    @Autowired
    FileService fileservice;

    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile multipartFile) {
        String url = fileservice.upload(multipartFile);
        return Result.ok(url);
    }
}
