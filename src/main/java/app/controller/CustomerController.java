package app.controller;

import app.dto.AirConditionerParams;
import app.dto.RoomState;
import app.service.AirConditionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    AirConditionerService acService;

    @Autowired
    AirConditionerParams acParams;
    @PostMapping("/setInitTemp")
    public String setInitTemp(@RequestParam(value="roomID") int roomID,
                              @RequestParam(value="initTemp") int initTemp) {
        System.out.println("=============[Debug]:/customer/setInitTemp====");
        return "{ \"statue\" : \""+acService.setInitTemp(roomID, initTemp)+"\"}";
    }

    @PostMapping("/requestOn")
    public String requestOn(@RequestParam(value="roomID") int roomID) {
        System.out.println("=============[Debug]:/customer/requsetOn====");
        return "{ \"statue\" : \""+acService.requestPowerOn(roomID)+"\"," +
                "\"tempLowLimit\": " + acParams.getTempLowLimit() +","+
                "\"tempHighLimit\":" + acParams.getTempHighLimit() + "}";
    }

    @PostMapping("/requestOff")
    public String requestOff(@RequestParam(value="roomID") int roomID) {
        System.out.println("==========[Debug]:/customer/requestOff======");
        return "{ \"statue\" : \""+acService.requestPowerOff(roomID)+"\"}";
    }

    @GetMapping("/requestRoomState")
    public RoomState requestRoomState(@RequestParam(value="roomID") int roomID) {
        System.out.println("=========[Debug]:/customer/requestRoomState====");
        return acService.checkRoomState(roomID);
    }

    @PostMapping("/changeTargetTemp")
    public String changeTargetTemp(@RequestParam(value="roomID") int roomID, @RequestParam(value="targetTemp") int targetTemp) {
        System.out.println("============[Debug]:/customer/changeTargetTemp====");
        return "{ \"statue\" : \""+acService.changeTargetTemp(roomID, targetTemp)+"\"}";
    }

    @PostMapping("/changeFanSpeed")
    public String changeTargetFunSpeed(@RequestParam(value="roomID") int roomID, @RequestParam(value="targetFanSpeed") String targetFanSpeed) {
        System.out.println("===========[Debug]:/customer/changeFanSpeed=======");
        return "{ \"statue\" : \""+acService.changeFanSpeed(roomID, targetFanSpeed)+"\"}";
    }
}