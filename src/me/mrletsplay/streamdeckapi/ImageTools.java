package me.mrletsplay.streamdeckapi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImageTools {
	
	public static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;
		int[][] result = new int[height][width];
		if (hasAlphaChannel) {
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += ((pixels[pixel] & 0xff) << 24); // alpha
				argb += (pixels[pixel + 1] & 0xff); // blue
				argb += ((pixels[pixel + 2] & 0xff) << 8); // green
				argb += ((pixels[pixel + 3] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += (pixels[pixel] & 0xff); // blue
				argb += ((pixels[pixel + 1] & 0xff) << 8); // green
				argb += ((pixels[pixel + 2] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		}

		return result;
	}
	
	public static BufferedImage resize(BufferedImage img, int nW, int nH) {
		BufferedImage tR = new BufferedImage(nW, nH, BufferedImage.TYPE_3BYTE_BGR);
		tR.createGraphics().drawImage(img, 0, 0, nW, nH, null);
		return tR;
	}
	
	public static BufferedImage solidColor(int width, int height, Color color) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics g2d = image.createGraphics();
		g2d.setColor(color);
		g2d.fillRect(0, 0, width, height);
		return image;
	}
	
	public static BufferedImage[] slice(BufferedImage img, int width, int height) {
		BufferedImage[] imgs = new BufferedImage[img.getWidth()/width * img.getHeight()/height];
		int count = 0;
		for (int x = 0; x < img.getWidth()/width; x++) {
			for (int y = 0; y < img.getHeight()/height; y++) {
				imgs[count] = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D g2d = imgs[count].createGraphics();
				g2d.drawImage(img, 0, 0, width, height, width * x, height * y, width * x + width, height * y + height, null);
				count++;
			}
		}
		return imgs;
	}

}
