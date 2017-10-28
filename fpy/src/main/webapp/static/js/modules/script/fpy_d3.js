$.namespace("fpy.report.d3");

var FpyD3Reports = {
	exampleCountry:{
		/**
		 * 示范村水滴图自动生成方法
		 * @param {} container 图形容器DIV的ID值
		 * @param {} data 具体数据 - eg: 60.4（数值）
		 */
		LiquidFillGauge:function(container,data){
			var config = liquidFillGaugeDefaultSettings();
			config.circleThickness = 0.1;
			config.circleFillGap = 0.1;
			if (data == "100") {
				//100%比例的配置
				config.circleColor = "#739ffa";
				config.textColor = "#f3e824";
				config.waveTextColor = "#f3e824";
				config.waveColor = "#6bdb1f";
			} else {
				config.circleColor = "#739ffa";
				config.textColor = "#fff";
				config.waveTextColor = "#fff";
				config.waveColor = "#6bdb1f";
			}
			config.textVertPosition = 0.5;
			config.waveAnimateTime = 1000;
			config.waveHeight = 0.05;
			config.waveAnimate = true;
			config.waveRise = false;
			config.waveHeightScaling = false;
			config.waveOffset = 0.25;
			config.textSize = 0.75;
			config.waveCount = 3;
			
			loadLiquidFillGauge(container,data,config);
		}
	}	
}





