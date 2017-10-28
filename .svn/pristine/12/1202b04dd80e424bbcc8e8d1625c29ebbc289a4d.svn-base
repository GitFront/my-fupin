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
                    url:URLS.URL_FAMILY_FILE_IMPLEMENT,
                    success:function(data){
                        var d=data.data;
                        if(data.code==0) {
                            _self.$set(_self, "summary", d.summary);
                            _self.$set(_self, "projects_completed", d.projects_completed);
                            _self.$set(_self, "projects_running", d.projects_running);
                        }
                    }
                });
            },
            data:{
                summary:{},
                projects_completed: [],
                projects_running: []
            },
            methods:{
                /*
                * tab切换
                * */
                switchTab: function (e) {
                    var name = e.currentTarget.dataset.name;
                    this.showActive(this.$refs.tabs, name);
                    this.showActive(this.$refs.contents, name);
                },
                /*
                * 展示对应的tab和content
                * */
                showActive: function ($els, name) {
                    var lis = $els.children;
                    ([].slice.call(lis)).forEach(function (el) {
                        if (el.dataset.name === name) {
                            el.classList.add('active');
                        } else {
                            el.classList.remove('active');
                        }
                    })
                }
            }
        });
    });

})(Helpers);