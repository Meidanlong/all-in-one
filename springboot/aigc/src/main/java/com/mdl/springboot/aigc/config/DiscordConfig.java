package com.mdl.springboot.aigc.config;

import com.maoyan.movie.aigc.paintbrush.common.utils.HttpUtils;
import com.maoyan.movie.aigc.paintbrush.provider.listener.ImageGenerateComplete;
import com.maoyan.movie.aigc.paintbrush.provider.listener.SingleImageComplete;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class DiscordConfig {

    public static final String ROBOT_TOKEN = "";

    @Bean
    public DiscordApi discordApi(){
        // Discord API
        DiscordApi api = new DiscordApiBuilder()
                .setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(HttpUtils.proxy_hostname, HttpUtils.proxy_port)))
                .setToken(ROBOT_TOKEN)
                .addListener(new ImageGenerateComplete())
                .addListener(new SingleImageComplete())
                .login()
                .join();
        return api;
    }
}
