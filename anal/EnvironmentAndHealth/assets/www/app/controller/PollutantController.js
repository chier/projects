/**
 * Created by Administrator on 2016/9/27.
 */
/**
 * 主页显示的业务类
 */
Ext.define('Sencha.controller.PollutantController', {
    extend : 'Sencha.controller.BaseController',
    config : {
        refs : {
            // TopTimeBarButton : '#topTimeBar_button',
            // ButtomTimeButn : '#buttomTimeButn'

        },
        control : {
            // TopTimeBarButton : {
            //     initialize : 'getYearsInit'
            // }
        },
        pilots:[],//存储试点选中数据
        sampletypes:[],//存储样品类型选中的数据
        sampletypesStr:[], // 存储样品类型选中的数据，存储的是名称
        //detect:[]       //存储污染物选中的数据
        detect:'',      //存储污染物选中的数据，暂时先做一个一维的数据
        algorithm:'count'    //存储污染物的统计算法
    },
    /**
     * 初始化后的逻辑, 在init 方法后调用
     */
    launch: function () {
        // 获取年份时间，并且初始化
        // this.getYearsInit();
        // this.getTimeInit();
        //  this.totalMoneyIndex(globalTimeScope, globalTimeNumber, 0, globalIndexLineBtn);
        console.info("PollutantController launch");

    },
    /**
     * 初始化方案，在 launch 之法前调用
     */
    init : function() {
        console.info("PollutantController init");
    },
    // 初始化数据
    initList : function() {
        // 初始化数据存储
        Global.PollutantController.config.pilots = [];
        Global.PollutantController.config.sampletypes = [];
        Global.PollutantController.config.sampletypesStr = [];
        Global.PollutantController.config.detect = '';
        Global.PollutantController.config.algorithm = 'count';

        // 删除样品类别
        Ext.getCmp("sampleTypePanel").items.each(function(item) {
            Ext.getCmp("sampleTypePanel").remove(item);
        });
        // 删除污染物
        Ext.getCmp("pollutantButtonPanelId").items.each(function(item) {
            Ext.getCmp("pollutantButtonPanelId").remove(item);
        });


        console.info("Global.PollutantController.initList();");
        Global.PollutantController.ajaxGetPilots();
    },
    // 初始化试点信息
    ajaxGetPilots:function(){
        console.info("Global.PollutantController.initGetPilots();");
        var _year = Global.currentYears;
        var the_param = '{"op":"Pollutant.getPilots","source_id":"'
            + Global.SourceId + '","view_id":"' + Global.ViewId
            + '","data":{"years":' + Global.currentYears + '}}';
        Ext.data.JsonP.request({
            url : Global.URL,
            callbackKey : 'callback',
            type : "POST",
            params : {
                tt_requestbody : the_param,
                format : 'json'
            },
            callback : function(success, result) {
                //console.info(success);
                //console.info(result);
                if (!result || result.code != 0) {
                    console.log(result.msg);
                } else {
                    Global.PollutantController.doSetPilots(result);
                }
            }
        });

        //Global.CaseInfoController.onDetailList();
        //Global.CaseInfoController.onDetailChart();
    },
    doSetPilots:function(result) {
        console.info("Global.PollutantController.doSetPilots();");
        Ext.getCmp("buttomPilotButn").items.each(function(item) {
            Ext.getCmp("buttomPilotButn").remove(item);
        });
        var _cls = "x-button-pressed";
        var _isPressed = true;
        for (var i =0; i < result.data.length; i++) {
            if(i != 0){
                _isPressed = false;
                _cls = "";
            }else{
                Global.PollutantController.config.pilots.push(result.data[i].statunitcode);
            }
            Ext.getCmp("buttomPilotButn").insert(Ext.getCmp("buttomPilotButn").items.length, {
                _code : result.data[i].statunitcode,
                text: result.data[i].shartName,
                cls:_cls,
                pressed: false
            });
        }
        Global.PollutantController.ajaxGetSampletype();
    },

    // 请求获取样品类型
    ajaxGetSampletype:function(){
        console.info("Global.PollutantController.ajaxGetSampletype();");
        var _year = Global.currentYears;
//		var dataArr = []; // 将返回的数据封装成数组后，再渲染成布局
        var the_param = '{"op":"Pollutant.getSampletype","source_id":"'
            + Global.SourceId + '","view_id":"' + Global.ViewId
            + '","data":{"years":' + Global.currentYears + ',"pilots":"' + Global.PollutantController.config.pilots.join(",") + '"}}';
        Ext.data.JsonP.request({
            url : Global.URL,
            callbackKey : 'callback',
            type : "POST",
            params : {
                tt_requestbody : the_param,
                format : 'json'
            },
            callback : function(success, result) {
                console.info(success);
                console.info(result);
                if (!result || result.code != 0) {
                    console.log(result.msg);
                } else {
                    Global.PollutantController.doSetSampletype(result);
                }
            }
        });
    },
    // 将样品类别数据解析成HTML
    doSetSampletype:function(result){
        console.info(" Global.PollutantController.doSetSampletype(result);");
        //sampleTypePanel
        Ext.getCmp("sampleTypePanel").items.each(function(item) {
            Ext.getCmp("sampleTypePanel").remove(item);
        });

        var _barNum;
        var _isPressed = true;
        var _cls = "btn_con3 x-button-pressing";

        for (var i =0; i < result.data.length; i++) {
            if(i != 0){
                _isPressed = false;
                _cls = "btn_con3";
            }else{
                Global.PollutantController.config.sampletypes.push(result.data[i].FID);
                Global.PollutantController.config.sampletypesStr.push(result.data[i].SAMPLETYPE);

            }

            _barNum = (i + 1) % 7 == 0 ? 1 : (i + 1) % 7;

            Ext.getCmp("sampleTypePanel").insert(Ext.getCmp("buttomPilotButn").items.length, {
                xtype : 'button',
                _code : result.data[i].FID,
                _codeName:result.data[i].SAMPLETYPE,
                width : '100%',
                cls : _cls,
                // pressed : isPressed,
                // id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
                html : '<em class="square_dept'+_barNum+'"></em><div style="position:absolute;left:44px;top:10px;">'+result.data[i].SAMPLETYPE+'</div>',
                //text: result.data[i].SAMPLETYPE,
                _myPressed: _isPressed,
                //xtype: 'button',
                //width: '100%',
                //cls: _btnClass,
                //pressed: isPressed,
                //id: 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
                //html: '<em class="square_dept' + _barNum + '"></em><div style="position:absolute;left:44px;top:10px;">' + indexDeptStr(_data.pname) + '：' + _data.ratios + '%</div>',
                handler: function (button) {
                    console.info(button);
                    if(button.config._myPressed == false){ // 当 false时，表示按钮是非按下状态，改成按下状态，存储样品类别数据
                        button.setCls("btn_con3 x-button-pressing");
                        button.config._myPressed = true;

                        Global.PollutantController.config.sampletypes.push(button.config._code);
                        Global.PollutantController.config.sampletypesStr.push(button.config._codeName);
                        //console.info(true);
                        //Global.PollutantController.pilots.push(button.getText());
                    }else{ // 当== true时，表示该按钮是按下状态，取消按下状态，并删除存储的样品类别数据
                        button.setCls("btn_con3");
                        button.config._myPressed = false;


                        var _arr = Global.PollutantController.config.sampletypes;
                        for(var i= 0;i<_arr.length;i++){
                            if(button.config._code == _arr[i]){
                                _arr.splice(i,1);
                                Global.PollutantController.config.sampletypesStr.splice(i,1);
                            }
                        }
                        Global.PollutantController.config.sampletypes = _arr;

                        //console.info(false);
                    }

                    var _arr = Global.PollutantController.config.sampletypes;
                    for(var i=0;i<_arr.length;i++){
                    	console.info(_arr[i]);
                    }
                    Global.PollutantController.ajaxGetDetect();

                }
                //toggle: function(container, button, pressed){
                //    alert("toggle");
                //    console.info("User toggled the '" + button.getText() + "' button: " + (pressed ? 'on' : 'off'));
                //}
            });
        }
        Global.PollutantController.ajaxGetDetect();
    },
    // 请求污染物类别数据
    ajaxGetDetect:function(){
        console.info("Global.PollutantController.ajaxGetDetect();");
        var _year = Global.currentYears;
//		var dataArr = []; // 将返回的数据封装成数组后，再渲染成布局
        var the_param = '{"op":"Pollutant.getDetect","source_id":"'
            + Global.SourceId + '","view_id":"' + Global.ViewId
            + '","data":{"years":' + Global.currentYears + ',"sampletypes":"'
                + Global.PollutantController.config.sampletypes.join(",") +'"}}';
        Ext.data.JsonP.request({
            url : Global.URL,
            callbackKey : 'callback',
            type : "POST",
            params : {
                tt_requestbody : the_param,
                format : 'json'
            },
            callback : function(success, result) {
                console.info(success);
                console.info(result);
                if (!result || result.code != 0) {
                    console.log(result.msg);
                } else {
                    Global.PollutantController.doSetDetect(result);
                }
            }
        });
    },
    // 污染物根据数据解析成HTML代码
    doSetDetect:function(result){
        console.info("Global.PollutantController.doSetDetect();");

        Ext.getCmp("pollutantButtonPanelId").items.each(function(item) {
            Ext.getCmp("pollutantButtonPanelId").remove(item);
        });

        var _barNum;
        var _isPressed = true;
        var _cls = "c-button-pressing";

        for (var i =0; i < result.data.length; i++) {
            if(i != 0){
                _isPressed = false;
                _cls = "";
            }else{
                //Global.PollutantController.config.detect.push(result.data[i].DETECTINDEX);
                Global.PollutantController.config.detect = result.data[i].DETECTINDEX;
            }
            _barNum = (i + 1) % 7 == 0 ? 1 : (i + 1) % 7;

            Ext.getCmp("pollutantButtonPanelId").insert(Ext.getCmp("pollutantButtonPanelId").items.length, {
                xtype : 'button',
                _code : result.data[i].DETECTINDEX,
                width : '100%',
                cls : _cls,
                height : 50,
                //cls : 'btn_con3',
                style:'border-radius:0;',
                // pressed : isPressed,
                // id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
                //html : '<em class="square_dept'+_barNum+'"></em><div style="position:absolute;left:44px;top:10px;">'+result.data[i].DETECTINDEX+'</div>',
                html:'<span  class="num_w1" style="position:absolute;left:20px;" >'+result.data[i].DETECTINDEX+'</span><div style="position:absolute;left:318px;">'+
                    result.data[i].UNITS+'</div>' +'<span class="pro_nr"></span>',
                _myPressed: _isPressed,
                pressed:true,
                handler: function (button) {
                    console.info(button);

                    Ext.getCmp("pollutantButtonPanelId").items.each(function(item) {
                        console.info(item);
                        if(button.config._code != item.config._code){
                            if(item.config._myPressed==true){
                                item.config._myPressed = false;
                                item.setCls("");
                            }
                        }
                    });

                    if(button.config._myPressed == false){ // 当 false时，表示按钮是非按下状态，改成按下状态，存储样品类别数据
                        button.setCls("c-button-pressing");
                        button.config._myPressed = true;

                        //Global.PollutantController.config.detect.push(button.config._code);
                        Global.PollutantController.config.detect = button.config._code;

                        //Global.PollutantController.pilots.push(button.getText());
                    }

                    //if(button.config._myPressed == false){ // 当 false时，表示按钮是非按下状态，改成按下状态，存储样品类别数据
                    //    button.setCls("c-button-pressing");
                    //    button.config._myPressed = true;
                    //
                    //    //Global.PollutantController.config.detect.push(button.config._code);
                    //    Global.PollutantController.config.detect = button.config._code;
                    //
                    //    //console.info(true);
                    //
                    //    //Global.PollutantController.pilots.push(button.getText());
                    //}else{ // 当== true时，表示该按钮是按下状态，取消按下状态，并删除存储的样品类别数据
                    //    button.setCls("");
                    //    button.config._myPressed = false;



                        //var _arr = Global.PollutantController.config.detect;
                        //for(var i= 0;i<_arr.length;i++){
                        //    if(button.config._code == _arr[i]){
                        //        _arr.splice(i,1);
                        //    }
                        //}
                        //Global.PollutantController.config.detect = _arr;
                    //}
                    Global.PollutantController.ajaxGetDetectData();
                }
            });
        }
        Global.PollutantController.ajaxGetDetectData();
    },
    // 请求污染物详细数据
    ajaxGetDetectData:function(){
        console.info("Global.PollutantController.ajaxGetDetectData();");
        var _year = Global.currentYears;
//		var dataArr = []; // 将返回的数据封装成数组后，再渲染成布局
        var the_param = '{"op":"Pollutant.getDetectData","source_id":"'
            + Global.SourceId + '","view_id":"' + Global.ViewId
            + '","data":{"years":' + Global.currentYears + ',"sampletypes":"'
            + Global.PollutantController.config.sampletypesStr.join(",") +'","detectIndex":"'
            + Global.PollutantController.config.detect +  '","algorithm":"'+
            Global.PollutantController.config.algorithm+'","pilots":"' + Global.PollutantController.config.pilots.join(",") + '"}}';
        Ext.data.JsonP.request({
            url : Global.URL,
            callbackKey : 'callback',
            type : "POST",
            params : {
                tt_requestbody : the_param,
                format : 'json'
            },
            callback : function(success, result) {
                console.info(success);
                console.info(result);
                if (!result || result.code != 0) {
                    console.log(result.msg);
                } else {
                     Global.PollutantController.doSetDetectChartsData(result);
                    Global.PollutantController.doSetDetectTableData(result);
                }
            }
        });
    },
    doSetDetectChartsData:function(result){
        //    result.data.chartsList

        //var storeData = Ext.getStore("PollutantTableStore");
        //storeData.setData(result.data.tableList);

        var store = Ext.getStore("PollutantChartStore");
        store.removeAll();
        store.setData(result.data.chartsList);
        store.insert(0,{"DETECTINDEX":"","SAMPLETYPE":"","TESTRESULTS":"","SURVEYYEAR":""});
        store.insert(result.data.chartsList.length + 1,{"DETECTINDEX":"","SAMPLETYPE":"","TESTRESULTS":"","SURVEYYEAR":""});


        //var storeData = Ext.getStore("PollutantChartStore");
        //storeData.setData(result.data.chartsList);

        //Ext.getCmp("pollutantLineChart_1").setStore(store);
    },
    doSetDetectTableData:function(result) {


        //    result.data.tableList

        var storeData = Ext.getStore("PollutantTableStore");
        storeData.setData(result.data.tableList);

        //Ext.getCmp("pollutantDataList").setStore(storeData);
    }
});
