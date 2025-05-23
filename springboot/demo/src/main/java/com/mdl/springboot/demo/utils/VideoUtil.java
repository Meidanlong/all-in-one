package com.mdl.springboot.demo.utils;

import com.alibaba.fastjson.JSON;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * 视频工具
 *
 * @author meidanlong
 */
public class VideoUtil {
    static class TimeRange {
        double startTime;
        double endTime;


        public TimeRange(double startTime, double endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }


    public static void splitVideoByTimeRanges(String inputFilePath, List<TimeRange> timeRanges) {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFilePath)) {
            grabber.start();


            for (int i = 0; i < timeRanges.size(); i++) {
                TimeRange range = timeRanges.get(i);
                String outputFilePath = "output_video_" + i + ".mp4";
                try (FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFilePath, grabber.getImageWidth(), grabber.getImageHeight())) {
                    // 开始录制
                    recorder.setVideoCodec(grabber.getVideoCodec());
                    recorder.setFormat("mp4");
                    recorder.setFrameRate(grabber.getFrameRate());
                    recorder.setVideoBitrate(grabber.getVideoBitrate());
                    recorder.start();


                    // 设置开始时间
                    grabber.setTimestamp((long) (range.startTime * 1000000));


                    // 帧计数器
                    int frameCount = 0;
                    while (true) {
                        // 读取一帧
                        Frame frame = grabber.grabFrame();
                        if (frame == null) {
                            break;
                        }
                        // 计算当前帧的时间戳
                        double currentTime = (double) frame.timestamp / 1000000.0;
                        if (currentTime >= range.endTime) {
                            break;
                        }
                        // 录制帧
                        recorder.record(frame);
                        frameCount++;
                    }


                    // 停止录制
                    recorder.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            // 停止抓取
            grabber.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void splitVideoFromUrlByTimeRanges(String url, List<TimeRange> timeRanges) {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(url)) {
            grabber.start();

            for (int i = 0; i < timeRanges.size(); i++) {
                TimeRange range = timeRanges.get(i);
                String outputFilePath = "output_video_from_url_" + i + ".mp4";
                try (FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFilePath, grabber.getImageWidth(), grabber.getImageHeight())) {
                    recorder.setAudioChannels(grabber.getAudioChannels());
                    recorder.setVideoCodec(grabber.getVideoCodec());
                    recorder.setFormat("mp4");
                    recorder.setFrameRate(grabber.getFrameRate());
                    recorder.setVideoBitrate(grabber.getVideoBitrate());
                    recorder.start();

                    // 设置开始时间
                    grabber.setTimestamp((long) (range.startTime * 1000000));

                    while (true) {
                        Frame frame = grabber.grabFrame();
                        if (frame == null) {
                            break;
                        }
                        double currentTime = (double) frame.timestamp / 1000000.0;
                        if (currentTime >= range.endTime) {
                            break;
                        }
                        recorder.record(frame);
                    }

                    recorder.stop();
                } catch (FrameRecorder.Exception e) {
                    e.printStackTrace();
                }
            }

            grabber.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteVideoFiles(List<String> filePaths) {
        for (String filePath : filePaths) {
            try {
                Path path = Paths.get(filePath);
                Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static double convertTimeToSeconds(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        int frames = Integer.parseInt(parts[3]);
        // 假设帧率为25fps，将帧转换为毫秒
        double milliseconds = frames * (1000.0 / 25.0);
        return hours * 3600 + minutes * 60 + seconds + milliseconds / 1000.0;
    }

    public static List<TimeRange> convertTimePointsToRanges(List<String> timePoints) {
        List<TimeRange> timeRanges = new ArrayList<>();
        for (int i = 0; i < timePoints.size(); i += 2) {
            double startTime = convertTimeToSeconds(timePoints.get(i));
            double endTime = convertTimeToSeconds(timePoints.get(i + 1));
            timeRanges.add(new TimeRange(startTime, endTime));
        }
        return timeRanges;
    }


    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 将给定的时间点转换为TimeRange列表
        List<String> timePoints = new ArrayList<>();
        timePoints.add("00:00:00:00");
        timePoints.add("00:05:12:23");
        timePoints.add("00:05:12:23");
        timePoints.add("00:06:31:18");
        timePoints.add("00:06:31:18");
        timePoints.add("00:06:47:18");

        List<TimeRange> timeRanges = convertTimePointsToRanges(timePoints);
        System.out.println(JSON.toJSONString(timeRanges));

        // 拆分网络视频文件
        String videoUrl = "https://vod.beebo.media/c8694cc4vodtranssgp1500034986/0e8ddb2a1397757902710361728/v.f100840.mp4";
        splitVideoFromUrlByTimeRanges(videoUrl, timeRanges);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());

        // 删除拆分后的文件
        List<String> outputFiles = new ArrayList<>();
        for (int i = 0; i < timeRanges.size(); i++) {
            outputFiles.add("output_video_" + i + ".mp4");
            outputFiles.add("output_video_from_url_" + i + ".mp4");
        }
//        deleteVideoFiles(outputFiles);
    }
}