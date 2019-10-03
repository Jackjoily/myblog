package code.jack.myblog.controller;

import code.jack.myblog.mapper.CommentMapper;
import code.jack.myblog.mapper.NotificationMapper;
import code.jack.myblog.model.Comment;
import code.jack.myblog.model.Notification;
import code.jack.myblog.model.NotificationExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NotiyController {
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    CommentMapper commentMapper;
    @GetMapping("/notify/{id}")
    public String toNotify(
            @PathVariable("id") Integer id) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        notification.setStaus(1);
        NotificationExample example=new NotificationExample();
        example.createCriteria().andIdEqualTo(notification.getId());
        notificationMapper.updateByExample(notification, example);
        if(notification.getType()==1){
            return "redirect:/question/"+id;
        }else{
            Comment comment=commentMapper.selectByPrimaryKey(notification.getOuterId());
            return "redirect:/question/"+comment.getParentId();
        }

    }
}
