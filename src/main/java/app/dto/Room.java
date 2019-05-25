package app.dto;

public class Room {
    private double nowTemp;            //当前室温
    private int tarTemp;            //最后一次的目标温度
    private String funSpeed;           //最后一次的目标风速
    private boolean isPowerOn;      //是否开机
    private boolean isInService;    //是否服务

    public Room(double nowTemp) {
        this.nowTemp = nowTemp;
        this.isPowerOn = false;
        this.isInService = false;
    }

    public void clear() {
        this.isPowerOn = false;
        this.isInService = false;
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

    public Boolean getInService() {
        return isInService;
    }

    public void setInService(Boolean inService) {
        isInService = inService;
    }

    public double getNowTemp() {
        return nowTemp;
    }

    public Boolean getPowerOn() {
        return isPowerOn;
    }

    public void setNowTemp(int nowTemp) {
        this.nowTemp = nowTemp;
    }

    public void setPowerOn(Boolean powerOn) {
        isPowerOn = powerOn;
    }

}
