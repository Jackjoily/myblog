package code.jack.myblog.controller;

import code.jack.myblog.cache.TagCache;
import code.jack.myblog.dto.QuestionDto;
import code.jack.myblog.mapper.QuestionMapper;
import code.jack.myblog.mapper.UserMapper;
import code.jack.myblog.model.Question;
import code.jack.myblog.model.User;
import code.jack.myblog.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    TagCache tagCache=new TagCache();
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
        QuestionDto question = questionService.getQuestionDtoById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        model.addAttribute("tagDtos", tagCache.get());
        return "publish";
    }
    @GetMapping("/topublish")
    public String publish(Model model) {
        model.addAttribute("tagDtos", tagCache.get());
        return "publish";
    }

    @PostMapping("/dopublish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam(name = "id", required = false) Integer id,
            HttpServletRequest request,
            Model model
    ) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tagDtos", tagCache.get());
        if (title == null || title.equals("")) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description.equals("")) {
            model.addAttribute("error", "描述不能为空");
            return "publish";
        }
        if (tag == null || tag.equals("")) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        String invalid=tagCache.filterINvalid(tag);
         if(StringUtils.isNotBlank(invalid)){
             model.addAttribute("error", "输入了非法标签:"+invalid);
             return "publish";
         }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:index";
    }
}
