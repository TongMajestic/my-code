package com.red.thread;

import com.red.util.DataUtil;
import com.red.util.OperUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class UserInfoThread {

    ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void execute() {
        //延迟10秒执行，每60分钟执行一次
        schedule.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    String userId = DataUtil.USER_TOKEN.get(0);
                    String token = DataUtil.USER_TOKEN.get(1);
                    int amount = OperUtil.getUserInfo(userId, token);
                    System.out.println(sdf.format(new Date()) + "  " + userId + " has money: " + amount / 1000);
                } catch (Exception e) {
                    System.out.println(" UserResidualExecutor error !");
                    e.printStackTrace();
                }

            }
        }, 10, 60 * 60, TimeUnit.SECONDS);
    }

}
