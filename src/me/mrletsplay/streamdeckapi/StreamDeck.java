package me.mrletsplay.streamdeckapi;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import purejavahidapi.HidDevice;
import purejavahidapi.InputReportListener;

public class StreamDeck implements InputReportListener {
	
	private HidDevice device;
	private HashMap<String, StreamDeckProfile> profiles;
	private StreamDeckProfile currentProfile;

	public StreamDeck(HidDevice device) {
		this.device = device;
		device.setInputReportListener(this);
		this.profiles = new HashMap<>();
	}
	
	public void registerProfile(StreamDeckProfile profile) {
		profiles.put(profile.getName(), profile);
		profile.init(this);
	}
	
	public void selectProfile(String name) {
		if(!profiles.containsKey(name)) throw new IllegalArgumentException("Specified profile doesn't exist");
		currentProfile = profiles.get(name);
		update();
	}
	
	public StreamDeckProfile getCurrentProfile() {
		return currentProfile;
	}
	
	public StreamDeckButton getButton(int index) {
		return currentProfile.getButton(index);
	}
	
	public List<? extends StreamDeckButton> getButtons(StreamDeckProfile profile) {
		return currentProfile.getButtons();
	}
	
	public void setBrightness(int brightness) {
		byte[] data = Tools.copy(StreamDeckAPI.BRIGHTNESS_DATA);
		data[4] = (byte) brightness;
		device.setFeatureReport(StreamDeckAPI.BRIGHTNESS_DATA_ID, data, data.length);
	}
	
	public void update() {
		if(currentProfile == null) throw new IllegalArgumentException("No profile is currently selected");
		for(int i = 0; i < StreamDeckAPI.NUM_BUTTONS; i++) {
			StreamDeckButton b = getButton(i);
			if(b != null) {
				if(b.getImage() == null) throw new IllegalArgumentException("Button image is null");
				drawImage(i, b.getImage());
			}else {
				setColorRaw(i, Color.BLACK);
			}
		}
	}
	
	protected void setColorRaw(int buttonNumber, Color color) {
		byte[] colBuf = new byte[] {(byte) color.getBlue() /* red */, (byte) color.getGreen() /* blue */, (byte)  color.getRed() /* green */};
		byte[] packet = Tools.allocate(StreamDeckAPI.NUM_FIRST_PAGE_PIXELS * 3, colBuf);
		byte[] packet2 = Tools.allocate(StreamDeckAPI.NUM_SECOND_PAGE_PIXELS * 3, colBuf);
		writePage1(buttonNumber, packet);
		writePage2(buttonNumber, packet2);
	}
	
	protected void drawImage(int buttonNumber, BufferedImage image) {
		if(image == null) return;
		int[][] colors = ImageTools.convertTo2DWithoutUsingGetRGB(ImageTools.resize(image, 72, 72));

		ByteArrayOutputStream fOut = new ByteArrayOutputStream();
		
		
		for(int x = 0; x < 72; x++) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			for(int y = 0; y < 72; y++) {
				int color = colors[x][y];
				Color col = new Color(color);
				out.write(col.getRed());
				out.write(col.getGreen());
				out.write(col.getBlue());
			}
			try {
				fOut.write(Tools.reverse(out.toByteArray()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		byte[] pixels = fOut.toByteArray();
		
		byte[] p1 = new byte[StreamDeckAPI.NUM_FIRST_PAGE_PIXELS * 3];
		System.arraycopy(pixels, 0, p1, 0, p1.length);
		
		byte[] p2 = new byte[StreamDeckAPI.NUM_SECOND_PAGE_PIXELS * 3];
		System.arraycopy(pixels, p1.length, p2, 0, p2.length);
		
		writePage1(buttonNumber, p1);
		writePage2(buttonNumber, p2);
	}
	
	private void writePage1(int buttonNumber, byte[] buffer) {
		byte[] pageHeader = new byte[] {
				/*0x02,*/ 0x01, 0x01, 0x00, 0x00, (byte) (buttonNumber + 1), 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x42, 0x4d, (byte) 0xf6, 0x3c, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x36, 0x00, 0x00, 0x00, 0x28, 0x00,
				0x00, 0x00, 0x48, 0x00, 0x00, 0x00, 0x48, 0x00,
				0x00, 0x00, 0x01, 0x00, 0x18, 0x00, 0x00, 0x00,
				0x00, 0x00, (byte) 0xc0, 0x3c, 0x00, 0x00, (byte) 0xc4, 0x0e,
				0x00, 0x00, (byte) 0xc4, 0x0e, 0x00, 0x00, 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00
		};
		byte[] packet = Tools.padBytes(Tools.concat(pageHeader, buffer), StreamDeckAPI.PAGE_PACKET_SIZE - 1);
		device.setOutputReport((byte) 0x02, packet, packet.length);
		
	}
	
	private void writePage2(int buttonNumber, byte[] buffer) {
		byte[] pageHeader = new byte[] {
				/*0x02,*/ 0x01, 0x02, 0x00, 0x01, (byte) (buttonNumber + 1), 0x00, 0x00,
				0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
		};
		byte[] packet = Tools.padBytes(Tools.concat(pageHeader, buffer), StreamDeckAPI.PAGE_PACKET_SIZE - 1);
		device.setOutputReport((byte) 0x02, packet, packet.length);
	}
	
	@Override
	public void onInputReport(HidDevice device, byte reportID, byte[] reportData, int reportLength) {
		HIDUpdate upd = new HIDUpdate(device, reportID, reportData, reportLength);
		for(int i = 0; i < reportData.length; i++) {
			StreamDeckButton button = currentProfile.getButton(i);
			if(button == null) continue;
			if(reportData[i] == 1) {
				if(!button.isPressed()) {
					if(button.getOnPressed() != null) button.getOnPressed().accept(upd);
				}
				button.setPressed(true);
			}else {
				if(!button.isPressed()) {
					if(button.getOnReleased() != null) button.getOnReleased().accept(upd);
				}
				button.setPressed(false);
			}
		}
	}
	
	public static class HIDUpdate {
		
		private HidDevice device;
		private byte reportID;
		private byte[] reportData;
		private int reportLength;
		
		public HIDUpdate(HidDevice device, byte reportID, byte[] reportData, int reportLength) {
			this.device = device;
			this.reportID = reportID;
			this.reportData = reportData;
			this.reportLength = reportLength;
		}
		
		public HidDevice getDevice() {
			return device;
		}
		
		public byte getReportID() {
			return reportID;
		}
		
		public byte[] getReportData() {
			return reportData;
		}
		
		public int getReportLength() {
			return reportLength;
		}
		
	}
	
	public static class UpdateMethod {
		
		public static final UpdateMethod MANUAL = new UpdateMethod();
		
		
		
	}
	
}
