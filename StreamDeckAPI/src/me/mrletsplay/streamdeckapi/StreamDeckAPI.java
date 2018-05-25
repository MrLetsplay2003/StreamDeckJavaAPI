package me.mrletsplay.streamdeckapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import purejavahidapi.HidDeviceInfo;
import purejavahidapi.PureJavaHidApi;

public class StreamDeckAPI {
	
	static final int
			PAGE_PACKET_SIZE = 8191,
			NUM_FIRST_PAGE_PIXELS = 2583,
			NUM_SECOND_PAGE_PIXELS = 2601,
			ICON_SIZE = 72,
			NUM_TOTAL_PIXELS = NUM_FIRST_PAGE_PIXELS + NUM_SECOND_PAGE_PIXELS,
			NUM_BUTTON_COLUMNS = 5,
			NUM_BUTTON_ROWS = 3,
			NUM_BUTTONS = NUM_BUTTON_COLUMNS * NUM_BUTTON_ROWS;
	
	static final short
			VENDOR_ID = 4057,
			PRODUCT_ID = 96;
	
	static final byte
			RESET_DATA_ID = 0x0B,
			BRIGHTNESS_DATA_ID = 0x05;
	
	static final byte[]
			RESET_DATA = new byte[] {0x63, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			BRIGHTNESS_DATA = new byte[] {0x55, (byte) 0xAA, (byte) 0xD1, 0x01, 0x0A, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	
	private static List<StreamDeck> decks = new ArrayList<>();

	static {
		for(HidDeviceInfo d : PureJavaHidApi.enumerateDevices()) {
			if(d.getProductId() == PRODUCT_ID && d.getVendorId() == VENDOR_ID) {
				try {
					decks.add(new StreamDeck(PureJavaHidApi.openDevice(d)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static List<StreamDeck> getStreamDecks() {
		return decks;
	}
	
	public static StreamDeck getDefaultStreamDeck() {
		return decks.get(0);
	}
	
}
