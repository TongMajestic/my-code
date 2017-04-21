package com.red.util;

import com.google.gson.JsonParser;
import com.red.constant.CommonConstants;

import javax.websocket.Session;
import java.net.HttpURLConnection;
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
//    public static Map<String /*userId*/, String /*token*/> UESER_TOKEN = new HashMap<String, String>();

    public static List<String> MASTER_TOKEN = new ArrayList<String>();
//    public static Map<String /*userId*/, String /*token*/> MASTER_TOKEN = new HashMap<String, String>();

    public static BlockingQueue<String/*roomId*/> ROOM_QUEUE = new LinkedBlockingQueue<String>();

    public static ConcurrentHashMap<String/*sendId*/, Long/*expiredTime*/> RED_FILTER = new ConcurrentHashMap<String, Long>();

    public static ConcurrentHashMap<String /*roomId_userId*/, List<Object>> sessionMap = new ConcurrentHashMap<String, List<Object>>();

    public static void putSession(String userId, String roomId, Long time, Session session) {
        List<Object> list = new ArrayList<Object>(2);
        list.add(time);
        list.add(session);
        sessionMap.put(String.format(CommonConstants.ROOM_USER_FORMAT, roomId, userId), list);
    }

    public static ConcurrentHashMap<String /*roomId*/, List<HttpURLConnection>> ROOM_HTTP = new ConcurrentHashMap<String, List<HttpURLConnection>>();

    private static int count = 0;

    public static void printRedCount(){
        System.out.println("red amount:" + count++);
    }

    public static List<String> nickname = new ArrayList<String>();
}
