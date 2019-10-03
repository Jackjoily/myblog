package code.jack.myblog.controller;

import code.jack.myblog.dto.NotificationDto;
import code.jack.myblog.dto.PageDto;
import code.jack.myblog.mapper.UserMapper;
import code.jack.myblog.model.User;
import code.jack.myblog.service.NotificationService;
import code.jack.myblog.service.QuestionService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    UserMapper mapper;
    @Autowired
    QuestionService questionService;
    @Autowired
    NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model
            , @RequestParam(name = "page", defaultValue = "1") Integer page
            , @RequestParam(name = "size", defaultValue = "5") Integer size,
                          HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PageDto pageDto = questionService.getPageDtoListByCreator(user.getId(), page, size);
            model.addAttribute("pageDto", pageDto);
            int unreadNotiyCount=notificationService.unreadNotifyCount(user.getId());
            model.addAttribute("unreadNotiyCount",unreadNotiyCount);
        } else if ("replys".equals(action)) {
            //找出与该用户相关的所有的通知
            PageDto<NotificationDto>  pageDto=notificationService.list(user.getId(),page,size);
            int unreadNotiyCount=notificationService.unreadNotifyCount(user.getId());
            model.addAttribute("section", "replys");
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("pageDto", pageDto);
            model.addAttribute("unreadNotiyCount",unreadNotiyCount);
        }
        return "profile";
    }

}
