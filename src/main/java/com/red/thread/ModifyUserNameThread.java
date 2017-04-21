package com.red.thread;

import com.red.util.OperUtil;

/**
 * Created by Tong on 17/4/20.
 */
public class ModifyUserNameThread extends Thread {

    public void run(){
        while (true){

            try {
                OperUtil.modifyUserName();
                Thread.sleep(30 * 60 * 1000);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

}
