package org.owoto.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.owoto.service.SysConfigService;
import org.owoto.util.RedisUtil;
import org.owoto.util.ResultUtil;
import org.owoto.vo.RequestVO;
import io.swagger.annotations.Api;
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
@Api(tags = "文件管理")
public class FileController {
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    RedisUtil redisUtil;

    @GetMapping("/druid/stat")
    public Object druidStat() {
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }

    @GetMapping("listFiles")
    public Object listFiles(String path) {
         String endpoint=(String)redisUtil.get("endpoint");
         String accessKeyId=(String)redisUtil.get("accessKeyId");
         String accessKeySecret=(String)redisUtil.get("accessKeySecret");
         String bucketName=(String)redisUtil.get("bucketName");
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setPrefix(path);
        return ResultUtil.success(ossClient.listObjects(listObjectsRequest));
    }

    @GetMapping("listImages")
    public Object listImages() {
        String endpoint=(String)redisUtil.get("endpoint");
        String accessKeyId=(String)redisUtil.get("accessKeyId");
        String accessKeySecret=(String)redisUtil.get("accessKeySecret");
        String bucketName=(String)redisUtil.get("bucketName");
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setPrefix("img/");
        return ResultUtil.success(ossClient.listObjects(listObjectsRequest));
    }

    @PostMapping("upload")
    public Object upload(@RequestBody MultipartFile file) throws IOException {
        String endpoint=(String)redisUtil.get("endpoint");
        String accessKeyId=(String)redisUtil.get("accessKeyId");
        String accessKeySecret=(String)redisUtil.get("accessKeySecret");
        String bucketName=(String)redisUtil.get("bucketName");
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, "img/" + file.getOriginalFilename(), file.getInputStream());
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
        return null;
    }

    @DeleteMapping("deleteFile")
    public Object deleteFile(@RequestBody RequestVO requestVo) throws IOException {
        String endpoint=(String)redisUtil.get("endpoint");
        String accessKeyId=(String)redisUtil.get("accessKeyId");
        String accessKeySecret=(String)redisUtil.get("accessKeySecret");
        String bucketName=(String)redisUtil.get("bucketName");
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName,requestVo.getId());
        ossClient.shutdown();
        return null;
    }
}
