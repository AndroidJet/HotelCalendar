package jet.com.calendar;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Administrator on 2016/6/15.
 */
public class CalendarUtil {

    private int weeks = 0;// 用来全局控制 上一周，本周，下一周的周数变化
    private int MaxDate; // 一月最大天数
    private int MaxYear; // 一年最大天数


    public static void main(String[] args) {
        String[] aa=new String[]{"1","b","s"};
        System.out.println(Arrays.toString(aa));

        CalendarUtil tt = new CalendarUtil();
        System.out.println("获取当天日期:" + tt.getNowTime("yyyy-MM-dd"));
        System.out.println("获取本周一日期:" + tt.getMondayOFWeek());
        System.out.println("获取本周日的日期~:" + tt.getCurrentSunday());
        System.out.println("获取上周一日期:" + tt.getPreviousMonday());
        System.out.println("获取上周日日期:" + tt.getPreviousWeekSunday());
        System.out.println("获取下周一日期:" + tt.getNextMonday());
        System.out.println("获取下周日日期:" + tt.getNextSunday());
        System.out.println("获取本月第一天日期:" + tt.getFirstDayOfMonth());
        System.out.println("获取本月最后一天日期:" + tt.getFinalDayOnMonth());
        System.out.println("获取上月第一天日期:" + tt.getPreviousMonthFirst());
        System.out.println("获取上月最后一天的日期:" + tt.getPreviousMonthEnd());
        System.out.println("获取下月第一天日期:" + tt.getNextMonthFirst());
        System.out.println("获取下月最后一天日期:" + tt.getNextMonthEnd());
        System.out.println("获取本年的第一天日期:" + tt.getCurrentYearFirst());
        System.out.println("获取本年最后一天日期:" + tt.getCurrentYearEnd());
        System.out.println("获取去年的第一天日期:" + tt.getPreviousYearFirst());
        System.out.println("获取去年的最后一天日期:" + tt.getPreviousYearEnd());
        System.out.println("获取明年第一天日期:" + tt.getNextYearFirst());
        System.out.println("获取明年最后一天日期:" + tt.getNextYearEnd());
        System.out.println("获取本季度第一天:" + tt.getThisSeasonFirstTime(11));
        System.out.println("获取本季度最后一天:" + tt.getThisSeasonFinallyTime(11));
        System.out.println("获取两个日期之间间隔天数2008-12-1~2008-9.29:"
                + CalendarUtil.getTwoDay("2008-12-1", "2008-9-29"));
        System.out.println("获取当前年份：" + tt.getYear());
        System.out.println("获取当前月份：" + tt.getMonth());
        System.out.println("获取今天在本年的第几天：" + tt.getDayOfYear());
        System.out.println("获得今天在本周的第几天：" + tt.getDayOnWeek());
        System.out.println("获得今天在本月的第几周：" + tt.getWeekOnMonth());
        System.out.println("获得半年后的日期："
                + tt.convertDateToString(tt.getTimeYearNext()));

        System.out.println("前1天"+getBeforeOrAfterDay(-0));
        System.out.println("前1天"+getBeforeOrAfterDay(-1));
        System.out.println("前2天"+getBeforeOrAfterDay(-2));
        System.out.println("前3天"+getBeforeOrAfterDay(-3));
        System.out.println("前4天"+getBeforeOrAfterDay(-4));
        System.out.println("前5天"+getBeforeOrAfterDay(-5));
        System.out.println("前6天"+getBeforeOrAfterDay(-6));



        System.out.println("上个月今天的日期"+getPerviouMonthToday());

        System.out.println("计算2个时间差"+getScondDifference("2016-02-03 12:23:03","2016-02-03 12:24:09"));

        System.out.println("时间戳转时间"+StampToTime(1471339298*1000L));


    }


    public static String getTimeMinute(int time, String nowTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Calendar c = Calendar.getInstance(); //当前时间
        try {
            c.setTime(dateFormat.parse(nowTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.MINUTE,time);
       return  dateFormat.format(c.getTime());
    }



    public static List<String> getIntervalTimeList(String sourceTime, String nowetime){

        List<String> list=new ArrayList<>();
        for (int i=0;i<60;i++){
            String t=getTimeMinute((i+1)*30,nowetime);
            boolean isIn=isInTime(sourceTime,t);
            if(isIn){
                list.add(t);
            }else {
                break;
            }
        }
        return list;
    }

    /**
     * 时间戳转时间
     * @param s
     * @return
     */
    public static String stampToDate(String s, String format){

        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long  l = Long.valueOf(s);
        if (String.valueOf(l).length() == 10) {
            l = l * 1000;
        }
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

    /**
     * 是否在配送时间内
     * 判断某一时间是否在一个区间内
     *
     * @param sourceTime
     *            时间区间,半闭合,如[10:00-20:00)
     * @param curTime
     *            需要判断的时间 如10:00
     * @param sourceTime
     * @param curTime
     * @return
     */
    public static boolean isInTime(String sourceTime, String curTime) {
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
        if (curTime == null || !curTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
        }
        String[] args = sourceTime.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            long now = sdf.parse(curTime).getTime();
            long start = sdf.parse(args[0]).getTime();
            long end = sdf.parse(args[1]).getTime();
            if (args[1].equals("00:00")) {
                args[1] = "24:00";
            }
            if (end < start) {
                if (now >= end && now < start) {
                    return false;
                } else {
                    return true;
                }
            }
            else {
                if (now >= start && now < end) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }

    }

    /**
     * 注意时间戳是已经*1000L
     * @param time
     * @return
     */
    public static String StampToTime(long time){
        Calendar lastDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lastDate.setTime(new Date(time));
        String date=sdf.format(lastDate.getTime());

        return date;
    }

    /**
     * 计算2个时间的差 秒表示
     *
     */
    public static long getScondDifference(String starTime, String endTime){
        long timeString = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);
            long diff = parse1.getTime() - parse.getTime();
            timeString=diff/1000;

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timeString;

    }

    /**
     * 计算2个时间的差 分秒表示
     * @param starTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    public static String getTimeDifference(String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");
            long hour1 = diff / (60 * 60 * 1000);
            String hourString = hour1 + "";
            long min1 = ((diff / (60 * 1000)) - hour1 * 60);
            timeString = hour1 + "小时" + min1 + "分";
             System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return timeString;

    }

    //获取上个月今天的日期
    public static String getPerviouMonthToday(){
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.add(calendar.MONTH,-1);
        date=calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String getBeforeOrAfterDay(int day){
        //以当前时间,计算获取前或者后第几天的日期
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,day);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 获取当前年份
     * @return
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     * @return
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }


    /**
     *  获取今天在本年的第几天
     * @return
     */
    public static int getDayOfYear() {
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }

    public static Date getTimeYearNext() {
        Calendar.getInstance().add(Calendar.DAY_OF_YEAR, 183);
        return Calendar.getInstance().getTime();
    }


    public static String convertDateToString(Date dateTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(dateTime);
    }


    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            java.util.Date date = myFormatter.parse(sj1);
            java.util.Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 获取本月第一天日期
     * @return
     */
    public static String getFirstDayOfMonth() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        str = sdf.format(lastDate.getTime());
        return str;
    }
    /**
     * 获取本月最后一天日期
     * @return
     */
    public static String getFinalDayOnMonth() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获取上月第一天日期
     * @return
     */
    public static String getPreviousMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号

        str = sdf.format(lastDate.getTime());
        return str;
    }




    /**
     * 获取上月最后一天的日期
     * @return
     */
    public static String getPreviousMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, -1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获取上月最后一天的日期
     * @return
     */
    public static String getPreviousMonthEndByDate(String source) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date=sdf.parse(source);

            Calendar lastDate = Calendar.getInstance();
            lastDate.setTime(date);
            lastDate.add(Calendar.MONTH, -1);// 减一个月
            lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
            lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
            str = sdf.format(lastDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return str;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */

    //  String pTime = "2012-03-12";
    public static int getWeekNoFormat(String pTime) {




        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(pTime));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
//            Week += "天";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
//            Week += "一";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
//            Week += "二";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
//            Week += "三";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
//            Week += "四";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
//            Week += "五";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
//            Week += "六";
//        }


        return c.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * 格式：2013-3-2
     * @param date
     */
    public static String FormatDate(String date){
        if(TextUtils.isEmpty(date)){
            new Throwable();
        }
        String year=date.split("-")[0];
        String month=date.split("-")[1].length()<2?"0"+date.split("-")[1] : date.split("-")[1];
        String day=date.split("-")[2].length()<2?"0"+date.split("-")[2] : date.split("-")[2];
        return year+"-"+month+"-"+day;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 设置的需要判断的时间  //格式如2012-09-08
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */

    //  String pTime = "2012-03-12";
    public static String getWeekByFormat(String pTime) {



        String week="";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(pTime));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
                if (c.get(Calendar.DAY_OF_WEEK) == 1) {
                    week += "日";
                }
                if (c.get(Calendar.DAY_OF_WEEK) == 2) {
                    week += "一";
                }
                if (c.get(Calendar.DAY_OF_WEEK) == 3) {
                    week += "二";
                }
                if (c.get(Calendar.DAY_OF_WEEK) == 4) {
                    week += "三";
                }
                if (c.get(Calendar.DAY_OF_WEEK) == 5) {
                    week += "四";
                }
                if (c.get(Calendar.DAY_OF_WEEK) == 6) {
                    week += "五";
                }
                if (c.get(Calendar.DAY_OF_WEEK) == 7) {
                    week += "六";
                }


        return week;
    }


    /**
     * 获取下月第一天日期
     * @return
     */
    public String getNextMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获取下月最后一天日期
     * @return
     */
    public String getNextMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 加一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    public String getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }


    /**
     * 获取本日是本月的第几周
      */
    public int getWeekOnMonth(){
        Calendar calendar= Calendar.getInstance();
        //获取当前时间为本月的第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        //获取当前时间为本周的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day==1) {
            day=7;
            week=week-1;
        } else {
            day=day-1;
        }
        return week;
    }
    /**
     * 获取本日是本周的第几日
     */
    public int getDayOnWeek(){
        Calendar calendar= Calendar.getInstance();
        //获取当前时间为本月的第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        //获取当前时间为本周的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day==1) {
            day=7;
            week=week-1;
        } else {
            day=day-1;
        }
        return day;

    }

    /**
     * 获取本周日的日期
     * @return
     */
    public static String getCurrentSunday() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return sdf.format(c.getTime());
    }

    /**
     * 获取本周一的日期
     * @return
     */
    public static String getMondayOFWeek() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        return sdf.format(c.getTime());
    }


    /**
     * 获取上周日的日期
     * @return
     */
    public static String getPreviousWeekSunday() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7-7);
        return sdf.format(c.getTime());
    }
    /**
     * 根据日期获取上周日的日期
     * @return
     */
    public static String getPreviousWeekSundayByDate(String course) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = sdf.parse(course);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
            if (day_of_week == 0)
                day_of_week = 7;
            c.add(Calendar.DATE, -day_of_week + 7-7);
            return sdf.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 获取上周一的日期
     * @return
     */
    public static String getPreviousMonday() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1-7);
        return sdf.format(c.getTime());
    }

    /**
     * 获取下周一的日期
     * @return
     */
    public static String getNextMonday() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1+7);
        return sdf.format(c.getTime());

    }

    /**
     * 获取下周日的日期
     * @return
     */
    public static String getNextSunday() {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7+7);
        return sdf.format(c.getTime());
    }

    /**
     * 获取明年最后一天日期
     * @return
     */
    public String getNextYearEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// 加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        lastDate.roll(Calendar.DAY_OF_YEAR, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获取明年第一天日期
     * @return
     */
    public String getNextYearFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// 加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        str = sdf.format(lastDate.getTime());
        return str;

    }



    private int getYearPlus() {
        Calendar cd = Calendar.getInstance();
        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        if (yearOfNumber == 1) {
            return -MaxYear;
        } else {
            return 1 - yearOfNumber;
        }
    }

    /**
     * 获取本年的第一天日期
     * @return
     */
    public String getCurrentYearFirst() {
        int yearPlus = this.getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus);
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        return preYearDay;
    }

    /**
     * 获得本年最后一天的日期 *
     * @return
     */
    public String getCurrentYearEnd() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        return years + "-12-31";
    }

    // 获得上年第一天的日期 *
    public String getPreviousYearFirst() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        years_value--;
        return years_value + "-1-1";
    }

    // 获得上年最后一天的日期
    public String getPreviousYearEnd() {
        weeks--;
        int yearPlus = this.getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks
                + (MaxYear - 1));
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        return preYearDay;
    }


    public String getThisSeasonFirstTime(int month) {
        int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int start_month = array[season - 1][0];
        int end_month = array[season - 1][2];

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);

        int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
        int end_days = getLastDayOfMonth(years_value, end_month);
        String seasonDate = years_value + "-" + start_month + "-" + start_days;
        return seasonDate;

    }


    public String getThisSeasonFinallyTime(int month) {
        int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int start_month = array[season - 1][0];
        int end_month = array[season - 1][2];

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);

        int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
        int end_days = getLastDayOfMonth(years_value, end_month);
        String seasonDate = years_value + "-" + end_month + "-" + end_days;
        return seasonDate;

    }


    private int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }


    public boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

}
