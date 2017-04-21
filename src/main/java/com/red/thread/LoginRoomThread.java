package com.red.thread;

import com.red.client.SocketClientSlave;
import com.red.util.DataUtil;
import com.red.util.OperUtil;
import com.red.util.PoolUtil;

public class LoginRoomThread extends Thread {

    public void run() {
        while (true) {
            try {
                final String roomId = DataUtil.ROOM_QUEUE.take();
                final String userId = DataUtil.USER_TOKEN.get(0);
                final String token = DataUtil.USER_TOKEN.get(1);
                    PoolUtil.ROOM_WS_POOL.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String ws = OperUtil.getWsByRoomId(roomId);
                                SocketClientSlave.connect(userId, roomId, token, ws);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
