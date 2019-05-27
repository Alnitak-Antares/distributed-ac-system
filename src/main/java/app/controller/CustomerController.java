package app.controller;

import app.service.AirConditionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    AirConditionerService acService;

    @PostMapping("/requestOn")
    public void requestOn(@RequestParam(value="roomID") int roomID) {
        acService.requestPowerOn(roomID);
    }

    @PostMapping("/requestOff")
    public void requestOff(@RequestParam(value="roomID") int roomID) {
        acService.requestPowerOff(roomID);
    }

    @GetMapping("/requestFee")
    public double requestFee(@RequestParam(value="roomID") int roomID) {
        return acService.requestFee(roomID);
    }

    @PostMapping("/changeTargetTemp")
    public void changeTargetTemp(@RequestParam(value="roomID") int roomID, @RequestParam(value="targetTemp") int targetTemp) {
        acService.changeTargetTemp(roomID, targetTemp);
    }

    @PostMapping("/changeFanSpeed")
    public void changeTargetFunSpeed(@RequestParam(value="roomID") int roomID, @RequestParam(value="targetFanSpeed") String targetFanSpeed) {
        acService.changeFanSpeed(roomID, targetFanSpeed);
    }
}