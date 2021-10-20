package com.zzf.controller;

import com.alibaba.fastjson.JSON;
import com.zzf.component.IgnoreAuth;
import com.zzf.entity.Trace;
import com.zzf.util.HttpUtil;
import com.zzf.util.ResultUtil;
import com.zzf.vo.PageVO;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * TRACE(Trace)表控制层
 *
 * @author makejava
 * @since 2021-06-25 16:22:10
 */
@RestController
@Slf4j
@RequestMapping("trace")
public class TraceController {
    /**
     * 服务对象
     */
    @Resource
    private MongoTemplate mongoTemplate;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Configuration configuration;
    @Value("${spring.mail.username}")
    private String from;
    @ApiOperation("ip查询")
    @GetMapping("ip")
    @IgnoreAuth
    public Object selectOne(HttpServletRequest request) {
        return ResultUtil.success(HttpUtil.getIp());
    }

    @ApiOperation("mail")
    @GetMapping("mail")
    @IgnoreAuth
    public Object mail() throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo("admin@annyyy.com");
        mimeMessageHelper.setSubject("SpringBoot测试邮件发送");

        Map<String, Object> map = new HashMap<>();
        map.put("title", "邮件标题");
        map.put("content", "邮件正文");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("mail.ftl"), map);
        mimeMessageHelper.setText(text, true);

        String url = "https://cdn.zzfzzf.com/1621500127578INeO4C.jpg";
        URL imgUrl = new URL(url);
        mimeMessageHelper.addAttachment("img.jpg", imgUrl::openStream);

        javaMailSender.send(mimeMailMessage);
        return ResultUtil.success(HttpUtil.getIp());
    }

    @GetMapping("list")
    @IgnoreAuth
    public Object send(PageVO pageVO) {
        Query query = new Query();
        query.limit(pageVO.getPageSize());
        query.skip((long) (pageVO.getCurrent() - 1) * pageVO.getPageSize());
        query.with(Sort.by("time").descending());
        long count=mongoTemplate.count(query, Trace.class,"logs");
        Map<Object, Object> map=new HashMap<>();
        map.put("count",count);
        map.put("list",mongoTemplate.find(query, Trace.class, "logs"));
        return ResultUtil.success(map);
    }

    @ApiOperation("服务器信息")
    @GetMapping("server/info")
    public Object sys() {
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.getForObject("http://47.100.47.169/json/stats.json", String.class);
        return ResultUtil.success(JSON.parse(res));
    }
}
