package app.service;

import app.dto.AirConditionerParams;
import app.dto.Room;
import app.dto.RoomState;
import app.dto.Service;
import app.entity.User;
import app.entity.bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
public class AirConditionerService {

    private List<Service> waitingList;
    private List<Service> runningList;
    private List<Room> roomList;
    private List<bill> billList;

    @Autowired
    private AirConditionerParams acParams;

    @Autowired
    private BillService billService;

    @Autowired
    private ServiceDetailService serviceDetailService;

    public void init() {
        waitingList = Collections.synchronizedList(new ArrayList<Service>());
        runningList = Collections.synchronizedList(new ArrayList<Service>());
        roomList = Collections.synchronizedList(new ArrayList<Room>());
        billList = Collections.synchronizedList(new ArrayList<bill>());
        for(int i = 0; i < 4; i++) {
            roomList.add(new Room(acParams.getDefaultRoomTemp()));
            billList.add(new bill(i));
        }
    }

    private Service findRoomService(int roomId) {
        for (Service serv: runningList) {
            if(serv.getRoomId() == roomId)
                return serv;
        }
        for (Service serv: waitingList) {
            if(serv.getRoomId() == roomId)
                return serv;
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

    //===========================================================
    //=====房客==================================================
    //=========================================================

    //----------------------------------------------------------
    /*房客请求打开空调
      说明：
     */
    public void requestPowerOn(int roomId) {
        if (roomList.get(roomId).isPowerOn()) return;
        roomList.get(roomId).setPowerOn(true);
        billService.addPowerOn(billList.get(roomId));

        Service service = new Service(roomId, acParams.getDefaultTargetTemp(), acParams.getDefaultFunSpeed(), LocalDateTime.now(), acParams.getDefaultFeeRate());
        waitingList.add(service);

        billList.get(roomId).setStarttime(LocalDateTime.now().toString());

    }
    //--------------------------------------------------------
    //房客请求调节温度
    // 说明：
    public void changeTargetTemp(int roomId, int tarTemp) {

        //service持久化 当前时间：LocalDateTime.now()
        Service serv = findRoomService(roomId);
        if (runningList.contains(serv))
            serviceDetailService.sumbitDetail(serv);

        //增加bill中的更改温度计数器
        billService.addTempCounter(billList.get(roomId),serv);


        serv.setTarTemp(tarTemp);
        serv.setCurrentFee(0);
        serv.setStartTime(LocalDateTime.now());
    }

    //---------------------------------------------------------
    // 房客请求调节风速
    // 说明：
    public void changeFanSpeed(int roomId, String funSpeed) {

        Service serv = findRoomService(roomId);
        //service持久化 当前时间：LocalDateTime.now()

        if (runningList.contains(serv))
            serviceDetailService.sumbitDetail(serv);
        //增加bill中的更改风速计数器

        billService.addFunCounter(billList.get(roomId),serv);

        serv.setFunSpeed(funSpeed);
        serv.setFeeRate(acParams.getFeeRateByFunSpeed(funSpeed));
        serv.setStartTime(LocalDateTime.now());
    }

    /*-----------------------------------------------------------
       房客请求关机：
     */
    public void requestPowerOff(int roomId) {
        Service serv = findRoomService(roomId);
        serviceDetailService.sumbitDetail(serv);
        billService.addRunningService(billList.get(roomId),serv);
       // billService.submitBill(billList.get(roomId));

        deleteRoomService(roomId);
        roomList.get(roomId).clear();

    }

    //-------------------------------------------------------------
    // 房客请求查询费用
    public double requestFee(int roomId) {
        return billList.get(roomId).getTotalfee();
    }

    //=================================================
    //=====管理员========================================
    //==================================================

    //--------------------------------------------------
    //管理员监视房间
    public RoomState checkRoomState(int roomId) {
        Service serv = findRoomService(roomId);
        Room room = roomList.get(roomId);
        bill b = billList.get(roomId);

        RoomState rs = new RoomState();
        if (serv != null) {
            rs.setFeeRate(serv.getFeeRate());
            rs.setFunSpeed(serv.getFunSpeed());
            rs.setTarTemp(serv.getTarTemp());
        }
        rs.setPowerOn(room.isPowerOn());
        rs.setInService(room.isInService());
        rs.setTotalFee(b.getTotalfee());
        rs.setRunningTime(b.getRunningtime());

        return rs;
    }

    /*=====================================================
    =============前台服务人员================================
    ========================================================
     */
    //-----------------------------------------------------
    //前台服务人员办理入住
    public User checkInCustom(String phoneNumber) {
        LocalDateTime nowtime=LocalDateTime.now();
        for(int indexRoom=0;indexRoom<4;indexRoom++) {
            Room nowRoom = roomList.get(indexRoom);
            if (!nowRoom.isCheckIn()) {
                User nowuser = new User();
                nowuser.setRoomid(indexRoom);
                nowuser.setUsername(phoneNumber);
                nowuser.setPassword(createRandomNumber(4));
                nowRoom.setStartTime(nowtime);
                billService.initBill(billList.get(indexRoom),nowtime.toString(),nowuser);
                return nowuser;
            }
        }
        return null;
    }
    //创建随机密码
    private String createRandomNumber(int length) {
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();
        for(int i=0;i<length;i++)
                sb.append(rand.nextInt(10));
        return sb.toString();
    }
    //-------------------------------------------------------------
    //前台服务人员办理退房
    public String checkOutCustom(int roomId) {
        LocalDateTime nowtime=LocalDateTime.now();
        Room nowRoom=roomList.get(roomId);
        bill nowBill=billList.get(roomId);
        if (nowRoom==null) return "Error";
        if (nowRoom.isCheckIn()) return "Error";
        if (nowRoom.isPowerOn()) {
            requestPowerOff(roomId);
        }
        nowBill.setStoptime(nowtime.toString());
        billService.submitBill(nowBill);
        return "Success";
    }
    //---------------------------------------------------------
}
