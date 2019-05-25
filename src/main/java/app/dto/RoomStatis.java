package app.dto;

import app.entity.bill;

public class RoomStatis {
    private Integer roomid;
    private Float totalfee;
    private Integer runningtime;
    private Integer totalservicecount;
    private Integer changetermcounter;
    private Integer changefuncounter;

    public void addBill(bill nowbill) {
        this.changefuncounter+=nowbill.getChangefuncounter();
        this.changetermcounter+=nowbill.getChangetermcounter();
        this.totalfee+=nowbill.getTotalfee();
        this.runningtime+=nowbill.getRunningtime();
        this.totalservicecount+=nowbill.getTotalservicecount();
    }

    public RoomStatis(int roomid){
        this.roomid=roomid;
        this.totalfee=Float.valueOf("0.0");
        this.runningtime=0;
        this.totalservicecount=0;
        this.changetermcounter=0;
    }
}
