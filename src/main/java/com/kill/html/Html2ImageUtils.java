package com.kill.html;

import gui.ava.html.image.generator.HtmlImageGenerator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * html转图片的工具类
 * 需要用到 html2image 的jar包
 * @author kill
 * @date 2021/11/2 15:42
 */
public class Html2ImageUtils {

    /**
     * 将html转成图片字节流
     * @param html html内容
     * @return 图片字节流
     * @throws IOException 异常
     */
    public static byte[] htmlAsImageByte(String html) throws IOException {
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.loadHtml(html);
        ByteArrayOutputStream outputStream = null;
        try {
            BufferedImage bufferedImage = imageGenerator.getBufferedImage();
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream );
            outputStream.flush();
            return outputStream.toByteArray();
        } finally {
            if (null != outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    //
                }
            }
        }
    }

    /**
     * 将html转成图片
     * @param html html
     * @param filePath 图片路径，示例：d:/a.png
     */
    public static void htmlAsImageFile(String html, String filePath){
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        imageGenerator.loadHtml(html);
        imageGenerator.saveAsImage(filePath);
    }

}
