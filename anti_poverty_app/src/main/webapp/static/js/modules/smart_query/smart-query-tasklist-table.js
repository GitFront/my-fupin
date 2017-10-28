function formatGap(val, row, index) {
	var type = row["type"];
	if('0' == type) {
		return "";
	}
	var gap = row["cyc_type"];
	var num = row["cyc_len"];
	var value_ = "";
	var gapName = "";
	switch (gap) {
	case "d":
		gapName = "天";
		break;
	case "w":
		gapName = "周";
		break;
	case "m":
		gapName = "月";
		break;
	case 'y':
		gapName = "年";
		break;
	default:
		break;
	}

	switch (num) {
	case 1:
		if (gapName === "")
			break;
		value_ = "每" + gapName;
		break;
	case "" : 
		value_ = "";
		break;
	default:
		value_ = "每" + num + gapName;
		break;
	}

	return value_;

}

function exptypeformatter(value, row, index) {
	var value_ = value;
	switch (value) {
		case "1":
			value_ = "自动任务";
			break;
		case "0":
			value_ = "手动任务";
			break;

		default:
			break;
	}
	return value_;
}


/* 运行时长格式化 */
function tickCountformatter(value, row, index) {
	var type = row["type"];
	if('0' == type) {
		return "";
	}
	var value_ = "";
	if('' != value) {
		if (value < 1000) {
			value_ = value + "毫秒";
		} else if (value > 1000 && value <= (60 * 1000)) {
			var ms = value % 1000;
			var s = parseInt(value / 1000);
			value_ = (value % 1000) === 0 ? s + "秒" : s + "秒" + ms + "毫秒";
		} else if (value > (60 * 1000) && value <= (60 * 60 * 1000)) {
			var m = parseInt(value / (60 * 1000));
			var temp_ = value % (60 * 1000);
			var s = Math.ceil(temp_ / 1000);
			value_ = temp_ === 0 ? m + "分" : m + "分" + s + "秒";
		} else if (value > (60 * 60 * 1000)) {
			var h = parseInt(value / (60 * 60 * 1000));
			var temp_ = value % (60 * 60 * 1000);
			var m = parseInt(temp_ / (60 * 1000));
			var temp1_ = temp_ % (60 * 1000);
			var s = Math.ceil(temp1_ / 1000);
			value_ = ((temp_ === 0) && (temp1_ === 0)) ? h + "小时" : h + "小时" + m
			+ "分" + s + "秒";
		}
	}
	return value_;
}

/* 状态格式化 */
function statusformatter(value, row, index) {
	var type = row["type"];
	if('0' == type) {
		return "";
	}
	var task_status = row['status'];
	if('0' == task_status) {
		return "已挂起";
	}
	var value_ = "";
	switch (value) {
		case "1":
			value_ = "运行中";
			break;
		case "2":
			value_ = "已挂起";
			break;
		case "3":
			value_ = "已完成";
			break;
		case "4":
			value_ = '<a href="#" title="' + row['description'] + '" class="easyui-tooltip">异常</a>';
			break;
		default:
			break;
	}
	return value_;
}

function formatCommon(value, row, index) {
	var type = row["type"];
	if('0' == type) {
		return "";
	}else {
		return value;
	}
}
