package app.dto;

public class RoomState {
    private int nowTemp;            //当前室温
    private int tarTemp;            //最后一次的目标温度
    private int funSpeed;           //最后一次的目标风速
    private Boolean isPowerOn;      //是否开机
    private Boolean isInService;    //是否服务

    public int getTarTemp() {
        return tarTemp;
    }

    public void setTarTemp(int tarTemp) {
        this.tarTemp = tarTemp;
    }

    public int getFunSpeed() {
        return funSpeed;
    }

    public void setFunSpeed(int funSpeed) {
        this.funSpeed = funSpeed;
    }

    public Boolean getInService() {
        return isInService;
    }

    public void setInService(Boolean inService) {
        isInService = inService;
    }

    public int getNowTemp() {
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
