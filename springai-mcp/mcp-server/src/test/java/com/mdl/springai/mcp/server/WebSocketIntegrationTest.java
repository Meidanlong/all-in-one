package com.mdl.springai.mcp.server;

import com.alibaba.fastjson.JSON;
import com.mdl.springai.mcp.server.domain.TPushMessageReq;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * WebSocket集成测试类
 *
 * @author meidanlong
 * @date 2025年11月17日
 * @version: 1.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    public void testWebSocketCommunication() throws URISyntaxException, InterruptedException, ExecutionException, TimeoutException {
        // WebSocket服务器地址
        URI uri = new URI("ws://localhost:" + port + "/mcp/ws");

        // 用于接收异步消息的future
        CompletableFuture<String> messageFuture = new CompletableFuture<>();

        // 创建WebSocket客户端
        WebSocketClient client = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("WebSocket连接已建立");
            }

            @Override
            public void onMessage(String message) {
                System.out.println("收到服务器消息: " + message);
                messageFuture.complete(message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("WebSocket连接已关闭, code: " + code + ", reason: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                System.err.println("WebSocket发生错误: " + ex.getMessage());
                messageFuture.completeExceptionally(ex);
            }
        };

        // 连接到服务器
        client.connect();

        // 等待连接建立
        Thread.sleep(1000);

        // 验证连接已建立
        assertTrue(client.isOpen(), "WebSocket连接已建立");

        // 发送消息
        TPushMessageReq request = new TPushMessageReq();
        request.setBusinessId("test");
        request.setData("Hello WebSocket");

        client.send(JSON.toJSONString(request));

        // 等待接收消息
        String response = messageFuture.get(5, TimeUnit.SECONDS);

        // 验证收到响应
        TPushMessageReq responseMessage = JSON.parseObject(response, TPushMessageReq.class);
//        assertTrue(responseMessage.getData().contains("收到消息"), "应该收到服务器的响应消息");

        TimeUnit.SECONDS.sleep(10);
        // 关闭连接
        client.close();
    }
}

