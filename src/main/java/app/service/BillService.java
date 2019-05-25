package app.service;

import app.dao.billMapper;
import app.dto.Service;
import app.entity.bill;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@org.springframework.stereotype.Service
public class BillService {
    @Autowired
    private billMapper billmapper;

    public void addTempCounter(bill nowBill, Service nowServ) {
        nowBill.setChangetempcounter(
                nowBill.getChangetempcounter()+1);
        nowBill.setTotalfee(
                nowBill.getBillid()+nowServ.getCurrentFee());
        nowBill.setRunningtime(nowBill.getRunningtime()+
                (int)(java.time.Duration.between(nowServ.getStartTime(), LocalDateTime.now()).getSeconds()));
        nowBill.setDetailedrecordcounter(
                nowBill.getDetailedrecordcounter()+1);

        /*如果需要每次都更新数据库的话
           billmapper.updateByPrimaryKeySelective(nowBill);
         */

    }

    public void addFunCounter(bill nowBill, Service nowServ) {
        nowBill.setChangefuncounter(nowBill.getChangefuncounter()+1);
        nowBill.setTotalfee(
                nowBill.getBillid()+nowServ.getCurrentFee());
        nowBill.setRunningtime(nowBill.getRunningtime()+
                (int)((long)java.time.Duration.between(nowServ.getStartTime(), LocalDateTime.now()).getSeconds()));
        nowBill.setDetailedrecordcounter(
                nowBill.getDetailedrecordcounter()+1);

        /* billmapper.updateByPrimaryKeySelective(nowBill);*/
    }

    public void addPowerOn(bill nowBill){
        nowBill.setPoweroncounter(
                nowBill.getPoweroncounter()+1
        );
    }


    public void submitBill(bill nowBill) {


    }

   /* public void addTempCounter(bill nowBill) {


    }

    public void addTempCounter(bill nowBill) {


    }
*/
}
