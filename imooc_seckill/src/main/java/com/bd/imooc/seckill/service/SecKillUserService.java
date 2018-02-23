package com.bd.imooc.seckill.service;

import com.bd.imooc.seckill.dao.SecKillUserDao;
import com.bd.imooc.seckill.domain.SecKillUser;
import com.bd.imooc.seckill.exception.GlobalException;
import com.bd.imooc.seckill.redis.SecKillUserKey;
import com.bd.imooc.seckill.result.CodeMsg;
import com.bd.imooc.seckill.util.Md5Util;
import com.bd.imooc.seckill.util.UUIDUtil;
import com.bd.imooc.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class SecKillUserService {
    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    SecKillUserDao secKillUserDao;

    @Autowired
    RedisService redisService;

    public SecKillUser getById(long id) {
        SecKillUser user = redisService.get(SecKillUserKey.getById,
                "" + id, SecKillUser.class);
        if (user != null) {
            return user;
        }
        user = secKillUserDao.getById(id);
        if (user != null) {
            redisService.set(SecKillUserKey.getById, "" + id, user);
        }

        return user;
    }

    public boolean updatePassword(String token, long id, String formPass) {
        SecKillUser user = getById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        /*
            更新数据库
         */
        SecKillUser toBeUpdateUser = new SecKillUser();
        toBeUpdateUser.setId(id);
        toBeUpdateUser.setPassword(Md5Util.formPassToDbPass(formPass, user.getSalt()));
        secKillUserDao.update(toBeUpdateUser);
        /*
            更新缓存
         */
        redisService.delete(SecKillUserKey.getById, "" + id);
        user.setPassword(toBeUpdateUser.getPassword());
        redisService.set(SecKillUserKey.token, token, user);

        return true;
    }

    public SecKillUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        SecKillUser user = redisService.get(SecKillUserKey.token, token, SecKillUser.class);
        //延长会话有效期
        if (user != null) {
            addCookie(response, token, user);
        }

        return user;
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        /*
            判断手机号是否存在
         */
        SecKillUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        /*
            验证密码
         */
        String dbPass = user.getPassword();
        String salt = user.getSalt();
        String calcPass = Md5Util.formPassToDbPass(formPass, salt);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        /*
            生成cookie
         */
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);

        return token;
    }

    private void addCookie(HttpServletResponse response, String token, SecKillUser user) {
        redisService.set(SecKillUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(SecKillUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
