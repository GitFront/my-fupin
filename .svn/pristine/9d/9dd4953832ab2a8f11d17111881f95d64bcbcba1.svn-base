$(function() {
	var countDownTimer = null;
	var ajax=Helpers.ajax;
	
	$.poorEastWest = {
		renderPoorEastWestIndex: function() {
			var self = this;
			ajax({
				url: URLS.URL_POOR_EAST_WEST_INDEX,
				success: function(data) {
					var code = data.code,
						d = data.data;
					if (code == 0 && d) {
						$('#poorEastWestWrap').html(template('tplPoorEastWestIndex', d));
						renderCountDown(d.time_end);
						bindPeerPopup(d.help_targets);
					}
				},
				error: function() {
					// self.renderPoorEastWestIndex();
				}
			})
			//绑定结对县弹窗
			function bindPeerPopup(dataObj){
				var $targetEle= $(".left-help-province .block em"),
					$peerCountyPopupWrap=$('#peerCountyPopupWrap');

				$targetEle.on('click',  function(event) {
					var targetData={},
						$this=$(this),
						_target=$this.attr("data-target");


					for(var key in dataObj){
						if(_target === key){
							targetData = dataObj[key]
						}
					}

					$peerCountyPopupWrap.html(template('tplPeerCountyPopup', targetData));
					$peerCountyPopupWrap.removeClass("hide");

				});

				$peerCountyPopupWrap.on('click','.btn-close' , function(event) {
					$peerCountyPopupWrap.addClass("hide")
				});
			}
		}
	}

	$.poorEastWest.renderPoorEastWestIndex();

	function renderCountDown(timestamp) {
		//毫秒1000*60*60*24*2
		countdown();

		function countdown() {
			if (countDownTimer) {
				clearInterval(countDownTimer);
			}
			countDownTimer = setInterval(count, 1000);

			function count() {
				var intDiff = parseInt((timestamp - Date.now()) / 1000),
					day = 0,
					hour = 0,
					minute = 0,
					second = 0; //时间默认值
				if (intDiff > 0) {
					day = Math.floor(intDiff / (60 * 60 * 24));
					hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
					minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
					second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
				}
				if (day <= 9) {
					day = '0' + day;
				}
				if (hour <= 9) {
					hour = '0' + hour;
				}
				if (minute <= 9) {
					minute = '0' + minute;
				}
				if (second <= 9) {
					second = '0' + second;
				}
				$('#dayshow').html(day);
				$('#hourshow').html(hour);
				$('#minuteshow').html(minute);
				$('#secondshow').html(second);
			}
		}
	}

})