
package com.red;

import com.google.gson.JsonObject;
import com.red.util.DataUtil;
import com.red.util.OperUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws Exception {

        //slave
        String encoding = "UTF-8";
        File file = new File(Main.class.getResource("/").getPath() + "user");
        System.out.println(Main.class.getResource("/").getPath() + "user");
        if (file.isFile() && file.exists()) {
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                JsonObject json = DataUtil.JSON_PARSER.parse(lineTxt).getAsJsonObject();
                String userId = json.get("userId").getAsString();
                String up = json.get("up").getAsString();
                String room = json.get("room").getAsString();
                String token = OperUtil.login(userId, up);
                DataUtil.USER_TOKEN.add(userId);
                DataUtil.USER_TOKEN.add(token);
                DataUtil.USER_TOKEN.add(room);
                System.out.println("login " + userId);
                Thread.sleep(5000);
            }
            read.close();
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
//        UserInfoThread userInfoThread = new UserInfoThread();
//        userInfoThread.execute();

//        LoginRoomThread loginRoomThread = new LoginRoomThread();
//        loginRoomThread.start();

//        CloseSessionThread closeSessionThread = new CloseSessionThread();
//        closeSessionThread.start();

//        CloseHomePageSessionThread closeHomePageSessionThread = new CloseHomePageSessionThread();
//        closeHomePageSessionThread.start();

//        ClearRedFilterThread clearRedFilterThread = new ClearRedFilterThread();
//        clearRedFilterThread.start();

//        HomePageRoomExecutor homePageRoomExecutor = new HomePageRoomExecutor();
//        homePageRoomExecutor.start();

//        ModifyUserNameThread modifyUserNameThread = new ModifyUserNameThread();
//        modifyUserNameThread.start();

        //main account
        OperUtil.masterConnect();

        Thread.sleep(24 * 60 * 60 * 1000);

    }
}
