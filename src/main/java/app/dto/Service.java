package app.dto;

import java.time.LocalDateTime;

public class Service {
    private int roomId;             //房间id
    private int tarTemp;
    private String funSpeed;
    private LocalDateTime startTime;
    private double feeRate;
    private double currentFee;

    public Service(int roomId, int tarTemp, String funSpeed, LocalDateTime startTime, double feeRate) {
        this.roomId = roomId;
        this.tarTemp = tarTemp;
        this.funSpeed = funSpeed;
        this.startTime = startTime;
        this.feeRate = feeRate;
        this.currentFee = 0;
    }

    public double getCurrentFee() {
        return currentFee;
    }

    public void setCurrentFee(double currentFee) {
        this.currentFee = currentFee;
    }

    public double getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(double feeRate) {
        this.feeRate = feeRate;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
