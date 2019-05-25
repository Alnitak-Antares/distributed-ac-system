package app.dto;

public class RoomState {
    private int nowTemp;            //当然室温
    private Boolean isPowerOn;          //

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
