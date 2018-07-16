/**
 * 判断是否在固定时间内
 */

var begindate = '2018-04-28';
var enddate = '2018-05-22';

//获取当前时间，格式YYYY-MM-DD
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

/**
 * 日期解析，字符串转日期
 * @param dateString 可以为2017-02-16，2017/02/16，2017.02.16
 * @returns {Date} 返回对应的日期对象
 */
function dateParse(dateString){
    var SEPARATOR_BAR = "-";
    var SEPARATOR_SLASH = "/";
    var SEPARATOR_DOT = ".";
    var dateArray;
    if(dateString.indexOf(SEPARATOR_BAR) > -1){
        dateArray = dateString.split(SEPARATOR_BAR);
    }else if(dateString.indexOf(SEPARATOR_SLASH) > -1){
        dateArray = dateString.split(SEPARATOR_SLASH);
    }else{
        dateArray = dateString.split(SEPARATOR_DOT);
    }
    return new Date(dateArray[0], dateArray[1]-1, dateArray[2]);
};

/**
 * 日期比较大小
 * compareDateString大于dateString，返回1；
 * 等于返回0；
 * compareDateString小于dateString，返回-1
 * @param dateString 日期
 * @param compareDateString 比较的日期
 */
function dateCompare(dateString, compareDateString){
    var dateTime = dateParse(dateString).getTime();
    var compareDateTime = dateParse(compareDateString).getTime();
    if(compareDateTime > dateTime){
        return 1;
    }else if(compareDateTime == dateTime){
        return 0;
    }else{
        return -1;
    }
};
/**
 * 判断日期是否在区间内，在区间内返回true，否返回false
 * @param dateString 日期字符串
 * @param startDateString 区间开始日期字符串
 * @param endDateString 区间结束日期字符串
 * @returns {Number}
 */
function isDateBetween(dateString, startDateString, endDateString){
    var flag = false;
    var startFlag = (dateCompare(dateString, startDateString) < 1);
    var endFlag = (dateCompare(dateString, endDateString) > -1);
    if(startFlag && endFlag){
        flag = true;
    }
    return flag;
};

console.log(getNowFormatDate());
/*console.log(isDateBetween(getNowFormatDate(), begindate, enddate));*/