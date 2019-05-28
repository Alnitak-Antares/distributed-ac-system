package app.controller;


import app.dto.AirConditionerParams;
import app.dto.DetailRecord;
import app.entity.User;
import app.entity.bill;
import app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

//前台服务人员
@RestController
@RequestMapping("/receptionist")
public class ReceptionistController {

    @Autowired
    private AirConditionerService airConditionerService;
    @Autowired
    private ServiceDetailService serviceDetailService;
    @Autowired
    private BillService billService;
    @Autowired
    private UserService userService;

    @PostMapping("/adduser")
    /*  接收参数 房客的手机号码
        返回参数 User
     */
    // finish test by postman
    public User checkInCustomer(@RequestParam(value="idnumber") String idNumber) {
        //前台增加用户，需要房客的手机号码，返回房客房间空调系统的密码
        //如果房间满，返回空对象
        User nowUser=airConditionerService.checkInCustom(idNumber);
        if (nowUser!=null) userService.submitUser(nowUser);
        else {
            nowUser=new User();
            nowUser.setUserid(-1);
        }
        return nowUser;
    }

    @PostMapping("/checkout")
    /* 接收参数 房间id
        返回参数 执行结果?"success":"error".
     */
    // finish test by postman

    public String checkOutCustomer(@RequestParam(value="roomid") int roomid) {
        return airConditionerService.checkOutCustom(roomid);
    }

    //finist test by postman
    /* test data
        roomid=1
        starttime=2019-01-01T01:00:00
        stoptime=2019-01-01T01:00:00
     */
    @GetMapping("/createrdr")
    public DetailRecord createRDR(@RequestParam(value="roomid") int roomId,
                                  @RequestParam(value="starttime") String startTime,
                                  @RequestParam(value="stoptime") String stopTime) {
        return serviceDetailService.
                selectByRoomIdAndTime(roomId,startTime,stopTime);
    }

    //finist test by postman
    /* test data
        roomid=1
        starttime=2019-01-01T01:00:00
        stoptime=2019-01-01T01:00:00
     */
    @GetMapping("/createinvoice")
    public bill CreateInvoice(@RequestParam(value="roomid") int roomId,
                              @RequestParam(value="starttime") String startTime,
                              @RequestParam(value="stoptime") String stopTime) {

        return billService.selectByRoomIdAndTime(roomId,startTime,stopTime);
    }

}
