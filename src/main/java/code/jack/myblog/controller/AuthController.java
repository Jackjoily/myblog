package code.jack.myblog.controller;

import code.jack.myblog.mapper.UserMapper;
import code.jack.myblog.dto.Accessdto;
import code.jack.myblog.dto.GithubUser;
import code.jack.myblog.model.User;
import code.jack.myblog.provider.GithubProvider;
import code.jack.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthController {
    @Autowired
    GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;
    @Autowired
    UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        Accessdto dto = new Accessdto();
        dto.setClient_id(clientId);
        dto.setClient_secret(clientSecret);
        dto.setCode(code);
        dto.setRedirect_uri(redirectUrl);
        dto.setState(state);
        String accesstoken = githubProvider.getAccesstoken(dto);
        GithubUser githubuser = githubProvider.getUser(accesstoken);
        if (githubuser != null && githubuser.getId() != null) {
            //获取登录态
            User user = new User();
            String token = UUID.randomUUID().toString().replace("-", "");
            user.setToken(token);
            user.setName(githubuser.getName());
            user.setAccountid(String.valueOf(githubuser.getId()));
            user.setBio(githubuser.getBio());
            user.setAvatarUrl(githubuser.getAvatar_url());
            userService.createOrUpdate(user);
            //生成cookie对象
            response.addCookie(new Cookie("token", token));
            return "redirect:index";
        } else {
            //登录失败，重新登录
            return "redirect:index";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        request.removeAttribute("user");
        request.getSession().invalidate();
        return "redirect:/";
    }
}
