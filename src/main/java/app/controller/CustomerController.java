package app.controller;

import app.service.AirConditionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    AirConditionerService acService;

    @PostMapping("/RequestOn")
    public void requestOn(@RequestParam(value="roomID") int roomID) {
        acService.requestPowerOn(roomID);
    }

    @PostMapping("/RequestOff")
    public void requestOff(@RequestParam(value="roomID") int roomID) {
        acService.requestPowerOff(roomID);
    }

    @GetMapping("/RequestFee")
    public double requestFee(@RequestParam(value="roomID") int roomID) {
        return acService.requestFee(roomID);
    }

    @PostMapping("/ChangeTargetTemp")
    public void changeTargetTemp(@RequestParam(value="roomID") int roomID, @RequestParam(value="targetTemp") int targetTemp) {
        acService.changeTargetTemp(roomID, targetTemp);
    }

    @PostMapping("/ChangeTargetTemp")
    public void changeTargetFunSpeed(@RequestParam(value="roomID") int roomID, @RequestParam(value="targetFanSpeed") String targetFanSpeed) {
        acService.changeFanSpeed(roomID, targetFanSpeed);
    }
}