package me.mrletsplay.streamdeckapi;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class StreamDeckButton {

	private int buttonNumer;
	private StreamDeck deck;
	private BufferedImage image;
	
	public StreamDeckButton(int buttonNumber, StreamDeck deck) {
		this.buttonNumer = buttonNumber;
		this.deck = deck;
	}
	
	public int getButtonNumer() {
		return buttonNumer;
	}
	
	public StreamDeck getDeck() {
		return deck;
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
