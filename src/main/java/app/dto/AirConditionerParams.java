package app.dto;

import org.springframework.stereotype.Component;

@Component
public class AirConditionerParams {
    private int tempHighLimit;
    private int tempLowLimit;
    private int defaultTargetTemp;
    private double feeRateHigh;
    private double feeRateMiddle;
    private double feeRateLow;
    public enum FunSpeed {LOW, MIDDLE, HIGH}
    public enum SystemState {OFF, READY, ON}
    private FunSpeed defaultFunSpeed;
    private SystemState systemState;

    public FunSpeed getDefaultFunSpeed() {
        return defaultFunSpeed;
    }

    public void setDefaultFunSpeed(FunSpeed defaultFunSpeed) {
        this.defaultFunSpeed = defaultFunSpeed;
    }

    public SystemState getSystemState() {
        return systemState;
    }

    public void setSystemState(SystemState systemState) {
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
}
