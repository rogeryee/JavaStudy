package com.yee.study.util;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具测试类
 *
 * @author Roger.Yi
 */
public class DateUtilTest
{
    /**
     * 测试getSQLDate
     */
    @Test
    public void testGetSQLDate()
    {
        // getSQLDate(Date)
        Date date1 = DateUtil.getDate(2012, 1, 3);
        Assert.assertEquals(date1.getTime(), DateUtil.getSQLDate(date1).getTime());
        Assert.assertNull(DateUtil.getSQLDate(null));

        // 时分秒毫秒不同
        Date date2 = new Date();
        Assert.assertFalse(date2.getTime() == DateUtil.getSQLDate(date2).getTime());

        // getSQLDate(year, month, day)
        Assert.assertEquals(DateUtil.getDate(2012, 11, 11).getTime(), DateUtil.getSQLDate(2012, 11, 11).getTime());
        Assert.assertNull(DateUtil.getSQLDate(-1, -2, -3));
    }

    /**
     * 测试getDate
     */
    @Test
    public void testGetDate()
    {
        // 1
        Calendar cdr = Calendar.getInstance();
        cdr.set(2009, 9, 9, 8, 7, 6); // 2009/10/09 08:07:06
        cdr.clear(Calendar.MILLISECOND);
        Date date = cdr.getTime();

        Assert.assertEquals(date.getTime(), DateUtil.getDate(2009, 10, 9, 8, 7, 6).getTime());

        // 2
        cdr.clear();
        cdr.set(2009, 9, 9);
        date = cdr.getTime();

        Assert.assertEquals(date.getTime(), DateUtil.getDate(2009, 10, 9).getTime());

        Assert.assertNull(DateUtil.getDate(0, 0, 0));
        Assert.assertNull(DateUtil.getDate(2009, 2, 29));
        Assert.assertNull(DateUtil.getDate(2009, 10, 9, 8, 7, 61));

        // 3
        date = new Date();
        Assert.assertEquals(date.getTime(), DateUtil.getDate(date).getTime());
        Assert.assertNull(DateUtil.getDate(null));
    }

    /**
     * 测试daysBetween方法
     */
    @Test
    public void testDaysBetween()
    {
        final Date date1 = DateUtil.getDate(2002, 5, 18, 12, 0, 0);
        final Date date2 = DateUtil.getDate(2002, 5, 1, 12, 0, 0);
        final Date date3 = DateUtil.getDate(2002, 5, 1, 5, 0, 0);
        final Date date4 = DateUtil.getDate(2002, 4, 1, 12, 0, 0);
        final Date date5 = DateUtil.getDate(2000, 1, 1, 0, 0, 0);

        Assert.assertEquals(17, DateUtil.daysBetween(date1, date2)); // 比较时精确到日
        Assert.assertEquals(-17, DateUtil.daysBetween(date2, date1));
        Assert.assertEquals(17, DateUtil.daysBetween(date1, date3));
        Assert.assertEquals(-17, DateUtil.daysBetween(date3, date1));
        Assert.assertEquals(47, DateUtil.daysBetween(date1, date4));
        Assert.assertEquals(-47, DateUtil.daysBetween(date4, date1));
        Assert.assertEquals(868, DateUtil.daysBetween(date1, date5));
        Assert.assertEquals(-868, DateUtil.daysBetween(date5, date1));

        try
        {
            DateUtil.daysBetween(date1, null);
            Assert.fail("Expect exception");
        }
        catch (Exception e)
        {
        }

        try
        {
            DateUtil.daysBetween(null, date1);
            Assert.fail("Expect exception");
        }
        catch (Exception e)
        {
        }

        try
        {
            DateUtil.daysBetween(null, null);
            Assert.fail("Expect exception");
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 测试monthsBetween方法
     */
    @Test
    public void testMonthsBetween()
    {
        final Date date1 = DateUtil.getDate(2002, 5, 5, 5, 5, 5);
        final Date date2 = DateUtil.getDate(2002, 2, 2, 2, 2, 2);
        final Date date3 = DateUtil.getDate(2001, 1, 1, 1, 1, 1);
        final Date date4 = DateUtil.getDate(2001, 9, 9, 9, 9, 9);

        Assert.assertEquals(3, DateUtil.monthsBetween(date1, date2)); // 比较时精确到月
        Assert.assertEquals(-3, DateUtil.monthsBetween(date2, date1));
        Assert.assertEquals(16, DateUtil.monthsBetween(date1, date3));
        Assert.assertEquals(-16, DateUtil.monthsBetween(date3, date1));
        Assert.assertEquals(8, DateUtil.monthsBetween(date1, date4));
        Assert.assertEquals(-8, DateUtil.monthsBetween(date4, date1));

        try
        {
            DateUtil.monthsBetween(date1, null);
            Assert.fail("Expect exception");
        }
        catch (Exception e)
        {
        }

        try
        {
            DateUtil.monthsBetween(null, date1);
            Assert.fail("Expect exception");
        }
        catch (Exception e)
        {
        }

        try
        {
            DateUtil.monthsBetween(null, null);
            Assert.fail("Expect exception");
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 测试yearsBetween方法
     */
    @Test
    public void testYearsBetween()
    {
        final Date date1 = DateUtil.getDate(2012, 12, 12, 12, 12, 12);
        final Date date2 = DateUtil.getDate(2012, 5, 5, 5, 5, 5);
        final Date date3 = DateUtil.getDate(2001, 1, 1, 1, 1, 1);

        Assert.assertEquals(0, DateUtil.yearsBetween(date1, date2)); // 比较时精确到年
        Assert.assertEquals(0, DateUtil.yearsBetween(date2, date1));
        Assert.assertEquals(11, DateUtil.yearsBetween(date1, date3));
        Assert.assertEquals(-11, DateUtil.yearsBetween(date3, date1));

        try
        {
            DateUtil.yearsBetween(date1, null);
            Assert.fail("Expect exception");
        }
        catch (Exception e)
        {
        }

        try
        {
            DateUtil.yearsBetween(null, date1);
            Assert.fail("Expect exception");
        }
        catch (Exception e)
        {
        }

        try
        {
            DateUtil.yearsBetween(null, null);
            Assert.fail("Expect exception");
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 测试rollDate方法
     */
    @Test
    public void testRollDate()
    {
        Date date1 = DateUtil.getDate(2012, 12, 1, 0, 0, 0);
        Date date2 = DateUtil.getDate(2012, 12, 2);
        java.sql.Date date3 = DateUtil.getSQLDate(2012, 12, 1);

        Assert.assertEquals(date2, DateUtil.rollDate(date1, Calendar.DATE, 1));
        Assert.assertEquals(date3, DateUtil.rollDate(date2, Calendar.DATE, -1));

        Assert.assertNull(DateUtil.rollDate(null, 0, 0));

        date1 = DateUtil.getDate(2016, 7, 17, 1, 0, 0);
        date2 = DateUtil.getDate(2016, 7, 18, 1, 0, 0);
        Assert.assertEquals(date2, DateUtil.rollDate(date1, "1D", true));
        date2 = DateUtil.getDate(2016, 7, 16, 1, 0, 0);
        Assert.assertEquals(date2, DateUtil.rollDate(date1, "1D", false));
    }

    /**
     * 测试setField方法
     */
    @Test
    public void testSetField()
    {
        Date date1 = DateUtil.getDate(2012, 12, 1, 0, 0, 0);
        Date date2 = DateUtil.getDate(2012, 12, 1, 12, 0, 0);
        java.sql.Date date3 = DateUtil.getSQLDate(2012, 12, 2);
        Assert.assertEquals(date2, DateUtil.setDateField(date1, Calendar.HOUR_OF_DAY, 12));
        Assert.assertEquals(date3, DateUtil.setDateField(date1, Calendar.DATE, 2));

        Assert.assertNull(DateUtil.setDateField(null, 0, 0));
    }

    @Test
    public void testQuarter() throws ParseException
    {
        Date date = DateUtil.parse("yyyy-MM-dd", "2013-01-05");
        Assert.assertEquals(0, DateUtil.getCurrentQuarter(date));
        date = DateUtil.parse("yyyy-MM-dd", "2013-03-05");
        Assert.assertEquals(0, DateUtil.getCurrentQuarter(date));
        date = DateUtil.parse("yyyy-MM-dd", "2013-04-05");
        Assert.assertEquals(1, DateUtil.getCurrentQuarter(date));
        date = DateUtil.parse("yyyy-MM-dd", "2013-06-05");
        Assert.assertEquals(1, DateUtil.getCurrentQuarter(date));
        date = DateUtil.parse("yyyy-MM-dd", "2013-07-05");
        Assert.assertEquals(2, DateUtil.getCurrentQuarter(date));
        date = DateUtil.parse("yyyy-MM-dd", "2013-12-05");
        Assert.assertEquals(3, DateUtil.getCurrentQuarter(date));

        date = DateUtil.parse("yyyy-MM-dd", "2013-01-05");
        date = DateUtil.nextQuarterFirstMonth(date);
        Assert.assertEquals("2013-04-05", DateUtil.format("yyyy-MM-dd", date));

        date = DateUtil.parse("yyyy-MM-dd", "2013-03-05");
        date = DateUtil.nextQuarterFirstMonth(date);
        Assert.assertEquals("2013-04-05", DateUtil.format("yyyy-MM-dd", date));

        date = DateUtil.parse("yyyy-MM-dd", "2013-04-05");
        date = DateUtil.nextQuarterFirstMonth(date);
        Assert.assertEquals("2013-07-05", DateUtil.format("yyyy-MM-dd", date));

        date = DateUtil.parse("yyyy-MM-dd", "2013-12-05");
        date = DateUtil.nextQuarterFirstMonth(date);
        Assert.assertEquals("2014-01-05", DateUtil.format("yyyy-MM-dd", date));
    }

    @Test
    public void testInTerm()
    {
        Date startTime = new Date();
        Date nowTime = DateUtil.rollDate(startTime, Calendar.DAY_OF_MONTH, 2);
        Assert.assertTrue(DateUtil.inTerm("1Y", startTime, nowTime));
        Assert.assertTrue(DateUtil.inTerm("1M", startTime, nowTime));
        Assert.assertTrue(DateUtil.inTerm("1W", startTime, nowTime));
        Assert.assertTrue(DateUtil.inTerm("10D", startTime, nowTime));
        Assert.assertFalse(DateUtil.inTerm("1D", startTime, nowTime));
        Assert.assertFalse(DateUtil.inTerm("1h", startTime, nowTime));
        Assert.assertFalse(DateUtil.inTerm("1m", startTime, nowTime));
        Assert.assertFalse(DateUtil.inTerm("1s", startTime, nowTime));
    }

    @Test
    public void testIsSameDay()
    {
        Assert.assertTrue(DateUtil.isSameDay(new Date(), new Date()));
        Assert.assertFalse(DateUtil.isSameDay(new Date(), DateUtil.rollDate(new Date(), Calendar.DATE, -1)));
    }

    @Test
    public void testDateFromString()
    {
        Assert.assertEquals(DateUtil.getDate(2014, 1, 24), DateUtil.dateFromString("2014-01-24"));
    }

    @Test
    public void testDateToString()
    {
        Assert.assertEquals("2014-01-24", DateUtil.dateToString(DateUtil.getDate(2014, 1, 24)));
    }

    @Test
    public void testMaxDate()
    {
        Date d1 = DateUtil.getDate(2016, 8, 14);
        Date d2 = null;
        Date d3 = DateUtil.getDate(2016, 8, 14, 23, 00, 00);
        Date d4 = DateUtil.getDate(2000, 1, 1);

        Assert.assertEquals(d3, DateUtil.max(d1, d2, d3, d4));
        Assert.assertEquals(d3, DateUtil.max(d1, d2, d3));
        Assert.assertEquals(d1, DateUtil.max(d1, d2));
        Assert.assertEquals(d1, DateUtil.max(d1, d4));
        Assert.assertNull(DateUtil.max(d2));
        Assert.assertNull(DateUtil.max());
        Assert.assertNull(DateUtil.max((Date[]) null));
    }

    @Test
    public void testMinDate()
    {
        Date d1 = DateUtil.getDate(2016, 8, 14);
        Date d2 = null;
        Date d3 = DateUtil.getDate(2016, 8, 14, 23, 00, 00);
        Date d4 = DateUtil.getDate(2000, 1, 1);

        Assert.assertEquals(d4, DateUtil.min(d1, d2, d3, d4));
        Assert.assertEquals(d1, DateUtil.min(d1, d2, d3));
        Assert.assertEquals(d1, DateUtil.min(d1, d2));
        Assert.assertEquals(d4, DateUtil.min(d1, d4));
        Assert.assertNull(DateUtil.min(d2));
        Assert.assertNull(DateUtil.min());
        Assert.assertNull(DateUtil.min((Date[]) null));
    }
}
