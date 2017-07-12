package com.yee.study.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * 集合工具测试类
 *
 * @author Roger.Yi
 */
public class CollectionUtilTest
{
    /**
     * 测试size方法
     */
    @Test
    public void testSize()
    {
        // size(Collection)
        Assert.assertEquals(1, CollectionUtil.size(ArrayUtil.asList("1")));
        Assert.assertEquals(2, CollectionUtil.size(ArrayUtil.asList("1", "2")));
        Assert.assertEquals(0, CollectionUtil.size(new HashSet<String>()));
        Assert.assertEquals(0, CollectionUtil.size((Collection<?>) null));

        // size(Collection[])
        Assert.assertEquals(3, CollectionUtil.size((Collection<?>) null, ArrayUtil.asList("1"), ArrayUtil.asList("1", "2")));
        Assert.assertEquals(0, CollectionUtil.size((Collection<?>[]) null));
        Assert.assertEquals(0, CollectionUtil.size());
    }

    /**
     * 测试isEmpty方法
     */
    @Test
    public void testIsEmpty()
    {
        Assert.assertTrue(CollectionUtil.isEmpty(null));
        Assert.assertTrue(CollectionUtil.isEmpty(new ArrayList<Object>()));
        Assert.assertTrue(CollectionUtil.isEmpty(new HashSet<Object>()));
        Assert.assertFalse(CollectionUtil.isEmpty(Arrays.asList(new String[]{""})));
    }

    /**
     * 测试isNotEmpty方法
     */
    @Test
    public void testIsNotEmpty()
    {
        Assert.assertFalse(CollectionUtil.isNotEmpty(null));
        Assert.assertFalse(CollectionUtil.isNotEmpty(new ArrayList<Object>()));
        Assert.assertFalse(CollectionUtil.isNotEmpty(new HashSet<Object>()));
        Assert.assertTrue(CollectionUtil.isNotEmpty(Arrays.asList(new String[]{""})));
    }

    /**
     * 测试contains方法
     */
    @Test
    public void testContains()
    {
        Collection<String> c = ArrayUtil.asList("1", "2", "3", null);
        Assert.assertTrue(CollectionUtil.contains(c, "1"));
        Assert.assertTrue(CollectionUtil.contains(c, "3"));
        Assert.assertTrue(CollectionUtil.contains(c, null));
        Assert.assertFalse(CollectionUtil.contains(c, "4"));
        Assert.assertFalse(CollectionUtil.contains(new ArrayList<String>(), ""));
        Assert.assertFalse(CollectionUtil.contains(null, ""));
    }

    /**
     * 测试containsAny方法
     */
    @Test
    public void testContainsAny()
    {
        // List
        List<String> list = ArrayUtil.asList("1", "2", "3", null);
        Assert.assertTrue(CollectionUtil.containsAny(list, "1"));
        Assert.assertTrue(CollectionUtil.containsAny(list, "3"));
        Assert.assertTrue(CollectionUtil.containsAny(list, (String) null));
        Assert.assertFalse(CollectionUtil.containsAny(list, "4"));
        Assert.assertFalse(CollectionUtil.containsAny(new ArrayList<String>(), ""));
        Assert.assertFalse(CollectionUtil.containsAny(null, ""));
        Assert.assertTrue(CollectionUtil.containsAny(list, "1", "0"));
        Assert.assertTrue(CollectionUtil.containsAny(list, "0", "1"));
        Assert.assertTrue(CollectionUtil.containsAny(list, "3", "0"));
        Assert.assertTrue(CollectionUtil.containsAny(list, (String) null, "0"));
        Assert.assertFalse(CollectionUtil.containsAny(list, "4", "0"));
        Assert.assertFalse(CollectionUtil.containsAny(list, "0", "4"));
        Assert.assertFalse(CollectionUtil.containsAny(new ArrayList<String>(), "", "0"));
        Assert.assertFalse(CollectionUtil.containsAny(null, "", "0"));
        Assert.assertFalse(CollectionUtil.containsAny(null));

        // Set
        Set<String> set = ArrayUtil.asSet("1", "2", "3", null);
        Assert.assertTrue(CollectionUtil.containsAny(set, "1"));
        Assert.assertTrue(CollectionUtil.containsAny(set, "3"));
        Assert.assertTrue(CollectionUtil.containsAny(set, (String) null));
        Assert.assertFalse(CollectionUtil.containsAny(set, "4"));
        Assert.assertFalse(CollectionUtil.containsAny(new ArrayList<String>(), ""));
        Assert.assertFalse(CollectionUtil.containsAny(null, ""));
        Assert.assertTrue(CollectionUtil.containsAny(set, "1", "0"));
        Assert.assertTrue(CollectionUtil.containsAny(set, "0", "1"));
        Assert.assertTrue(CollectionUtil.containsAny(set, "3", "0"));
        Assert.assertTrue(CollectionUtil.containsAny(set, (String) null, "0"));
        Assert.assertFalse(CollectionUtil.containsAny(set, "4", "0"));
        Assert.assertFalse(CollectionUtil.containsAny(set, "0", "4"));
        Assert.assertFalse(CollectionUtil.containsAny(new ArrayList<String>(), "", "0"));
        Assert.assertFalse(CollectionUtil.containsAny(null, "", "0"));
        Assert.assertFalse(CollectionUtil.containsAny(null));
    }

    /**
     * 测试containsAll方法
     */
    @Test
    public void testContainsAll()
    {
        // List
        List<String> list = ArrayUtil.asList("1", "2", "3", null);
        Assert.assertTrue(CollectionUtil.containsAll(list, "1"));
        Assert.assertTrue(CollectionUtil.containsAll(list, "3"));
        Assert.assertTrue(CollectionUtil.containsAll(list, (String) null));
        Assert.assertFalse(CollectionUtil.containsAll(list, "4"));
        Assert.assertFalse(CollectionUtil.containsAll(new ArrayList<String>(), ""));
        Assert.assertFalse(CollectionUtil.containsAll(null, ""));
        Assert.assertTrue(CollectionUtil.containsAll(list, "1", "2"));
        Assert.assertTrue(CollectionUtil.containsAll(list, "2", "1"));
        Assert.assertTrue(CollectionUtil.containsAll(list, "3", "2"));
        Assert.assertTrue(CollectionUtil.containsAll(list, (String) null, "2"));
        Assert.assertFalse(CollectionUtil.containsAll(list, "4", "2"));
        Assert.assertFalse(CollectionUtil.containsAll(list, "2", "4"));
        Assert.assertFalse(CollectionUtil.containsAll(new ArrayList<String>(), "", "2"));
        Assert.assertFalse(CollectionUtil.containsAll(null, "", "2"));
        Assert.assertFalse(CollectionUtil.containsAll(null));

        // Set
        Set<String> set = ArrayUtil.asSet("1", "2", "3", null);
        Assert.assertTrue(CollectionUtil.containsAll(set, "1"));
        Assert.assertTrue(CollectionUtil.containsAll(set, "3"));
        Assert.assertTrue(CollectionUtil.containsAll(set, (String) null));
        Assert.assertFalse(CollectionUtil.containsAll(set, "4"));
        Assert.assertFalse(CollectionUtil.containsAll(new ArrayList<String>(), ""));
        Assert.assertFalse(CollectionUtil.containsAll(null, ""));
        Assert.assertTrue(CollectionUtil.containsAll(set, "1", "2"));
        Assert.assertTrue(CollectionUtil.containsAll(set, "2", "1"));
        Assert.assertTrue(CollectionUtil.containsAll(set, "3", "2"));
        Assert.assertTrue(CollectionUtil.containsAll(set, (String) null, "2"));
        Assert.assertFalse(CollectionUtil.containsAll(set, "4", "2"));
        Assert.assertFalse(CollectionUtil.containsAll(set, "2", "4"));
        Assert.assertFalse(CollectionUtil.containsAll(new ArrayList<String>(), "", "2"));
        Assert.assertFalse(CollectionUtil.containsAll(null, "", "2"));
        Assert.assertFalse(CollectionUtil.containsAll(null));
    }

    /**
     * 测试getElement方法
     */
    @Test
    public void testGetElement()
    {
        // ArrayList
        List<String> list = ArrayUtil.asList("1", "2", "3", null);

        // 1 getElement(List)
        Assert.assertEquals("1", CollectionUtil.getElement(list, 0));
        Assert.assertEquals("3", CollectionUtil.getElement(list, 2));
        Assert.assertEquals("3", CollectionUtil.getElement(list, -2));
        Assert.assertNull(CollectionUtil.getElement(list, 3));
        Assert.assertNull(CollectionUtil.getElement(list, 999));
        Assert.assertNull(CollectionUtil.getElement(list, -999));
        Assert.assertNull(CollectionUtil.getElement(null, 0));
        Assert.assertNull(CollectionUtil.getElement(new ArrayList<String>(), 0));

        // 2
        Assert.assertEquals("1", CollectionUtil.getElement(list, 0, "4"));
        Assert.assertEquals("3", CollectionUtil.getElement(list, 2, "4"));
        Assert.assertEquals("3", CollectionUtil.getElement(list, -2, "4"));
        Assert.assertNull(CollectionUtil.getElement(list, 3, "4"));
        Assert.assertEquals("4", CollectionUtil.getElement(list, 999, "4"));
        Assert.assertEquals("4", CollectionUtil.getElement(list, -999, "4"));
        Assert.assertEquals("4", CollectionUtil.getElement((Collection<String>) null, 0, "4"));
        Assert.assertEquals("4", CollectionUtil.getElement(new ArrayList<String>(), 0, "4"));

        // LinkedHashSet
        Set<String> set = ArrayUtil.asSet("1", "2", "3", null);

        // 1 getElement(Collection)
        Assert.assertEquals("1", CollectionUtil.getElement(set, 0));
        Assert.assertEquals("3", CollectionUtil.getElement(set, 2));
        Assert.assertEquals("3", CollectionUtil.getElement(set, -2));
        Assert.assertNull(CollectionUtil.getElement(set, 3));
        Assert.assertNull(CollectionUtil.getElement(set, 999));
        Assert.assertNull(CollectionUtil.getElement(set, -999));
        Assert.assertNull(CollectionUtil.getElement(null, 0));
        Assert.assertNull(CollectionUtil.getElement(new ArrayList<String>(), 0));

        // 2
        Assert.assertEquals("1", CollectionUtil.getElement(set, 0, "4"));
        Assert.assertEquals("3", CollectionUtil.getElement(set, 2, "4"));
        Assert.assertEquals("3", CollectionUtil.getElement(set, -2, "4"));
        Assert.assertNull(CollectionUtil.getElement(set, 3, "4"));
        Assert.assertEquals("4", CollectionUtil.getElement(set, 999, "4"));
        Assert.assertEquals("4", CollectionUtil.getElement(set, -999, "4"));
        Assert.assertEquals("4", CollectionUtil.getElement(new ArrayList<String>(), 0, "4"));

        // HashSet
        set = new HashSet<String>(ArrayUtil.asSet("1", "2", "3"));

        // 1 getElement(Collection)
        Assert.assertNotNull(CollectionUtil.getElement(set, 0));
        Assert.assertNotNull(CollectionUtil.getElement(set, 1));
        Assert.assertNotNull(CollectionUtil.getElement(set, 2));
        Assert.assertNotNull(CollectionUtil.getElement(set, -1));
        Assert.assertNotNull(CollectionUtil.getElement(set, -3));
        Assert.assertNull(CollectionUtil.getElement(set, 3));
        Assert.assertNull(CollectionUtil.getElement(set, 999));
        Assert.assertNull(CollectionUtil.getElement(set, -999));
    }

    /**
     * 测试getElements方法
     */
    @Test
    public void testGetElements()
    {
        // 1 ArrayList
        List<String> list = ArrayUtil.asList("1", "2", "3", null);
        List<String> subList = CollectionUtil.getElements(list, 0, 4);
        Assert.assertTrue(subList instanceof List);
        Assert.assertEquals(4, subList.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subList, 0));
        Assert.assertNull(CollectionUtil.getElement(subList, 3));

        // 2
        subList = CollectionUtil.getElements(list, 0, 10);
        Assert.assertEquals(4, subList.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subList, 0));
        Assert.assertNull(CollectionUtil.getElement(subList, 3));

        // 3
        subList = CollectionUtil.getElements(list, 1, 2);
        Assert.assertEquals(2, subList.size());
        Assert.assertEquals("2", CollectionUtil.getElement(subList, 0));
        Assert.assertEquals("3", CollectionUtil.getElement(subList, 1));

        // 4
        subList = CollectionUtil.getElements(list, 2, 1);
        Assert.assertEquals(1, subList.size());
        Assert.assertEquals("3", CollectionUtil.getElement(subList, 0));

        // 5
        subList = CollectionUtil.getElements(list, -2, 2);
        Assert.assertEquals(2, subList.size());
        Assert.assertEquals("3", CollectionUtil.getElement(subList, 0));
        Assert.assertNull(CollectionUtil.getElement(subList, 1));

        // 6
        subList = CollectionUtil.getElements(list, -999, 2);
        Assert.assertEquals(2, subList.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subList, 0));
        Assert.assertEquals("2", CollectionUtil.getElement(subList, 1));

        // 7
        subList = CollectionUtil.getElements(list, 1, 999);
        Assert.assertEquals(3, subList.size());
        Assert.assertEquals("2", CollectionUtil.getElement(subList, 0));
        Assert.assertNull(CollectionUtil.getElement(subList, 2));

        // 8
        subList = CollectionUtil.getElements(list, -999, 999);
        Assert.assertEquals(4, subList.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subList, 0));
        Assert.assertNull(CollectionUtil.getElement(subList, 3));

        // 9
        subList = CollectionUtil.getElements(list, 0, 0);
        Assert.assertEquals(0, subList.size());

        // 10
        subList = CollectionUtil.getElements(list, -1, 0);
        Assert.assertEquals(0, subList.size());

        // 11
        subList = CollectionUtil.getElements(list, 4, 1);
        Assert.assertEquals(0, subList.size());

        // 12
        subList = CollectionUtil.getElements(list, 0, -1);
        Assert.assertEquals(0, subList.size());

        // 13
        subList = CollectionUtil.getElements((List<String>) null, 0, 4);
        Assert.assertNull(subList);

        // 14
        subList = CollectionUtil.getElements((List<String>) null, -1, 10);
        Assert.assertNull(subList);

        // 1 LinkedHashSet
        Set<String> set = ArrayUtil.asSet("1", "2", "3", null);
        Set<String> subSet = CollectionUtil.getElements(set, 0, 4);
        Assert.assertTrue(subSet instanceof Set);
        Assert.assertEquals(4, subSet.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subSet, 0));
        Assert.assertNull(CollectionUtil.getElement(subSet, 3));

        // 2
        subSet = CollectionUtil.getElements(set, 0, 10);
        Assert.assertEquals(4, subSet.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subSet, 0));
        Assert.assertNull(CollectionUtil.getElement(subSet, 3));

        // 3
        subSet = CollectionUtil.getElements(set, 1, 2);
        Assert.assertEquals(2, subSet.size());
        Assert.assertEquals("2", CollectionUtil.getElement(subSet, 0));
        Assert.assertEquals("3", CollectionUtil.getElement(subSet, 1));

        // 4
        subSet = CollectionUtil.getElements(set, 2, 1);
        Assert.assertEquals(1, subSet.size());
        Assert.assertEquals("3", CollectionUtil.getElement(subSet, 0));

        // 5
        subSet = CollectionUtil.getElements(set, -2, 2);
        Assert.assertEquals(2, subSet.size());
        Assert.assertEquals("3", CollectionUtil.getElement(subSet, 0));
        Assert.assertNull(CollectionUtil.getElement(subSet, 1));

        // 6
        subSet = CollectionUtil.getElements(set, -999, 2);
        Assert.assertEquals(2, subSet.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subSet, 0));
        Assert.assertEquals("2", CollectionUtil.getElement(subSet, 1));

        // 7
        subSet = CollectionUtil.getElements(set, 1, 999);
        Assert.assertEquals(3, subSet.size());
        Assert.assertEquals("2", CollectionUtil.getElement(subSet, 0));
        Assert.assertNull(CollectionUtil.getElement(subSet, 2));

        // 8
        subSet = CollectionUtil.getElements(set, -999, 999);
        Assert.assertEquals(4, subSet.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subSet, 0));
        Assert.assertNull(CollectionUtil.getElement(subSet, 3));

        // 9
        subSet = CollectionUtil.getElements(set, 0, 0);
        Assert.assertEquals(0, subSet.size());

        // 10
        subSet = CollectionUtil.getElements(set, -1, 0);
        Assert.assertEquals(0, subSet.size());

        // 11
        subSet = CollectionUtil.getElements(set, 4, 1);
        Assert.assertEquals(0, subSet.size());

        // 12
        subSet = CollectionUtil.getElements(set, 0, -1);
        Assert.assertEquals(0, subSet.size());

        // 1 HashSet
        set = new HashSet<String>(ArrayUtil.asSet("1", "2", "3", null));
        subSet = CollectionUtil.getElements(set, 0, 4);
        Assert.assertEquals(4, subSet.size());

        // 2
        subSet = CollectionUtil.getElements(set, -2, 2);
        Assert.assertEquals(2, subSet.size());

        // 3
        subSet = CollectionUtil.getElements(set, 1, 2);
        Assert.assertEquals(2, subSet.size());

        // 4
        subSet = CollectionUtil.getElements(set, 2, 1);
        Assert.assertEquals(1, subSet.size());

        // 5
        subSet = CollectionUtil.getElements(set, -999, 2);
        Assert.assertEquals(2, subSet.size());

        // 6
        subSet = CollectionUtil.getElements(set, 0, 999);
        Assert.assertEquals(4, subSet.size());

        // 7
        subSet = CollectionUtil.getElements(set, -3, 999);
        Assert.assertEquals(3, subSet.size());

        // 8
        subSet = CollectionUtil.getElements(set, 0, 0);
        Assert.assertEquals(0, subSet.size());

        // 9
        subSet = CollectionUtil.getElements(set, 0, -1);
        Assert.assertEquals(0, subSet.size());
    }

    /**
     * 测试subCollection方法
     */
    @Test
    public void testSubCollection()
    {
        // ArrayList
        List<String> list = ArrayUtil.asList("1", "2", "3", null);

        // subCollection(list, start, end)
        // 1
        List<String> subList = CollectionUtil.subCollection(list, 0, 3);
        Assert.assertTrue(subList instanceof List);
        Assert.assertEquals(3, subList.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subList, 0));
        Assert.assertEquals("3", CollectionUtil.getElement(subList, 2));

        // 2
        subList = CollectionUtil.subCollection(list, 1, 2);
        Assert.assertEquals(1, subList.size());
        Assert.assertEquals("2", CollectionUtil.getElement(subList, 0));

        // 3
        subList = CollectionUtil.subCollection(list, 0, 999);
        Assert.assertEquals(4, subList.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subList, 0));
        Assert.assertNull(CollectionUtil.getElement(subList, 3));

        // 4
        subList = CollectionUtil.subCollection(list, -3, 2);
        Assert.assertEquals(1, subList.size());
        Assert.assertEquals("2", CollectionUtil.getElement(subList, 0));

        // 5
        subList = CollectionUtil.subCollection(list, 0, -2);
        Assert.assertEquals(2, subList.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subList, 0));
        Assert.assertEquals("2", CollectionUtil.getElement(subList, 1));

        // 6
        subList = CollectionUtil.subCollection(list, -999, 2);
        Assert.assertEquals(2, subList.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subList, 0));
        Assert.assertEquals("2", CollectionUtil.getElement(subList, 1));

        // 7
        subList = CollectionUtil.subCollection(list, -4, -1);
        Assert.assertEquals(3, subList.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subList, 0));
        Assert.assertEquals("3", CollectionUtil.getElement(subList, 2));

        // 8
        subList = CollectionUtil.subCollection(list, -999, 999);
        Assert.assertEquals(4, subList.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subList, 0));
        Assert.assertNull(CollectionUtil.getElement(subList, 3));

        // 9
        subList = CollectionUtil.subCollection(list, 0, 0);
        Assert.assertEquals(4, subList.size());
        Assert.assertEquals("1", subList.get(0));
        Assert.assertNull(subList.get(3));

        // 10
        subList = CollectionUtil.subCollection(list, 1, 0);
        Assert.assertEquals(3, subList.size());
        Assert.assertEquals("2", subList.get(0));
        Assert.assertNull(subList.get(2));

        // 11
        subList = CollectionUtil.subCollection(list, -1, 0);
        Assert.assertEquals(1, subList.size());
        Assert.assertNull(subList.get(0));

        // 12
        subList = CollectionUtil.subCollection(list, 4, 1);
        Assert.assertEquals(0, subList.size());

        // 13
        subList = CollectionUtil.subCollection(list, 0, -999);
        Assert.assertEquals(0, subList.size());

        // 14
        subList = CollectionUtil.subCollection(list, -1, -3);
        Assert.assertEquals(0, subList.size());

        // 14
        subList = CollectionUtil.subCollection((List<String>) null, 0, 1);
        Assert.assertNull(subList);

        // 15
        subList = CollectionUtil.subCollection((List<String>) null, -999, 999);
        Assert.assertNull(subList);

        // subCollection(list, start)
        // 1
        subList = CollectionUtil.subCollection(list, 0);
        Assert.assertTrue(subList instanceof List);
        Assert.assertEquals(4, subList.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subList, 0));
        Assert.assertNull(CollectionUtil.getElement(subList, 3));

        // 2
        subList = CollectionUtil.subCollection(list, 4);
        Assert.assertEquals(0, subList.size());

        // 3
        subList = CollectionUtil.subCollection(list, 999);
        Assert.assertEquals(0, subList.size());

        // 4
        subList = CollectionUtil.subCollection(list, -3);
        Assert.assertEquals(3, subList.size());
        Assert.assertEquals("2", CollectionUtil.getElement(subList, 0));
        Assert.assertNull(CollectionUtil.getElement(subList, 2));

        // 5
        subList = CollectionUtil.subCollection(list, -999);
        Assert.assertEquals(4, subList.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subList, 0));
        Assert.assertNull(CollectionUtil.getElement(subList, 3));

        // 6
        subList = CollectionUtil.subCollection((List<String>) null, 9);
        Assert.assertNull(subList);

        // LinkedHashSet
        // subCollection(set, start, end)
        // 1
        Set<String> set = ArrayUtil.asSet("1", "2", "3", null);
        Set<String> subSet = CollectionUtil.subCollection(set, 0, 4);
        Assert.assertTrue(subSet instanceof Set);
        Assert.assertEquals(4, subSet.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subSet, 0));
        Assert.assertNull(CollectionUtil.getElement(subSet, 3));

        // 2
        subSet = CollectionUtil.subCollection(set, 1, 2);
        Assert.assertEquals(1, subSet.size());
        Assert.assertEquals("2", CollectionUtil.getElement(subSet, 0));

        // 3
        subSet = CollectionUtil.subCollection(set, 0, 999);
        Assert.assertEquals(4, subSet.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subSet, 0));
        Assert.assertNull(CollectionUtil.getElement(subSet, 3));

        // 4
        subSet = CollectionUtil.subCollection(set, -3, 2);
        Assert.assertEquals(1, subSet.size());
        Assert.assertEquals("2", CollectionUtil.getElement(subSet, 0));

        // 5
        subSet = CollectionUtil.subCollection(set, 0, -2);
        Assert.assertEquals(2, subSet.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subSet, 0));
        Assert.assertEquals("2", CollectionUtil.getElement(subSet, 1));

        // 6
        subSet = CollectionUtil.subCollection(set, -999, 2);
        Assert.assertEquals(2, subSet.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subSet, 0));
        Assert.assertEquals("2", CollectionUtil.getElement(subSet, 1));

        // 7
        subSet = CollectionUtil.subCollection(set, -4, -1);
        Assert.assertEquals(3, subSet.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subSet, 0));
        Assert.assertEquals("3", CollectionUtil.getElement(subSet, 2));

        // 8
        subSet = CollectionUtil.subCollection(set, -999, 999);
        Assert.assertEquals(4, subSet.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subSet, 0));
        Assert.assertNull(CollectionUtil.getElement(subSet, 3));

        // 9
        subSet = CollectionUtil.subCollection(set, 0, 0);
        Assert.assertEquals(subSet.size(), subSet.size());

        // 10
        subSet = CollectionUtil.subCollection(set, -1, 0);
        Assert.assertEquals(1, subSet.size());

        // 11
        subSet = CollectionUtil.subCollection(set, 4, 1);
        Assert.assertEquals(0, subSet.size());

        // 12
        subSet = CollectionUtil.subCollection(set, 0, -999);
        Assert.assertEquals(0, subSet.size());

        // 13
        subSet = CollectionUtil.subCollection(set, -1, -3);
        Assert.assertEquals(0, subSet.size());

        // subCollection(set, start)
        // 1
        Collection<String> subCol = CollectionUtil.subCollection(list, 0);
        Assert.assertTrue(subCol instanceof List);
        Assert.assertEquals(4, subCol.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subCol, 0));
        Assert.assertNull(CollectionUtil.getElement(subCol, 3));

        // 2
        subCol = CollectionUtil.subCollection(list, 4);
        Assert.assertEquals(0, subCol.size());

        // 3
        subCol = CollectionUtil.subCollection(list, 999);
        Assert.assertEquals(0, subCol.size());

        // 4
        subCol = CollectionUtil.subCollection(list, -3);
        Assert.assertEquals(3, subCol.size());
        Assert.assertEquals("2", CollectionUtil.getElement(subCol, 0));
        Assert.assertNull(CollectionUtil.getElement(subCol, 2));

        // 5
        subCol = CollectionUtil.subCollection(list, -999);
        Assert.assertEquals(4, subCol.size());
        Assert.assertEquals("1", CollectionUtil.getElement(subCol, 0));
        Assert.assertNull(CollectionUtil.getElement(subCol, 3));

        // 6
        subCol = CollectionUtil.subCollection((Collection<String>) null, 9);
        Assert.assertNull(subCol);

        // 1 HashSet
        set = new HashSet<String>(ArrayUtil.asSet("1", "2", "3", null));
        subSet = CollectionUtil.subCollection(set, 0, 3);
        Assert.assertEquals(3, subSet.size());

        // 2
        subSet = CollectionUtil.subCollection(set, 1, 2);
        Assert.assertEquals(1, subSet.size());

        // 3
        subSet = CollectionUtil.subCollection(set, -2, 3);
        Assert.assertEquals(1, subSet.size());

        // 4
        subSet = CollectionUtil.subCollection(set, -999, 3);
        Assert.assertEquals(3, subSet.size());

        // 5
        subSet = CollectionUtil.subCollection(set, 0, -3);
        Assert.assertEquals(1, subSet.size());

        // 6
        subSet = CollectionUtil.subCollection(set, 0, 999);
        Assert.assertEquals(4, subSet.size());

        // 7
        subSet = CollectionUtil.subCollection(set, -3, 999);
        Assert.assertEquals(3, subSet.size());

        // 8
        subSet = CollectionUtil.subCollection(set, -3, -1);
        Assert.assertEquals(2, subSet.size());

        // 9
        subSet = CollectionUtil.subCollection(set, 0, 0);
        Assert.assertEquals(set.size(), subSet.size());

        // 10
        subSet = CollectionUtil.subCollection(set, 0, -999);
        Assert.assertEquals(0, subSet.size());
    }

    /**
     * 测试split方法
     */
    @Test
    public void testSplit()
    {
        // 1 ArrayList
        List<String> list = ArrayUtil.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        Collection<String>[] sepLists = CollectionUtil.split(list, 10);
        Assert.assertTrue(sepLists[0] instanceof List);
        Assert.assertEquals(1, sepLists.length);
        Assert.assertEquals(10, sepLists[0].size());
        Assert.assertEquals("9", CollectionUtil.getElement(sepLists[0], 9));

        // 2
        sepLists = CollectionUtil.split(list, 5);
        Assert.assertEquals(2, sepLists.length);
        Assert.assertEquals(5, sepLists[0].size());
        Assert.assertEquals(5, sepLists[1].size());
        Assert.assertEquals("4", CollectionUtil.getElement(sepLists[0], 4));
        Assert.assertEquals("9", CollectionUtil.getElement(sepLists[1], 4));

        // 3
        sepLists = CollectionUtil.split(list, 3);
        Assert.assertEquals(4, sepLists.length);
        Assert.assertEquals(3, sepLists[0].size());
        Assert.assertEquals(3, sepLists[1].size());
        Assert.assertEquals(3, sepLists[2].size());
        Assert.assertEquals(1, sepLists[3].size());
        Assert.assertEquals("2", CollectionUtil.getElement(sepLists[0], 2));
        Assert.assertEquals("5", CollectionUtil.getElement(sepLists[1], 2));
        Assert.assertEquals("8", CollectionUtil.getElement(sepLists[2], 2));
        Assert.assertEquals("9", CollectionUtil.getElement(sepLists[3], 0));

        // 4
        sepLists = CollectionUtil.split(list, 20);
        Assert.assertEquals(1, sepLists.length);
        Assert.assertEquals(10, sepLists[0].size());
        Assert.assertEquals("9", CollectionUtil.getElement(sepLists[0], 9));

        // 5
        sepLists = CollectionUtil.split(list, 0);
        Assert.assertEquals(1, sepLists.length);
        Assert.assertEquals(10, sepLists[0].size());
        Assert.assertEquals("9", CollectionUtil.getElement(sepLists[0], 9));

        // 6
        sepLists = CollectionUtil.split(list, -1);
        Assert.assertEquals(1, sepLists.length);
        Assert.assertEquals(10, sepLists[0].size());
        Assert.assertEquals("9", CollectionUtil.getElement(sepLists[0], 9));

        // 7
        sepLists = CollectionUtil.split(new ArrayList<String>(), 10);
        Assert.assertEquals(1, sepLists.length);
        Assert.assertEquals(0, sepLists[0].size());

        // 8
        sepLists = CollectionUtil.split((List<String>) null, 10);
        Assert.assertNull(sepLists);

        // 9
        sepLists = CollectionUtil.split(list, 10);
        list.remove("0"); // c != sepColls[0]
        Assert.assertEquals("0", CollectionUtil.getElement(sepLists[0], 0));

        // 1 LinkedHashSet
        Set<String> set = ArrayUtil.asSet("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        Collection<String>[] sepSets = CollectionUtil.split(set, 10);
        Assert.assertTrue(sepSets[0] instanceof Set);
        Assert.assertEquals(1, sepSets.length);
        Assert.assertEquals(10, sepSets[0].size());
        Assert.assertEquals("9", CollectionUtil.getElement(sepSets[0], 9));

        // 2
        sepSets = CollectionUtil.split(set, 5);
        Assert.assertEquals(2, sepSets.length);
        Assert.assertEquals(5, sepSets[0].size());
        Assert.assertEquals(5, sepSets[1].size());
        Assert.assertEquals("4", CollectionUtil.getElement(sepSets[0], 4));
        Assert.assertEquals("9", CollectionUtil.getElement(sepSets[1], 4));

        // 3
        sepSets = CollectionUtil.split(set, 3);
        Assert.assertEquals(4, sepSets.length);
        Assert.assertEquals(3, sepSets[0].size());
        Assert.assertEquals(3, sepSets[1].size());
        Assert.assertEquals(3, sepSets[2].size());
        Assert.assertEquals(1, sepSets[3].size());
        Assert.assertEquals("2", CollectionUtil.getElement(sepSets[0], 2));
        Assert.assertEquals("5", CollectionUtil.getElement(sepSets[1], 2));
        Assert.assertEquals("8", CollectionUtil.getElement(sepSets[2], 2));
        Assert.assertEquals("9", CollectionUtil.getElement(sepSets[3], 0));

        // 4
        sepSets = CollectionUtil.split(set, 20);
        Assert.assertEquals(1, sepSets.length);
        Assert.assertEquals(10, sepSets[0].size());
        Assert.assertEquals("9", CollectionUtil.getElement(sepSets[0], 9));

        // 5
        sepSets = CollectionUtil.split(set, 0);
        Assert.assertEquals(1, sepSets.length);
        Assert.assertEquals(10, sepSets[0].size());
        Assert.assertEquals("9", CollectionUtil.getElement(sepSets[0], 9));

        // 5
        sepSets = CollectionUtil.split(set, -1);
        Assert.assertEquals(1, sepSets.length);
        Assert.assertEquals(10, sepSets[0].size());
        Assert.assertEquals("9", CollectionUtil.getElement(sepSets[0], 9));

        // 6
        sepSets = CollectionUtil.split(new HashSet<String>(), 10);
        Assert.assertEquals(1, sepSets.length);
        Assert.assertEquals(0, sepSets[0].size());

        // 8
        sepSets = CollectionUtil.split((Set<String>) null, 10);
        Assert.assertNull(sepSets);

        // 9
        sepSets = CollectionUtil.split(set, 10);
        set.remove("0"); // c != sepColls[0]
        Assert.assertEquals("0", CollectionUtil.getElement(sepSets[0], 0));

        // 1 HashSet
        set = new HashSet<String>(ArrayUtil.asSet("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
        sepSets = CollectionUtil.split(set, 3);
        Assert.assertEquals(4, sepSets.length);
        Assert.assertEquals(3, sepSets[0].size());
        Assert.assertEquals(3, sepSets[1].size());
        Assert.assertEquals(3, sepSets[2].size());
        Assert.assertEquals(1, sepSets[3].size());
    }

    /**
     * 测试newCollection方法
     */
    @Test
    public void testNewCollection()
    {
        // 1
        List<Object> list = CollectionUtil.newCollection(new ArrayList<String>());
        Assert.assertNotNull(list);
        list.add("Test");

        // 2
        list = CollectionUtil.newCollection(ArrayList.class);
        Assert.assertNotNull(list);
        list.add("Test");

        // 3
        list = CollectionUtil.newCollection(List.class);
        Assert.assertNotNull(list);
        Assert.assertEquals(ArrayList.class, list.getClass());
        list.add("Test");

        // 4
        Set<Object> set = CollectionUtil.newCollection(Set.class);
        Assert.assertNotNull(set);
        Assert.assertEquals(LinkedHashSet.class, set.getClass());
        set.add("Test");

        // 5
        Assert.assertNull(CollectionUtil.newCollection(null));
        Assert.assertNull(CollectionUtil.newCollection(Object.class));
        Assert.assertNull(CollectionUtil.newCollection(new Object()));
        Assert.assertNull(CollectionUtil.newCollection(SingletonList.class));
    }

    /**
     * 测试addIfNotNull方法
     */
    @Test
    public void testAddIfNotNull()
    {
        List<String> list = new ArrayList<String>();
        Assert.assertTrue(CollectionUtil.addIfNotNull(list, "1"));
        Assert.assertTrue(CollectionUtil.addIfNotNull(list, "2"));
        Assert.assertTrue(CollectionUtil.addIfNotNull(list, "1"));
        Assert.assertTrue(CollectionUtil.addIfNotNull(list, "2"));
        Assert.assertFalse(CollectionUtil.addIfNotNull(list, null));

        Set<String> set = new HashSet<String>();
        Assert.assertTrue(CollectionUtil.addIfNotNull(set, "1"));
        Assert.assertTrue(CollectionUtil.addIfNotNull(set, "2"));
        Assert.assertFalse(CollectionUtil.addIfNotNull(set, "1"));
        Assert.assertFalse(CollectionUtil.addIfNotNull(set, "2"));
        Assert.assertFalse(CollectionUtil.addIfNotNull(set, null));

        Assert.assertFalse(CollectionUtil.addIfNotNull(null, "1"));
        Assert.assertFalse(CollectionUtil.addIfNotNull(null, null));
    }

    /**
     * 测试addAllIfNotEmpty方法
     */
    @Test
    public void testAddAllIfNotEmpty()
    {
        // 1
        List<String> l1 = ArrayUtil.asList("a");
        List<String> l2 = ArrayUtil.asList("1", "2", "3", null);
        Assert.assertTrue(CollectionUtil.addAllIfNotEmpty(l1, l2));
        Assert.assertEquals("a", l1.get(0));
        Assert.assertEquals("1", l1.get(1));
        Assert.assertEquals("2", l1.get(2));
        Assert.assertEquals("3", l1.get(3));
        Assert.assertNull(l1.get(4));

        Assert.assertFalse(CollectionUtil.addAllIfNotEmpty(l1, null));
        Assert.assertFalse(CollectionUtil.addAllIfNotEmpty(l1, new ArrayList<String>()));

        // 2
        Set<String> s1 = ArrayUtil.asSet("a");
        Assert.assertTrue(CollectionUtil.addAllIfNotEmpty(s1, l2));
        Assert.assertTrue(s1.contains("a"));
        Assert.assertTrue(s1.contains("1"));
        Assert.assertTrue(s1.contains("2"));
        Assert.assertTrue(s1.contains("3"));
        Assert.assertTrue(s1.contains(null));

        Assert.assertFalse(CollectionUtil.addAllIfNotEmpty(s1, null));
        Assert.assertFalse(CollectionUtil.addAllIfNotEmpty(s1, new HashSet<String>()));

        // 3
        Set<String> s2 = ArrayUtil.asSet("1", "2", "3", null); // LinkedHashSet
        Assert.assertFalse(CollectionUtil.addAllIfNotEmpty(s1, s2)); // no-change

        l1 = ArrayUtil.asList("a");
        Assert.assertTrue(CollectionUtil.addAllIfNotEmpty(l1, s2));
        Assert.assertEquals("a", l1.get(0));
        Assert.assertEquals("1", l1.get(1)); // with-orders
        Assert.assertEquals("2", l1.get(2));
        Assert.assertEquals("3", l1.get(3));
        Assert.assertNull(l1.get(4));
    }

    /**
     * 测试单例列表类
     *
     * @author Roger.Yi
     */
    static class SingletonList extends ArrayList<String>
    {
        private static final long serialVersionUID = 1L;

        private static SingletonList instance = new SingletonList();

        private SingletonList()
        {
        }

        public static SingletonList getInstance()
        {
            return instance;
        }
    }
}
