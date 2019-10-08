package code.jack.myblog.controller;

import code.jack.myblog.cache.TagCache;
import code.jack.myblog.dto.PageDto;
import code.jack.myblog.dto.QuestionDto;
import code.jack.myblog.mapper.QuestionMapper;
import code.jack.myblog.mapper.UserMapper;
import code.jack.myblog.model.Question;
import code.jack.myblog.model.User;
import code.jack.myblog.service.NotificationService;
import code.jack.myblog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexCotroller {
    @Autowired
    UserMapper mapper;
    @Autowired
    QuestionService questionService;
    @Autowired
    NotificationService notificationService;
    TagCache tagCache=new TagCache();

    @RequestMapping({"/", "index"})
    public String index(HttpServletRequest request
            , Model model
            , @RequestParam(name = "page", defaultValue = "1") Integer page
            , @RequestParam(name = "size", defaultValue = "5") Integer size
            ,@RequestParam(name="search",defaultValue = "") String search

    ) {
        PageDto pageDto = questionService.getQuestionDtoList(search,page,size);
        model.addAttribute("pageDto", pageDto);
        model.addAttribute("tagDtos", tagCache.get());
        return "index";
    }
}
