(function (Helpers, win) {
	var
		ajax = Helpers.ajaxCommon,
		ComponentsHelper = Helpers.Components,

		familyID = Helpers.getSearchObj()['family_id'];

	ComponentsHelper.wait(['app-header'], function () {
		var app = new Vue({
			el: '#app',
			data: {
				familyID: familyID,
				family_pics: [],
				base_info: {},
				poor_reason: [],
				other_info: {}
			},
			components: {
				swiper: VueSwiper
			},
			filters: {
				linkParams: function (url) {
					return url + '?family_id=' + familyID
				}
			}
		});

		ajax({
			data:{
				family_id:	familyID
			},
			url: URLS.URL_FAMILY_FILE_POOR_DETAIL,
			success: function (data) {
				var code = data.code,
					d = data.data;
				if (code == 0 && d) {
					for (var k in d) {
						app[k] = d[k];
					}
				}
			}
		})

	});
})(Helpers, window);
