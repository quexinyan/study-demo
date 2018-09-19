package demo.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class getMonthDiff {

    /**
     *  获取两个日期相差的月数
     * @param d1    较大的日期
     * @param d2    较小的日期
     * @return  如果d1>d2返回 月数差 否则返回0
     */
    public static int getMonthDiff(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if (c1.getTimeInMillis() < c2.getTimeInMillis()) return 0;
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) yearInterval--;
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) monthInterval--;
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;
    }

    /**
     *
     * 计算两个日期相差的月份数
     *
     * @param date1 日期1
     * @param date2 日期2
     * @param pattern 日期1和日期2的日期格式
     * @return 相差的月份数
     * @throws ParseException
     */
    public static int countMonths(String date1,String date2,String pattern) throws ParseException{
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));
        int year =c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
        //开始日期若小月结束日期
        if(year<0){
            year=-year;
            return year*12+c1.get(Calendar.MONTH)-c2.get(Calendar.MONTH);
        }
        return year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH);
    }

    public static void  main(String[] args) throws ParseException {
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d2 = sdf.parse("2018-09-15");
        Date d1 = sdf.parse("2018-11-10");
        System.out.println(getMonthDiff(d1, d2));*/

        System.out.println(countMonths("", "", ""));
    }
}
