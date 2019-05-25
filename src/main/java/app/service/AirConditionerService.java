package app.service;


import app.dto.AirConditionerParams;
import app.dto.Room;
import app.dto.Service;
import app.entity.bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

@Component
public class AirConditionerService {

    private Semaphore waitingListSemaphore;

    private List<Service> waitingList;
    private List<Service> runningList;
    private List<Room> roomList;
    private List<bill> billList;

    @Autowired
    private AirConditionerParams acParams;

    public AirConditionerService() {
        waitingList = Collections.synchronizedList(new ArrayList<Service>());
        runningList = Collections.synchronizedList(new ArrayList<Service>());
        roomList = Collections.synchronizedList(new ArrayList<Room>());
        billList = Collections.synchronizedList(new ArrayList<bill>());
        for(int i = 0; i < 4; i++)
            roomList.add(new Room(acParams.getDefaultRoomTemp()));
    }

    private Service findRoomService(int roomId) {
        for (Service s: runningList) {
            if(s.getRoomId() == roomId)
                return s;
        }
        for (Service s: waitingList) {
            if(s.getRoomId() == roomId)
                return s;
        }
        return null;
    }

    private void deleteRoomService(int roomId) {
        for (Service s: runningList) {
            if(s.getRoomId() == roomId)
                runningList.remove(s);
        }
        for (Service s: waitingList) {
            if(s.getRoomId() == roomId)
                waitingList.remove(s);
        }
    }

    public void requestPowerOn(int roomId) {
        roomList.get(roomId).setPowerOn(true);

        Service service = new Service(roomId, acParams.getDefaultTargetTemp(), acParams.getDefaultFunSpeed(), LocalDateTime.now(), acParams.getDefaultFeeRate());

        waitingList.add(service);

    }
    //调节温度
    public void ChangeTargetTemp(int roomId, int tarTemp) {
        //
        Service s = findRoomService(roomId);
        s.setTarTemp(tarTemp);
    }

    //调节风速
    public void ChangeFanSpeed(int roomId, String funSpeed) {
        //
        Service s = findRoomService(roomId);
        s.setFunSpeed(funSpeed);
    }

    //关机
    public void requestPowerOff(int roomId) {
        deleteRoomService(roomId);
        roomList.get(roomId).clear();
        //To-do 将roomId对应的详单和账单持久化
    }

    public double requestFee(int roomId) {
        return billList.get(roomId).getTotalfee();
    }
    //2. 空调管理员
    //检查房间
    public Room getRoomState(int roomId) {
        return roomList.get(1);
    }

}
