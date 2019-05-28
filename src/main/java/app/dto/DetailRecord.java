package app.dto;


import app.entity.serviceDetail;
import org.omg.CORBA.ServiceDetail;

import java.util.ArrayList;
import java.util.List;

public class DetailRecord{
    private ArrayList<serviceDetail> serviceDetailList=new ArrayList<serviceDetail>() {
    };

    public void addServiceDetail(serviceDetail nowServ) {
        serviceDetailList.add(nowServ);
    }

    public ArrayList<serviceDetail> getServiceDetailList() {
        return serviceDetailList;
    }

    public void setServiceDetailList(ArrayList<serviceDetail> serviceDetailList) {
        this.serviceDetailList = serviceDetailList;
    }
}