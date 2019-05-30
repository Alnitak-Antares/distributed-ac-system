package app.dto;

import org.springframework.stereotype.Component;

@Component
public class AirConditionerParams {
    private String mode;
    private int tempHighLimit;
    private int tempLowLimit;
    private int defaultTargetTemp;
    private double feeRateHigh;
    private double feeRateMiddle;
    private double feeRateLow;
    private String defaultFunSpeed; //LOW, MIDDLE, HIGH
    private String systemState; //OFF, READY, ON

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDefaultFunSpeed() {
        return defaultFunSpeed;
    }

    public void setDefaultFunSpeed(String defaultFunSpeed) {
        this.defaultFunSpeed = defaultFunSpeed;
    }

    public String getSystemState() {
        return systemState;
    }

    public void setSystemState(String systemState) {
        this.systemState = systemState;
    }

    public int getTempHighLimit() {
        return tempHighLimit;
    }

    public void setTempHighLimit(int tempHighLimit) {
        this.tempHighLimit = tempHighLimit;
    }

    public int getTempLowLimit() {
        return tempLowLimit;
    }

    public void setTempLowLimit(int tempLowLimit) {
        this.tempLowLimit = tempLowLimit;
    }

    public int getDefaultTargetTemp() {
        return defaultTargetTemp;
    }

    public void setDefaultTargetTemp(int defaultTargetTemp) {
        this.defaultTargetTemp = defaultTargetTemp;
    }

    public double getFeeRateHigh() {
        return feeRateHigh;
    }

    public void setFeeRateHigh(double feeRateHigh) {
        this.feeRateHigh = feeRateHigh;
    }

    public double getFeeRateMiddle() {
        return feeRateMiddle;
    }

    public void setFeeRateMiddle(double feeRateMiddle) {
        this.feeRateMiddle = feeRateMiddle;
    }

    public double getFeeRateLow() {
        return feeRateLow;
    }

    public void setFeeRateLow(double feeRateLow) {
        this.feeRateLow = feeRateLow;
    }

    public double getDefaultFeeRate() {
        switch (defaultFunSpeed) {
            case "LOW":
                return feeRateLow;
            case "MIDDLE":
                return feeRateMiddle;
            case "HIGH":
                return feeRateHigh;
        }
        return -1;
    }
    public double getFeeRateByFunSpeed(String funSpeed) {
        switch (defaultFunSpeed) {
            case "LOW":
                return feeRateLow;
            case "MIDDLE":
                return feeRateMiddle;
            case "HIGH":
                return feeRateHigh;
        }
        return -1;
    }
}
