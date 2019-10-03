package code.jack.myblog.controller;

import code.jack.myblog.dto.CommentCreateDto;
import code.jack.myblog.dto.CommentDto;
import code.jack.myblog.dto.PageDto;
import code.jack.myblog.dto.QuestionDto;
import code.jack.myblog.enums.CommentTypeEnums;
import code.jack.myblog.service.CommentService;
import code.jack.myblog.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model) {
        QuestionDto questionDto = questionService.getQuestionDtoById(id);
        List<CommentDto> commentDtos = commentService.getCommentByTtype(id, CommentTypeEnums.QUESTION.getType());
        List<QuestionDto> relateQuestion = questionService.selectRelated(questionDto);
        questionService.incView(id);
        model.addAttribute("qdto", questionDto);
        model.addAttribute("comments", commentDtos);
        model.addAttribute("relatedQuesions", relateQuestion);
        return "question";
    }

    @PostMapping("searchQuestion")
    @ResponseBody
    private PageDto searchByTitle(HttpServletRequest request
            , Model model
            , @RequestParam(name = "page", defaultValue = "1") Integer page
            , @RequestParam(name = "size", defaultValue = "5") Integer size
            , @RequestParam(name="title") String title) {
        PageDto  pageDto = questionService.getQuestionDtoList(page, size);
        model.addAttribute("PageDto", pageDto);
        return pageDto;
    }
}
