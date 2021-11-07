package com.zzf.util;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cc
 */
@Component
public class MailUtil {
    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private Configuration configuration;

    public Object sendEmail(String mailTo, String mailTittle, String mailContent) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(mailTo);
        message.setSubject(mailTittle);
        message.setText(mailContent);
        javaMailSender.send(message);
        return true;
    }

    public Object sendMailWithAttachment(String mailTo, String mailTittle, String mailContent, String fileName, File file) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(mailTo);
        helper.setSubject(mailTittle);
        helper.setText(mailContent);
        helper.addAttachment(fileName, file);
        javaMailSender.send(mimeMessage);
        return true;
    }

    public Object sendRichMail(String mailTo, String mailTittle, String mailContent, String template, Map<String, String> map) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(mailTo);
        mimeMessageHelper.setSubject(mailTittle);
        mimeMessageHelper.setText(mailContent);
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate(template), map);
        mimeMessageHelper.setText(text, true);
        javaMailSender.send(mimeMessage);
        return true;
    }
}
