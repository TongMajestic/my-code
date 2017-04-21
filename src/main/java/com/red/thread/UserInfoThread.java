package com.red.thread;

import com.red.util.DataUtil;
import com.red.util.OperUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class UserInfoThread {

    ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();

    public void execute() {
        //延迟60分钟执行，每天执行一次
        schedule.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    String userId = DataUtil.USER_TOKEN.get(0);
                    String token = DataUtil.USER_TOKEN.get(1);
                    int amount = OperUtil.getUserInfo(userId, token);
                    System.out.println(userId + " has money: " + amount / 1000);
                } catch (Exception e) {
                    System.out.println(" UserResidualExecutor error !");
                    e.printStackTrace();
                }

            }
        }, 10, 2 * 60 * 60, TimeUnit.SECONDS);
    }


}
