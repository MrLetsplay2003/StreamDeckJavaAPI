package me.mrletsplay.streamdeckapi;

import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class StreamDeckButton {

	private int buttonNumer;
	private BufferedImage image;
	private boolean pressed;
	private StreamDeckProfile profile;
	
	private StreamDeckButtonListener listener;
	
	protected void init(int buttonNumber, StreamDeckProfile profile) {
		this.buttonNumer = buttonNumber;
		this.profile = profile;
	}
	
	public StreamDeckButton withListener(StreamDeckButtonListener listener) {
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
	
	public abstract BufferedImage getImage();
	
	public StreamDeckButton fillColor(Color color) {
		this.image = ImageTools.solidColor(72, 72, color);
		return this;
	}
	
	protected void draw() {
		profile.getDeck().drawImage(buttonNumer, image);
	}
	
}
