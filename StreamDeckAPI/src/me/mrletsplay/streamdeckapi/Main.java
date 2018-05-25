package me.mrletsplay.streamdeckapi;
import java.awt.Color;
import java.io.IOException;

public class Main {
	
	public static void main(String[] args) throws IOException {
		StreamDeck sd = StreamDeckAPI.getDefaultStreamDeck();
		sd.getButtons().forEach(b -> b.fillColor(Color.RED).update());
	}
	
}
