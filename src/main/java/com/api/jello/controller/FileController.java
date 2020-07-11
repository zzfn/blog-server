package com.api.jello.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.api.jello.service.SysConfigService;
import com.api.jello.util.ResultUtil;
import com.api.jello.vo.RequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @program: jello
 * @author: nanaouyang
 * @create: 2020/03/24 12:08
 */
@RestController
@RequestMapping("oss")
@Slf4j
public class FileController {
    @Autowired
    private SysConfigService sysConfigService;
    @GetMapping("/druid/stat")
    public Object druidStat() {
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }

    @GetMapping("listFiles")
    public Object listFiles(String path) {
        String endpoint=sysConfigService.selectOne("endpoint").getValue();
        String accessKeyId=sysConfigService.selectOne("accessKeyId").getValue();
        String accessKeySecret=sysConfigService.selectOne("accessKeySecret").getValue();
        String bucketName=sysConfigService.selectOne("bucketName").getValue();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setPrefix(path);
        return ResultUtil.success(ossClient.listObjects(listObjectsRequest));
    }

    @GetMapping("listImages")
    public Object listImages() {
        String endpoint=sysConfigService.selectOne("endpoint").getValue();
        String accessKeyId=sysConfigService.selectOne("accessKeyId").getValue();
        String accessKeySecret=sysConfigService.selectOne("accessKeySecret").getValue();
        String bucketName=sysConfigService.selectOne("bucketName").getValue();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setPrefix("img/");
        return ResultUtil.success(ossClient.listObjects(listObjectsRequest));
    }

    @PostMapping("upload")
    public Object upload(MultipartFile file) throws IOException {
        String endpoint=sysConfigService.selectOne("endpoint").getValue();
        String accessKeyId=sysConfigService.selectOne("accessKeyId").getValue();
        String accessKeySecret=sysConfigService.selectOne("accessKeySecret").getValue();
        String bucketName=sysConfigService.selectOne("bucketName").getValue();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, "img/" + file.getOriginalFilename(), file.getInputStream());
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
        return null;
    }

    @DeleteMapping("deleteFile")
    public Object deleteFile(@RequestBody RequestVO requestVo) throws IOException {
        String endpoint=sysConfigService.selectOne("endpoint").getValue();
        String accessKeyId=sysConfigService.selectOne("accessKeyId").getValue();
        String accessKeySecret=sysConfigService.selectOne("accessKeySecret").getValue();
        String bucketName=sysConfigService.selectOne("bucketName").getValue();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName,requestVo.getId());
        ossClient.shutdown();
        return null;
    }
}
