package app.controller;


import app.dto.RoomStatis;
import app.service.ManagerService;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private ManagerService managerserivce;


    @GetMapping("/queryreport")
    public ArrayList<RoomStatis> QueryReport(HttpServletRequest request) {
        List<Integer> roomlist=(List<Integer>)JSON.parse(request.getParameter("list_Roomid"));
        ArrayList<RoomStatis> roomStatisList=new ArrayList<RoomStatis>();
        Integer typeReport=Integer.valueOf(request.getParameter("type_Report"));
        String startTime=;
        String stopTime=;
        for(int roomid:roomlist) {
            roomStatisList.add(managerserivce.queryRoom(roomid,startTime,stopTime));
        }

        return roomStatisList;
    }



}
