package app.controller;


import app.dto.AirConditionerParams;
import app.dto.AirConditionerParams.FunSpeed;
import app.dto.AirConditionerParams.SystemState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    @Autowired
    private AirConditionerParams acParams;

    @RequestMapping(value = "/powerOn", method = RequestMethod.POST)
    public SystemState powerOn() {
        acParams.setSystemState(SystemState.READY);
        return acParams.getSystemState();
    }

    @RequestMapping(value = "/setParams", method = RequestMethod.POST)
    public AirConditionerParams setParams(@RequestParam(value="tempHighLimit") int tempHighLimit,
                                          @RequestParam(value="tempLowLimit") int tempLowLimit,
                                          @RequestParam(value="defaultTargetTemp") int defaultTargetTemp,
                                          @RequestParam(value="feeRateHigh") double feeRateHigh,
                                          @RequestParam(value="feeRateMiddle") double feeRateMiddle,
                                          @RequestParam(value="feeRateLow") double feeRateLow,
                                          @RequestParam(value="defaultFunSpeed") String defaultFunSpeed) {

        acParams.setTempHighLimit(tempHighLimit);
        acParams.setTempLowLimit(tempLowLimit);
        acParams.setDefaultTargetTemp(defaultTargetTemp);
        acParams.setFeeRateHigh(feeRateHigh);
        acParams.setFeeRateMiddle(feeRateMiddle);
        acParams.setFeeRateLow(feeRateLow);
        switch(defaultFunSpeed) {
            case "LOW":
                acParams.setDefaultFunSpeed(FunSpeed.LOW);
                break;
            case "MIDDLE":
                acParams.setDefaultFunSpeed(FunSpeed.MIDDLE);
                break;
            case "HIGH":
                acParams.setDefaultFunSpeed(FunSpeed.HIGH);
                break;
        }
        return acParams;
    }

    @RequestMapping(value = "/startup", method = RequestMethod.POST)
    public SystemState startup() {
        acParams.setSystemState(SystemState.ON);
        return acParams.getSystemState();
    }

    @RequestMapping(value = "/room/{id}")
    public int getID(@PathVariable int id) {
        return id;
    }
}
