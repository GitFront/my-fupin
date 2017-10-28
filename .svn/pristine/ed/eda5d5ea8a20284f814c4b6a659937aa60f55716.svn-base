$.namespace("common.report.highcharts");
/**
 * 使用ID来管理图表对象
 * @type 
 */
common.report.highcharts.object={
	map:new Map(),
	/**
	 * 重置map列表
	 */
	init:function(){
		this.map.clear();
		/*重新创建一个map对象*/
		this.map = new Map();
	},
	/**
	 * 添加图表对象
	 * @param {} object 传入图表对象
	 * @return {} 返回保存对象的ID值
	 */
	add:function(object){
		/*生成随机的guid*/
		var id = common.utils.guid();
		this.map.put(id,object);
		return id;
	},
	/**
	 * 重置图表对象
	 * @param {} chartid 图表对象ID
	 * @param {} object 需要重置的对象
	 * @return {} 返回图表对象是否重置成功
	 */
	reset:function(chartid,object){
		if(!this.map.containsKey(chartid)){
			return false;		
		}
		if(this.map.remove(chartid)){
			this.map.put(chartid,object);
			return true;
		}
		return false;
	},
	/**
	 * 获取图表对象
	 * @param {} chartid 图表对象ID
	 * @return {} 返回图表对象
	 */
	get:function(chartid){
		if(!this.map.containsKey(chartid)){
			return null;
		}
		return this.map.get(chartid);
	},
	/**
	 * 删除图表对象
	 * @param {} chartid 图表对象ID
	 * @return {} 返回是否成功删除图表对象
	 */
	remove:function(chartid){
		if(!this.map.containsKey(chartid)){
			return false;		
		}
		/*销毁对象*/
		var obj = this.map.get(chartid);
		obj.chart.destroy();
		delete obj.chart;
		delete obj.data;
		delete obj.option;
		return this.map.remove(chartid);
	},
	/**
	 * 验证chartid是否存在
	 * @param {} chartid 图表对象ID
	 * @return {} 返回是否成功删除图表对象
	 */
	containsKey:function(chartid){
		if(this.map.containsKey(chartid)){
			return true;		
		}else{
			return false;
		}
	}
}