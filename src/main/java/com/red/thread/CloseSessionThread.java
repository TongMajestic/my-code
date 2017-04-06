package com.red.thread;

import com.red.util.DataUtil;

import javax.websocket.Session;
import java.util.List;
import java.util.Set;

public class CloseSessionThread extends Thread {

    public void run() {
        while (true) {
            try {
                Set<String> keys = DataUtil.sessionMap.keySet();
                Long now = System.currentTimeMillis();
                for (String key : keys) {
                    List<Object> list = DataUtil.sessionMap.get(key);
                    Long time = (Long) list.get(0);
                    if (time.longValue() <= now.longValue()) {
                        DataUtil.sessionMap.remove(key);
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
