package app.service;

import app.dao.UserMapper;
import app.entity.User;
import app.entity.UserExample;
import app.entity.bill;
import app.entity.billExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper usermapper;

    public User selectByUsername(String userName) {
        UserExample suitUser=new UserExample();
        suitUser.createCriteria()
                .andUsernameEqualTo(userName);
        List<User> userList=usermapper.selectByExample(suitUser);
        if (userList.size()==0) return null;
        return userList.get(userList.size()-1);
    }


}
