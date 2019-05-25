package app.dto;

import app.entity.bill;

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
}
