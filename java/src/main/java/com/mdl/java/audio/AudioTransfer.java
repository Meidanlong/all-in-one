package com.mdl.java.audio;

import lombok.extern.slf4j.Slf4j;
import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.InputFormatException;
import ws.schild.jave.MultimediaObject;

import java.io.File;

/**
 * @author meidanlong
 * @description: TODO
 * @date 2023年11月09日
 * @version: 1.0
 */
public class AudioTransfer {

    public static void main(String[] args) throws Exception {
        File source = new File("/Users/meidanlong/Desktop/aamr.amr");   //源文件
        File mp3Target = new File("/Users/meidanlong/Desktop/bmp3.mp3");   //目标文件
        audioTransfer(source, mp3Target,"mp3");
        System.out.println("finished transfer mp3");
        File amrTarget = new File("/Users/meidanlong/Desktop/newaamr.amr");
        audioTransfer(mp3Target, amrTarget,"mp3");
        System.out.println("finished transfer amr");
    }
    public static void audioTransfer(File source, File target, String format) throws InputFormatException {
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat(format);
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
