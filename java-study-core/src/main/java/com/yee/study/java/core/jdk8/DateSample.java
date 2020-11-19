package com.yee.study.java.core.jdk8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ofLocalizedTime;

/**
 * JDK1.8 Date 新特性
 * 
 * @author Roger.Yi
 */
public class DateSample {

    private static final Logger logger = LoggerFactory.getLogger(DateSample.class);

    public static void main(String[] args) {
        LocalDateTime local = LocalDateTime.now();
        Date date = new Date();
//Date 类型的时间使用SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date));
//LocalDateTime 类型的使用DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        System.out.println(formatter.format(local));

        Integer integer = Integer.parseInt("-9999");
        System.out.println(integer);
    }

    public static void main1(String[] args) {
        // Clock 提供了访问当前日期和时间的方法，Clock是时区敏感的，可以用来取代 System.currentTimeMillis() 来获取当前的微秒数。
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();
        long millis2 = System.currentTimeMillis();
        logger.info("millis by Clock = {}", millis);
        logger.info("millis by System = {}" + millis2);

        // Zone 某一个特定的时间点也可以使用Instant类来表示，Instant类也可以用来创建老的java.util.Date对象。
        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);
        logger.info("Date by Instant", legacyDate);

        // 时区
        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Asia/Shanghai");
        logger.info("Zone rules of Europe/Berlin = {}", zone1.getRules());
        logger.info("Zone rules of Asia/Shanghai = {}", zone2.getRules());
        logger.info("All available zones = {}", ZoneId.getAvailableZoneIds());

        // LocalTime 本地时间(没有时区信息的时间)
        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);
        logger.info("localTime of Berlin comparing with Shanghai = {}", now1.isBefore(now2)); // true
        logger.info("Hours diff between Berlin and Shanghai = {}", ChronoUnit.HOURS.between(now1, now2));
        logger.info("Minutes diff between Berlin and Shanghai = {}", ChronoUnit.MINUTES.between(now1, now2));

        LocalTime late = LocalTime.of(23, 59, 59);
        logger.info("late time = {}", late);

        DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.GERMAN);
        LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
        logger.info("leetTime time = {}", leetTime);

        // LocalDate 本地日期
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);
        LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);

        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        logger.info("dayOfWeek = {}", dayOfWeek);

        germanFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.GERMAN);
        LocalDate xmas = LocalDate.parse("24.12.2014", germanFormatter);
        logger.info("xmas = {}", xmas);

        // LocalDateTime 本地日期时间
        LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);
        logger.info("dayOfWeek={}, month={}, minuteOfDay={}", sylvester.getDayOfWeek(), sylvester.getMonth(), sylvester.getLong(ChronoField.MINUTE_OF_DAY));
        
        instant = sylvester.atZone(ZoneId.systemDefault()).toInstant();
        legacyDate = Date.from(instant);
        logger.info("Date by Instant", legacyDate);

        // 自定义格式 解析日期
        String dateStr= "2016年10月25日";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        logger.info("Formatted date = {}", formatter.format(date));

        // 自定义格式 日期转换为字符串
        formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 hh:mm a");
        LocalDateTime now = LocalDateTime.now();
        dateStr = now.format(formatter);
        logger.info("Formatted date = {}", dateStr);

        // 使用SimpleFormat
        dateStr = format(new Date());
        logger.info("SimpleFormatted date = {}", dateStr);

        DateTimeFormatter DAILY_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime local = LocalDateTime.now();
        Integer dateInt = Integer.parseInt(DAILY_DATE_FORMATTER.format(local));

        LocalDate date2 = LocalDate.parse(String.valueOf(dateInt), formatter1);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 E | yyyy年M月d日", Locale.CHINA);
        System.out.println(formatter2.format(date2));


    }

    // 在并发环境下使用ThreadLocal来限制SimpleDateFormat只能在线程内共享，这样就避免了多线程导致的线程安全问题。
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static String format(Date date) {
        return threadLocal.get().format(date);
    }
}
