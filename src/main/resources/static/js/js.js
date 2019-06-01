//Test
/*$(function () {
   $('#testid').click(function () {
       $('#testid').text(23.5)
   });
});*/

//The function of Login
/*
$(function () {
    $('#login').click(function () {
        var user = $("#user").val();
        var password = $("#password").val();

        if(user == "" || password == ""){
            alert("用户名和密码不能为空");
        }
        else{
            var data = {
                "username": user,
                "password": password
            };

            $.ajax({
                url: "http://localhost:8080/login",
                type: "GET",
                dataType: "json",
                data: data,
                global:"false"
            });
        }
    });
});*/


//The function of admin.html
$(function () {
    $('#saveParamsSection').hide();
    $('#startAirSys').hide();
    $('#roomSelectSection').hide();
});

var roomTimer;
function roomTimerFunction(str){
    $.ajax({
        url: "/admin/roomState/" + str,
        type: "GET",
        global: "false",
        dataType: "json",
        data: {},
        success: function (data) {
            $('#roomState').show();
            $('#roomSelect').hide();
            if(!data['powerOn']){
                $('#roomPowerOn').text("OFF");
            }
            else{
                $('#roomPowerOn').text("ON");
            }
            if(!data['inService']){
                $('#roomServer').text("OFF");
            }
            else{
                $('#roomServer').text("ON");
            }
            $('#roomCurTemperature').text(Math.round(data['nowTemp']));
            $('#roomTarTemperature').text(Math.round(data['tarTemp']));
            $('#roomSpeed').text(data['funSpeed']);
            $('#roomFeeRate').text(data['feeRate']);
            $('#roomTotalFee').text(data['totalFee'].toFixed(2));

            var runningTime = data['runningTime'];
            var hour = Math.floor(runningTime / 3600);
            runningTime = runningTime % 3600;
            var min = Math.floor(runningTime / 60);
            min = (Array(2).join('0') + min).slice(-2);
            var sec = runningTime % 60;
            sec = (Array(2).join('0') + sec).slice(-2);
            runningTime = hour.toString() + "h " + min.toString() + "m " + sec.toString() + "s";
            $('#roomRunningTime').text(runningTime);
        },
        error: function () {
            alert("查询失败！");
        }
    });
}

$(function () {
    //Control power on
    $('#powerOn').click(function () {
        $.ajax({
            url: "/admin/powerOn",
            type: "GET",
            global: "false",
            success: function () {
                $('#powerOnSection').hide();
                $('#saveParamsSection').show();
                alert("请填写参数");
            },
            error: function () {
                alert("请重试！");
            }
        })
    });

    //Save parameters
    $('#saveParams').click(function () {
        var isCooling = $('#isCooling').val();
        var tempHighLimit = $("#tempHighLimit").val();
        var tempLowLimit = $("#tempLowLimit").val();
        var defaultTargetTemp = $("#defaultTargetTemp").val();
        var feeRateHigh = $("#feeRateHigh").val();
        var feeRateMiddle = $("#feeRateMiddle").val();
        var feeRateLow = $("#feeRateLow").val();
        var defaultFanSpeed = $("#defaultFanSpeed").val();

        var isPositiveInt = /^[0-9]*[1-9][0-9]*$/;
        var isPositiveFloat = /^\\d+(\\.\\d+)?$/;

        $.ajax({
            url: "/admin/setParams",
            type: "POST",
            global: "false",
            dataType: 'json',
            data: {
                "isCooling": Boolean(isCooling),
                "tempHighLimit": parseInt(tempHighLimit),
                "tempLowLimit": parseInt(tempLowLimit),
                "defaultTargetTemp": parseInt(defaultTargetTemp),
                "feeRateHigh": parseFloat(feeRateHigh),
                "feeRateMiddle": parseFloat(feeRateMiddle),
                "feeRateLow": parseFloat(feeRateLow),
                "defaultFunSpeed": defaultFanSpeed
            },
            success: function () {
                $("#saveParams").hide();
                $("#resetParams").hide();
                $("#startAirSys").show();
                alert("保存成功，请启动")
            },
            error: function () {
                alert("保存失败！");
            }
        })
    });
    //启动系统
    $("#startAirSys").click(function () {
        $.ajax({
            url: "/admin/startup",
            type: "GET",
            global: "false",
            success: function () {
                $('#saveParamsSection').hide();
                $('#roomSelectSection').show();
                $('#roomState').hide();
            },
            error: function () {
                alert("启动失败！");
            }
        });
    });

    //监视房间
    $('#room1').click(function () {
        $('#monitorRoomID').text("一");
        clearInterval(roomTimer);
        roomTimer = setInterval(function () {
            roomTimerFunction("1");
        }, 1000);
    });

    $('#room2').click(function () {
        $('#monitorRoomID').text("二");
        clearInterval(roomTimer);
        roomTimer = setInterval(function () {
            roomTimerFunction("2");
        }, 1000);
    });
    $('#room3').click(function () {
        $('#monitorRoomID').text("三");
        clearInterval(roomTimer);
        roomTimer = setInterval(function () {
            roomTimerFunction("3");
        }, 1000);
    });

    $('#room4').click(function () {
        $('#monitorRoomID').text("四");
        clearInterval(roomTimer);
        roomTimer = setInterval(function () {
            roomTimerFunction("4");
        }, 1000);
    });
});

//The function of Receptionist
$(function () {
    $('#checkInPage').hide();
    $('#showCustomerAccount').hide();
    $('#createRDRPage').hide();
    $('#RDRInformationPage').hide();
    $('#createInvoicePage').hide();
    $('#InvoiceInformationPage').hide();
    $('#checkOutPage').hide();

    //用户入住
    $('#checkin').click(function () {
        $('#showCustomerAccount').hide();
        $('#createRDRPage').hide();
        $('#RDRInformationPage').hide();
        $('#createInvoicePage').hide();
        $('#InvoiceInformationPage').hide();
        $('#checkOutPage').hide();
        $('#checkInPage').show();
        $('#receptionistInit').hide();
        $('.addCustomer').css('display', 'block');
        $('#addCustomer').fadeIn();
    });

    $('#saveIdentifier').click(function () {
        var identifier = $('#identifier').val();
        if(identifier == ""){
            alert("身份证号不能为空！");
        }
        else {
            $.ajax({
                url: "/receptionist/adduser",
                type: "POST",
                global: "false",
                dataType: 'json',
                data: {'idnumber': identifier},
                success: function (data) {
                    if(data['userid'] == -1){
                        alert("请求房间失败");
                        $('.addCustomer').css('display', 'none');
                        $('#addCustomer').fadeOut();
                        $('#checkInPage').hide();
                        $('#receptionistInit').show();
                    }
                    else {
                        $('.addCustomer').css('display', 'none');
                        $('#addCustomer').fadeOut();
                        $('#checkInPage').hide();
                        $('#showCustomerAccount').show();
                        $('#checkin_userID').text(data['userid']);
                        $('#checkin_userName').text(data['username']);
                        $('#checkin_userPassword').text(data['password']);
                        $('#checkin_roomID').text(data['roomid']);
                    }
                },
                error: function () {
                    $('.addCustomer').css('display', 'none');
                    $('#addCustomer').fadeOut();
                    $('#checkInPage').hide();
                    //$('#showCustomerAccount').show();
                     $('#receptionistInit').show();
                    alert("房间已满");
                }
            });
        }

    });
    $('#cancelIdentifier').click(function () {
        $('#showCustomerAccount').hide();
        $('#createRDRPage').hide();
        $('#RDRInformationPage').hide();
        $('#createInvoicePage').hide();
        $('#InvoiceInformationPage').hide();
        $('#checkOutPage').hide();
        $('#checkInPage').hide();
        $('#receptionistInit').show();
        $('.addCustomer').css('display', 'none');
        $('#addCustomer').fadeOut();
    });

    //查询详单
    $('#createRDR').click(function () {
        $('#receptionistInit').hide();
        $('#checkInPage').hide();
        $('#showCustomerAccount').hide();
        $('#createRDRPage').show();
        $('#RDRInformationPage').hide();
        $('#createInvoicePage').hide();
        $('#InvoiceInformationPage').hide();
        $('#checkOutPage').hide();
    });
    //请求展示详单
    $('#requstRDR').click(function () {
        var roomID = $("#RDR_roomID").val();
        var starttime = $('#RDR_starttime').val();
        var stoptime = $('#RDR_stoptime').val();
        $.ajax({
            url: "/receptionist/createrdr",
            type: "GET",
            global: "false",
            dataType: 'json',
            data: {'roomid': roomID, 'starttime':starttime, 'stoptime':stoptime},
            success: function (data) {
                $('#createRDRPage').hide();
                $('#RDRInformationPage').show();
                 
                var tab = document.getElementById("RDRtable");
   
                $("#RDRtable").empty("");
                var tr0 = document.createElement("tr");
                 var td01 = document.createElement("td");
                 tr0 = document.createElement("tr");
                 td01 = document.createElement("td");
                 td01.innerHTML="房间"+$("#RDR_roomID").val()+"的详单";
                 
                  tr0.appendChild(td01);
                  tab.appendChild(tr0);

                td01.innerHTML = "详单ID";
                tr0.appendChild(td01);

                         var td02 = document.createElement("td");
                         td02.innerHTML = "开始时间";
                         tr0.appendChild(td02);

                         var td03 = document.createElement("td");
                         td03.innerHTML = "停止时间";
                         tr0.appendChild(td03);

                         var td04 = document.createElement("td");
                         td04.innerHTML = "房间号";
                         tr0.appendChild(td04);

                         var td05 = document.createElement("td");
                         td05.innerHTML = '风速';
                         tr0.appendChild(td05);

                         var td06 = document.createElement("td");
                         td06.innerHTML = '费率';
                         tr0.appendChild(td06);

                         var td07 = document.createElement("td");
                         td07.innerHTML = "总费用";
                         tr0.appendChild(td07);

                         tab.appendChild(tr0);
                for(var i=0;i<data.serviceDetailList.length;i++)
                    {
                       
                         var tr = document.createElement("tr");
                         var td1 = document.createElement("td");
                         td1.innerHTML = data.serviceDetailList[i]['servicedetailid'];
                         tr.appendChild(td1);

                         var td2 = document.createElement("td");
                         td2.innerHTML = timeFormatTrans(data.serviceDetailList[i]['starttime']);
                         tr.appendChild(td2);

                         var td3 = document.createElement("td");
                         td3.innerHTML =timeFormatTrans(data.serviceDetailList[i]['stoptime']) ;
                         tr.appendChild(td3);

                         var td4 = document.createElement("td");
                         td4.innerHTML = data.serviceDetailList[i]['roomid'];
                         tr.appendChild(td4);

                         var td5 = document.createElement("td");
                         td5.innerHTML = data.serviceDetailList[i]['funspeed'];
                         tr.appendChild(td5);

                         var td6 = document.createElement("td");
                         td6.innerHTML = data.serviceDetailList[i]['feerate'];
                         tr.appendChild(td6);

                         var td7 = document.createElement("td");
                         td7.innerHTML = data.serviceDetailList[i]['fee'];
                         tr.appendChild(td7);

                         tab.appendChild(tr)
                    
                    }
             
                /*
                $('#RDRInfo_ID').text(data['servicedetailid']);
                $('#RDRInfo_starttime').text(data['starttime']);
                $('#RDRInfo_stoptime').text(data['stoptime']);
                $('#RDRInfo_roomID').text(data['roomid']);
                $('#RDRInfo_roomSpeed').text(data['fanspeed']);
                $('#RDRInfo_roomFeeRate').text(data['feerate']);
                $('#RDRInfo_roomTotalFee').text(data['fee']);
                 */

            },
            error: function () {
                alert("请求失败！");
            }
        });
    });

    //请求账单
    $('#createInvoice').click(function () {
        $('#receptionistInit').hide();
        $('#checkInPage').hide();
        $('#showCustomerAccount').hide();
        $('#createRDRPage').hide();
        $('#RDRInformationPage').hide();
        $('#createInvoicePage').show();
        $('#InvoiceInformationPage').hide();
        $('#checkOutPage').hide();
    });

    //请求展示账单
    $('#requstInvoice').click(function () {
        var roomID = $("#Invoice_roomID").val();
        var starttime = $('#Invoice_starttime').val();
        var stoptime = $('#Invoice_stoptime').val();
        $.ajax({
            url: "/receptionist/createinvoice",
            type: "GET",
            global: "false",
            dataType: 'json',
            data: {'roomid': roomID, 'starttime':starttime, 'stoptime':stoptime},
            success: function (data) {
                $('#createInvoicePage').hide();
                $('#InvoiceInformationPage').show();
                $('#InvoiceInfo_ID').text(data['billid']);
                $('#InvoiceInfo_starttime').text(timeFormatTrans(data['starttime']));
                $('#InvoiceInfo_stoptime').text(timeFormatTrans(data['stoptime']));
                $('#InvoiceInfo_roomID').text(data['roomid']);
                $('#InvoiceInfo_userID').text(data['userid']);
                $('#InvoiceInfo_roomTotalFee').text(data['totalfee']);
                $('#InvoiceInfo_runningTime').text(data['runningtime']);
                $('#InvoiceInfo_scheduleCounter').text(data['schedulecounter']);
                $('#InvoiceInfo_detailedRecordCounter').text(data['detailedrecordcounter']);
                $('#InvoiceInfo_powerOnCounter').text(data['poweroncounter']);
                $('#InvoiceInfo_changeTempCounter').text(data['changetempcounter']);
                $('#InvoiceInfo_changeFanCounter').text(data['changefuncounter']);
            },
            error: function () {
                alert("请求失败！");
            }
        });
    });

    //退房
    $('#checkout').click(function () {
        $('#receptionistInit').hide();
        $('#checkInPage').hide();
        $('#showCustomerAccount').hide();
        $('#createRDRPage').hide();
        $('#RDRInformationPage').hide();
        $('#createInvoicePage').hide();
        $('#InvoiceInformationPage').hide();
        $('#checkOutPage').show();
        $('.addCustomer').css('display', 'block');
        $('#addCustomer').fadeIn();
    });

    //请求房间号
    $('#saveCheckOutRoomID').click(function () {
        var roomID = $("#checkOut_RoomID").val();
        roomID = parseInt(roomID);

        $.ajax({
            url: "/receptionist/checkout",
            type: "POST",
            global: "false",
            dataType: 'json',
            data: {'roomid': roomID},
            success: function (data) {
                if(data['statue']=="Error"){
                     alert("退房失败！");
                 }
                 else
                 {
                       alert("成功退房");
                 }
                $('#checkOutPage').hide();
                $('#receptionistInit').show();
                $('.addCustomer').css('display', 'none');
                $('#addCustomer').fadeOut();
             
            },
            error: function () {
                $('#checkOutPage').hide();
                $('#receptionistInit').show();
                $('.addCustomer').css('display', 'none');
                $('#addCustomer').fadeOut();
                alert("退房失败");
            }
        });
    });
    $('#cancelCheckOutRoomID').click(function () {
        $('#receptionistInit').show();
        $('#checkInPage').hide();
        $('#showCustomerAccount').hide();
        $('#createRDRPage').hide();
        $('#RDRInformationPage').hide();
        $('#createInvoicePage').hide();
        $('#InvoiceInformationPage').hide();
        $('#checkOutPage').hide();
        $('.addCustomer').css('display', 'none');
        $('#addCustomer').fadeOut();
    });

});

function timeFormatTrans(str){
    var words = str.split("T");
    var words1 = words[1].split(".");
    return words[0]+" "+words1[0];
}

//The function of manager
$(function () {
    $('#createReportPage').hide();
    $('#ReportInformationPage').hide();

    $('#queryReport').click(function () {
        $('#managerInit').hide();
        $('#ReportInformationPage').hide();
        $('#createReportPage').show();
    });

    $('#requestReport').click(function () {
        var roomid1 = parseInt($('#Report_roomID1').val());
        var reportType = parseInt($('#Report_type').val());
        var reportYear = parseInt($('#Report_year').val());
        var reportMonth = parseInt($('#Report_month').val());
        var reportDay = parseInt($('#Report_day').val());

        $.ajax({
            url: "/manager/queryreport",
            type: "GET",
            global: "false",
            dataType: "json",
            data: {
                "list_Roomid": roomid1,
                "type_Report": reportType,
                "year":reportYear,
                "month": reportMonth,
                "day": reportDay
            },
            success: function (data) {
                $('#createReportPage').hide();
                $('#ReportInformationPage').show();
                var tab=document.getElementById("reporttables");
                $("#reporttables").empty("");
                var tr=document.createElement("tr");
                var td=document.createElement("th");
                //tr.setAttribute("align","center");
                td.setAttribute("colspan",8);
                
                td.innerHTML=$('#Report_year').val()+"年"+$('#Report_month').val()+"月"+$('#Report_day').val() +"日 ";
                if($('#Report_type').val()=="0"){
                    td.innerHTML+="日报";
                }
                else if($('#Report_type').val()=="1"){
                    td.innerHTML+="周报";
                }
                else td.innerHTML+="年报";
                tr.appendChild(td);
                tab.appendChild(tr);

                tr=document.createElement("tr");
                 
                 td=document.createElement("td");
                 td.innerHTML = "房间号";
                 tr.appendChild(td);

                 td=document.createElement("td");
                 td.innerHTML = "总费用";
                 tr.appendChild(td);

                 td=document.createElement("td");
                 td.innerHTML = "运行时间";
                 tr.appendChild(td);

                 td=document.createElement("td");
                 td.innerHTML = "空调调度次数";
                 tr.appendChild(td);

                 td=document.createElement("td");
                 td.innerHTML = "详单请求次数";
                 tr.appendChild(td);

                 td=document.createElement("td");
                 td.innerHTML = "空调启动次数";
                 tr.appendChild(td);

                 td=document.createElement("td");
                 td.innerHTML = "温度调节次数";
                 tr.appendChild(td);

                 td=document.createElement("td");
                 td.innerHTML = "风速调节次数";
                 tr.appendChild(td);
                 tab.appendChild(tr);
                 for(var i=0;i<data.length;i++){
                    tr=document.createElement("tr");
                    
                    td=document.createElement("td");
                    td.innerHTML = data[i]['roomid']
                    tr.appendChild(td);

                    td=document.createElement("td");
                    td.innerHTML = data[i]['totalfee']
                    tr.appendChild(td);

                    td=document.createElement("td");
                    td.innerHTML = data[i]['runningtime']
                    tr.appendChild(td);

                    td=document.createElement("td");
                    td.innerHTML = data[i]['scheduleCounter']
                    tr.appendChild(td);

                    td=document.createElement("td");
                    td.innerHTML = data[i]['detailedRecordCounter']
                    tr.appendChild(td);

                    td=document.createElement("td");
                    td.innerHTML = data[i]['powerOnCounter']
                    tr.appendChild(td);

                    td=document.createElement("td");
                    td.innerHTML = data[i]['changetempcounter']
                    tr.appendChild(td);

                    td=document.createElement("td");
                    td.innerHTML = data[i]['changefuncounter']
                    tr.appendChild(td);
                    tab.appendChild(tr);
                 }
                /*
                $('#createReportPage').hide();
                $('#ReportInformationPage').show();
                $('#ReportInfo_roomID').text(data[0]['roomid']);
                $('#ReportInfo_roomTotalFee').text(data[0]['totalfee']);
                $('#ReportInfo_runningTime').text(data[0]['runningtime']);
                $('#ReportInfo_scheduleCounter').text(data[0]['scheduleCounter']);
                $('#ReportInfo_detailedRecordCounter').text(data[0]['detailedRecordCounter']);
                $('#ReportInfo_powerOnCounter').text(data[0]['powerOnCounter']);
                $('#ReportInfo_changeTempCounter').text(data[0]['changetempcounter']);
                $('#ReportInfo_changeFanCounter').text(data[0]['changefuncounter']);
                */

                alert("请求成功");
            },
            error: function () {
                alert("请求失败");
            }
        });
    });
});

var idTmr;

function getExplorer() {
    var explorer = window.navigator.userAgent;
    //ie
    if (explorer.indexOf("MSIE") >= 0) {
        return 'ie';
    }
    //firefox
    else if (explorer.indexOf("Firefox") >= 0) {
        return 'Firefox';
    }
    //Chrome
    else if (explorer.indexOf("Chrome") >= 0) {
        return 'Chrome';
    }
    //Opera
    else if (explorer.indexOf("Opera") >= 0) {
        return 'Opera';
    }
    //Safari
    else if (explorer.indexOf("Safari") >= 0) {
        return 'Safari';
    }
}

function method5(tableid) {
    if (getExplorer() == 'ie') {
        var curTbl = document.getElementById(tableid);
        var oXL = new ActiveXObject("Excel.Application");
        var oWB = oXL.Workbooks.Add();
        var xlsheet = oWB.Worksheets(1);
        var sel = document.body.createTextRange();
        sel.moveToElementText(curTbl);
        sel.select();
        sel.execCommand("Copy");
        xlsheet.Paste();
        oXL.Visible = true;

        try {
            var fname = oXL.Application.GetSaveAsFilename("Excel.xls",
                "Excel Spreadsheets (*.xls), *.xls");
            print("Nested catch caught " + e);
        } finally {
            oWB.SaveAs(fname);
            oWB.Close(savechanges = false);
            oXL.Quit();
            oXL = null;
            idTmr = window.setInterval("Cleanup();", 1);
        }

    } else {
        tableToExcel(tableid);
    }
}

function Cleanup() {
    window.clearInterval(idTmr);
    CollectGarbage();
}

var tableToExcel = (function () {
    var uri = 'data:application/vnd.ms-excel;base64,',
        template = '<html><head><meta charset="UTF-8"></head><body><table  border="1">{table}</table></body></html>',
        base64 = function (
            s) {
            return window.btoa(unescape(encodeURIComponent(s)))
        }, format = function (s, c) {
            return s.replace(/{(\w+)}/g, function (m, p) {
                return c[p];
            })
        };
    return function (table, name) {
        if (!table.nodeType)
            table = document.getElementById(table);
        var ctx = {
            worksheet: name || 'Worksheet',
            table: table.innerHTML
        };
        window.location.href = uri + base64(format(template, ctx))
    }
})();


//The function of customer
var customerTimer;
function refreshCustomerRoomSate(roomID){
    $.ajax({
        headers: {},
        url: "/customer/requestRoomState",
        type: "GET",
        dataType: "json",
        data: {"roomID": parseInt(roomID)},
        success: function (data) {
            if(!data['powerOn']){
                $('#customerRoomPowerOn').text("OFF");
            }
            else{
                $('#customerRoomPowerOn').text("ON");
            }
            if(!data['inService']){
                $('#customerRoomServer').text("OFF");
            }
            else{
                $('#customerRoomServer').text("ON");
            }
            $('#customerRoomCurTemperature').text(Math.round(data['nowTemp']));
            $('#customerRoomTarTemperature').text(Math.round(data['tarTemp']));
            $('#customerRoomSpeed').text(data['funSpeed']);
            $('#customerRoomFeeRate').text(data['feeRate']);
            $('#customerRoomTotalFee').text(data['totalFee'].toFixed(2));

            var runningTime = data['runningTime'];
            var hour = Math.floor(runningTime / 3600);
            runningTime = runningTime % 3600;
            var min = Math.floor(runningTime / 60);
            min = (Array(2).join('0') + min).slice(-2);
            var sec = runningTime % 60;
            sec = (Array(2).join('0') + sec).slice(-2);
            runningTime = hour.toString() + "h " + min.toString() + "m " + sec.toString() + "s";
            $('#customerRoomRunningTime').text(runningTime);
        },
        error: function () {
            alert("房间状态信息获取失败");
        }
    });
}

$(function () {
    $('#customerFunctionExe').hide();
    $('#saveCustomerRoomInitTemp').click(function () {
        var roomInitTemp = parseInt($('#customerRoomInitTemp').val());
        var curRoomID = window.location.href;
        curRoomID = curRoomID.charAt(curRoomID.length - 1);

        if(roomInitTemp > 30){
            alert("初始室温不能超过30度！");
        }
        else if(roomInitTemp < 16){
            alert("初始室温不能低于16度！");
        }
        else{
            $.ajax({
                headers: {},
                url: "/customer/setInitTemp",
                type: "POST",
                dataType: "json",
                data: {"roomID": parseInt(curRoomID), "initTemp": roomInitTemp},
                success: function (data) {
                    if(data['statue'] == "success"){
                        $('#customerFunctionInit').hide();
                        $('#customerFunctionExe').show();
                    }
                    else{
                        alert("设置失败");
                    }
                },
                error: function () {
                    alert("设置失败");
                }
            });
        }

    });
});

$(function () {
    $('#customerRoomStatePage').hide();
    $('#customerTemperaturePage').hide();
    $('#customerFanSpeedPage').hide();

    //Turn on the air-conditioner
    $('#customerRequestOn').click(function () {
        var curRoomID = window.location.href;
        curRoomID = curRoomID.charAt(curRoomID.length - 1);
        clearInterval(customerTimer);
        $.ajax({
            headers: {},
            url: "/customer/requestOn",
            type: "POST",
            dataType: "json",
            data: {"roomID": parseInt(curRoomID)},
            success: function (data) {
                if(data['statue'] == "success"){
                    alert("成功打开");
                }
                else{
                    alert("空调已打开");
                }
            },
            error: function () {
                alert("打开失败");
            }
        });
    });

    //Turn off the air-conditioner
    $('#customerRequestOff').click(function () {
        clearInterval(customerTimer);
        var curRoomID = window.location.href;
        curRoomID = curRoomID.charAt(curRoomID.length - 1);
        $.ajax({
            headers: {},
            url: "/customer/requestOff",
            type: "POST",
            dataType: "json",
            data: {"roomID": parseInt(curRoomID)},
            success: function (data) {
                if(data['statue'] == "success"){
                    alert("成功关闭");
                }
                else{
                    alert("空调已关闭");
                }
            },
            error: function () {
                alert("关闭失败");
            }
        });
    });

    //Show the room state
    $('#customerRequestRoomState').click(function () {
        clearInterval(customerTimer);
        var curRoomID = window.location.href;
        curRoomID = curRoomID.charAt(curRoomID.length - 1);
        $('#customerInit').hide();
        $('#customerRoomStatePage').show();
        $('#customerTemperaturePage').hide();
        $('#customerFanSpeedPage').hide();
        customerTimer = setInterval(function () {
            refreshCustomerRoomSate(curRoomID);
        }, 1000);
    });

    //Change the temperature
    $('#customerRequestChangingTemperature').click(function () {
        clearInterval(customerTimer);
        var curRoomID = window.location.href;
        curRoomID = curRoomID.charAt(curRoomID.length - 1);
        $.ajax({
            headers: {},
            url: "/customer/requestRoomState",
            type: "GET",
            dataType: "json",
            data: {"roomID": parseInt(curRoomID)},
            success: function (data) {
                $('#customerRoomStatePage').hide();
                $('#customerInit').hide();
                $('#customerTemperaturePage').show();
                $('#customerFanSpeedPage').hide();
                $('.addCustomer').css('display', 'block');
                $('#addCustomerTemp').fadeIn();
                $('#CustomerTemp').val(data['nowTemp']);
            },
            error: function () {
                alert("房间状态信息获取失败");
            }
        });
    });
    $('#minTemp').click(function () {
        var curTemp = parseInt($('#CustomerTemp').val());
        if(curTemp < 17){
            alert("这是最低温度了");
        }
        else{
            curTemp = curTemp - 1;
            $('#CustomerTemp').val(curTemp);
        }
    });
    $('#addTemp').click(function () {
        var curTemp = parseInt($('#CustomerTemp').val());
        if(curTemp > 28){
            alert("这是最高温度了");
        }
        else{
            curTemp = curTemp + 1;
            $('#CustomerTemp').val(curTemp);
        }
    });
    $('#cancelCustomerTemp').click(function () {
        $('#customerRoomStatePage').hide();
        $('#customerInit').show();
        $('#customerTemperaturePage').hide();
        $('#customerFanSpeedPage').hide();
    });
    $('#saveCustomerTemp').click(function () {
        var curRoomID = window.location.href;
        curRoomID = curRoomID.charAt(curRoomID.length - 1);
        var curTemp = parseInt($('#CustomerTemp').val());
        $.ajax({
            headers: {},
            url: "/customer/changeTargetTemp",
            type: "POST",
            dataType: "json",
            data: {"roomID": parseInt(curRoomID), "targetTemp": curTemp},
            success: function (data) {
                alert("调节温度成功");
                $('#customerRoomStatePage').hide();
                $('#customerInit').show();
                $('#customerTemperaturePage').hide();
                $('#customerFanSpeedPage').hide();
            },
            error: function () {
                alert("调节温度失败");
            }
        });
    });

    //Change fan speed
    $('#customerRequestChangingFanSpeed').click(function () {
        clearInterval(customerTimer);
        $('#customerRoomStatePage').hide();
        $('#customerInit').hide();
        $('#customerTemperaturePage').hide();
        $('#customerFanSpeedPage').show();
        $('.addCustomer').css('display', 'block');
        $('#addCustomerFSpeed').fadeIn();
    });

    $('#cancelCustomerFSpeed').click(function () {
        $('#customerRoomStatePage').hide();
        $('#customerInit').show();
        $('#customerTemperaturePage').hide();
        $('#customerFanSpeedPage').hide();
    });
    $('#saveCustomerFSpeed').click(function () {
        var curRoomID = window.location.href;
        curRoomID = curRoomID.charAt(curRoomID.length - 1);
        var curFSpeed = $('#selectedFSpeed').val();
        $.ajax({
            headers: {},
            url: "/customer/changeFanSpeed",
            type: "POST",
            dataType: "json",
            data: {"roomID": parseInt(curRoomID), "targetFanSpeed": curFSpeed},
            success: function () {
                alert("调节风速成功");
                $('#customerRoomStatePage').hide();
                $('#customerInit').show();
                $('#customerTemperaturePage').hide();
                $('#customerFanSpeedPage').hide();
            },
            error: function () {
                alert("调节风速失败");
            }
        });
    });
});