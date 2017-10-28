/**
 * Created by fengxiqiu on 2016/12/17.
 */
(function (Helpers) {
    var
        ajax = Helpers.ajaxCommon,
        ComponentsHelper = Helpers.Components,

        familyID = Helpers.getSearchObj()['family_id'];

    ComponentsHelper.wait(['app-header'], function(){
        var app = new Vue({
            el: '#app',
            mounted:function(){
                var _self=this;
                ajax({
                    data:{
                        family_id:	familyID
                    },
                    url:URLS.URL_FAMILY_FAMILY_MEMBER,
                    success:function(data){
                        var d=data.data;
                        if(data.code==0) {
                            _self.$set(_self, "member_list", d.list);
                            _self.$set(_self, "current_member", d.list[0]);
                        }
                    }
                });
            },
            data:{
                member_list:[],
                current_member:{},
                current:0//当前家庭成员序号
            },
            methods:{
                /**
                 * 查看上一个家庭成员
                 */
                showPre:function(){
                    if(this.current>0){
                        this.current--;
                        this.$set(this, "current_member", this.member_list[this.current]);
                    }
                },
                /**
                 * 查看下一个家庭成员
                 */
                showNext:function(){
                    if(this.current<this.member_list.length-1){
                        this.current++;
                        this.$set(this, "current_member", this.member_list[this.current]);
                    }
                }
            }
        });
    });

})(Helpers);