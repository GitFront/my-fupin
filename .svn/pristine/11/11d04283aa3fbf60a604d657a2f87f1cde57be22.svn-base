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
					url:URLS.URL_FAMILY_FILE_INDEX,
					success:function(data){
						var d=data.data;
						if(data.code==0){
							_self.$set(_self, 'poor_business_card', d.poor_business_card);
							_self.$set(_self, 'help_policy', d.help_policy);
							_self.$set(_self, 'project_working', d.project_working);
							_self.poor_business_card.detail_url='status.html?family_id='+_self.poor_business_card.id;
						}
					}
				});
			},
			data:{
				poor_business_card:{},
				help_policy:{},
				project_working:{}
			},
			methods:{
				creating:function(){
					this.$refs.dialog.openDialog();
				}
			}
		});
	});

})(Helpers);