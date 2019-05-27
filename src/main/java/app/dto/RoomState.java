package app.dto;

//空调运维人员的统计量
public class RoomState {
    private boolean isPowerOn;      //是否开机
    private boolean isInService;    //是否服务
    private double nowTemp;            //当前室温
    private int tarTemp;
    private String funSpeed = "OFF";
    private double feeRate;
    private double totalFee;
    private int runningTime;    //单位为秒

    public boolean isPowerOn() {
        return isPowerOn;
    }

    public void setPowerOn(boolean powerOn) {
        isPowerOn = powerOn;
    }

    public boolean isInService() {
        return isInService;
    }

    public void setInService(boolean inService) {
        isInService = inService;
    }

    public double getNowTemp() {
        return nowTemp;
    }

    public void setNowTemp(double nowTemp) {
        this.nowTemp = nowTemp;
    }

    public int getTarTemp() {
        return tarTemp;
    }

    public void setTarTemp(int tarTemp) {
        this.tarTemp = tarTemp;
    }

    public String getFunSpeed() {
        return funSpeed;
    }

    public void setFunSpeed(String funSpeed) {
        this.funSpeed = funSpeed;
    }

    public double getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(double feeRate) {
        this.feeRate = feeRate;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }
}
