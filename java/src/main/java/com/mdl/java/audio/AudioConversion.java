package com.mdl.java.audio;

import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.MultimediaObject;

import java.io.File;

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
        File amrTarget = new File("java/src/main/java/com/mdl/java/audio/new-amr.amr");  // 目标amr文件
        convertAudio(mp3Target, amrTarget);
        System.out.println("conversion finished mp3 -> amr");
    }
    public static void convertAudio(File source, File target) {
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        try {
            MultimediaObject multimediaObject  = new MultimediaObject(source);
            encoder.encode(multimediaObject,target, attrs);
        } catch (IllegalArgumentException | EncoderException e) {
            e.printStackTrace();
        }
    }
}
