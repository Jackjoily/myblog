package code.jack.myblog.service;

import code.jack.myblog.mapper.UserMapper;
import code.jack.myblog.model.User;
import code.jack.myblog.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void createOrUpdate(User user) {
        User myuser=null;
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountidEqualTo(user.getAccountid());
        try{
             myuser = userMapper.selectByExample(userExample).get(0);
                User updateUser = new User();
                updateUser.setGmtModified(user.getGmtModified());
                updateUser.setAvatarUrl(user.getAvatarUrl());
                updateUser.setName(user.getName());
                updateUser.setToken(user.getToken());
                userMapper.updateByExampleSelective(updateUser,userExample);

        }catch (Exception e){
            e.printStackTrace();
        }
        if (myuser == null) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtModified());
            userMapper.insert(user);
        }
    }
}
