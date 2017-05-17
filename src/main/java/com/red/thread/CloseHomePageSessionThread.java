package com.red.thread;

import com.red.util.DataUtil;

import javax.websocket.Session;
import java.util.List;
import java.util.Set;

public class CloseHomePageSessionThread extends Thread {

    public void run() {
        while (true) {
            try {

                Set<String> keys = DataUtil.homePageSessionMap.keySet();
                Long now = System.currentTimeMillis();
                for (String key : keys) {
                    List<Object> list = DataUtil.homePageSessionMap.get(key);
                    Long time = (Long) list.get(0);
                    if (time.longValue() <= now.longValue()) {
                        DataUtil.homePageSessionMap.remove(key);
                        Session session = (Session) list.get(1);
                        session.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
