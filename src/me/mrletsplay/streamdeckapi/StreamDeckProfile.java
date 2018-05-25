package me.mrletsplay.streamdeckapi;

import java.util.ArrayList;
import java.util.List;

public class StreamDeckProfile {
	
	private List<StreamDeckButton> buttons;
	private StreamDeck deck;
	
	public StreamDeckProfile() {
		buttons = new ArrayList<>(StreamDeckAPI.NUM_BUTTONS);
	}
	
	protected void init(StreamDeck deck) {
		this.deck = deck;
	}
	
	public StreamDeckProfile setButton(int index, StreamDeckButton button) {
		buttons.set(index, button);
		return this;
	}
	
	public StreamDeckButton getButton(int index) {
		return buttons.get(index);
	}
	
	public List<? extends StreamDeckButton> getButtons() {
		return buttons;
	}
	
	public StreamDeck getDeck() {
		return deck;
	}
	
}
