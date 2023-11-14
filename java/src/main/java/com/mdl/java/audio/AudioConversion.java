package com.mdl.java.audio;

//import ws.schild.jave.AudioAttributes;
import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
//import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.MultimediaObject;
//import ws.schild.jave.encode.AudioAttributes;
//import ws.schild.jave.encode.EncodingAttributes;
//import ws.schild.jave.process.ProcessWrapper;
//import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author meidanlong
 * @description: TODO
 * @date 2023年11月09日
 * @version: 1.0
 */
public class AudioConversion {

    public static void main(String[] args) {
        File source = new File("java/src/main/java/com/mdl/java/audio/ori-amr.amr");   // 源amr文件
        File mp3Target = new File("java/src/main/java/com/mdl/java/audio/new-p3.mp3");   // 目标mp3文件
        convertAudio(source, mp3Target);
        System.out.println("conversion finished amr -> mp3");
        File amrTarget = new File("java/src/main/java/com/mdl/java/audio/new-amr2.amr");  // 目标amr文件
        convertAudio(mp3Target, amrTarget);
        convertAudio(mp3Target, amrTarget);
        System.out.println("conversion finished mp3 -> amr");
    }


    public static void convertAudio(File source, File target) {
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");//mp3
        audio.setCodec("libopencore_amrnb");//amr-nb
        audio.setCodec("libvo_amrwbenc");//amr-wb
        audio.setCodec("pcm_s16le");//wav
        audio.setChannels(1);
        audio.setSamplingRate(16000);
        EncodingAttributes attrs = new EncodingAttributes();
//        attrs.setInputFormat("mp3");
        attrs.setFormat("amr");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        try {
            MultimediaObject multimediaObject  = new MultimediaObject(source);
            encoder.encode(multimediaObject,target, attrs);
        } catch (IllegalArgumentException | EncoderException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对于格式属性，可以调整成需要的数值。如果需要其他的Codec，可以使用如下方法打印出所有支持的Codec，选择所需要的。
     */
    public static void codec() {
        Encoder encoder = new Encoder();
        try {
            for (int i = 0; i < encoder.getAudioEncoders().length; i++) {
                System.out.println(encoder.getAudioEncoders()[i].toString());
            }
        } catch (EncoderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 对于支持的格式种类，可以使用如下方法打印出所有支持的格式。
     */
    public static void encodingFormats() {
        Encoder encoder = new Encoder();
        try {
            for (int i = 0; i < encoder.getSupportedEncodingFormats().length; i++) {
                System.out.println(encoder.getSupportedEncodingFormats()[i].toString());
            }
        } catch (EncoderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
