/**
 * Created by fengxiqiu on 2016/12/19.
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
                    url:URLS.URL_FAMILY_FILE_INVESTED,
                    success:function(data){
                        var d=data.data;
                        if(data.code==0) {
                            _self.$set(_self, "summary", d.summary);
                            _self.$set(_self, "table", d.table);
                            _self.$set(_self, "helper_city", d.table.helper_city);
                            _self.$set(_self, "central_province", d.table.central_province);
                        }
                    }
                });
            },
            data:{
                summary:{},
                table:{},
                helper_city:{},
                central_province:{}

            }
        });
    });

})(Helpers);