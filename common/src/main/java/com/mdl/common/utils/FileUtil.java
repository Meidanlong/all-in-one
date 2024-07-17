package com.mdl.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * 文件工具
 *
 * @author meidanlong
 * @date 2024年07月17日
 * @version: 1.0
 */
public class FileUtil {

    public static void saveBase64Image(String base64String, String outputFileName) {
        try {
            // 去除Base64字符串中的前缀（如果有）
            if (base64String.contains(",")) {
                base64String = base64String.split(",")[1];
            }

            // 解码Base64字符串
            byte[] imageBytes = Base64.getDecoder().decode(base64String);

            // 将字节数组写入文件
            try (FileOutputStream fos = new FileOutputStream(outputFileName)) {
                fos.write(imageBytes);
            }
            System.out.println("图片已成功保存到 " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        String base64 = "";
        File file = new File("~/Desktop/swap_face_image.png");
        File.createTempFile("", "", file);
        saveBase64Image(base64, file.getName());

    }
}
