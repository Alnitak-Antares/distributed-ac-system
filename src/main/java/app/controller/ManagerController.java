package app.controller;


import app.entity.roomStatis;
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
    public List<roomStatis> QueryReport(HttpServletRequest request) {
        List<Integer> roomlist=(List<Integer>)JSON.parse(request.getParameter("roomlist"));
        List<roomStatis> roomStatisList=new List<roomStatis>();
        return roomStatisList;
    }



}
