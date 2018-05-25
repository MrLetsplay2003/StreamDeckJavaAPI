package me.mrletsplay.streamdeckapi;

public class Tools {
	
	public static byte[] copy(byte[] bytes) {
		byte[] newBs = new byte[bytes.length];
		System.arraycopy(bytes, 0, newBs, 0, bytes.length);
		return newBs;
	}
	
	public static byte[] reverse(byte[] bytes) {
		byte[] newBs = new byte[bytes.length];
		for(int i = 0; i < bytes.length; i++) {
			newBs[bytes.length - i - 1] = bytes[i];
		}
		return newBs;
	}

	public static byte[] concat(byte[] bytes, byte[] bytes2) {
		byte[] newBs = new byte[bytes.length + bytes2.length];
		System.arraycopy(bytes, 0, newBs, 0, bytes.length);
		System.arraycopy(bytes2, 0, newBs, bytes.length, bytes2.length);
		return newBs;
	}

	public static byte[] allocate(int size, byte[] fill) {
		byte[] buf = new byte[size];
		int bytesWritten = 0;
		for(int i = 0; i < size / fill.length; i++) {
			if(bytesWritten + fill.length < size) {
				System.arraycopy(fill, 0, buf, i * fill.length, fill.length);
			}else {
				System.arraycopy(fill, 0, buf, i * fill.length, size - bytesWritten);
			}
			bytesWritten += fill.length;
		}
		return buf;
	}
	
	public static byte[] padBytes(byte[] bytes, int toLength) {
		return concat(bytes, new byte[toLength - bytes.length]);
	}
	
}
