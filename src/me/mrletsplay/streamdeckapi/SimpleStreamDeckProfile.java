package me.mrletsplay.streamdeckapi;

import java.awt.image.BufferedImage;
import java.util.List;

public class SimpleStreamDeckProfile extends StreamDeckProfile {
	
	private List<StaticStreamDeckButton> buttons;
	
	public void fillWithImage(BufferedImage image) {
		BufferedImage[] sliced = ImageTools.slice(ImageTools.resize(image, 72 * StreamDeckAPI.NUM_BUTTON_COLUMNS, 72 * StreamDeckAPI.NUM_BUTTON_ROWS), 72, 72);
		for(int i = 0; i < sliced.length; i++) {
			StaticStreamDeckButton button = getButton(i);
			button.setImage(sliced[i]);
			button.draw();
		}
		getDeck().update();
	}
	
	@Override
	public List<? extends StreamDeckButton> getButtons() {
		return buttons;
	}
	
	@Deprecated
	@Override
	public StreamDeckProfile setButton(int index, StreamDeckButton button) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public StaticStreamDeckButton getButton(int index) {
		return buttons.get(index);
	}
	
}
