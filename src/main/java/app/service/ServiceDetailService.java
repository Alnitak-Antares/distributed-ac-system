package app.service;

import app.dao.billMapper;
import app.dao.serviceDetailMapper;
import app.dto.Service;
import app.entity.bill;
import app.entity.serviceDetail;
import org.omg.CORBA.ServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@org.springframework.stereotype.Service
public class ServiceDetailService {
    @Autowired
    private serviceDetailMapper servicedetailmapper;

    private String trDateToStr(LocalDateTime date) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .format(date);
    }
    public serviceDetail trServiceToDetail(Service nowService) {
        serviceDetail nowDetail=new serviceDetail();
        nowDetail.setFee(nowService.getCurrentFee());
        nowDetail.setFunspeed(nowService.getFunSpeed());
        nowDetail.setRoomid(nowService.getRoomId());
        nowDetail.setStarttime(trDateToStr(nowService.getStartTime()));
        nowDetail.setStoptime(trDateToStr(LocalDateTime.now()));
        nowDetail.setFeerate(nowService.getFeeRate());
        nowDetail.setServicedetailid(0);
        return nowDetail;
    }
    public void sumbitDetail(Service nowService) {
        servicedetailmapper.insert(
                trServiceToDetail(nowService)
        );
    }


}
