package com.mdl.springai.mcp.client.utils;

import javazoom.jl.player.Player;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 音频播放器
 *
 * @author meidanlong
 * @date 2025年08月06日
 * @version: 1.0
 */
@Slf4j
public class AudioPlayer {
    private static final int BUFFER_SIZE = 1024 * 8; // 8KB缓冲区
    private final BlockingQueue<byte[]> audioQueue = new LinkedBlockingQueue<>(20); // 限制队列大小，防止内存溢出
    private final ExecutorService playerExecutor = Executors.newSingleThreadExecutor();
    private final AtomicBoolean isPlaying = new AtomicBoolean(false);
    private final AtomicBoolean isShutdown = new AtomicBoolean(false);

    public AudioPlayer() {
        startPlaybackThread();
    }

    /**
     * 添加音频数据到播放队列
     *
     * @param audioData 音频数据
     */
    public void addAudioData(byte[] audioData) {
        if (isShutdown.get()) {
            return;
        }

        try {
            // 如果队列已满，会阻塞
            audioQueue.put(audioData);
            log.debug("Added audio chunk to queue, current size: {}", audioQueue.size());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interrupted while adding audio data to queue", e);
        }
    }

    /**
     * 启动播放线程
     */
    private void startPlaybackThread() {
        playerExecutor.submit(() -> {
            log.info("Audio playback thread started");

            while (!isShutdown.get()) {
                try {
                    // 从队列中获取音频数据，如果队列为空，会阻塞
                    byte[] audioData = audioQueue.take();
                    isPlaying.set(true);

                    // 播放音频
                    playAudio(audioData);

                    // 如果队列为空，设置播放状态为false
                    if (audioQueue.isEmpty()) {
                        isPlaying.set(false);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Audio playback thread interrupted", e);
                    break;
                } catch (Exception e) {
                    log.error("Error playing audio", e);
                }
            }

            log.info("Audio playback thread stopped");
        });
    }

    /**
     * 播放音频数据
     *
     * @param audioData 音频数据
     */
    private void playAudio(byte[] audioData) {
        try (InputStream inputStream = new ByteArrayInputStream(audioData)) {
            Player player = new Player(inputStream);
            player.play();
            player.close();
        } catch (Exception e) {
            log.error("Error playing audio chunk", e);
        }
    }

    /**
     * 关闭播放器
     */
    public void shutdown() {
        isShutdown.set(true);
        playerExecutor.shutdownNow();
        audioQueue.clear();
    }

    /**
     * 检查是否正在播放
     *
     * @return 是否正在播放
     */
    public boolean isPlaying() {
        return isPlaying.get();
    }

    /**
     * 清空播放队列
     */
    public void clearQueue() {
        audioQueue.clear();
    }
}

