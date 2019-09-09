package com.meidl.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WSServer {

	public static void main(String[] args) throws Exception {

		// 定义一对线程组
		// 主线程组： 用于接受客户端的连接，但不做任何处理
		EventLoopGroup mainGroup = new NioEventLoopGroup();
		// 从线程组：接受主线程组的任务，让手下线程组实际执行
		EventLoopGroup subGroup = new NioEventLoopGroup();
		
		try {
			// netty服务器的创建, ServerBootstrap 是一个启动类
			ServerBootstrap server = new ServerBootstrap();
			server.group(mainGroup, subGroup)			// 设置主从线程组
				.channel(NioServerSocketChannel.class)  // 设置nio的双向通道
				.childHandler(new WSServerInitialzer());// 指定channel的初始化器

			// 启动server，并且设置8089为启动的端口号，同时启动方式为同步
			ChannelFuture future = server.bind(8089).sync();
			// 监听关闭的channel，设置位同步方式
			future.channel().closeFuture().sync();
		} finally {
			//优雅关闭
			mainGroup.shutdownGracefully();
			subGroup.shutdownGracefully();
		}
	}
	
}
