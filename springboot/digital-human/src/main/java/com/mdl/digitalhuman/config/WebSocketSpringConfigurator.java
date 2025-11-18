package com.mdl.digitalhuman.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import javax.websocket.server.ServerEndpointConfig;

/**
 * 自定义Spring配置器，用于在WebSocket端点中注入Spring Bean
 *
 * @author meidanlong
 * @date 2025年11月18日
 * @version: 1.0
 */
@Configuration
public class WebSocketSpringConfigurator extends ServerEndpointConfig.Configurator implements ApplicationContextAware {
    private static volatile BeanFactory context;

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        return context.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        WebSocketSpringConfigurator.context = applicationContext;
    }
}

