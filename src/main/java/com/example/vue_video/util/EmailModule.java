package com.example.vue_video.util;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class EmailModule {
	
	private SimpleMailMessage simpleMailMessage;
	
	public EmailModule() {
		this.simpleMailMessage =new SimpleMailMessage();
		//发送给谁
//		simpleMailMessage.setTo("332150177@qq.com");
		//谁发送
		simpleMailMessage.setFrom("2601449063@qq.com ");
		//邮件名称：标题
//		simpleMailMessage.setSubject("微视频邮件验证");

		//邮件内容
//		simpleMailMessage.setText("来自微视频的验证码：123456789");

		simpleMailMessage.setSentDate(new Date());
		//发送邮件
//		mailSender.send(simpleMailMessage);
	}
	
	@Resource
	private MailSender mailSender;
	
	/**
	 * 给用户发送验证码
	 * @param email
	 * @return
	 */
	public String sendCode(String username,String email) {
		//随机字符串
		String code =MD5Util.createSlat(8);
		//发送给谁
		simpleMailMessage.setTo(email);
		//邮件名称：标题
		simpleMailMessage.setSubject("微视频邮件验证码");
		//邮件内容
		simpleMailMessage.setText(username+"：这是来自微视频的验证码："+code+"，5分钟内有效，请注意查收。");
		try {
			//发送
			this.mailSender.send(simpleMailMessage);
			return code;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 发送密码
	 * @param email
	 * @return
	 */
	public String sendPass(String email) {
		//随机字符串
		String code =MD5Util.createSlat(6);
		//发送给谁
		simpleMailMessage.setTo(email);
		//邮件名称：标题
		simpleMailMessage.setSubject("微视频邮件找回密码");
		//邮件内容
		simpleMailMessage.setText("亲：这是来自微视频的密码："+code+"，请查收。");
		try {
			//发送
			this.mailSender.send(simpleMailMessage);
			return code;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
