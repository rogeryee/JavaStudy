package com.yee.study.util.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yee.study.util.DateUtil;
import com.yee.study.util.StringUtil;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

/**
 * JSON工具测试类
 */
public class JSONTest {
	static class InnerMapObject {
		private String name;

		private String address;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
	}

	static class BeanA {
		private String ipAddress;

		public String getIpAddress() {
			return ipAddress;
		}

		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}
	}

	@SuppressWarnings("unused")
	private Map<String, Object> fakeField;

	@Test
	@SuppressWarnings("unchecked")
	public void testExplictType() throws Exception {
		JSON json = JSON.getDefault();
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("a", "aString");
		attributes.put("b", new String[] { "barray0", "barray1" });
		InnerMapObject innerObject = new InnerMapObject();
		innerObject.setName("innername");
		innerObject.setAddress("inneraddress");
		attributes.put("c", innerObject);
		String serializeString = json.toExplictJSONString(attributes);
		System.out.println(serializeString);
		Map<String, Object> deserializeAttrs = json.parseToExplictObject(serializeString, Map.class);
		verifyResult(deserializeAttrs);
		deserializeAttrs = (Map<String, Object>) json.parseToExplictObject(serializeString,
				JSONTest.class.getDeclaredField("fakeField").getType());
		verifyResult(deserializeAttrs);

	}

	private void verifyResult(Map<String, Object> deserializeAttrs) {
		assertEquals("aString", deserializeAttrs.get("a"));
		Object barray = deserializeAttrs.get("b");
		assertEquals(2, ((String[]) barray).length);
		assertEquals("barray0", ((String[]) barray)[0]);
		Object cobj = deserializeAttrs.get("c");
		assertTrue(cobj instanceof InnerMapObject);
		assertEquals("innername", ((InnerMapObject) cobj).getName());
	}

	@Test
	public void test() {
		// 1
		String datePattern = "yyyyMMddHHmmss";
		JSON json = JSON.newInstance(datePattern);

		Obj obj = new Obj();
		obj.setD(DateUtil.getDate(2013, 10, 30, 12, 34, 56));
		obj.setB(new BigDecimal("123.45"));

		String jsonStr = json.toJSONString(obj);
		assertFalse(jsonStr.contains("\"s\""));
		assertTrue(jsonStr.contains("\"d\":\"20131030123456\""));
		assertTrue(jsonStr.contains("\"i\":0"));
		assertTrue(jsonStr.contains("\"b\":123.45"));

		// 2
		obj = json.parseToObject(jsonStr, Obj.class);
		assertEquals("20131030123456", StringUtil.format("yyyyMMddHHmmss", obj.getD()));
		assertEquals(new BigDecimal("123.45"), obj.getB());
		assertNull(obj.getS());

		// 3
		String jsonArrStr = "[0, 1, 2, 3, 4]";
		List<Integer> intList = json.parseToObject(jsonArrStr, new TypeReference<List<Integer>>() {
		}.getType());
		assertEquals(5, intList.size());
		assertEquals(2, (int) intList.get(2));

		// 4
		Set<Integer> intSet = json.parseToObject(jsonArrStr, new TypeReference<Set<Integer>>() {
		}.getType());
		assertEquals(5, intSet.size());
		assertTrue(intSet.contains(2));

		// 5
		Integer[] intArr = json.parseToObject(jsonArrStr, Integer[].class);
		assertEquals(5, intArr.length);
		assertEquals(2, (int) intArr[2]);

		// 6
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		json.writeObjectToJSONStream(bout, obj);

		ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
		Obj obj2 = json.readObjectFromJSONStream(bin, Obj.class);
		assertEquals(obj.getB().toString(), obj2.getB().toString());

		// 7
		json = JSON.getDefaultWithSnakeCaseName();
		BeanA beanA = new BeanA();
		beanA.setIpAddress("192.168.0.1");
		System.out.println(json.toPrettyJSONString(beanA));
		beanA = json.parseToObject(json.toJSONString(beanA), BeanA.class);
		System.out.println(beanA.getIpAddress());
		assertEquals("192.168.0.1", beanA.getIpAddress());
		
		// 7 upper camel case
		Obj uccObj = new Obj();
		uccObj.setS("1");
		uccObj.setB(BigDecimal.ZERO);
		uccObj.setD(DateUtil.getDate(2017, 7, 20));
		uccObj.setI(-1);
		uccObj.setBool(true);
		
		String uccJSON = JSON.getDefault().toUpperCamelCaseJSONString(uccObj);
		assertEquals("{\"S\":\"1\",\"D\":\"20170720000000\",\"B\":0,\"I\":-1,\"Bool\":true}", uccJSON);
		
		Obj uccObj2 = JSON.getDefault().parseToUpperCamelCaseObject(uccJSON, Obj.class);
		assertEquals(uccObj.s, uccObj2.s);
		assertEquals(uccObj.d, uccObj2.d);
		assertEquals(uccObj.b, uccObj2.b);
		assertEquals(uccObj.i, uccObj2.i);
		assertEquals(uccObj.bool, uccObj2.bool);
	}

	static class Obj {
		private String s;

		private Date d;

		private BigDecimal b;

		private int i;
		
		private boolean bool;

		public String getS() {
			return s;
		}

		public void setS(String s) {
			this.s = s;
		}

		public Date getD() {
			return d;
		}

		public void setD(Date d) {
			this.d = d;
		}

		public BigDecimal getB() {
			return b;
		}

		public void setB(BigDecimal b) {
			this.b = b;
		}

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public boolean isBool() {
			return bool;
		}

		public void setBool(boolean bool) {
			this.bool = bool;
		}
	}
}
