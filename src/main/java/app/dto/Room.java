package app.dto;

import java.time.LocalDateTime;

public class Room {
    private double nowTemp;            //当前室温
    private int lastTarTemp;            //最后一次的目标温度
    private String lastFanSpeed;           //最后一次的目标风速
    private boolean isPowerOn;      //是否开机
    private boolean isInService;    //是否服务
    private boolean isCheckIn;      //是否入住
    private LocalDateTime startTime;         //入住时间

    public Room(double nowTemp) {
        this.nowTemp = nowTemp;
        this.isPowerOn = false;
        this.isInService = false;
        this.isCheckIn = false;
        this.lastFanSpeed = "OFF";
        this.startTime = LocalDateTime.now();

    }

    public void clear() {
        this.isPowerOn = false;
        this.isInService = false;
    }

    public void init(LocalDateTime nowtime) {
        this.nowTemp = nowTemp;
        this.isPowerOn = false;
        this.isInService = false;
        this.lastFanSpeed = "OFF";
        this.startTime = nowtime;
        this.isCheckIn = true;
    }

    public double getNowTemp() {
        return nowTemp;
    }

    public void setNowTemp(double nowTemp) {
        this.nowTemp = nowTemp;
    }

    public int getLastTarTemp() {
        return lastTarTemp;
    }

    public void setLastTarTemp(int tarTemp) {
        this.lastTarTemp = tarTemp;
    }

    public String getLastFanSpeed() {
        return lastFanSpeed;
    }

    public void setLastFanSpeed(String funSpeed) {
        this.lastFanSpeed = funSpeed;
    }

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

    public boolean isCheckIn() {
        return isCheckIn;
    }

    public void setCheckIn(boolean checkIn) {
        isCheckIn = checkIn;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
