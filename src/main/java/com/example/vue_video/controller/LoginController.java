package com.example.vue_video.controller;

import com.example.vue_video.pojo.User;
import com.example.vue_video.result.Result;
import com.example.vue_video.service.UserService;
import com.example.vue_video.util.CheckEmailUtil;
import com.example.vue_video.util.EmailModule;
import com.example.vue_video.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Pattern;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.example.vue_video.constant.Constant.*;

@RestController
@RequestMapping("/login")
@Slf4j
@CrossOrigin
public class LoginController {
    @Resource
    private EmailModule emailModule;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private UserService userService;

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //根据用户名/电子邮件查询用户
        User user = userService.findByUserName(username);
        if (user == null) {
            return Result.error(USER_NOT_EXIST);
        }
        //判断密码是否正确
        if (StringUtils.hasText(password)) {
            //取得加密盐值
            String salt = user.getSalt();
            //给输入的明文密码加密
            String pass = MD5Util.MD5Encode(password, salt);

            //比较密码相等
            if (pass.equals(user.getPassword())) {
                //比较成功
                //生成口令
                String token = UUID.randomUUID().toString();
                //保存数据到redis
                String key = "token:" + username;
                redisTemplate.opsForValue().set(key, token, 2, TimeUnit.HOURS);
                //登录成功
                return Result.success(token);
            } else {
                return Result.error(PASSWORD_ERR);
            }
        }
        return Result.error(LOGIN_ERR);
    }

    /**
     * 发送邮箱
     * @param username
     * @param email
     * @return
     */
    @PostMapping("/code")
    public Result<String> sentEmail(String username,String email){
        if(CheckEmailUtil.checkEmail(email)==false){
            return Result.error(EMAIL_FORMAT_ERR);
        }
        String code = emailModule.sendCode(username,email);
        if (code == null && code.length()==0){
            return Result.error(EMAIL_SENT_ERR);
        }
        String key="code:"+email;
        redisTemplate.opsForValue().set(key, code,5, TimeUnit.HOURS);
        return Result.success("邮箱发送成功",code);
    }

    /**
     * 注册用户
     * @param username
     * @param password
     * @param email
     * @param code
     * @return
     */
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password,String email,String code) {

        //查询用户
        User user = userService.findByUserName(username);
        if (user == null) {
            //没有占用
            //注册
            String codeEmail = (String) redisTemplate.opsForValue().get(email);
            if (codeEmail == code){
                return Result.error(CODE_ERR);
            }
            userService.register(username,password,email);
            return Result.success(REGISTERED_SUCCESSFULLY);
        } else {
            //占用
            return Result.error(USER_NAME_USERD);
        }
    }
}
