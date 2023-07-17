package com.mdl.springboot.aigc.config;

import com.mdl.springboot.aigc.constant.MidjourneyConstants;
import com.mdl.springboot.aigc.message.GridImageListener;
import com.mdl.springboot.aigc.message.UpscalerImageListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscordConfig {

//    @Bean
    public DiscordApi discordApi(){
        // Discord API
        DiscordApi api = new DiscordApiBuilder()
                // 添加自己的代理
//                .setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(HttpUtils.proxy_hostname, HttpUtils.proxy_port)))
                .setToken(MidjourneyConstants.ROBOT_TOKEN)
                .addListener(new GridImageListener())
                .addListener(new UpscalerImageListener())
                .login()
                .join();
        return api;
    }
}
