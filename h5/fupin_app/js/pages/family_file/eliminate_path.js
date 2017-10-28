(function (Helpers) {
    var
        ajax = Helpers.ajaxCommon,
        ComponentsHelper = Helpers.Components,

        familyID = Helpers.getSearchObj()['family_id'];

    ComponentsHelper.wait(['app-header', 'score-chart'], function(){
        var app = new Vue({
            el: '#app',
            mounted:function(){
                var _self=this;
                ajax({
                    data:{
                        family_id:	familyID
                    },
                    url:URLS.URL_FAMILY_FILE_ELIMINATE_PATH,
                    success:function(data){
                        var d=data.data;
                        if(data.code==0) {
                            _self.$set(_self, "track", d);
                        }
                    }
                });
            },
            data:{
                track:{}
            },
            methods:{

            }
        });
    });

})(Helpers);