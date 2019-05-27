package app.dto;

import app.entity.bill;
//酒店管理人员的统计量
public class RoomStatis {
    private Integer roomid;
    private double totalfee;
    private Integer runningtime;
    private Integer scheduleCounter;
    private Integer detailedRecordCounter;
    private Integer powerOnCounter;
    private Integer changetempcounter;
    private Integer changefuncounter;

    public void addBill(bill nowBill) {
        this.changefuncounter+=nowBill.getChangefuncounter();
        this.totalfee+=nowBill.getTotalfee();
        this.runningtime+=nowBill.getRunningtime();
        this.scheduleCounter+=nowBill.getSchedulecounter();
        this.detailedRecordCounter+=nowBill.getDetailedrecordcounter();
        this.powerOnCounter+=nowBill.getPoweroncounter();
        this.changetempcounter+=nowBill.getChangetempcounter();
        this.changefuncounter+=nowBill.getChangefuncounter();
    }

    public RoomStatis(int roomid){
        this.roomid=roomid;
        this.changefuncounter=0;
        this.totalfee=0.0;
        this.runningtime=0;
        this.scheduleCounter=0;
        this.detailedRecordCounter=0;
        this.powerOnCounter=0;
        this.changetempcounter=0;
        this.changefuncounter=0;
    }

    public Integer getRoomid() {
        return roomid;
    }

    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    public double getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(double totalfee) {
        this.totalfee = totalfee;
    }

    public Integer getRunningtime() {
        return runningtime;
    }

    public void setRunningtime(Integer runningtime) {
        this.runningtime = runningtime;
    }

    public Integer getScheduleCounter() {
        return scheduleCounter;
    }

    public void setScheduleCounter(Integer scheduleCounter) {
        this.scheduleCounter = scheduleCounter;
    }

    public Integer getDetailedRecordCounter() {
        return detailedRecordCounter;
    }

    public void setDetailedRecordCounter(Integer detailedRecordCounter) {
        this.detailedRecordCounter = detailedRecordCounter;
    }

    public Integer getPowerOnCounter() {
        return powerOnCounter;
    }

    public void setPowerOnCounter(Integer powerOnCounter) {
        this.powerOnCounter = powerOnCounter;
    }

    public Integer getChangetempcounter() {
        return changetempcounter;
    }

    public void setChangetempcounter(Integer changetempcounter) {
        this.changetempcounter = changetempcounter;
    }

    public Integer getChangefuncounter() {
        return changefuncounter;
    }

    public void setChangefuncounter(Integer changefuncounter) {
        this.changefuncounter = changefuncounter;
    }
}
