package com.yee.study.util;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

/**
 * 数组工具测试类
 * 
 * @author Roger.Yi
 */
public class ArrayUtilTest
{
	/**
	 * 测试length方法
	 */
	@Test
	public void testLength() {
		// length(Object[])
		Assert.assertEquals(1, ArrayUtil.length(new String[] { "1" }));
		Assert.assertEquals(2, ArrayUtil.length(new String[] { "1", "2" }));
		Assert.assertEquals(0, ArrayUtil.length(new String[0]));
		Assert.assertEquals(0, ArrayUtil.length((Object[]) null));

		// length(Object[][])
		Assert.assertEquals(3, ArrayUtil.length((String[]) null, new String[] { "1" }, new String[] { "1", "2" }));
		Assert.assertEquals(0, ArrayUtil.length((Object[][]) null));
		Assert.assertEquals(0, ArrayUtil.length());
	}

	/**
	 * 测试isEmpty方法
	 */
	@Test
	public void testIsEmpty() {
		Assert.assertTrue(ArrayUtil.isEmpty(null));
		Assert.assertTrue(ArrayUtil.isEmpty(new Object[0]));
		Assert.assertFalse(ArrayUtil.isEmpty(new String[] { "" }));
	}

	/**
	 * 测试isNotEmpty方法
	 */
	@Test
	public void testIsNotEmpty() {
		Assert.assertFalse(ArrayUtil.isNotEmpty(null));
		Assert.assertFalse(ArrayUtil.isNotEmpty(new Object[0]));
		Assert.assertTrue(ArrayUtil.isNotEmpty(new String[] { "" }));
	}

	/**
	 * 测试contains方法
	 */
	@Test
	public void testContains() {
		String[] sArr = { "1", "2", "3", null };
		Assert.assertTrue(ArrayUtil.contains(sArr, "1"));
		Assert.assertTrue(ArrayUtil.contains(sArr, "3"));
		Assert.assertTrue(ArrayUtil.contains(sArr, null));
		Assert.assertFalse(ArrayUtil.contains(sArr, "4"));
		Assert.assertFalse(ArrayUtil.contains(new String[0], ""));
		Assert.assertFalse(ArrayUtil.contains(null, ""));
	}

	/**
	 * 测试containsAny方法
	 */
	@Test
	public void testContainsAny() {
		String[] sArr = { "1", "2", "3", null };
		Assert.assertTrue(ArrayUtil.containsAny(sArr, "1"));
		Assert.assertTrue(ArrayUtil.containsAny(sArr, "3"));
		Assert.assertTrue(ArrayUtil.containsAny(sArr, (String) null));
		Assert.assertFalse(ArrayUtil.containsAny(sArr, "4"));
		Assert.assertFalse(ArrayUtil.containsAny(new String[0], ""));
		Assert.assertFalse(ArrayUtil.containsAny(null, ""));
		Assert.assertTrue(ArrayUtil.containsAny(sArr, "1", "0"));
		Assert.assertTrue(ArrayUtil.containsAny(sArr, "0", "1"));
		Assert.assertTrue(ArrayUtil.containsAny(sArr, "3", "0"));
		Assert.assertTrue(ArrayUtil.containsAny(sArr, (String) null, "0"));
		Assert.assertFalse(ArrayUtil.containsAny(sArr, "4", "0"));
		Assert.assertFalse(ArrayUtil.containsAny(sArr, "0", "4"));
		Assert.assertFalse(ArrayUtil.containsAny(new String[0], "", "0"));
		Assert.assertFalse(ArrayUtil.containsAny(null, "", "0"));
		Assert.assertFalse(ArrayUtil.containsAny(null));
	}

	/**
	 * 测试containsAll方法
	 */
	@Test
	public void testContainsAll() {
		String[] sArr = { "1", "2", "3", null };
		Assert.assertTrue(ArrayUtil.containsAll(sArr, "1"));
		Assert.assertTrue(ArrayUtil.containsAll(sArr, "3"));
		Assert.assertTrue(ArrayUtil.containsAll(sArr, (String) null));
		Assert.assertFalse(ArrayUtil.containsAll(sArr, "4"));
		Assert.assertFalse(ArrayUtil.containsAll(new String[0], ""));
		Assert.assertFalse(ArrayUtil.containsAll(null, ""));
		Assert.assertTrue(ArrayUtil.containsAll(sArr, "1", "2"));
		Assert.assertTrue(ArrayUtil.containsAll(sArr, "2", "1"));
		Assert.assertTrue(ArrayUtil.containsAll(sArr, "3", "2"));
		Assert.assertTrue(ArrayUtil.containsAll(sArr, (String) null, "2"));
		Assert.assertFalse(ArrayUtil.containsAll(sArr, "4", "2"));
		Assert.assertFalse(ArrayUtil.containsAll(sArr, "2", "4"));
		Assert.assertFalse(ArrayUtil.containsAll(new String[0], "", "2"));
		Assert.assertFalse(ArrayUtil.containsAll(null, "", "2"));
		Assert.assertFalse(ArrayUtil.containsAll(null));
	}

	/**
	 * 测试getElement方法
	 */
	@Test
	public void testGetElement() {
		// 1
		String[] sArr = { "1", "2", "3", null };
		Assert.assertEquals("1", ArrayUtil.getElement(sArr, 0));
		Assert.assertEquals("3", ArrayUtil.getElement(sArr, 2));
		Assert.assertEquals("2", ArrayUtil.getElement(sArr, -3));
		Assert.assertNull(ArrayUtil.getElement(sArr, 3));
		Assert.assertNull(ArrayUtil.getElement(sArr, -999));
		Assert.assertNull(ArrayUtil.getElement(sArr, 999));
		Assert.assertNull(ArrayUtil.getElement(null, 0));
		Assert.assertNull(ArrayUtil.getElement(new String[0], 0));

		// 2
		Assert.assertEquals("1", ArrayUtil.getElement(sArr, 0, "4"));
		Assert.assertEquals("3", ArrayUtil.getElement(sArr, 2, "4"));
		Assert.assertEquals("2", ArrayUtil.getElement(sArr, -3, "4"));
		Assert.assertNull(ArrayUtil.getElement(sArr, -1, "4")); // 只有下标越界时才会返回defElement
		Assert.assertEquals("4", ArrayUtil.getElement(sArr, -999, "4"));
		Assert.assertNull(ArrayUtil.getElement(sArr, 3, "4"));
		Assert.assertEquals("4", ArrayUtil.getElement(sArr, 999, "4"));
		Assert.assertEquals("4", ArrayUtil.getElement(null, 3, "4"));
		Assert.assertEquals("4", ArrayUtil.getElement(new String[0], 0, "4"));
	}

	/**
	 * 测试getElements方法
	 */
	@Test
	public void testGetElements() {
		// 1 getElements(offset, length)
		final String[] sArr = { "1", "2", "3", null };
		String[] subArr = ArrayUtil.getElements(sArr, 0, 4);

		Assert.assertEquals(4, subArr.length);
		Assert.assertEquals("1", subArr[0]);
		Assert.assertNull(subArr[3]);

		// 2
		subArr = ArrayUtil.getElements(sArr, 0, 10);
		Assert.assertEquals(4, subArr.length);
		Assert.assertEquals("1", subArr[0]);
		Assert.assertNull(subArr[3]);

		// 3
		subArr = ArrayUtil.getElements(sArr, -999, 2);
		Assert.assertEquals(2, subArr.length);
		Assert.assertEquals("1", subArr[0]);
		Assert.assertEquals("2", subArr[1]);

		// 4
		subArr = ArrayUtil.getElements(sArr, -2, 1);
		Assert.assertEquals(1, subArr.length);
		Assert.assertEquals("3", subArr[0]);

		// 5
		subArr = ArrayUtil.getElements(sArr, -2, 999);
		Assert.assertEquals(2, subArr.length);
		Assert.assertEquals("3", subArr[0]);
		Assert.assertNull(subArr[1]);

		// 6
		subArr = ArrayUtil.getElements(sArr, 0, 0);
		Assert.assertEquals(0, subArr.length);

		// 7
		subArr = ArrayUtil.getElements(sArr, -1, 0);
		Assert.assertEquals(0, subArr.length);

		// 8
		subArr = ArrayUtil.getElements(sArr, 4, 1);
		Assert.assertEquals(0, subArr.length);

		// 9
		subArr = ArrayUtil.getElements(sArr, 0, -1);
		Assert.assertEquals(0, subArr.length);

		// 10
		subArr = ArrayUtil.getElements(null, 0, 4);
		Assert.assertNull(subArr);

		// 11
		subArr = ArrayUtil.getElements(null, -1, 10);
		Assert.assertNull(subArr);
	}

	/**
	 * 测试subArray方法
	 */
	@Test
	public void testSubArray() {
		final String[] sArr = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

		// subArray(arr, start, end[exclude])
		// 1
		String[] subArr = ArrayUtil.subArray(sArr, 1, 3);
		Assert.assertEquals(2, subArr.length);
		Assert.assertEquals(sArr[1], subArr[0]);
		Assert.assertEquals(sArr[2], subArr[1]);

		// 2
		subArr = ArrayUtil.subArray(sArr, 1, 999);
		Assert.assertEquals(9, subArr.length);
		Assert.assertEquals(sArr[1], subArr[0]);
		Assert.assertEquals(sArr[9], subArr[8]);

		// 3
		subArr = ArrayUtil.subArray(sArr, -3, 9);
		Assert.assertEquals(2, subArr.length);
		Assert.assertEquals(sArr[7], subArr[0]);
		Assert.assertEquals(sArr[8], subArr[1]);

		// 4
		subArr = ArrayUtil.subArray(sArr, -999, 1);
		Assert.assertEquals(1, subArr.length);
		Assert.assertEquals(sArr[0], subArr[0]);

		// 5
		subArr = ArrayUtil.subArray(sArr, -999, 999);
		Assert.assertEquals(10, subArr.length);
		Assert.assertEquals(sArr[0], subArr[0]);
		Assert.assertEquals(sArr[9], subArr[9]);

		// 6
		subArr = ArrayUtil.subArray(sArr, 5, -2);
		Assert.assertEquals(3, subArr.length);
		Assert.assertEquals(sArr[5], subArr[0]);
		Assert.assertEquals(sArr[7], subArr[2]);

		// 7
		subArr = ArrayUtil.subArray(sArr, -4, -2);
		Assert.assertEquals(2, subArr.length);
		Assert.assertEquals(sArr[6], subArr[0]);
		Assert.assertEquals(sArr[7], subArr[1]);

		// 8
		subArr = ArrayUtil.subArray(sArr, 0, 0);
		Assert.assertArrayEquals(sArr, subArr);

		// 9
		subArr = ArrayUtil.subArray(sArr, 1, 0);
		Assert.assertEquals(9, subArr.length);
		Assert.assertEquals(sArr[1], subArr[0]);
		Assert.assertEquals(sArr[9], subArr[8]);

		// 10
		subArr = ArrayUtil.subArray(sArr, -1, 0);
		Assert.assertEquals(1, subArr.length);
		Assert.assertEquals(sArr[9], subArr[0]);

		// 11
		subArr = ArrayUtil.subArray(sArr, 4, 2);
		Assert.assertEquals(0, subArr.length);

		// 12
		subArr = ArrayUtil.subArray(sArr, 100, 102);
		Assert.assertEquals(0, subArr.length);

		// 13
		subArr = ArrayUtil.subArray(sArr, -1, -2);
		Assert.assertEquals(0, subArr.length);

		// 14
		subArr = ArrayUtil.subArray(sArr, 0, -999);
		Assert.assertEquals(0, subArr.length);

		// 15
		subArr = ArrayUtil.subArray(new String[0], 0, 1);
		Assert.assertEquals(0, subArr.length);

		// 16
		Assert.assertNull(ArrayUtil.subArray(null, 0, 1));

		// subArray(arr, start)
		// 1
		subArr = ArrayUtil.subArray(sArr, 1);
		Assert.assertEquals(9, subArr.length);
		Assert.assertEquals(sArr[1], subArr[0]);
		Assert.assertEquals(sArr[9], subArr[8]);

		// 2
		subArr = ArrayUtil.subArray(sArr, 10);
		Assert.assertEquals(0, subArr.length);

		// 3
		subArr = ArrayUtil.subArray(sArr, 999);
		Assert.assertEquals(0, subArr.length);

		// 4
		subArr = ArrayUtil.subArray(sArr, -9);
		Assert.assertEquals(9, subArr.length);
		Assert.assertEquals(sArr[1], subArr[0]);
		Assert.assertEquals(sArr[9], subArr[8]);

		// 5
		subArr = ArrayUtil.subArray(sArr, -10);
		Assert.assertEquals(10, subArr.length);
		Assert.assertEquals(sArr[0], subArr[0]);
		Assert.assertEquals(sArr[9], subArr[9]);

		// 6
		subArr = ArrayUtil.subArray(sArr, -999);
		Assert.assertEquals(10, subArr.length);
		Assert.assertEquals(sArr[0], subArr[0]);
		Assert.assertEquals(sArr[9], subArr[9]);

		// 7
		Assert.assertNull(ArrayUtil.subArray(null, 0));
	}

	/**
	 * 测试split方法
	 */
	@Test
	public void testSplit() {
		// 1
		final String[] sArr = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[][] sepArrs = ArrayUtil.split(sArr, 10);
		Assert.assertEquals(1, sepArrs.length);
		Assert.assertEquals(10, sepArrs[0].length);
		Assert.assertEquals("9", sepArrs[0][9]);

		// 2
		sepArrs = ArrayUtil.split(sArr, 5);
		Assert.assertEquals(2, sepArrs.length);
		Assert.assertEquals(5, sepArrs[0].length);
		Assert.assertEquals(5, sepArrs[1].length);
		Assert.assertEquals("4", sepArrs[0][4]);
		Assert.assertEquals("9", sepArrs[1][4]);

		// 3
		sepArrs = ArrayUtil.split(sArr, 3);
		Assert.assertEquals(4, sepArrs.length);
		Assert.assertEquals(3, sepArrs[0].length);
		Assert.assertEquals(3, sepArrs[1].length);
		Assert.assertEquals(3, sepArrs[2].length);
		Assert.assertEquals(1, sepArrs[3].length);
		Assert.assertEquals("2", sepArrs[0][2]);
		Assert.assertEquals("5", sepArrs[1][2]);
		Assert.assertEquals("8", sepArrs[2][2]);
		Assert.assertEquals("9", sepArrs[3][0]);

		// 4
		sepArrs = ArrayUtil.split(sArr, 3, true);
		Assert.assertEquals(4, sepArrs.length);
		Assert.assertEquals(3, sepArrs[0].length);
		Assert.assertEquals(3, sepArrs[1].length);
		Assert.assertEquals(3, sepArrs[2].length);
		Assert.assertEquals(3, sepArrs[3].length);
		Assert.assertEquals("2", sepArrs[0][2]);
		Assert.assertEquals("5", sepArrs[1][2]);
		Assert.assertEquals("8", sepArrs[2][2]);
		Assert.assertEquals("9", sepArrs[3][0]);
		Assert.assertEquals(null, sepArrs[3][2]);

		// 5
		sepArrs = ArrayUtil.split(sArr, 20);
		Assert.assertEquals(1, sepArrs.length);
		Assert.assertEquals(10, sepArrs[0].length);
		Assert.assertEquals("9", sepArrs[0][9]);

		// 6
		sepArrs = ArrayUtil.split(sArr, 20, true);
		Assert.assertEquals(1, sepArrs.length);
		Assert.assertEquals(20, sepArrs[0].length);
		Assert.assertEquals("9", sepArrs[0][9]);
		Assert.assertEquals(null, sepArrs[0][19]);

		// 7
		sepArrs = ArrayUtil.split(sArr, 0);
		Assert.assertEquals(1, sepArrs.length);
		Assert.assertEquals(10, sepArrs[0].length);
		Assert.assertEquals("9", sepArrs[0][9]);

		// 8
		sepArrs = ArrayUtil.split(sArr, -1);
		Assert.assertEquals(1, sepArrs.length);
		Assert.assertEquals(10, sepArrs[0].length);
		Assert.assertEquals("9", sepArrs[0][9]);

		// 9
		sepArrs = ArrayUtil.split(new String[0], 10);
		Assert.assertEquals(1, sepArrs.length);
		Assert.assertEquals(0, sepArrs[0].length);

		// 10
		sepArrs = ArrayUtil.split(null, 10);
		Assert.assertNull(sepArrs);

		// 11
		sepArrs = ArrayUtil.split(sArr, 10);
		sArr[0] = "00"; // sArr != sArr[0]
		Assert.assertEquals("0", sepArrs[0][0]);
	}

	/**
	 * 测试concat方法
	 */
	@Test
	public void testConcat() {
		final Object[] arr1 = { "a", 'a', (byte) 1 };
		final Object[] arr2 = ArrayUtil.asArray(null, 1, new Object());
		final String[] arr3 = { "a", "测试", "C" };
		final Object[] arr4 = ArrayUtil.concat((Integer[]) null, arr1, arr2, arr3);
		Assert.assertEquals(arr1[0], arr4[0]);
		Assert.assertEquals(arr1[1], arr4[1]);
		Assert.assertEquals(arr1[2], arr4[2]);
		Assert.assertEquals(arr2[0], arr4[3]);
		Assert.assertEquals(arr2[1], arr4[4]);
		Assert.assertEquals(arr2[2], arr4[5]);
		Assert.assertEquals(arr3[0], arr4[6]);
		Assert.assertEquals(arr3[1], arr4[7]);
		Assert.assertEquals(arr3[2], arr4[8]);

		Assert.assertEquals(0, ArrayUtil.concat((Object[]) null, new Integer[0]).length);
		Assert.assertEquals(1, ArrayUtil.concat((Object[]) null, new Object[] { null }).length);
		Assert.assertNull(ArrayUtil.concat((Object[][]) null));
	}

	/**
	 * 测试newArray方法
	 */
	@Test
	public void testNewArray() {
		// 1
		Object[] array = ArrayUtil.newArray(new Object[0], new int[] { 5 });
		Assert.assertEquals(5, array.length);
		array[0] = new Object();

		// 2
		array = ArrayUtil.newArray(Integer.class, 5);
		Assert.assertEquals(5, array.length);
		array[0] = Integer.valueOf(1);
		try {
			array[1] = new Object(); // ClassCastException
			Assert.fail();
		} catch (Exception e) {
		}

		// 3
		array = ArrayUtil.newArray(new Long[0], new int[] { 2 });
		Assert.assertEquals(2, array.length);
		array[0] = Long.valueOf(1L);
		try {
			array[1] = new Object(); // ClassCastException
			Assert.fail();
		} catch (Exception e) {
		}

		// 4
		array = ArrayUtil.newArray(Double.valueOf(1D), 5);
		Assert.assertEquals(5, array.length);
		array[0] = Double.valueOf(1L);
		try {
			array[1] = new Object(); // ClassCastException
			Assert.fail();
		} catch (Exception e) {
		}

		// 5
		array = ArrayUtil.newArray(new Void[0], 0);
		Assert.assertEquals(0, array.length);

		// 6
		try {
			array = ArrayUtil.newArray(Integer.TYPE, 5); // ClassCastException
			Assert.fail();
		} catch (Exception e) {
		}

		// 7
		String[][] sArr = ArrayUtil.newArray(new String[0], new int[] { 5, 5 });
		Assert.assertEquals(5, sArr.length);
		Assert.assertEquals(5, sArr[0].length);
		sArr[0][0] = "Test";

		// 8
		Assert.assertNull(ArrayUtil.newArray(new Object[0], new int[] { 1, -1 }));
		Assert.assertNull(ArrayUtil.newArray(new Object(), new int[] { -1 }));
		Assert.assertNull(ArrayUtil.newArray(null, new int[] { 1 }));

		// 9
		Assert.assertNull(ArrayUtil.newArray(new Object(), -1));
		Assert.assertNull(ArrayUtil.newArray(null, 1));

		// 10
		long[] lArr = ArrayUtil.newArray(Long.TYPE, 1);
		Assert.assertEquals(1, lArr.length);
		lArr[0] = 1L;

		// 11
		int[] dimensions = new int[256];
		for (int i = 0; i < dimensions.length; i++) {
			dimensions[i] = 1;
		}
		// 数组维数最高为255
		Assert.assertNull(ArrayUtil.newArray(new Object[0], dimensions));
	}

	/**
	 * 测试asArray方法
	 */
	@Test
	@SuppressWarnings("all")
	public void testAsArray() {
		String[] sArr = ArrayUtil.asArray("1", "2", "3");
		Assert.assertEquals(3, sArr.length);
		Assert.assertEquals("3", sArr[2]);

		Object[] iArr = ArrayUtil.asArray();
		Assert.assertEquals(0, iArr.length);

		iArr = ArrayUtil.asArray((Integer) null);
		Assert.assertEquals(1, iArr.length);

		Object[] oArr = ArrayUtil.asArray(null);
		Assert.assertNull(oArr);

		oArr = ArrayUtil.asArray(new Object[0]);
		Assert.assertEquals(0, oArr.length);
	}

	/**
	 * 测试asMap
	 */
	@Test
	public void testAsMap() {
		/*
		 * Map<T, T> asMap(T[][] objs)
		 */
		String[][] arrayA = new String[][] { { "a", "1" }, { "b", "2" } };
		Map<String, String> map = ArrayUtil.asMap(arrayA);

		Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
		Map.Entry<String, String> entry = iterator.next();
		Assert.assertEquals("a", entry.getKey());
		Assert.assertEquals("1", entry.getValue());

		entry = iterator.next();
		Assert.assertEquals("b", entry.getKey());
		Assert.assertEquals("2", entry.getValue());

		Object[][] arrayB = new Object[][] { { "a", "1" }, { "b", "2" } };
		Map<Object, Object> mapB = (Map<Object, Object>) ArrayUtil.asMap(arrayB);

		Iterator<Map.Entry<Object, Object>> iteratorB = mapB.entrySet().iterator();
		Map.Entry<Object, Object> entryB = iteratorB.next();
		Assert.assertEquals("a", entryB.getKey());
		Assert.assertEquals("1", entryB.getValue());

		entryB = iteratorB.next();
		Assert.assertEquals("b", entryB.getKey());
		Assert.assertEquals("2", entryB.getValue());

		map = ArrayUtil.asMap(new String[0][0]);
		Assert.assertEquals(0, map.size());
		map = ArrayUtil.asMap((String[][]) null);
		Assert.assertEquals(0, map.size());

		/*
		 * Map<T, T> asMap(T... objs)
		 */
		map = ArrayUtil.asMap("a", "1");
		iterator = map.entrySet().iterator();
		entry = iterator.next();
		Assert.assertEquals("a", entry.getKey());
		Assert.assertEquals("1", entry.getValue());

		map = ArrayUtil.asMap("a", "1", "b");
		iterator = map.entrySet().iterator();
		entry = iterator.next();
		Assert.assertEquals("a", entry.getKey());
		Assert.assertEquals("1", entry.getValue());

		entry = iterator.next();
		Assert.assertEquals("b", entry.getKey());
		Assert.assertNull(entry.getValue());

		map = ArrayUtil.asMap(new String[0]);
		Assert.assertEquals(0, map.size());
		map = ArrayUtil.asMap((String[]) null);
		Assert.assertEquals(0, map.size());

		/*
		 * Map<K, V> asMultiTypeMap(T... objs)
		 */
		Map<String, Object> mapKV = ArrayUtil.asMultiTypeMap("a", "b");
		Iterator<Map.Entry<String, Object>> iteratorKV = mapKV.entrySet().iterator();
		Map.Entry<String, Object> entryKV = iteratorKV.next();
		Assert.assertEquals("c", entryKV.getKey().replaceAll("a", "c"));
		Assert.assertEquals("d", ((String) entryKV.getValue()).replaceAll("b", "d"));

		mapKV = ArrayUtil.asMultiTypeMap(new Object[0]);
		Assert.assertEquals(0, mapKV.size());
		mapKV = ArrayUtil.asMultiTypeMap((Object[]) null);
		Assert.assertEquals(0, mapKV.size());

		/*
		 * Map<K, V> asMultiTypeMap(T[][] objs)
		 */
		Object[][] arrayC = new Object[][] { { "a", "1" }, { "b", "2" } };
		mapKV = ArrayUtil.asMultiTypeMap(arrayC);
		iteratorKV = mapKV.entrySet().iterator();
		entryKV = iteratorKV.next();
		Assert.assertEquals("c", entryKV.getKey().replaceAll("a", "c"));
		Assert.assertEquals("2", ((String) entryKV.getValue()).replaceAll("1", "2"));
		entryKV = iteratorKV.next();
		Assert.assertEquals("d", entryKV.getKey().replaceAll("b", "d"));
		Assert.assertEquals("3", ((String) entryKV.getValue()).replaceAll("2", "3"));

		mapKV = ArrayUtil.asMultiTypeMap(new Object[0][0]);
		Assert.assertEquals(0, mapKV.size());
		mapKV = ArrayUtil.asMultiTypeMap((Object[][]) null);
		Assert.assertEquals(0, mapKV.size());
	}

	/**
	 * 测试asList方法
	 */
	@Test
	@SuppressWarnings("all")
	public void testAsList() {
		// asList
		List<String> list = ArrayUtil.asList("1", null, "3");
		Assert.assertEquals(3, list.size());
		Assert.assertEquals(null, list.get(1));
		Assert.assertEquals("3", list.get(2));

		list = ArrayUtil.asList((String) null);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(null, list.get(0));

		list = ArrayUtil.asList(null, null);
		Assert.assertEquals(2, list.size());
		Assert.assertEquals(null, list.get(1));

		list = ArrayUtil.asList((String[]) null);
		Assert.assertNull(list);

		Assert.assertNull(ArrayUtil.asList(null));
		Assert.assertTrue(ArrayUtil.asList(new Object[0]).isEmpty());
		Assert.assertTrue(ArrayUtil.asList().isEmpty());

		// asListNoNull
		list = ArrayUtil.asListNoNull("1", null, "3");
		Assert.assertEquals(2, list.size());
		Assert.assertEquals("1", list.get(0));
		Assert.assertEquals("3", list.get(1));

		list = ArrayUtil.asListNoNull(null, null);
		Assert.assertNull(list);

		Assert.assertNull(ArrayUtil.asListNoNull(null));
		Assert.assertNull(ArrayUtil.asListNoNull(new Object[0]));
		Assert.assertNull(ArrayUtil.asListNoNull());
	}

	/**
	 * 测试asSet方法
	 */
	@Test
	@SuppressWarnings("all")
	public void testAsSet() {
		// asSet
		Set<String> set = ArrayUtil.asSet("1", null, "3");
		Assert.assertEquals(3, set.size());
		Assert.assertEquals(null, set.toArray()[1]);
		Assert.assertEquals("3", set.toArray()[2]);

		set = ArrayUtil.asSet((String) null);
		Assert.assertEquals(1, set.size());
		Assert.assertEquals(null, set.toArray()[0]);

		set = ArrayUtil.asSet(null, null);
		Assert.assertEquals(1, set.size()); // same object
		Assert.assertEquals(null, set.toArray()[0]);

		set = ArrayUtil.asSet((String[]) null);
		Assert.assertNull(set);
		
		Assert.assertNull(ArrayUtil.asSet(null));
		Assert.assertTrue(ArrayUtil.asSet(new Object[0]).isEmpty());
		Assert.assertTrue(ArrayUtil.asSet().isEmpty());

		// asSetNoNull
		set = ArrayUtil.asSetNoNull("1", null, "3");
		Assert.assertEquals(2, set.size());
		Assert.assertEquals("1", set.toArray()[0]);
		Assert.assertEquals("3", set.toArray()[1]);

		set = ArrayUtil.asSetNoNull(null, null);
		Assert.assertNull(set);

		Assert.assertNull(ArrayUtil.asSetNoNull(null));
		Assert.assertNull(ArrayUtil.asSetNoNull(new Object[0]));
		Assert.assertNull(ArrayUtil.asSetNoNull());
	}

	@Test
	public void toArray() {
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.add(BigDecimal.ONE);
		BigDecimal[] array = ArrayUtil.toArray(list, BigDecimal.class);
		Assert.assertEquals(BigDecimal.ONE, array[0]);

		list.clear();
		array = ArrayUtil.toArray(list, BigDecimal.class);
	}
}
