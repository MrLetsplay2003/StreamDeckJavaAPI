package me.mrletsplay.streamdeckapi;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class TextPainter {
	
	private static final Canvas dbCom = new Canvas();

	private Font font;
	private StreamDeckButton button;
	
	public TextPainter(Font f, StreamDeckButton button) {
		this.font = f;
		this.button = button;
	}
	
	public static TextPainter loadOtf(File file, StreamDeckButton button) {
		try {
			InputStream stream = new FileInputStream(file);
			return new TextPainter(Font.createFont(Font.TRUETYPE_FONT, stream), button);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static TextPainter loadOtfFromClasspath(String path, StreamDeckButton button) {
		try {
			InputStream stream = Main.class.getResourceAsStream(path);
			return new TextPainter(Font.createFont(Font.TRUETYPE_FONT, stream), button);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int stringWidth(float size, String str) {
		return dbCom.getFontMetrics(font.deriveFont(size)).stringWidth(str);
	}
		
	public int getHeight(float size) {
		FontMetrics fm = dbCom.getFontMetrics(font.deriveFont(size));
		return fm.getHeight()+fm.getDescent();
	}
	
	public FontMetrics getFontMetrics(float size) {
		return  dbCom.getFontMetrics(font.deriveFont(size));
	}
	
	public void drawString(String str, int x, int y, float size, Color color) {
		Graphics2D g2d = button.getGraphics();
		Font f = g2d.getFont();
		g2d.setColor(color);
		g2d.setFont(font.deriveFont(size));
		g2d.drawString(str, x, y);
		g2d.setFont(f);
	}
	
	public void drawStringCenteredHorizontally(String str, int x, int y, float size, Color color) {
		List<String> lines = Arrays.asList(str.split("\n"));
		Graphics2D g2d = button.getGraphics();
		Font f = g2d.getFont();
		g2d.setColor(color);
		g2d.setFont(font.deriveFont(size));
		FontMetrics m = g2d.getFontMetrics(g2d.getFont());
		int fontHeight = m.getHeight() + m.getDescent() + m.getAscent();
		for(int i = 0; i < lines.size(); i++) {
			String str2 = lines.get(i);
			g2d.drawString(str2, x-m.stringWidth(str2)/2, y + fontHeight * i);
		}
		g2d.setFont(f);
	}
	
	public void drawStringCenteredVertically(String str, int x, int y, float size, Color color) {
		List<String> lines = Arrays.asList(str.split("\n"));
		Graphics2D g2d = button.getGraphics();
		Font f = g2d.getFont();
		g2d.setColor(color);
		g2d.setFont(font.deriveFont(size));
		FontMetrics m = g2d.getFontMetrics(g2d.getFont());
		int fontHeight = m.getHeight() + m.getDescent() + m.getAscent();
		int height = fontHeight * (lines.size() - 1);
		for(int i = 0; i < lines.size(); i++) {
			String str2 = lines.get(i);
			g2d.drawString(str2, x, y - height/2 + fontHeight * i + fontHeight/2);
		}
		g2d.setFont(f);
	}
	
	public void drawStringCenteredBoth(String str, int x, int y, float size, Color color) {
		List<String> lines = Arrays.asList(str.split("\n"));
		Graphics2D g2d = button.getGraphics();
		Font f = g2d.getFont();
		g2d.setColor(color);
		g2d.setFont(font.deriveFont(size));
		FontMetrics m = g2d.getFontMetrics(g2d.getFont());
		int fontHeight = m.getHeight() + m.getDescent() + m.getAscent();
		int height = fontHeight * (lines.size() - 1);
		for(int i = 0; i < lines.size(); i++) {
			String str2 = lines.get(i);
			g2d.drawString(str2, x-m.stringWidth(str2)/2, y - height/2 + fontHeight * i + fontHeight/2);
		}
		g2d.setFont(f);
	}
		
}
