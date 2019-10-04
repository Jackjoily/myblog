package code.jack.myblog.service;

import code.jack.myblog.dto.CommentCreateDto;
import code.jack.myblog.dto.CommentDto;
import code.jack.myblog.enums.CommentTypeEnums;
import code.jack.myblog.enums.NotificationEnum;
import code.jack.myblog.enums.NotificationStausEnum;
import code.jack.myblog.exception.CustomizeErrorCode;
import code.jack.myblog.exception.CustomizeException;
import code.jack.myblog.mapper.*;
import code.jack.myblog.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    public boolean ifExistsParentId(Integer parentId) {
        Question question = questionMapper.selectByPrimaryKey(parentId);
        Comment comment = commentMapper.selectByPrimaryKey(parentId);
        if (question == null && comment == null) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    public void insertSelective(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0 || !ifExistsParentId(comment.getParentId())) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnums.isExistsType(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnums.COMMENT.getType()) {
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbcomment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FUND);
            } else {
                commentMapper.insertSelective(comment);
                Comment parentComment=new Comment();
                parentComment.setId(dbcomment.getId());
                parentComment.setCommentCount(1);
                commentExtMapper.incComment(parentComment);
                Notification notification=new Notification();
                notification.setGmtCreate(System.currentTimeMillis());
                notification.setType(NotificationEnum.REPLY_COMMENT.getType());
                notification.setOuterTitle(dbcomment.getContent());
                notification.setNotifier(comment.getCommentator());
                notification.setOuterId(dbcomment.getId());
                notification.setStaus(NotificationStausEnum.UNREAD.getStatus());
                notification.setReceiver(dbcomment.getCommentator());
                notificationMapper.insertSelective(notification);
            }
        } else {
            //回复问题
            Question notifyquestion=questionMapper.selectByPrimaryKey(comment.getParentId());
            notifyquestion.setCommentCount(1);
            Notification notification=new Notification();
            notification.setGmtCreate(System.currentTimeMillis());
            notification.setType(NotificationEnum.REPLY_QUESTION.getType());
            notification.setOuterTitle(notifyquestion.getTitle());
            notification.setNotifier(comment.getCommentator());
            notification.setOuterId(notifyquestion.getId());
            notification.setStaus(NotificationStausEnum.UNREAD.getStatus());
            notification.setReceiver(notifyquestion.getCreator());
            notificationMapper.insertSelective(notification);
            commentMapper.insertSelective(comment);
            questionExtMapper.incComment(notifyquestion);
        }
    }
    public List<CommentDto> getCommentByTtype(Integer id,Integer type) {
        CommentExample example = new CommentExample();
        example.setOrderByClause("gmt_create desc");
        example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type);
        List<Comment> comments = commentMapper.selectByExample(example);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        Set<Integer> collect = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList();
        userIds.addAll(collect);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDto> commentsDto = comments.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);
            commentDto.setUser(userMap.get(comment.getCommentator()));
            return commentDto;
        }).collect(Collectors.toList());
        return commentsDto;
    }
}
