package me.mrletsplay.streamdeckapi;
import java.awt.image.BufferedImage;
import java.io.IOException;

import me.mrletsplay.streamdeckapi.StreamDeck.HIDUpdate;

public class Main {
	
	public static void main(String[] args) throws IOException {
		StreamDeck sd = StreamDeckAPI.getDefaultStreamDeck();
		sd.setProfile(new StreamDeckProfile()
				.setButton(0, new StreamDeckButton() {
					
					@Override
					public BufferedImage getImage() {
						return null;
					}
				}.withListener(new StreamDeckButtonListener() {
					@Override
					public void onButtonPressed(HIDUpdate event) {
						System.out.println("HI");
					}
				})));
//		sd.getButtons().forEach(b -> b.fillColor(Color.RED).update());
	}
	
}
