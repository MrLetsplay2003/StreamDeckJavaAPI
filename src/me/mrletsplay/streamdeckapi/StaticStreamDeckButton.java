package me.mrletsplay.streamdeckapi;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class StaticStreamDeckButton extends StreamDeckButton {

	private int buttonNumer;
	private BufferedImage image;
	private boolean pressed;
	private StreamDeckProfile profile;
	
	private StreamDeckButtonListener listener;
	
	public StaticStreamDeckButton(BufferedImage image) {
		this.image = image;
	}
	
	protected void init(int buttonNumber, StreamDeckProfile profile) {
		this.buttonNumer = buttonNumber;
		this.profile = profile;
	}
	
	public StaticStreamDeckButton withListener(StreamDeckButtonListener listener) {
		this.listener = listener;
		return this;
	}
	
	public boolean hasListener() {
		return listener != null;
	}
	
	public StreamDeckButtonListener getListener() {
		return listener;
	}
	
	public int getButtonNumer() {
		return buttonNumer;
	}
	
	public StreamDeckProfile getProfile() {
		return profile;
	}
	
	public boolean isPressed() {
		return pressed;
	}
	
	protected void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	public StaticStreamDeckButton setImage(BufferedImage image) {
		this.image = image;
		return this;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public StaticStreamDeckButton fillColor(Color color) {
		this.image = ImageTools.solidColor(72, 72, color);
		return this;
	}
	
	public void update() {
		profile.getDeck().drawImage(buttonNumer, image);
	}
	
}
