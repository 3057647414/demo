package com.linghushaoxia.kaptcha.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import sun.misc.BASE64Encoder;

import com.linghushaoxia.com.google.code.kaptcha.Producer;
import com.linghushaoxia.com.google.code.kaptcha.util.Config;

/**
 * 功能说明：验证码生成工具类
 * 生成浏览器可以识别的图片
 * 使用场景
 * 在网站任意需要验证码的位置，通过ajax向后台发请求，生成图片填充到image标签即可
 *
 * @author:linghushaoxia 生成算法来自google公司的kaptcha, 进行了封装
 * @time:2016年3月2日下午8:02:23
 * @version:1.0
 */
public class KaptchaServiceUtil {
    /**
     * 功能说明:
     *
     * @param properties
     * @return String[]
     * 长度为2的数组
     * 0：验证码图片的base64编码
     * 1：验证码的文本字符
     * @time:2016年3月3日下午8:31:23
     * @author:linghushaoxia
     * @exception:
     */
    public static String[] geneValidateCode(Properties properties) {
        //生成的验证码结果
        String[] result = {"", ""};
        ImageIO.setUseCache(false);
        //初始化配置
        Config config = new Config(properties);
        Producer kaptchaProducer = config.getProducerImpl();
        StringBuffer imageBuffer = new StringBuffer();
        // 创建图像中的文本
        String capText = kaptchaProducer.createText();
        // create the image with the text
        BufferedImage bi = kaptchaProducer.createImage(capText);
        // write the data out
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            //保存图片
            ImageIO.write(bi, "jpg", out);
            ImageIO.write(bi, "jpg", new File("kapatch.jpg"));
            //对图片进行base64编码，可以在浏览器中直接显示，可作为图片标签使用
            byte[] data = out.toByteArray();
            imageBuffer.append(new BASE64Encoder().encode(data));
        } catch (IOException e) {
            System.out.println("生成验证码出现异常:" + e.getMessage());
            e.printStackTrace();
        }
        if (imageBuffer != null) {
            //将字符拼接在前面和append方法相反
            imageBuffer.insert(0, "data:image/jpeg;base64,");
            result[0] = imageBuffer.toString();
            result[1] = capText;
        }
        return result;
    }

    public static String[] yzm(String[] args) {
        //验证码的属性
        Properties properties = new Properties();
        //边框
        properties.put("kaptcha.border", "yes");
        //边框为绿色
        properties.put("kaptcha.border.color", "green");
        //图片宽度
        properties.put("kaptcha.image.width", "200");
        //高度
        properties.put("kaptcha.image.height", "50");
        //字符数量
        properties.put("kaptcha.textproducer.char.length", "4");
        //字体大小
        properties.put("kaptcha.textproducer.font.size", "40");
        //字符间隔
        properties.put("kaptcha.textproducer.char.space", "2");
        //噪声颜色
        properties.put("kaptcha.noise.color", "black");
        //粘贴到浏览器，可以查看浏览器中的显示效果
        String[] validateCode = KaptchaServiceUtil.geneValidateCode(properties);
        System.out.println(validateCode[1]);
        System.out.println(validateCode[0]);
        return validateCode;
    }
}
