package cn.guo.privacyfix;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import android.content.Context;

public class ImeiStore {
	private static String[] str = new String[256];
	public static String getFromAssets(Context context) {
		try {
			InputStream is = context.getAssets().open("imeiStore");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			// Convert the buffer into a string.
			String result = new String(buffer, "GB2312");
			str = result.split("\n");

			Random r = new Random();
			int n = r.nextInt(207);
			while (str[n].subSequence(0, 1).equals("A")||str[n].subSequence(0, 1).equals("a")){
				 n = r.nextInt(207);
			} 
			System.out.println(str[n] + "+++" + str[n].subSequence(0, 1));
			return str[n];
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
