package com.red.thread;

import com.red.util.OperUtil;

import java.util.Calendar;

/**
 * Created by Tong on 17/4/20.
 */
public class ModifyUserNameThread extends Thread {

    public void run(){
        while (true){

            try {

                OperUtil.modifyUserName();
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                if(hour >= 21){
                    Thread.sleep(60 * 60 * 1000);
                }else{
                    Thread.sleep(120 * 60 * 1000);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

}
