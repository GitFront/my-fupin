var
  LEVELS = {
    PROVINCE: 'province',
    CITY: 'city',
    COUNTY: 'county',
    TOWN: 'town',
    COUNTRY:"country"
  },
  TAB_NAMES = {
    CUNLICUNWAI: 'cunlicunwai',
    ZHIPINYUANYIN: 'zhipinyuanyin',
    PINKUNSHUXING: 'pinkunshuxing',
    LAOLIQINGKUANG: 'laoliqingkuang',
    JIANKANGQINGKUANG: 'jiankangqingkuang',
    ZHUFANGQINGKUANG: 'zhufangqingkuang',
    JIAOYUWENHUA: 'jiaoyuwenhua',
    YILIAOSHEBAO: 'yiliaoshebao',
    ZHENGZHIMIANMAO: 'zhengzhimianmao'
  },
  RANK_ARR=[],   //左侧贫困指数排名数组（取前3个字）
  AREA_LEVEL="province",  //贫困分析用的区域level
  AREA_NOW="guangdong", //贫困分析用的当前区域
  DUTY_LEVEL="province",//责任监控用的区域level
  DUTY_NOW="guangdong",//责任监控用的当前区域
  DUTY_JianPinTable_ID="",  //责任监控-减贫计划表格ID
  DUTY_OrganizationTable_ID="",//责任监控-帮扶单位表格ID


  ajax = $.ajax,

  $leftRank= $('#leftRank');  //贫困分析左侧贫困户数排名
  $centerIndex = $('#centerIndex'); //贫困分析中顶部数据
  $echartDiv = $('#echartDiv'); //贫困分析右侧图表


function renderLocation(level, areaName) {
  ajax({
    url:'../data/' + level + '/' + areaName + '/normal.json',
    dataType:'json',
    success:function(data){
      var code = data.code,
        d = data.data;
      if(code == 0 && d){
        d.level = level;
        d.LEVELS = LEVELS;
       $leftRank.html(template('tplLeftRank', d));
        $centerIndex.html(template('tplCenterIndex', d));
        renderRank();
        RANK_ARR=[];
        for(var i=0;i<d.chart_config_top_poor.list.length;i++){
          var _result=d.chart_config_top_poor.list[i].name.substr(0,3);
          RANK_ARR.push(_result);
          console.log(RANK_ARR);
        }
      }
    }
  });

}


function renderTabCommon(level, areaName, tabName) {
  ajax({
    url:'../data/' + level + '/' + areaName + '/' + tabName + '.json',
    dataType:'json',
    success:function(data){
      var code = data.code,
        d = data.data;
      if(code == 0 && d){
        if(d.chart_config_tab.length==1){
         $("#echart2").addClass('hide');
         $("#echart1").addClass('OneChart');
        echarts.init(document.getElementById('echart1')).setOption(d.chart_config_tab[0]);
        }
        else{
          $("#echart1").removeClass('OneChart');
         $("#echart2").removeClass('hide');

           echarts.init(document.getElementById('echart1')).setOption(d.chart_config_tab[0]);
           echarts.init(document.getElementById('echart2')).setOption(d.chart_config_tab[1]);

        }
      }
    },
  });
}

/*再次渲染左侧排名*/
function renderRank(){
jQuery('.skillbar').each(function(){
    jQuery(this).find('.skillbar-bar').animate({
      width:jQuery(this).attr('data-percent')
    },2000);
  });
}

//切换贫困分析指标图表高度
function tabChartH1(){
  $("#echart2").addClass('hide');
  $("#poorMainRight").addClass('one-chart');
}
function tabChartH2(){
  $("#echart2").removeClass('hide');
  $("#poorMainRight").removeClass('one-chart');
}














