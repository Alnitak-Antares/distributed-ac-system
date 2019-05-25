package app.service;

import app.dao.billMapper;
import app.dto.Room;
import app.dto.RoomStatis;
import app.entity.bill;
import app.entity.billExample;

import java.util.List;

public class ManagerService {

    private billMapper billmapper;
    public RoomStatis queryRoom(int roomid, String starttime, String stoptime) {
        RoomStatis roomstatis=new RoomStatis(roomid);

        billExample suitbill=new billExample();
        billExample.Criteria criteria=suitbill.createCriteria();


        criteria.andRoomidEqualTo(roomid)
                .andStarttimeBetween(starttime,stoptime)
                .andStoptimeBetween(starttime,stoptime);

        List<bill> billList=billmapper.selectByExample(suitbill);
        for(bill nowbill:billList){
            roomstatis.addBill(nowbill);
        }
        return roomstatis;
    }
}
