package com.mdl.springai.mcp.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * TTS配置类
 *
 * @author meidanlong
 * @date 2025年08月06日
 * @version: 1.0
 */
@Configuration
public class TtsConfig {

    /**
     * 创建RestTemplate
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000); // 10秒连接超时
        factory.setReadTimeout(30000);    // 30秒读取超时
        return new RestTemplate(factory);
    }

    /**
     * 创建RetryTemplate
     *
     * @return RetryTemplate
     */
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // 设置退避策略
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000); // 初始间隔1秒
        backOffPolicy.setMultiplier(2.0);       // 每次重试间隔翻倍
        backOffPolicy.setMaxInterval(10000);    // 最大间隔10秒
        retryTemplate.setBackOffPolicy(backOffPolicy);

        // 设置重试策略
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);          // 最多重试3次
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }
}

