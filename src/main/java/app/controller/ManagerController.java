package app.controller;


import app.dto.RoomStatis;
import app.service.ManagerService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerSerivce;


    //---------------------------------------
    //finish test by postman
    /* test params:
        list_Roomid = 1
        list_Roomid = 2
        type_Report = 0
        year = 2019
        month =01
        day = 01
        日期之前的往前数的报表
      */
    @GetMapping("/queryreport")
    public ArrayList<RoomStatis> QueryReport(HttpServletRequest request) {
        System.out.println("====[Debug]:manager/queryreport======");
        /* "list_romeid":["1","2","4"],
           "type_report": 0/1/2,
           "year":xxxx,
           "month":mm, 01/02/.../12
           "day":dd,    01/02/../30
         */
        System.out.println(request.getParameterValues("list_Roomid"));
        String[] nowStrList=request.getParameterValues("list_Roomid");
        ArrayList<Integer> roomlist=new ArrayList<>();
        for(String nowStr:nowStrList) {
            roomlist.add(Integer.parseInt(nowStr));
        }
        ArrayList<RoomStatis> roomStatisList=new ArrayList<RoomStatis>();
        Integer typeReport=Integer.valueOf(request.getParameter("type_Report"));
        String year=request.getParameter("year");
        String month=request.getParameter("month");
        String day=request.getParameter("day");
        String stopTime=year+"-"+month+"-"+day+"T"+"23:59:59";
        String startTime="0000-00-00";
        switch (typeReport) {
            case 0:startTime=year+"-"+month+"-"+day+"T"+"00:00:00";break;
            case 1:
                month=String.format("%02d",Integer.valueOf(month)-1);
                startTime=year+"-"+month+"-"+"01"+"T"+"00:00:00";
                stopTime=year+"-"+month+"-"+"31"+"T"+"23:59:59";
                break;
            case 2:
                year=String.format("%04d",Integer.valueOf(year)-1);
                startTime=year+"-"+"01"+"-01"+"T"+"00:00:00";
                stopTime=year+"-"+"12"+"-"+"31"+"T"+"23:59:59";
                break;
        }
        //debug output
        System.out.println(typeReport+" "+startTime+" "+stopTime);

        for(int roomid:roomlist) {
            roomStatisList.add(managerSerivce.queryRoom(roomid,startTime,stopTime));
        }

        return roomStatisList;
    }



}
