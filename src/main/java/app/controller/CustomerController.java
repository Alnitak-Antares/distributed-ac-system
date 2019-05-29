package app.controller;

import app.dto.RoomState;
import app.service.AirConditionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    AirConditionerService acService;


    @PostMapping("/requestOn")
    public String requestOn(@RequestParam(value="roomID") int roomID) {
        System.out.println("=============[Debug]:/customr/requsetOn====");
        return acService.requestPowerOn(roomID);
    }

    @PostMapping("/requestOff")
    public String requestOff(@RequestParam(value="roomID") int roomID) {
        System.out.println("==========[Debug]:/customer/requestOff======");
        return acService.requestPowerOff(roomID);
    }

    @GetMapping("/requestRoomState")
    public RoomState requestRoomState(@RequestParam(value="roomID") int roomID) {
        System.out.println("=========[Debug]:/customer/requestRoomState====");
        return acService.checkRoomState(roomID);
    }

    @PostMapping("/changeTargetTemp")
    public void changeTargetTemp(@RequestParam(value="roomID") int roomID, @RequestParam(value="targetTemp") int targetTemp) {
        System.out.println("============[Debug]:/customer/changeTargetTemp====");
        acService.changeTargetTemp(roomID, targetTemp);
    }

    @PostMapping("/changeFanSpeed")
    public void changeTargetFunSpeed(@RequestParam(value="roomID") int roomID, @RequestParam(value="targetFanSpeed") String targetFanSpeed) {
        System.out.println("===========[Debug]:/customer/chageFanSpeed=======");
        acService.changeFanSpeed(roomID, targetFanSpeed);
    }
}