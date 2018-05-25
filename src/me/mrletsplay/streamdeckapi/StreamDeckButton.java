package me.mrletsplay.streamdeckapi;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class StreamDeckButton {

	private int buttonNumer;
	private StreamDeck deck;
	private BufferedImage image;
	private boolean pressed;
	private StreamDeckButtonListener listener;
	
	public StreamDeckButton(int buttonNumber, StreamDeck deck) {
		this.buttonNumer = buttonNumber;
		this.deck = deck;
	}
	
	public void setListener(StreamDeckButtonListener listener) {
		this.listener = listener;
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
	
	public StreamDeck getDeck() {
		return deck;
	}
	
	public boolean isPressed() {
		return pressed;
	}
	
	protected void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	public StreamDeckButton setImage(BufferedImage image) {
		this.image = image;
		return this;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public StreamDeckButton fillColor(Color color) {
		this.image = ImageTools.solidColor(72, 72, color);
		return this;
	}
	
	public void update() {
		deck.drawImage(buttonNumer, image);
	}
	
}
