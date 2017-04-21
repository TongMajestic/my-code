
package com.red;

import com.google.gson.JsonObject;
import com.red.thread.*;
import com.red.util.DataUtil;
import com.red.util.OperUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception {

        //slave
        File file = new File(Main.class.getResource("/").getPath() + "user");
        System.out.println(Main.class.getResource("/").getPath() + "user");
        String encoding = "UTF-8";
        Random random = new Random();
        if (file.isFile() && file.exists()) {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                JsonObject json = DataUtil.JSON_PARSER.parse(lineTxt).getAsJsonObject();
                String userId = json.get("userId").getAsString();
                String up = json.get("up").getAsString();
                String token = OperUtil.login(userId, up);
                DataUtil.USER_TOKEN.add(userId);
                DataUtil.USER_TOKEN.add(token);
                System.out.println("login " + userId);
                Thread.sleep(random.nextInt(20000) + 10000);
            }
            read.close();
        }

        //master
        File file_master = new File(Main.class.getResource("/").getPath() + "master");
        if (file_master.isFile() && file_master.exists()) {
            InputStreamReader read2 = new InputStreamReader(new FileInputStream(file_master), encoding);
            BufferedReader bufferedReader2 = new BufferedReader(read2);
            String lineTxt2 = null;
            while ((lineTxt2 = bufferedReader2.readLine()) != null) {
                DataUtil.MASTER_TOKEN.add(lineTxt2);
            }
            read2.close();
        }

        //nickname
        File file_nickname = new File(Main.class.getResource("/").getPath() + "nickname");
        if (file_nickname.isFile() && file_nickname.exists()) {
            InputStreamReader read1 = new InputStreamReader(new FileInputStream(file_nickname), encoding);
            BufferedReader bufferedReader1 = new BufferedReader(read1);
            String lineTxt1 = null;
            while ((lineTxt1 = bufferedReader1.readLine()) != null) {
                String nickname = lineTxt1;
                DataUtil.nickname.add(nickname);
            }
            read1.close();
        }

        //thread start
        UserInfoThread userInfoThread = new UserInfoThread();
        userInfoThread.execute();

        LoginRoomThread loginRoomThread = new LoginRoomThread();
        loginRoomThread.start();

        CloseSessionThread closeSessionThread = new CloseSessionThread();
        closeSessionThread.start();

        ClearRedFilterThread clearRedFilterThread = new ClearRedFilterThread();
        clearRedFilterThread.start();

        HomePageRoomExecutor homePageRoomExecutor = new HomePageRoomExecutor();
        homePageRoomExecutor.start();

        ModifyUserNameThread modifyUserNameThread = new ModifyUserNameThread();
        modifyUserNameThread.start();

        //main account
        OperUtil.masterConnect();

    }
}
