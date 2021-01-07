package com.yee.study.java;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roger.Yi
 */
public class Test {
    public static void main(String[] args) {
//        String allowedSubnetList = "10.29.23.100/30,10.1.96.0/24";
//        String allowedSubnetList = "10.30.31.0/24,10.1.30.0/24";
        String allowedSubnetList = "10.7.171.0/30";
        Map<Integer, Integer> subnetMap = new HashMap<>();
        for (String ipMask : allowedSubnetList.split(",")) {
            if (ipMask.contains("/")) {
                String[] split = ipMask.split("/");
                try {
                    Inet4Address a = (Inet4Address) InetAddress.getByName(split[0]);
                    int mask = Integer.parseInt(split[1]);
                    mask = -1 << (32 - mask);
                    subnetMap.put(a.hashCode() & mask, mask);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }

        // 169746176 -256
        // 169746275
        String ipAddr = "10.7.171.171";
        boolean valid = false;
        if (ipAddr != null) {
            for (String ip : ipAddr.split(",")) {
                if (ip.startsWith("172")) {
                    continue;
                }
                try {
                    int i = InetAddress.getByName(ip).hashCode();
                    for (Integer checkIp : subnetMap.keySet()) {
                        Integer subnet = subnetMap.get(checkIp);
                        Integer tmp = i & subnet;
                        if (checkIp.equals(tmp)) {
                            valid = true;
                            break;
                        }
                    }
                    if (valid) {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("valid = " + valid);
    }
}