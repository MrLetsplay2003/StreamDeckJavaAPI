package me.mrletsplay.streamdeckapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StreamDeckProfile {
	
	private String name;
	private HashMap<Integer, StreamDeckButton> buttons;
	private StreamDeck deck;
	
	public StreamDeckProfile(String name) {
		buttons = new HashMap<>(StreamDeckAPI.NUM_BUTTONS);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	protected void init(StreamDeck deck) {
		this.deck = deck;
	}
	
	public StreamDeckProfile setButton(int index, StreamDeckButton button) {
		buttons.put(index, button);
		button.init(this, index);
		return this;
	}
	
	public StreamDeckButton getButton(int index) {
		return buttons.get(index);
	}
	
	public List<? extends StreamDeckButton> getButtons() {
		return new ArrayList<>(buttons.values());
	}
	
	public StreamDeck getDeck() {
		return deck;
	}
	
}
