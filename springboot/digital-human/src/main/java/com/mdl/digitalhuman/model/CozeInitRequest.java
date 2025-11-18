package com.mdl.digitalhuman.model;

import lombok.Data;

/**
 * Coze初始化请求实体类
 *
 * @author meidanlong
 * @date 2025年11月18日
 * @version: 1.0
 */
@Data
public class CozeInitRequest {
    private String action;
    private String version;
    private AudioConfig audio_config;

    public CozeInitRequest() {
    }

    public CozeInitRequest(String action, String version, AudioConfig audioConfig) {
        this.action = action;
        this.version = version;
        this.audio_config = audioConfig;
    }

    @Data
    public static class AudioConfig {
        private String format;
        private int sample_rate;
        private int channels;

        public AudioConfig() {
        }

        public AudioConfig(String format, int sample_rate, int channels) {
            this.format = format;
            this.sample_rate = sample_rate;
            this.channels = channels;
        }
    }
}
