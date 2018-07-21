package com.yee.study.util.json;

import com.yee.study.util.MapUtil;
import com.yee.study.util.StringUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JSON键值对工具类，用于修改JSON类型字符串
 */
public class JSONMap<K, V> {
	/**
	 * 键值对
	 */
	private Map<K, V> map;

	/**
	 * JSON解析器
	 */
	private JSON json;

	/**
	 * 构造函数
	 */
	public JSONMap() {
		this.json = JSON.getDefault();
	}

	/**
	 * 构造函数
	 * 
	 * @param json
	 */
	public JSONMap(JSON json) {
		this.json = (json != null ? json : JSON.getDefault());
	}

	/**
	 * 构造函数
	 * 
	 * @param jsonStr
	 */
	public JSONMap(String jsonStr) {
		this(jsonStr, null);
	}

	/**
	 * 构造函数
	 * 
	 * @param jsonStr
	 * @param json
	 */
	@SuppressWarnings("unchecked")
	public JSONMap(String jsonStr, JSON json) {
		this.json = (json != null ? json : JSON.getDefault());

		if (StringUtil.isNotBlank(jsonStr)) {
			map = this.json.parseToObject(jsonStr, Map.class);
		}
	}

	/**
	 * 构造函数
	 * 
	 * @param jsonMap
	 */
	public JSONMap(Map<K, V> jsonMap) {
		this(jsonMap, null);
	}

	/**
	 * 构造函数
	 * 
	 * @param jsonMap
	 * @param json
	 */
	public JSONMap(Map<K, V> jsonMap, JSON json) {
		this.json = (json != null ? json : JSON.getDefault());
		if (jsonMap != null) {
			this.map = new LinkedHashMap<K, V>(jsonMap);
		}
	}

	/**
	 * 根据key获取对应值，如果key不存在，则返回devVal
	 * 
	 * @param key
	 * @param defVal
	 * @return
	 */
	public V get(K key, V defVal) {
		return MapUtil.get(map, key, defVal);
	}

	/**
	 * 根据key获取对应值，如果key不存在，则返回null
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key) {
		return get(key, null);
	}

	/**
	 * 是否存在key。
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(K key) {
		if (map == null) {
			return false;
		}

		return map.containsKey(key);
	}

	/**
	 * 添加key/value键值对
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public V put(K key, V val) {
		if (map == null) {
			map = new LinkedHashMap<K, V>();
		}

		return map.put(key, val);
	}

	/**
	 * 添加map键值对
	 * 
	 * @param map
	 */
	public void putAll(Map<K, V> map) {
		if (map == null) {
			return;
		}

		if (this.map == null) {
			this.map = new LinkedHashMap<K, V>();
		}

		this.map.putAll(map);
	}

	/**
	 * 移除指定key对应value
	 * 
	 * @param key
	 * @return
	 */
	public V remove(K key) {
		if (map == null) {
			return null;
		}

		return map.remove(key);
	}

	/**
	 * 返回键值对大小
	 * 
	 * @return
	 */
	public int size() {
		return (map != null ? map.size() : 0);
	}

	/**
	 * 转换为JSON字符串，如果原jsonStr为null，则返回null
	 * 
	 * @return
	 */
	public String toJSONString() {
		if (map == null) {
			return null;
		}

		return json.toJSONString(map);
	}
}
