package app.controller;


import app.dto.AirConditionerParams;
import app.dto.RoomState;
import app.service.AirConditionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AirConditionerParams acParams;

    @Autowired
    AirConditionerService acService;

    //finish test by postman
    //response :READY
    @GetMapping(value = "/powerOn")
    public String powerOn() {
        acParams.setSystemState("READY");
        return "{ \"statue\" : \""+acParams.getSystemState()+"\"}";
    }

    //finish test by postman
    //test example:
    //http://localhost:8080/admin/setParams?defaultRoomTemp=30&tempHighLimit=30&tempLowLimit=16&defaultTargetTemp=24&feeRateHigh=3&feeRateMiddle=2&feeRateLow=1&defaultFunSpeed=MIDDLE
    //response:AirConditionerParams
    @PostMapping(value = "/setParams")
    public AirConditionerParams setParams(@RequestParam(value="isCooling") boolean isCooling,
                                          @RequestParam(value="tempHighLimit") int tempHighLimit,
                                          @RequestParam(value="tempLowLimit") int tempLowLimit,
                                          @RequestParam(value="defaultTargetTemp") int defaultTargetTemp,
                                          @RequestParam(value="feeRateHigh") double feeRateHigh,
                                          @RequestParam(value="feeRateMiddle") double feeRateMiddle,
                                          @RequestParam(value="feeRateLow") double feeRateLow,
                                          @RequestParam(value="defaultFunSpeed") String defaultFunSpeed) {

        acParams.setMode(isCooling ? "cool" : "heat");
        acParams.setTempHighLimit(tempHighLimit);
        acParams.setTempLowLimit(tempLowLimit);
        acParams.setDefaultTargetTemp(defaultTargetTemp);
        acParams.setFeeRateHigh(feeRateHigh);
        acParams.setFeeRateMiddle(feeRateMiddle);
        acParams.setFeeRateLow(feeRateLow);
        acParams.setDefaultFunSpeed(defaultFunSpeed);

        return acParams;
    }


    //finish test by postman
    //response :READY
    @GetMapping(value = "/startup")
    public String startup() {
        acParams.setSystemState("ON");
        acService.init();
		return "{ \"statue\" : \""+acParams.getSystemState()+"\"}";
    }

    //finish test by postman
    @GetMapping(value = "/roomState/{roomID}")
    public RoomState checkRoomState(@PathVariable int roomID) {
        return acService.checkRoomState(roomID);
    }

}
