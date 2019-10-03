package code.jack.myblog.controller;

import code.jack.myblog.dto.CommentCreateDto;
import code.jack.myblog.dto.CommentDto;
import code.jack.myblog.dto.ResultDto;
import code.jack.myblog.enums.CommentTypeEnums;
import code.jack.myblog.exception.CustomizeErrorCode;
import code.jack.myblog.model.Comment;
import code.jack.myblog.model.User;
import code.jack.myblog.service.CommentService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentCotroller {
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(
            @RequestBody CommentCreateDto commentDto,
            HttpServletRequest request
    )
    {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDto.errOf(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentDto == null || StringUtils.isBlank(commentDto.getContent())) {
            return ResultDto.errOf(CustomizeErrorCode.COMMENT_CONTENT_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());
        comment.setType(commentDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModify(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insertSelective(comment);
        return ResultDto.successOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDto<List<CommentDto>> comments(@PathVariable(name = "id") Integer id) {
        List<CommentDto> commentDtoList = commentService.getCommentByTtype(id, CommentTypeEnums.COMMENT.getType());
        return ResultDto.okOf(commentDtoList);
    }
}
