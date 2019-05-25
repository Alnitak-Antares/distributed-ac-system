package app.service;


import app.dto.AirConditionerParams;
import app.dto.RoomState;
import app.dto.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class scheduler {
    private ArrayList<Service> waitingList;
    private ArrayList<Service> runningList;
    private ArrayList<RoomState> roomList;
    @Autowired
    private AirConditionerParams acParams;

    //私有方法
    private Service findRoomService(int roomId)
    {
        return null;
    }
    //调度器
    private void dispatch(){

    }

    //==========================================
    //接收请求
    //==========================================
    //1. 房客
    //打开空调
    public void requestPowerOn(int roomId) {
        //新建服务对象
        /* 根据默认参数设置roomstate*/
        //直接放到等待队列
    }
    //调节温度
    public void ChangeTargetTemp(int roomId,int tarTemp) {
        //直接调用findRoomService,
    }

    //调节风速
    public void ChangeFanSpeed(int roomId, String FunSpeed) {
        //直接调用findRoomService,
    }
    //关机
    public void requestPowerOff(int roomId) {
        //删除服务
    }

    public int requestFee(int roomId) {

    }
    //2. 空调管理员
    //检查房间
    public RoomState getRoomState(int roomId) {
        return roomList.get(1);
    }





    //===========================================
    // 初始化
    //===========================================
    public void schduler() {


    }
}
