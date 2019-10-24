package code.jack.myblog.controller;

import code.jack.myblog.cache.TagCache;
import code.jack.myblog.dto.PageDto;
import code.jack.myblog.mapper.CommentMapper;
import code.jack.myblog.mapper.QuestionMapper;
import code.jack.myblog.mapper.UserMapper;
import code.jack.myblog.model.Comment;
import code.jack.myblog.model.CommentExample;
import code.jack.myblog.model.QuestionExample;
import code.jack.myblog.model.UserExample;
import code.jack.myblog.service.CommentService;
import code.jack.myblog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author jackjoily
 * @time ${Date}
 * @Description
 **/
@Controller
public class AdminController {

    @Autowired
    QuestionService questionService;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/admin")
    public String toadminLogin() {
        return "admin_login";
    }

    @RequestMapping("/index_v1")
    public String index_v1(HttpServletRequest request
            , Model model
            , @RequestParam(name = "page", defaultValue = "1") Integer page
            , @RequestParam(name = "size", defaultValue = "5") Integer size
            , @RequestParam(name = "search", defaultValue = "") String search) {
        PageDto pageDto = questionService.getQuestionDtoList(search, page, size);
        CommentExample example = new CommentExample();
        List<Comment> comments = commentMapper.selectByExample(new CommentExample());
        model.addAttribute("comments", comments);
        model.addAttribute("pageDto", pageDto);
        return "index_v1";
    }

    @PostMapping("/adminlogin")
    public String adminLogin(String username, String password, Model model) {
        int commenttotalcount = commentMapper.selectByExample(new CommentExample()).size();
        int questiontotalcount = questionMapper.selectByExample(new QuestionExample()).size();
        int usertotalcount = userMapper.selectByExample(new UserExample()).size();
        model.addAttribute("commenttotalcount", commenttotalcount);
        model.addAttribute("questiontotalcount", questiontotalcount);
        model.addAttribute("usertotalcount", usertotalcount);
        if ("admin".equals(username) && "admin".equals(password)) {
            return "admin_index";
        } else {
            return "admin_login";
        }
    }

}
