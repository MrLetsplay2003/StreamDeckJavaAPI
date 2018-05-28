package me.mrletsplay.streamdeckapi;
import java.io.IOException;

public class Main {
	
	public static void main(String[] args) throws IOException {
		StreamDeck sd = StreamDeckAPI.getDefaultStreamDeck();
		sd.registerProfile(new StreamDeckProfile("whatever")
				.setButton(0, new StreamDeckButton()
						.onPressed(u -> System.out.println(u.getReportID()))
				));
		sd.switchProfile("whatever");
		sd.update();
//		sd.getButtons().forEach(b -> b.fillColor(Color.RED).update());
	}
	
}
