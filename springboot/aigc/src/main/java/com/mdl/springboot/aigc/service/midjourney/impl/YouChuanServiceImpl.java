package com.mdl.springboot.aigc.service.midjourney.impl;

import com.mdl.common.utils.HttpUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.io.ByteArrayOutputStream;

/**
 * 悠船服务
 *
 * @author meidanlong
 * @date 2024年11月26日
 * @version: 1.0
 */
public class YouChuanServiceImpl {


    /**
     * 下载图片并转换为带 alpha 通道的 PNG 格式的 base64 编码
     *
     * @param imageUrl 图片的URL
     * @return base64 编码的字符串，格式为 "data:image/png;base64,..."
     * @throws IOException 如果下载或转换失败
     */
    public String downloadAndConvertToPngBase64WithAlpha(String imageUrl) throws IOException {
        File file = HttpUtil.downFile(imageUrl, ".png");
        BufferedImage image = ImageIO.read(new FileInputStream(file));
        if (image == null) {
            throw new IOException("Failed to read image: " + imageUrl);
        }

        // 确保图像有 alpha 通道
        BufferedImage pngImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        pngImage.getGraphics().drawImage(image, 0, 0, null);

        // 将图像转换为PNG格式的字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(pngImage, "png", baos);
        byte[] imageBytes = baos.toByteArray();

        // 将字节数组转换为base64编码
        String base64String = Base64.getEncoder().encodeToString(imageBytes);
        return "data:image/png;base64," + base64String;
    }


    public static void main(String[] args) {
        YouChuanServiceImpl service = new YouChuanServiceImpl();
        String imageUrl = "https://p0.pipi.cn/mediaplus/mobileios_fe_aigc/cdf05c5cd8a5c2e327d818763e6b63ee941e2.png?imageMogr2/quality/80";
        try {
            String base64Image = service.downloadAndConvertToPngBase64WithAlpha(imageUrl);
            System.out.println(base64Image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
