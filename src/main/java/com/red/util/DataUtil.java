package com.red.util;

import com.google.gson.JsonParser;
import com.red.constant.CommonConstants;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Admin on 2017/4/3.
 */
public class DataUtil {

    public static JsonParser JSON_PARSER = new JsonParser();

    public static List<String> USER_TOKEN = new ArrayList<String>();

    public static BlockingQueue<String/*roomId*/> ROOM_QUEUE = new LinkedBlockingQueue<String>();

    public static ConcurrentHashMap<String/*sendId*/, Long/*expiredTime*/> RED_FILTER = new ConcurrentHashMap<String, Long>();

    public static ConcurrentHashMap<String /*roomId_userId*/, List<Object>> sessionMap = new ConcurrentHashMap<String, List<Object>>();
    public static ConcurrentHashMap<String /*roomId_userId*/, List<Object>> homePageSessionMap = new ConcurrentHashMap<String, List<Object>>();

    public static void putSession(String userId, String roomId, Long time, Session session) {
        List<Object> list = new ArrayList<Object>(2);
        list.add(time);
        list.add(session);
        sessionMap.put(String.format(CommonConstants.ROOM_USER_FORMAT, roomId, userId), list);
    }

    public static void putSessionForHomePage(String userId, String roomId, Long time, Session session) {
        List<Object> list = new ArrayList<Object>(2);
        list.add(time);
        list.add(session);
        homePageSessionMap.put(String.format(CommonConstants.ROOM_USER_FORMAT, roomId, userId), list);
    }

    private static int count = 0;

    public static void printRedCount(){
        System.out.println("red amount:" + count++);
    }

    public static List<String> nickname = new ArrayList<String>();
}
