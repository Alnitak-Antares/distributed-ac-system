package app.dto;


import app.entity.serviceDetail;
import org.omg.CORBA.ServiceDetail;

import java.util.List;

public class DetailRecord{
    private List<serviceDetail> serviceDetailList;

    public void addServiceDetail(serviceDetail nowServ) {
        serviceDetailList.add(nowServ);
    }





}