package me.mrletsplay.streamdeckapi;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

import me.mrletsplay.streamdeckapi.StreamDeck.HIDUpdate;

public class StreamDeckButton {
	
	private BufferedImage image;
	private StreamDeckProfile profile;
	private boolean pressed;
	private int buttonNumber;
	private Consumer<HIDUpdate> onPressed, onReleased;

	protected void init(StreamDeckProfile profile, int buttonNumber) {
		this.profile = profile;
		this.buttonNumber = buttonNumber;
	}
	
	public int getButtonNumber() {
		return buttonNumber;
	}
	
	public StreamDeckProfile getProfile() {
		return profile;
	}
	
	protected void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	public boolean isPressed() {
		return pressed;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public StreamDeckButton onPressed(Consumer<HIDUpdate> onPressed) {
		this.onPressed = onPressed;
		return this;
	}
	
	public StreamDeckButton onReleased(Consumer<HIDUpdate> onReleased) {
		this.onReleased = onReleased;
		return this;
	}
	
	public Consumer<HIDUpdate> getOnPressed() {
		return onPressed;
	}
	
	public Consumer<HIDUpdate> getOnReleased() {
		return onReleased;
	}
	
	public StreamDeckButton fillColor(Color color) {
		this.image = ImageTools.solidColor(72, 72, color);
		return this;
	}
	
}
