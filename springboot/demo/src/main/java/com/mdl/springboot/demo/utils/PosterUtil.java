package com.mdl.springboot.demo.utils;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.AttributedString;

/**
 * 海报工具类
 *
 * @author meidanlong
 * @date 2024年11月11日
 * @version: 1.0
 */
public class PosterUtil {

    // 应在配置中心维护
    private final static int TO_LEFT = 100;
    private final static int TO_BOTTOM = 20;

    public static void main(String[] args) {
        String imgUrl = "https://p0.pipi.cn/mediaplus/platform_aigc_paintbrush/cdf05c5c5c28d34a185294501259b38fa2c6f.png?imageMogr2/quality/80|watermark/2/text/5YaF5a6555SxQUnnlJ_miJA/fontsize/20/fill/IzgwODA4MA/gravity/southeast/dx/50/dy/50/";
        try {
            BufferedImage image = downloadImage(imgUrl);
            Graphics2D g2d = image.createGraphics();
            try {
                processImage(g2d, image);
            } finally {
                g2d.dispose(); // 确保Graphics2D资源被释放
            }
            saveImage(image, "./poster.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage downloadImage(String urlString) throws IOException {
        URL url = new URL(urlString);
        try (InputStream in = url.openStream()) {
            return ImageIO.read(in);
        }
    }

    private static void processImage(Graphics2D g2d, BufferedImage image) {
        Color avgColor = getAverageColor(image);
        Color textColor = getContrastColor(avgColor);
        // 阴影效果
//            g2d.setPaint(Color.GRAY);
//            g2d.drawString("阴影文字", 53, 53);
//            g2d.setPaint(Color.BLACK);
//            g2d.drawString("阴影文字", 50, 50);

        // 渐变效果
//            GradientPaint gradient = new GradientPaint(0, 0, Color.BLUE, 100, 0, Color.RED);
//            g2d.setPaint(gradient);
//            g2d.drawString("渐变文字", 50, 100);

        // 描边效果
//            g2d.setColor(Color.BLACK);
//            g2d.setStroke(new BasicStroke(3));
//            FontRenderContext frc = g2d.getFontRenderContext();
//            TextLayout tl = new TextLayout("描边文字", customFont, frc);
//            Shape shape = tl.getOutline(AffineTransform.getTranslateInstance(50, 150));
//            g2d.draw(shape);
//            g2d.setColor(Color.YELLOW);
//            g2d.fill(shape);
        Font customFont = new Font("Serif", Font.BOLD, image.getWidth() / 20);
        g2d.setFont(customFont);
        g2d.setColor(textColor);
        String text = "这是一段很长的艺术字文本，需要自动换行以适应图片宽度。";
        int wrapWidth = image.getWidth() - TO_LEFT * 2;
        drawMultilineText(g2d, text, TO_LEFT, image.getHeight() - TO_BOTTOM, wrapWidth);
    }

    private static void saveImage(BufferedImage image, String filePath) throws IOException {
        File file = new File(filePath);
        ImageIO.write(image, "PNG", file);
    }

    private static void drawMultilineText(Graphics2D g2d, String text, float x, float bottomY, float wrapWidth) {
        AttributedString attributedString = new AttributedString(text);
        attributedString.addAttribute(TextAttribute.FONT, g2d.getFont());
        FontRenderContext frc = g2d.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(attributedString.getIterator(), frc);
        float textHeight = 0;
        while (measurer.getPosition() < text.length()) {
            TextLayout layout = measurer.nextLayout(wrapWidth);
            textHeight += layout.getAscent() + layout.getDescent() + layout.getLeading();
        }
        measurer = new LineBreakMeasurer(attributedString.getIterator(), frc);
        float drawPosY = bottomY - textHeight;
        while (measurer.getPosition() < text.length()) {
            TextLayout layout = measurer.nextLayout(wrapWidth);
            drawPosY += layout.getAscent();
            layout.draw(g2d, x, drawPosY);
            drawPosY += layout.getDescent() + layout.getLeading();
        }
    }

    private static Color getAverageColor(BufferedImage image) {
        long sumR = 0, sumG = 0, sumB = 0;
        int totalPixels = image.getWidth() * image.getHeight();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color pixel = new Color(image.getRGB(x, y));
                sumR += pixel.getRed();
                sumG += pixel.getGreen();
                sumB += pixel.getBlue();
            }
        }
        return new Color((int) (sumR / totalPixels), (int) (sumG / totalPixels), (int) (sumB / totalPixels));
    }

    private static Color getContrastColor(Color color) {
        double luminance = (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue()) / 255;
        return luminance > 0.5 ? Color.BLACK : Color.WHITE;
    }
}
