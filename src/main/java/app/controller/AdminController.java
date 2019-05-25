package app.controller;


import app.dto.AirConditionerParams;
import app.service.AirConditionerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    AirConditionerParams acParams;

    @Autowired
    AirConditionerService acService;

    @RequestMapping(value = "/powerOn", method = RequestMethod.POST)
    public String powerOn() {
        acParams.setSystemState("READY");
        return acParams.getSystemState();
    }

    @RequestMapping(value = "/setParams", method = RequestMethod.GET)
    public AirConditionerParams setParams(@RequestParam(value="defaultRoomTemp") int defaultRoomTemp,
                                          @RequestParam(value="tempHighLimit") int tempHighLimit,
                                          @RequestParam(value="tempLowLimit") int tempLowLimit,
                                          @RequestParam(value="defaultTargetTemp") int defaultTargetTemp,
                                          @RequestParam(value="feeRateHigh") double feeRateHigh,
                                          @RequestParam(value="feeRateMiddle") double feeRateMiddle,
                                          @RequestParam(value="feeRateLow") double feeRateLow,
                                          @RequestParam(value="defaultFunSpeed") String defaultFunSpeed) {

        acParams.setDefaultRoomTemp(defaultRoomTemp);
        acParams.setTempHighLimit(tempHighLimit);
        acParams.setTempLowLimit(tempLowLimit);
        acParams.setDefaultTargetTemp(defaultTargetTemp);
        acParams.setFeeRateHigh(feeRateHigh);
        acParams.setFeeRateMiddle(feeRateMiddle);
        acParams.setFeeRateLow(feeRateLow);
        acParams.setDefaultFunSpeed(defaultFunSpeed);

        return acParams;
    }

    @RequestMapping(value = "/startup", method = RequestMethod.POST)
    public String startup() {
        acParams.setSystemState("ON");
        acService.init();
        return acParams.getSystemState();
    }

    @RequestMapping(value = "/room/{id}")
    public int getID(@PathVariable int id) {
        return id;
    }


    @GetMapping(value = "/fun")
    public String getID() {
        return acService.getFunSpeed();
    }
}
