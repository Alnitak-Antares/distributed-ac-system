package app.controller;


import app.dto.AirConditionerParams;
import app.entity.User;
import app.service.AirConditionerService;
import app.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

//前台服务人员
@RestController
@RequestMapping("/deskstaff")
public class DeskStaffController {

    @Autowired
    private AirConditionerService airConditionerService;

    @PostMapping("/adduser")
    public String checkInCustomer(String phoneNumber) {
        //前台增加用户，需要房客的手机号码，返回房客房间空调系统的密码
        //
        //如果房间满，返回字符串为NOROOM

        return airConditionerService.checkInCustom(phoneNumber);
    }


}
