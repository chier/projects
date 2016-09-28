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
        }
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
    }

});
