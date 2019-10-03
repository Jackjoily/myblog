package code.jack.myblog.service;

import code.jack.myblog.dto.NotificationDto;
import code.jack.myblog.dto.PageDto;
import code.jack.myblog.dto.QuestionDto;
import code.jack.myblog.mapper.NotificationMapper;
import code.jack.myblog.mapper.UserMapper;
import code.jack.myblog.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    UserMapper userMapper;
    public PageDto list(Integer id, Integer page, Integer size) {
        //page
        int offset = size * (page - 1);
        NotificationExample notificationExample = new NotificationExample();
//        notificationExample.setOrderByClause("gmt_create desc");
        notificationExample .createCriteria().andReceiverEqualTo(id);
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(page-1, size));
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        PageDto pageDto = new PageDto();
        for (Notification notification : notificationList) {
            NotificationDto notificationDto =new NotificationDto();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(notification.getNotifier());
            User user = userMapper.selectByExample(userExample).get(0);
            BeanUtils.copyProperties(notification, notificationDto);
            notificationDto.setNotifier(user);
            notificationDtoList.add(notificationDto);
        }
        pageDto.setData(notificationDtoList);
        NotificationExample nexample = new NotificationExample();
        nexample.createCriteria().andReceiverEqualTo(id);
        int totalcount = (int) notificationMapper.countByExample(nexample);
        pageDto.setPagein(totalcount, page, size);
        return pageDto;
    }

    public int unreadNotifyCount(int userId){
        NotificationExample notificationExample=new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId).andStausEqualTo(0);
      return (int)notificationMapper.countByExample(notificationExample);
    }

}
