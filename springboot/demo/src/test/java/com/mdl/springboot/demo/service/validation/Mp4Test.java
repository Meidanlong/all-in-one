package com.mdl.springboot.demo.service.validation;

import com.mdl.common.utils.HttpUtil;
import org.mp4parser.IsoFile;
import org.mp4parser.boxes.iso14496.part12.MovieBox;
import org.mp4parser.boxes.iso14496.part12.MovieHeaderBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * TODO
 *
 * @author meidanlong
 * @date 2024年07月09日
 * @version: 1.0
 */
public class Mp4Test {

    public static void main(String[] args) {
        getVideoDuration("https://vod.pipi.cn/27df3294vodbj1251246104/27af23231253642700728575194/f0.mp4");
    }

    public static void getVideoDuration(String url) {
        try {
            File mp4File = HttpUtil.downFile(url, ".mp4");
            IsoFile isoFile = new IsoFile(new FileInputStream(mp4File).getChannel());
            MovieBox movieBox = isoFile.getMovieBox();
            if (movieBox != null) {
                MovieHeaderBox movieHeaderBox = movieBox.getMovieHeaderBox();
                long duration = movieHeaderBox.getDuration();
                long timescale = movieHeaderBox.getTimescale();
                double durationInSeconds = (double) duration / timescale;
                long durationInMilliseconds = (long) (durationInSeconds * 1000);
                System.out.println("Duration: " + durationInMilliseconds + " ms");
            } else {
                System.out.println("MovieBox not found in the MP4 file.");
            }
            isoFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
