package app.service;

import app.dao.UserMapper;
import app.entity.User;
import app.entity.bill;
import app.entity.billExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectByUsername(String userName) {
        billExample suitbill=new billExample();
        suitbill.createCriteria()
                .andUser(roomid)
                .andStarttimeBetween(starttime,stoptime)
                .andStoptimeBetween(starttime,stoptime);

        List<bill> billList=billmapper.selectByExample(suitbill);


    }


}
