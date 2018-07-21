package com.yee.study.util.json;

import com.yee.study.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JSONMap测试类
 *
 * @author alex
 */
public class JSONMapTest {
    @Test
    public void test() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("a", 1);
        map.put("b", "2");
        map.put("c", null);
        map.put("d", DateUtil.getDate(2015, 3, 3));

        String jsonStr = JSON.getDefault().toJSONString(map);

        // 1 - JSONMap(String)
        JSONMap<String, Object> jsonMap = new JSONMap<String, Object>(jsonStr);
        Assert.assertEquals(map.size(), jsonMap.size());
        Assert.assertEquals(map.get("a"), jsonMap.get("a"));
        Assert.assertEquals(map.get("b"), jsonMap.get("b", "123")); // key-exists
        Assert.assertNull(jsonMap.get("c"));
        Assert.assertTrue(jsonMap.containsKey("c"));
        Assert.assertEquals("123", jsonMap.get("c", "123")); // key-exists
        Assert.assertEquals("20150303000000", jsonMap.get("d")); // date-formated
        Assert.assertNull(jsonMap.get("e"));
        Assert.assertEquals("123", jsonMap.get("e", "123")); // key-inexists

        jsonMap.put("a", 2);
        jsonMap.remove("b");
        jsonMap.put("c", "3.0");

        Assert.assertEquals(3, jsonMap.size());
        Assert.assertEquals("{\"a\":2,\"c\":\"3.0\",\"d\":\"20150303000000\"}", jsonMap.toJSONString());

        // 2 - JSONMap(String, JSON)
        jsonMap = new JSONMap<String, Object>(jsonStr, JSON.newInstance("yyyy-MM-dd"));

        Assert.assertEquals(map.size(), jsonMap.size());
        Assert.assertEquals(map.get("a"), jsonMap.get("a"));
        Assert.assertEquals(map.get("b"), jsonMap.get("b", "123")); // key-exists
        Assert.assertNull(jsonMap.get("c"));
        Assert.assertEquals("123", jsonMap.get("c", "123")); // key-exists
        Assert.assertEquals("20150303000000", jsonMap.get("d")); // date-formated
        Assert.assertNull(jsonMap.get("e"));
        Assert.assertEquals("123", jsonMap.get("e", "123")); // key-inexists

        jsonMap.put("a", 2);
        jsonMap.remove("b");
        jsonMap.put("c", "3.0");
        jsonMap.put("d", DateUtil.getDate(2015, 3, 3));

        Assert.assertEquals(3, jsonMap.size());
        Assert.assertEquals("{\"a\":2,\"c\":\"3.0\",\"d\":\"2015-03-03\"}", jsonMap.toJSONString());

        // 3 - JSONMap(Map)
        jsonMap = new JSONMap<String, Object>(map);
        Assert.assertEquals(map.size(), jsonMap.size());
        Assert.assertEquals(map.get("a"), jsonMap.get("a"));
        Assert.assertEquals(map.get("b"), jsonMap.get("b", "123")); // key-exists
        Assert.assertNull(jsonMap.get("c"));
        Assert.assertEquals("123", jsonMap.get("c", "123")); // key-exists
        Assert.assertEquals(map.get("d"), jsonMap.get("d"));
        Assert.assertNull(jsonMap.get("e"));
        Assert.assertEquals("123", jsonMap.get("e", "123")); // key-inexists

        jsonMap.put("a", 2);
        jsonMap.remove("b");
        jsonMap.put("c", "3.0");

        Assert.assertEquals(3, jsonMap.size());
        Assert.assertEquals("{\"a\":2,\"c\":\"3.0\",\"d\":\"20150303000000\"}", jsonMap.toJSONString());
        Assert.assertEquals(4, map.size()); // not-modify

        // 4 - JSONMap(Map, JSON)
        jsonMap = new JSONMap<String, Object>(map, JSON.newInstance("yyyy-MM-dd"));
        Assert.assertEquals(map.size(), jsonMap.size());
        Assert.assertEquals(map.get("a"), jsonMap.get("a"));
        Assert.assertEquals(map.get("b"), jsonMap.get("b", "123")); // key-exists
        Assert.assertNull(jsonMap.get("c"));
        Assert.assertEquals("123", jsonMap.get("c", "123")); // key-exists
        Assert.assertEquals(map.get("d"), jsonMap.get("d"));
        Assert.assertNull(jsonMap.get("e"));
        Assert.assertEquals("123", jsonMap.get("e", "123")); // key-inexists

        jsonMap.put("a", 2);
        jsonMap.remove("b");
        jsonMap.put("c", "3.0");

        Assert.assertEquals(3, jsonMap.size());
        Assert.assertEquals("{\"a\":2,\"c\":\"3.0\",\"d\":\"2015-03-03\"}", jsonMap.toJSONString());
        Assert.assertEquals(4, map.size()); // not-modify

        // 5 - JSONMap()
        jsonMap = new JSONMap<String, Object>();
        jsonMap.putAll(map);
        Assert.assertEquals(map.size(), jsonMap.size());
        Assert.assertEquals(map.get("a"), jsonMap.get("a"));
        Assert.assertEquals("123", jsonMap.get("c", "123")); // key-exists
        Assert.assertNull(jsonMap.get("c"));
        Assert.assertEquals("123", jsonMap.get("c", "123")); // key-exists
        Assert.assertEquals(map.get("d"), jsonMap.get("d"));
        Assert.assertNull(jsonMap.get("e"));
        Assert.assertEquals("123", jsonMap.get("e", "123")); // key-inexists

        jsonMap.put("a", 2);
        jsonMap.remove("b");
        jsonMap.put("c", "3.0");

        Assert.assertEquals(3, jsonMap.size());
        Assert.assertEquals("{\"a\":2,\"c\":\"3.0\",\"d\":\"20150303000000\"}", jsonMap.toJSONString());
        Assert.assertEquals(4, map.size()); // not-modify

        // 6 - JSONMap(JSON)
        jsonMap = new JSONMap<String, Object>(JSON.newInstance("yyyy-MM-dd"));
        jsonMap.putAll(map);
        Assert.assertEquals(map.size(), jsonMap.size());
        Assert.assertEquals(map.get("a"), jsonMap.get("a"));
        Assert.assertEquals(map.get("b"), jsonMap.get("b", "123")); // key-exists
        Assert.assertNull(jsonMap.get("c"));
        Assert.assertEquals("123", jsonMap.get("c", "123")); // key-exists
        Assert.assertEquals(map.get("d"), jsonMap.get("d"));
        Assert.assertNull(jsonMap.get("e"));
        Assert.assertEquals("123", jsonMap.get("e", "123")); // key-inexists

        jsonMap.put("a", 2);
        jsonMap.remove("b");
        jsonMap.put("c", "3.0");

        Assert.assertEquals(3, jsonMap.size());
        Assert.assertEquals("{\"a\":2,\"c\":\"3.0\",\"d\":\"2015-03-03\"}", jsonMap.toJSONString());
        Assert.assertEquals(4, map.size()); // not-modify
    }
}
