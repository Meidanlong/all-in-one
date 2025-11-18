package com.mdl.springai.mcp.server;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * WebSocket测试类
 *
 * @author meidanlong
 * @date 2025年11月17日
 * @version: 1.0
 */
public class WebSocketTest {

    @Test
    public void testWebSocketConnection() throws URISyntaxException, InterruptedException {
        // WebSocket服务器地址
        URI uri = new URI("ws://localhost:9060/mcp/ws");

        // 创建WebSocket客户端
        WebSocketClient client = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("WebSocket连接已建立");
            }

            @Override
            public void onMessage(String message) {
                System.out.println("收到服务器消息: " + message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("WebSocket连接已关闭, code: " + code + ", reason: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                System.err.println("WebSocket发生错误: " + ex.getMessage());
                ex.printStackTrace();
            }
        };

        // 连接到服务器
        client.connect();

        // 等待连接建立
        Thread.sleep(1000);

        // 发送消息
        if (client.isOpen()) {
            client.send("{\"businessId\":\"test\",\"data\":\"Hello WebSocket\"}");
        }

        // 等待接收消息
        Thread.sleep(2000);

        // 关闭连接
        client.close();
    }
}

