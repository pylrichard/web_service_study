package com.imooc.mmall.controller.portal;

import com.imooc.mmall.common.Const;
import com.imooc.mmall.common.ServerResponse;
import com.imooc.mmall.pojo.User;
import com.imooc.mmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login.do")
    public ServerResponse<User> login(String userName, String password, HttpSession session) {
        ServerResponse<User> response = userService.login(userName, password);
        if(response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }

        return response;
    }

    @GetMapping("logout.do")
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);

        return ServerResponse.createBySuccess();
    }

    @PostMapping("register.do")
    public ServerResponse<String> register(User user){
        return userService.register(user);
    }

    @PostMapping("check_validity.do")
    public ServerResponse<String> checkValidity(String str, String type){
        return userService.checkValidity(str,type);
    }

    /**
     * 获取用户登录信息
     */
    @PostMapping("get_user_info.do")
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }

        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
    }

    @PostMapping("find_password_question.do")
    public ServerResponse<String> findPasswordQuestion(String username){
        return userService.findPasswordQuestion(username);
    }

    @PostMapping("check_password_answer.do")
    public ServerResponse<String> checkPasswordAnswer(String userName, String question, String answer){
        return userService.checkPasswordAnswer(userName, question, answer);
    }
}
