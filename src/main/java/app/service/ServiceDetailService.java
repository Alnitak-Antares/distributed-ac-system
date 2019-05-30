package app.service;

import app.dao.billMapper;
import app.dao.serviceDetailMapper;
import app.dto.DetailRecord;
import app.dto.Service;
import app.entity.bill;
import app.entity.billExample;
import app.entity.serviceDetail;
import app.entity.serviceDetailExample;
import org.omg.CORBA.ServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceDetailService {
    @Autowired
    private serviceDetailMapper servicedetailmapper;

//    private String trDateToStr(LocalDateTime date) {
//        return DateTimeFormatter.ofPattern("yyyy-MM-ddTHH:mm:ss")
//                .format(date);
//    }
    public serviceDetail trServiceToDetail(Service nowService) {
        serviceDetail nowDetail=new serviceDetail();
        nowDetail.setFee(nowService.getCurrentFee());
        nowDetail.setFunspeed(nowService.getFunSpeed());
        nowDetail.setRoomid(nowService.getRoomId());
        nowDetail.setStarttime(nowService.getStartTime().toString());
        nowDetail.setStoptime(LocalDateTime.now().toString() );
        nowDetail.setFeerate(nowService.getFeeRate());
        nowDetail.setServicedetailid(0);
        return nowDetail;
    }
    public void sumbitDetail(Service nowService) {
        if (nowService==null) return;
        servicedetailmapper.insert(
                trServiceToDetail(nowService)
        );
    }

    public DetailRecord selectByRoomIdAndTime(int roomId
            ,String startTime,String stopTime) {

        serviceDetailExample suitServ=new serviceDetailExample();
        suitServ.createCriteria()
                .andRoomidEqualTo(roomId)
                .andStarttimeBetween(startTime,stopTime)
                .andStoptimeBetween(startTime,stopTime);
        List<serviceDetail> serviceDetailList=
                servicedetailmapper.selectByExample(suitServ);
        DetailRecord nowRecord=new DetailRecord();
        for(serviceDetail nowServ:serviceDetailList) {
            nowRecord.addServiceDetail(nowServ);
        }
        if(nowRecord.getServiceDetailList().size() == 0)
            return null;
        else
            return nowRecord;
    }


}
