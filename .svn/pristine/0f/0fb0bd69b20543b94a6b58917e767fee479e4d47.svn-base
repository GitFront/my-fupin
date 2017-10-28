/*责任监控*/

//渲染帮扶详情

    //右侧选择地区模板
function renderCheckAreaLevel1(id) {
  ajax({
    url:'../data/duty/child_districts_list/' + id + '.json',
    dataType:'json',
    error:function(a,b){console.log(a,b)},
    success:function(data){
      var code = data.code,
        d = data.data;
      if(code == 0 && d){
        DUTY_LEVEL=d.level;
         $("#areaLevel1").html(template('tplCheckAreaLevel1', d));

         if(DUTY_LEVEL=="town"){
               renderAreaTopScrollBar("400px");
               $("#areaLevel2").addClass('hide');
          }
          else{
             renderAreaScrollBar("230px");
             $("#areaLevel2").removeClass('hide');
          }

          if(DUTY_LEVEL=="county"){
               renderAreaTopScrollBar("140px");
          }
      }
    }
  });
}

function renderCheckAreaLevel2(id) {
  ajax({
    url:'../data/duty/child_districts_list/' + id + '.json',
    dataType:'json',
    error:function(a,b){console.log(a,b)},
    success:function(data){
      var code = data.code,
        d = data.data;
      if(code == 0 && d){
         $("#areaLevel2").html(template('tplCheckAreaLevel2', d));
         
          if(DUTY_LEVEL=="province"){
               renderAreaScrollBar("250px");
          }
          else if(DUTY_LEVEL=="city"){
               renderAreaScrollBar("240px");
          }
           else if(DUTY_LEVEL=="county"){
               renderAreaScrollBar("230px");
          }

      }
    }
  });
}

function renderCheckAreaCommon(id) {
  ajax({
    url:'../data/duty/child_districts_list/' + id + '.json',
    dataType:'json',
    error:function(a,b){console.log(a,b)},
    success:function(data){
      var code = data.code,
        d = data.data;
      if(code == 0 && d){
         $("#areaLevel1").html(template('tplCheckAreaCommon', d));
      }
    }
  });
}

    //帮扶概况
function renderBangFuDetail(id) {
  ajax({
    url:'../data/duty/duty_intro/' + id + '.json',
    dataType:'json',
    error:function(a,b){console.log(a,b)},
    success:function(data){
      var code = data.code,
        d = data.data;
      if(code == 0 && d){
        d.table="jianpinPlan";
        DUTY_JianPinTable_ID=id;
         $("#bangfuDetail").html(template('tplBangFuDetail', d));
          renderJianPinPlanScrollBar($(".jianpinPlan_scroll_box"),"412px");
      }
    }
  });
}
    //帮扶单位
function renderBangFuOrganization(id) {
  ajax({
    url:'../data/duty/duty_organization_list/' + id + '.json',
    dataType:'json',
    error:function(a,b){console.log(a,b)},
    success:function(data){
      var code = data.code,
        d = data.data;
      if(code == 0 && d){
        d.table="bangFuOrganization";
          DUTY_OrganizationTable_ID=id;
         $("#bangfuDetail").html(template('tplBangFuDetail', d));
           renderJianPinPlanScrollBar($(".jianpinPlan_scroll_box"),"536px");
      }
    }
  });
}
    //帮扶责任人
function renderBangFuPeople(id){
  ajax({
    url:'../data/duty/duty_responsible_people_list/' + id + '.json',
    dataType:'json',
    error:function(a,b){console.log(a,b)},
    success:function(data){
      var code = data.code,
        d = data.data;
      if(code == 0 && d){
        d.table="bangFuPeople";
         $("#bangfuDetail").html(template('tplBangFuDetail', d));
           renderJianPinPlanScrollBar($(".jianpinPlan_scroll_box"),"536px");
      }
    }
  });
}  
    //单位帮扶村
function renderBangFuCountry(id){
  ajax({
    url:'../data/duty/duty_organization_country_list/' + id + '.json',
    dataType:'json',
    error:function(a,b){console.log(a,b)},
    success:function(data){
      var code = data.code,
        d = data.data;
      if(code == 0 && d){
        d.table="bangFuCountry";
      
         $("#bangfuDetail").html(template('tplBangFuDetail', d));
           renderJianPinPlanScrollBar($(".jianpinPlan_scroll_box"),"536px");
      }
    }
  });
}  


// 滚动条再次渲染
function renderJianPinPlanScrollBar($ele,height){
    $ele.slimScroll({
     width: '100%', //可见滚动区域的像素宽度。默认值:none
            height: height, //可见滚动区域的像素高度。默认值: 250px
            position: 'right', //滚动条位置。默认值: right
            size: '10px',
            color: '#d3d3d8', //滚动条颜色。默认值: #000000
            opacity: 1,
            alwaysVisible: true, //滚动条隐藏禁用。默认值: false
            distance: '4px', //滚动条到边缘距离。默认值: 1px
            railVisible: true, //滚动条轨迹。默认值: false
            railColor: '#fff', //滚动条轨迹颜色。默认值: #333333
            railOpacity: 0.9, //滚动条轨迹透明度。默认值: #333333
            wheelStep: 10, //鼠标滚轮的整数值。默认值: 20
            allowPageScroll: true, //检查如果鼠标滚轮滚动页面达到顶部或底部的容器
            disableFadeOut: true //禁用滚动条自动消退。默认值: false

});
}
function renderAreaScrollBar(height){
   $(".scroll_duty_right_province").slimScroll({
            width: '99%', //可见滚动区域的像素宽度。默认值:none
            height: height, //可见滚动区域的像素高度。默认值: 250px
            position: 'right', //滚动条位置。默认值: right
            size: '10px',
            color: '#6691f9', //滚动条颜色。默认值: #000000
            opacity: 0.2,
            alwaysVisible: true, //滚动条隐藏禁用。默认值: false
            distance: '4px', //滚动条到边缘距离。默认值: 1px
            railVisible: true, //滚动条轨迹。默认值: false
            railColor: '#282830', //滚动条轨迹颜色。默认值: #333333
            railOpacity: 0.01, //滚动条轨迹透明度。默认值: #333333
            wheelStep: 10, //鼠标滚轮的整数值。默认值: 20
            allowPageScroll: true, //检查如果鼠标滚轮滚动页面达到顶部或底部的容器
            disableFadeOut: true //禁用滚动条自动消退。默认值: false

        });
}

function renderAreaTopScrollBar(height){
   $(".scroll_duty_right_province_top").slimScroll({
            width: '99%', //可见滚动区域的像素宽度。默认值:none
            height: height, //可见滚动区域的像素高度。默认值: 250px
            position: 'right', //滚动条位置。默认值: right
            size: '10px',
            color: '#6691f9', //滚动条颜色。默认值: #000000
            opacity: 0.2,
            alwaysVisible: true, //滚动条隐藏禁用。默认值: false
            distance: '4px', //滚动条到边缘距离。默认值: 1px
            railVisible: true, //滚动条轨迹。默认值: false
            railColor: '#282830', //滚动条轨迹颜色。默认值: #333333
            railOpacity: 0.01, //滚动条轨迹透明度。默认值: #333333
            wheelStep: 10, //鼠标滚轮的整数值。默认值: 20
            allowPageScroll: true, //检查如果鼠标滚轮滚动页面达到顶部或底部的容器
            disableFadeOut: true //禁用滚动条自动消退。默认值: false

        });
}
//end渲染
$(function(){
    renderCheckAreaLevel1(440000000000);
    renderCheckAreaLevel2(441400000000);

    $(document).on("click",".bangfuDetail-btn",function(event) {
        _this=event.target;
        var id=$(_this).data("id");
        renderBangFuDetail(id);
        $(".pop-window-wrap").removeClass('hide');
    });

    $(document).on("click","#jianPinPlanTable .btn-amount-organization",function(event) {
        _this=event.target;
        var id=$(_this).data("id");
        renderBangFuOrganization(id);
    });
     $(document).on("click","#jianPinPlanTable .btn-amount-people",function(event) {
        _this=event.target;
        var id=$(_this).data("id");
        renderBangFuPeople(id);
    });
    $(document).on("click","#jianPinPlanTable .btn-amount-country",function(event) {
        _this=event.target;
        var id=$(_this).data("id");
        renderBangFuCountry(id);
    });



     //弹窗窗口返回按钮
    $(document).on("click",".back-btn.to-jianpinPlan-btn",function(event) {
       
        renderBangFuDetail(DUTY_JianPinTable_ID);
    });
      $(document).on("click",".back-btn.to-bangfuDanwei-btn",function(event) {
     
        renderBangFuOrganization(DUTY_OrganizationTable_ID);
    });

//弹出窗口
$(document).on("click","#bangfuDetail .close-btn",function(event) {
	$(".pop-window-wrap").addClass('hide');
});

})/*end all*/