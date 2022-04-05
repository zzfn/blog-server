package com.zzf.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.zzf.util.RedisUtil;
import com.zzf.util.ResultUtil;
import com.zzf.vo.RequestVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * @author cc
 */
@RestController
@RequestMapping("oss")
@Slf4j
@Api(tags = "文件管理")
public class FileController {
    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucketName}")
    private String bucketName;

    @GetMapping("listFiles")
    public Object listFiles(String path) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setPrefix(path);
        return ResultUtil.success(ossClient.listObjects(listObjectsRequest));
    }

    @PostMapping("upload")
    public Object upload(@RequestBody MultipartFile file,@RequestParam String path) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path + file.getOriginalFilename(), file.getInputStream());
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
        return null;
    }

    @DeleteMapping("deleteFile")
    public Object deleteFile(@RequestBody RequestVO requestVo) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName,requestVo.getId());
        ossClient.shutdown();
        return null;
    }
}
