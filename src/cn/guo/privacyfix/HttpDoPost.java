package cn.guo.privacyfix;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.os.Message;
import android.util.Log;


public class HttpDoPost {

	private String url;

	public String httpPost(String str0,String str1, String str2, String str3, String str4,
			String str5, String Str6, String str7, String str8, String str9,
			String str10, String str11, String str12) {// 通过Http Post请求
			url = "http://192.168.155.1:8282/MainHook/"+str0;
			System.out.println(url);
		HttpPost post = new HttpPost(url);
		// 设置POST请求中的参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(str1, str2));
		params.add(new BasicNameValuePair(str3, str4));
		params.add(new BasicNameValuePair(str5, Str6));
		params.add(new BasicNameValuePair(str7, str8));
		params.add(new BasicNameValuePair(str9, str10));
		params.add(new BasicNameValuePair(str11, str12));
		try {
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));// 将参数设置入POST请求
			HttpClient client = new DefaultHttpClient();
			/* 从连接池中取连接的超时时�? */
			ConnManagerParams.setTimeout(client.getParams(), 1000);
			/* 连接超时 */
			HttpConnectionParams.setConnectionTimeout(client.getParams(), 2000);
			/* 请求超时 */
			HttpConnectionParams.setSoTimeout(client.getParams(), 4000);
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String content = EntityUtils.toString(response.getEntity()); // 得到返回字符�?
				Log.i("info", content);// 打印到logcat
				// analysisJSON(content);
				return content;
			}
			if(response.getStatusLine().getStatusCode() == 404){
				Log.i("404", "404");
//				WarningMsgDialog.alertdialog("information","404", "服务器连接失�?",
//						HttpDoPost.this);
				Message msg = Message.obtain();
				msg.what = 8;
				msg.obj = "404";
						
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException cte) {
		//	System.out.println("ConnectTimeoutException");
			Log.i("ConnectTimeoutException", "ConnectTimeoutException");
			cte.printStackTrace();
		} catch (SocketTimeoutException ste) {
		//	System.out.println("SocketTimeoutException");
			Log.i("SocketTimeoutException", "SocketTimeoutException");
			ste.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return "";
	}

	public InputStream String2InputStream(String str) {
		ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		return stream;
	}

	public static String inputStream2String(InputStream in_st) {
		BufferedReader in = new BufferedReader(new InputStreamReader(in_st));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}
}
