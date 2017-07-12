package com.yee.study.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Map工具测试类
 *
 * @author Roger.Yi
 */
public class MapUtilTest
{
    /**
     * 测试size方法
     */
    @Test
    public void testSize()
    {
        // size(Map)
        Assert.assertEquals(1, MapUtil.size(ArrayUtil.asMap("Key1")));
        Assert.assertEquals(2, MapUtil.size(ArrayUtil.asMap("Key1", "Val1", "Key2")));
        Assert.assertEquals(0, MapUtil.size(new HashMap<String, String>()));
        Assert.assertEquals(0, MapUtil.size((Map<?, ?>) null));

        // size(Map[])
        Assert.assertEquals(3, MapUtil.size((Map<?, ?>) null, ArrayUtil.asMap("Key1"), ArrayUtil.asMap("Key1", "Val1", "Key2")));
        Assert.assertEquals(0, MapUtil.size((Map<?, ?>[]) null));
        Assert.assertEquals(0, MapUtil.size());
    }

    /**
     * 测试put方法
     */
    @Test
    public void testPut()
    {
        Map<String, String> src = new HashMap<String, String>();
        Assert.assertSame(src, MapUtil.put(src, "a", "1"));
        Assert.assertEquals("1", src.get("a"));

        src = MapUtil.put(null, "b", "2");
        Assert.assertNotNull(src);
        Assert.assertEquals("2", src.get("b"));

        Assert.assertSame(src, MapUtil.put(src, "c", "3"));
        Assert.assertEquals(2, src.size());
        Assert.assertEquals("2", src.get("b"));
        Assert.assertEquals("3", src.get("c"));
    }

    /**
     * 测试putIfValNotNull方法
     */
    @Test
    public void testPutIfValNotNull()
    {
        Map<String, String> src = new HashMap<String, String>();
        Assert.assertSame(src, MapUtil.putIfValNotNull(src, "a", "1"));
        Assert.assertEquals("1", src.get("a"));

        src = MapUtil.putIfValNotNull(null, "b", "2");
        Assert.assertNotNull(src);
        Assert.assertEquals("2", src.get("b"));

        Assert.assertSame(src, MapUtil.putIfValNotNull(src, "c", "3"));
        Assert.assertEquals(2, src.size());
        Assert.assertEquals("2", src.get("b"));
        Assert.assertEquals("3", src.get("c"));

        Assert.assertSame(src, MapUtil.putIfValNotNull(src, "d", null));
        Assert.assertEquals(2, src.size());
        Assert.assertEquals("2", src.get("b"));
        Assert.assertEquals("3", src.get("c"));
    }

    /**
     * 测试putOrRmIfValNull方法
     */
    @Test
    public void testPutOrRmIfValNull()
    {
        Map<String, String> src = new HashMap<String, String>();
        Assert.assertSame(src, MapUtil.putOrRmIfValNull(src, "a", "1"));
        Assert.assertEquals("1", src.get("a"));

        src = MapUtil.putOrRmIfValNull(null, "b", "2");
        Assert.assertNotNull(src);
        Assert.assertEquals("2", src.get("b"));

        Assert.assertSame(src, MapUtil.putOrRmIfValNull(src, "c", "3"));
        Assert.assertEquals(2, src.size());
        Assert.assertEquals("2", src.get("b"));
        Assert.assertEquals("3", src.get("c"));

        Assert.assertSame(src, MapUtil.putOrRmIfValNull(src, "d", null));
        Assert.assertEquals(2, src.size());
        Assert.assertEquals("2", src.get("b"));
        Assert.assertEquals("3", src.get("c"));

        Assert.assertSame(src, MapUtil.putOrRmIfValNull(src, "c", null));
        Assert.assertEquals(1, src.size());
        Assert.assertEquals("2", src.get("b"));
    }

    /**
     * 测试get方法
     */
    @Test
    public void testGet()
    {
        // 1
        Map<String, String> src = new HashMap<String, String>();
        src.put("a", null);
        src.put("b", "2");
        src.put("c", "3");

        Assert.assertNull(MapUtil.get(src, null));
        Assert.assertNull(MapUtil.get(src, "a"));
        Assert.assertEquals("2", MapUtil.get(src, "b"));
        Assert.assertEquals("3", MapUtil.get(src, "c"));
        Assert.assertNull(MapUtil.get(null, "b"));
        Assert.assertNull(MapUtil.get(null, null));

        // 2
        Assert.assertEquals("4", MapUtil.get(src, null, "4"));
        Assert.assertEquals("4", MapUtil.get(src, "a", "4"));
        Assert.assertEquals("2", MapUtil.get(src, "b", "4"));
        Assert.assertEquals("3", MapUtil.get(src, "c", "4"));
        Assert.assertEquals("4", MapUtil.get(null, "b", "4"));
        Assert.assertEquals("4", MapUtil.get(null, null, "4"));

        // 3
        src = new ConcurrentHashMap<String, String>();
        src.put("a", "1");

        try
        {
            src.get(null);
            Assert.fail("Expect NullpointerException");
        }
        catch (NullPointerException ex)
        {
        }

        Assert.assertNull(MapUtil.get(src, null)); // swallow-NullpointerException
        Assert.assertEquals("4", MapUtil.get(src, null, "4")); // swallow-NullpointerException
    }

    /**
     * 测试getKeys方法
     */
    @Test
    public void testGetKeys()
    {
        Map<String, String> src = new HashMap<String, String>();
        src.put("a", "1");
        src.put("b", "2");
        src.put("c", "3");
        src.put("d", "3");
        src.put("e", null);
        src.put("f", null);

        // 1
        Set<String> keys = MapUtil.getKeys(src, "1");
        Assert.assertEquals(1, keys.size());
        Assert.assertTrue(keys.contains("a"));

        keys = MapUtil.getKeys(src, "3");
        Assert.assertEquals(2, keys.size());
        Assert.assertTrue(keys.contains("c"));
        Assert.assertTrue(keys.contains("d"));

        keys = MapUtil.getKeys(src, null);
        Assert.assertEquals(2, keys.size());
        Assert.assertTrue(keys.contains("e"));
        Assert.assertTrue(keys.contains("f"));

        keys = MapUtil.getKeys(src, null);
        Assert.assertEquals(2, keys.size());
        Assert.assertTrue(keys.contains("e"));
        Assert.assertTrue(keys.contains("f"));

        // 2
        keys = MapUtil.getKeys(src, "g");
        Assert.assertEquals(0, keys.size());

        keys = MapUtil.getKeys(src, "a");
        Assert.assertEquals(0, keys.size());

        keys = MapUtil.getKeys(null, "1");
        Assert.assertEquals(0, keys.size());
    }

    /**
     * 测试getUniqueKey方法
     */
    @Test
    public void testGetUniqueKey()
    {
        Map<String, String> src = new HashMap<String, String>();
        src.put("a", "1");
        src.put("b", "2");
        src.put("c", "3");
        src.put("d", "3");
        src.put("e", null);
        src.put("f", null);

        // 1 getUniqueKey(map, value)
        String key = MapUtil.getUniqueKey(src, "1");
        Assert.assertEquals("a", key);

        key = MapUtil.getUniqueKey(src, "3");
        // 值不唯一的情况下，哪个键会返回不可预计
        Assert.assertTrue("c".equals(key) || "d".equals(key));

        key = MapUtil.getUniqueKey(src, null);
        Assert.assertTrue("e".equals(key) || "f".equals(key));

        // 2
        Assert.assertNull(MapUtil.getUniqueKey(src, "g"));
        Assert.assertNull(MapUtil.getUniqueKey(src, "a"));
        Assert.assertNull(MapUtil.getUniqueKey(null, "1"));

        // 1 getUniqueKey(map, value, defKey)
        key = MapUtil.getUniqueKey(src, "1", "z");
        Assert.assertEquals("a", key);

        key = MapUtil.getUniqueKey(src, "3", "z");
        Assert.assertTrue("c".equals(key) || "d".equals(key));

        // 2
        Assert.assertEquals("z", MapUtil.getUniqueKey(src, "g", "z"));
        Assert.assertEquals("z", MapUtil.getUniqueKey(src, "a", "z"));
        Assert.assertEquals("z", MapUtil.getUniqueKey(null, "1", "z"));
    }

    /**
     * 测试containsKey方法
     */
    @Test
    public void testContainsKey()
    {
        // 1
        Map<String, String> src = new HashMap<String, String>();
        src.put("a", null);
        src.put("b", "2");
        src.put("c", "3");

        Assert.assertTrue(MapUtil.containsKey(src, "a"));
        Assert.assertTrue(MapUtil.containsKey(src, "b"));
        Assert.assertTrue(MapUtil.containsKey(src, "c"));
        Assert.assertFalse(MapUtil.containsKey(src, null));
        Assert.assertFalse(MapUtil.containsKey(null, "a"));
        Assert.assertFalse(MapUtil.containsKey(null, null));
    }

    /**
     * 测试containsValue方法
     */
    @Test
    public void testContainsValue()
    {
        // 1
        Map<String, String> src = new HashMap<String, String>();
        src.put("a", null);
        src.put("b", "2");
        src.put("c", "3");

        Assert.assertTrue(MapUtil.containsValue(src, "2"));
        Assert.assertTrue(MapUtil.containsValue(src, "3"));
        Assert.assertTrue(MapUtil.containsValue(src, null));
        Assert.assertFalse(MapUtil.containsValue(src, "1"));
        Assert.assertFalse(MapUtil.containsValue(null, "a"));
        Assert.assertFalse(MapUtil.containsValue(null, null));
    }

    /**
     * 测试isEmpty方法
     */
    @Test
    public void testIsEmpty()
    {
        Assert.assertTrue(MapUtil.isEmpty(null));

        Map<Object, Object> map = new HashMap<Object, Object>();
        Assert.assertTrue(MapUtil.isEmpty(map));

        map.put("", "");
        Assert.assertFalse(MapUtil.isEmpty(map));
    }

    /**
     * 测试isNotEmpty方法
     */
    @Test
    public void testIsNotEmpty()
    {
        Assert.assertFalse(MapUtil.isNotEmpty(null));

        Map<Object, Object> map = new HashMap<Object, Object>();
        Assert.assertFalse(MapUtil.isNotEmpty(map));

        map.put("", "");
        Assert.assertTrue(MapUtil.isNotEmpty(map));
    }

    /**
     * 测试Entry类
     */
    @Test
    public void testToEntry()
    {
        // 1
        Entry<String, Object> entry = MapUtil.toEntry(null, null);
        Assert.assertNotNull(entry);
        Assert.assertNull(entry.getKey());
        Assert.assertNull(entry.getValue());
        Assert.assertEquals("null=null", entry.toString());

        // 2
        entry = MapUtil.toEntry("k", (Object) "v");
        Assert.assertNotNull(entry);
        Assert.assertEquals("k", entry.getKey());
        Assert.assertEquals("v", entry.getValue());
        Assert.assertEquals("k=v", entry.toString());

        // 3
        Entry<String, Object> entry1 = MapUtil.toEntry(null, null);
        Entry<String, Object> entry2 = MapUtil.toEntry(null, null);
        Assert.assertEquals(0, entry1.hashCode());
        Assert.assertTrue(entry1.equals(entry2));
        Assert.assertTrue(entry1.hashCode() == entry2.hashCode());

        // 4
        entry1 = MapUtil.toEntry("k", (Object) "v");
        entry2 = MapUtil.toEntry("k", (Object) "v");
        Assert.assertTrue(entry1.equals(entry2));
        Assert.assertTrue(entry1.hashCode() == entry2.hashCode());

        // 5
        entry1 = MapUtil.toEntry("k1", (Object) "v1");
        entry2 = MapUtil.toEntry("k2", (Object) "v2");
        Assert.assertFalse(entry1.equals(entry2));
        Assert.assertFalse(entry1.hashCode() == entry2.hashCode());
    }

    /**
     * 测试newMap方法
     */
    @Test
    public void testNewMap()
    {
        // 1
        Map<String, Number> map = MapUtil.newMap(new HashMap<String, Number>());
        Assert.assertNotNull(map);
        map.put("key", Integer.valueOf(1));

        // 2
        map = MapUtil.newMap(HashMap.class);
        Assert.assertNotNull(map);
        map.put("key", Integer.valueOf(1));

        // 3
        map = MapUtil.newMap(Map.class);
        Assert.assertNotNull(map);
        Assert.assertEquals(LinkedHashMap.class, map.getClass());
        map.put("key", Integer.valueOf(1));

        // 4
        Assert.assertNull(MapUtil.newMap(null));
        Assert.assertNull(MapUtil.newMap(Object.class));
        Assert.assertNull(MapUtil.newMap(new Object()));
        Assert.assertNull(MapUtil.newMap(SingletonMap.class));
    }

    /**
     * 测试单例列表类
     *
     * @author alex
     */
    static class SingletonMap extends HashMap<String, String>
    {
        private static final long serialVersionUID = 1L;

        private static SingletonMap instance = new SingletonMap();

        private SingletonMap()
        {
        }

        public static SingletonMap getInstance()
        {
            return instance;
        }
    }

    ;
}
