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

import java.time.Duration;
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

    private boolean isSystemStartup=false;

    @Autowired
    private ServiceDetailService serviceDetailService;

    //TODO: 调度计数逻辑变更，符合优先级或时间片条件换入/换出时才记一次调度 --finished
    //TODO: 回温和变温模块区分制热/制冷模式
    //TODO: 从机开机时指定房间初始温度
    //TODO: 经理报表中详单数定义更改：详单数指服务条目数，即记录下service的个数
    //TODO: 用户更改风速符合调度条件时，立即触发调度（可以通过调度器毫秒级运行解决，注意调度计数逻辑）--finished
    //TODO: 回温和变温速率固定 0.5度/分钟逐级递增

    public void init() {
        waitingList = Collections.synchronizedList(new ArrayList<Service>());
        runningList = Collections.synchronizedList(new ArrayList<Service>());
        roomList = Collections.synchronizedList(new ArrayList<Room>());
        billList = Collections.synchronizedList(new ArrayList<bill>());
        for(int i = 0; i <= 4; i++) {
            roomList.add(new Room(acParams.getDefaultRoomTemp()));
            billList.add(new bill(i));
        }
        //isSystemStartup=true;
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
        System.out.println(runningList.size()+" "+waitingList.size());
        System.out.println("Find:Roomid="+roomId);
        for (Service s: runningList) {
            if(s.getRoomId() == roomId) {
                runningList.remove(s);
                break;
            }
        }
        for (Service s: waitingList) {
            if(s.getRoomId() == roomId) {
                waitingList.remove(s);
                break;
            }
        }
    }

    //===========================================================
    //=====房客==================================================
    //=========================================================

    //----------------------------------------------------------
    /*房客请求打开空调
      说明：
     */
    public String requestPowerOn(int roomId) {
        if (roomList.get(roomId).isPowerOn()) return "Error: It's powerOn.";
        roomList.get(roomId).setPowerOn(true);
        billService.addPowerOn(billList.get(roomId));
        Service service = new Service(roomId,
                acParams.getDefaultTargetTemp(),
                acParams.getDefaultFunSpeed(),
                LocalDateTime.now(),
                acParams.getDefaultFeeRate());
        waitingList.add(service);

        billList.get(roomId).setStarttime(LocalDateTime.now().toString());
        return "success";
    }
    //--------------------------------------------------------
    //房客请求调节温度
    // 说明：
    public void changeTargetTemp(int roomId, int tarTemp) {

        //service持久化 当前时间：LocalDateTime.now()
        Service serv = findRoomService(roomId);
        if (runningList.contains(serv)) {
            serviceDetailService.sumbitDetail(serv);
            billService.addRunningService(billList.get(roomId), serv);
        }

        //增加bill中的更改温度计数器
        billService.addTempCounter(billList.get(roomId));


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

        if (runningList.contains(serv)) {
            serviceDetailService.sumbitDetail(serv);
            billService.addRunningService(billList.get(roomId), serv);
        }
        //计数器是计数器，账单是账单
        //增加bill中的更改风速计数器
        billService.addFunCounter(billList.get(roomId));

        serv.setFunSpeed(funSpeed);
        serv.setFeeRate(acParams.getFeeRateByFunSpeed(funSpeed));
        serv.setStartTime(LocalDateTime.now());
    }

    /*-----------------------------------------------------------
       房客请求关机：
     */
    public String requestPowerOff(int roomId) {
        if (!(roomList.get(roomId)).isPowerOn()) return "Error: It's powerOff.";
        Service serv = findRoomService(roomId);
        serviceDetailService.sumbitDetail(serv);
        billService.addRunningService(billList.get(roomId),serv);
       // billService.submitBill(billList.get(roomId));

        deleteRoomService(roomId);
        roomList.get(roomId).clear();
        return "success";
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
        rs.setNowTemp(room.getNowTemp());
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
        for(int indexRoom=1;indexRoom<=4;indexRoom++) {
            Room nowRoom = roomList.get(indexRoom);
            if (!nowRoom.isCheckIn()) {
                User nowuser = new User();
                nowuser.setRoomid(indexRoom);
                nowuser.setUsername(phoneNumber);
                nowuser.setPassword(createRandomNumber(4));
                nowRoom.init(nowtime);
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
        if (!nowRoom.isCheckIn()) return "Error";
        if (nowRoom.isPowerOn()) {
            requestPowerOff(roomId);
        }
        nowRoom.setCheckIn(false);
        nowBill.setStoptime(nowtime.toString());
        billService.submitBill(nowBill);
        return "Success";
    }
    //---------------------------------------------------------
    //---------------------------------------------------------
    //调度模块

    private void startService(Service serv) {
        int roomId = serv.getRoomId();
        serv.setStartTime(LocalDateTime.now());
        roomList.get(roomId).setInService(true);
        runningList.add(serv);
    }

    private void freeService(Service serv) {
        //运行中的service调度到等待队列
        int roomId = serv.getRoomId();

        serviceDetailService.sumbitDetail(serv);

        billService.addRunningCounter(billList.get(roomId));
        billService.addRunningService(billList.get(roomId),serv);

        serv.setStartTime(LocalDateTime.now());
        serv.setCurrentFee(0);
        roomList.get(roomId).setInService(false);
        runningList.remove(serv);
        waitingList.add(serv);
    }
    private String getLowestRunningFanSpeed() {
        for (Service s:runningList) {
            if(s.getFunSpeed().equals("HIGH"))
                return "HIGH";
        }
        for (Service s:runningList) {
            if(s.getFunSpeed().equals("MIDDLE"))
                return "MIDDLE";
        }
        for (Service s:runningList) {
            if(s.getFunSpeed().equals("LOW"))
                return "LOW";
        }
        return "OFF";
    }

    private int compareFanSpeed(String speed1, String speed2) {
        //两个风速 "LOW", "MIDDLE", "HIGH", 比较他们的概念上的大小
        //speed1 大于|等于|小于 speed2 分别返回 1|0|-1
        //注意java字符串比较要用equals()函数
        Integer spe1=0,spe2=0;
        switch (speed1) {
            case "LOW":spe1=1;break;
            case "MIDDLE":spe1=2;break;
            case "HIGH":spe1=3;break;
        }
        switch (speed2) {
            case "LOW":spe2=1;break;
            case "MIDDLE":spe2=2;break;
            case "HIGH":spe2=3;break;
        }
        return spe1.equals(spe2)?0:(spe1>spe2?1:-1);
    }

    //获取运行队列中风速最小且服务时间最长的服务对象
    private Service getLongestRunningServiceWithLowestFanSpeed() {
        if (runningList.size()==0) return null;
        Service lowestService=runningList.get(0);
        for(Service nowService:runningList) {
            int nowPrior=compareFanSpeed(nowService.getFunSpeed()
                    ,lowestService.getFunSpeed());
            if (nowPrior==1) continue;
            if (nowPrior==-1) {
                lowestService=nowService;
            }
            else {
                if (nowService.getStartTime().isBefore(lowestService.getStartTime())) {
                    lowestService = nowService;
                }
            }
        }
        return lowestService;
    }

    //根据roomId查看对应的房间是否正在等待服务
    private boolean isWaitingService(int roomID) {
        for (Service serv: waitingList) {
            if(serv.getRoomId() == roomID)
                return true;
        }
        return false;
    }

    //调度器
    @Scheduled(fixedRate = 1000)
    private void schedule() {
        if (acParams.getSystemState()==null) return;
        if (!(acParams.getSystemState().equals("ON"))) return;
        System.out.println("==============[Debug]:schedule=======");
        System.out.println("waiting: " + waitingList.size()+"  running: "+runningList.size());

        //检测房间是否达到目标温度
        for (int i = 1; i <= 4; i++) {
            Room room = roomList.get(i);
            if(room.isInService()) {
                Service serv = findRoomService(i);
                if (room.getNowTemp() <= serv.getTarTemp()) {
                    //达到目标温度，关闭服务，记录风速和目标温度以备重启
                    room.setInService(false);
                    room.setLastFanSpeed(serv.getFunSpeed());
                    room.setLastTarTemp(serv.getTarTemp());

                    runningList.remove(serv);
                }
            }
            else {  //  检测房间是否回温超过一度，超过则自动重启服务
                if(!room.isPowerOn())   continue;
                if(isWaitingService(i)) continue;
                if(Math.abs(room.getNowTemp() - room.getLastTarTemp()) >= 1) {
                    Service serv = new Service(i,
                            room.getLastTarTemp(),
                            room.getLastFanSpeed(),
                            LocalDateTime.now(),
                            acParams.getFeeRateByFunSpeed(room.getLastFanSpeed()));
                    waitingList.add(serv);
                }
            }
        }
        while(waitingList.size() > 0 && runningList.size() < 3)
            startService(waitingList.remove(0));

        if(waitingList.size() == 0)
            return;

        Service waitingServ = waitingList.get(0);
        String minFanSpeed = getLowestRunningFanSpeed();
        String waitingFanSpeed = waitingServ.getFunSpeed();
        //首先进行优先级调度
        //等待中的服务对象的风速最小
        if(compareFanSpeed(waitingFanSpeed, minFanSpeed) < 0)
            return;
        //等待中的服务对象风速比当前服务队列中最小风速大
        else if(compareFanSpeed(waitingFanSpeed, minFanSpeed) > 0) {
            Service toFree = getLongestRunningServiceWithLowestFanSpeed();
            freeService(toFree);
            startService(waitingServ);
            waitingList.remove(0);
        }
        //等待中风速与当前服务队列中最小风速相等
        else {
            int secInterval = (int) Duration.between(waitingServ.getStartTime(), LocalDateTime.now()).getSeconds();
            if(secInterval >= 120) {
                //等待时长超过两分钟
                Service toFree = getLongestRunningServiceWithLowestFanSpeed();
                freeService(toFree);
                startService(waitingServ);
                waitingList.remove(0);
            }
        }
    }

    //记账模块，定时更新费用、运行时间
    @Scheduled(fixedRate = 1000)
    private void billing() {
        if (acParams.getSystemState()==null) return;
        if (!(acParams.getSystemState().equals("ON"))) return;
        //只需要增加服务细节类的费用
        for(Service nowServ:runningList) {
            nowServ.setCurrentFee(nowServ.getFeeRate()
                    +nowServ.getCurrentFee());
        }
    //账单的计时是在退房后，统计所有服务细节的对象直接得到。
        //   for(int indexRoom=1;indexRoom<=4;indexRoom++) {
     //       if (!(roomList.get(indexRoom)).isCheckIn()) continue;
     //       billList.get(indexRoom).setRunningtime(
     //               billList.get(indexRoom).getRunningtime()+1);
     //   }
    }

    //回温和变温模块，定时更新房间温度
    @Scheduled(fixedRate = 60000)
    private void timerToChangeRoomTemp() {
        if (acParams.getSystemState()==null) return;
        if (!(acParams.getSystemState().equals("ON"))) return;
        System.out.println("==============[Debug]:AutoChangeTemp=======");
        for(int i = 1; i <= 4; i++) {
            Room nowRoom = roomList.get(i);

            System.out.println("当前温度："+nowRoom.getNowTemp());

            double nowRoomTemp=nowRoom.getNowTemp();

            if (nowRoom.isInService()) {
                Service nowServ = findRoomService(i);
                if (nowRoomTemp<=acParams.getTempLowLimit()) continue;
                switch (nowServ.getFunSpeed()) {
                    case "LOW":nowRoom.setNowTemp(nowRoomTemp-0.5);break;
                    case "MIDDLE":nowRoom.setNowTemp(nowRoomTemp-1.0);break;
                    case "HIGH":nowRoom.setNowTemp(nowRoomTemp-1.5);break;
                }
            }
            else {
                if (nowRoomTemp>=acParams.getTempHighLimit()) continue;
                nowRoom.setNowTemp(nowRoomTemp+0.5);
            }
        }
    }
}
