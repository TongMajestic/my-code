package com.red.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.red.util.DataUtil;
import com.red.util.OperUtil;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ClientEndpoint
public class SocketClient extends Thread {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("master open ... ");
    }

    @OnMessage
    public void onMessage(String message) throws Exception {
        try {
            if (message.contains("秀币的红包")) {
                JsonObject json = (JsonObject) DataUtil.JSON_PARSER.parse(message);
                JsonArray array = json.get("MsgList").getAsJsonArray();
                JsonObject obj = (JsonObject) array.get(0);
                String roomId = obj.get("roomId").getAsString();
                String content = obj.get("content").getAsString();
                String regEx = "[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(content);
                String red = m.replaceAll("").trim();
                if(red != null && !"".equals(red)){
                    int money = Integer.valueOf(red);
                    if(money >= 10000){
                        System.out.println(DataUtil.sdf.format(new Date(System.currentTimeMillis())) + "   " + money);
                        DataUtil.printRedCount();
                        System.out.println(content);
                    }
                }
//                DataUtil.ROOM_QUEUE.add(roomId);
            }
        } catch (Exception e) {
            System.out.println("master error !");
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() throws IOException {
        System.out.println("master closed...");
        try {
            OperUtil.masterConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    public static void connect(String userId, String roomId, String token, String ws) throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        Session session = container.connectToServer(SocketClient.class, URI.create(ws));
        JsonObject msg = new JsonObject();
        msg.addProperty("MsgTag", 10010201);
        msg.addProperty("platform", 1);
        msg.addProperty("roomId", roomId);
        msg.addProperty("container", 1);
        msg.addProperty("softVersion", 10040);
        msg.addProperty("linking", "www.kktv5.com");
        msg.addProperty("userId", userId);
        msg.addProperty("token", token);
        session.getBasicRemote().sendText(msg.toString());
    }

}
