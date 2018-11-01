package com.manage.utils;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import org.apache.log4j.Logger;


public class SecurityImageTool
{
  private static final Logger LOG = Logger.getLogger(SecurityImageTool.class);

  public static final String[] FONT_FAMILY = { "Arial", "Verdana", "Comic Sans MS", "Impact", "Haettenschweiler", "Lucida Sans Unicode", "Garamond", "Courier New", "Book Antiqua", "Arial Narrow" };

  public static BufferedImage createImage(String securityCode)
  {
    int codeLength = securityCode.length();
    int fSize = 15;
    int fWidth = fSize + 1;
    int width = codeLength * fWidth + 6;
    int height = fSize * 2 + 1;

    BufferedImage image = new BufferedImage(width, height, 1);
    Graphics g = image.createGraphics();

    g.setColor(Color.WHITE);
    g.fillRect(0, 0, width, height);

    g.setColor(new Color(RandomUtils.getInt(Integer.valueOf(0), Integer.valueOf(255)), RandomUtils.getInt(Integer.valueOf(0), Integer.valueOf(255)), RandomUtils.getInt(Integer.valueOf(0), Integer.valueOf(255))));
    g.fill3DRect(RandomUtils.getInt(Integer.valueOf(0), Integer.valueOf(width)), RandomUtils.getInt(Integer.valueOf(0), Integer.valueOf(height)), RandomUtils.getInt(Integer.valueOf(0), Integer.valueOf(width / 3)), RandomUtils.getInt(Integer.valueOf(0), Integer.valueOf(height / 3)), true);

    for (int i = 0; i < 3; i++) {
      g.setColor(new Color(RandomUtils.getInt(Integer.valueOf(0), Integer.valueOf(255)), RandomUtils.getInt(Integer.valueOf(0), Integer.valueOf(255)), RandomUtils.getInt(Integer.valueOf(0), Integer.valueOf(255))));
      g.drawLine(RandomUtils.getInt(Integer.valueOf(0), Integer.valueOf(width)), RandomUtils.getInt(Integer.valueOf(0), Integer.valueOf(height)), RandomUtils.getInt(Integer.valueOf(0), Integer.valueOf(width)), RandomUtils.getInt(Integer.valueOf(0), Integer.valueOf(height)));
    }

    g.setColor(Color.LIGHT_GRAY);

    g.setFont(new Font("Arial", 1, height - 2));
    g.drawRect(0, 0, width - 1, height - 1);

    Random rand = new Random();
    g.setColor(Color.LIGHT_GRAY);
    for (int i = 0; i < codeLength * 6 + RandomUtils.getInt(Integer.valueOf(10), Integer.valueOf(15)); i++) {
      int x = rand.nextInt(width);
      int y = rand.nextInt(height);
      g.drawRect(x, y, 1, 1);
    }

    int codeY = height - 10;
    for (int i = 0; i < codeLength; i++) {
      g.setColor(new Color(19, 148, RandomUtils.getInt(Integer.valueOf(100), Integer.valueOf(246))));
      g.setFont(new Font(FONT_FAMILY[RandomUtils.getInt(Integer.valueOf(0), Integer.valueOf(10))], 1, fSize+9));
      g.drawString(String.valueOf(securityCode.charAt(i)), i * 16 + 5, codeY);
    }

    g.dispose();
    return image;
  }

  public ByteArrayInputStream convertImageToStream(BufferedImage image)
  {
    ByteArrayInputStream inputStream = null;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(bos);
    try {
      jpeg.encode(image);
      byte[] bts = bos.toByteArray();
      inputStream = new ByteArrayInputStream(bts);
    } catch (ImageFormatException e) {
      LOG.error("文件转换失败！", e);
    } catch (IOException e) {
      LOG.error("文件创建异常！", e);
    }
    return inputStream;
  }

  public ByteArrayInputStream getImageAsInputStream(String securityCode)
  {
    BufferedImage image = createImage(securityCode);
    return convertImageToStream(image);
  }
}