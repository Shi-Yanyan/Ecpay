package org.syanstue.myapplication;

import android.app.Activity;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil
{

	
	public static void getUrl2Net(final Activity activity,final String url,final OnHttpRepsonLinstener onHttpRepsonLinstener)
	{
		new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				getUrlConnectJson(activity, url, "", onHttpRepsonLinstener);
			}
		}).start();
	}
	
	
	public static void getUrlConnectJson(Activity aactivity,
			final String urlString, String action,
			final OnHttpRepsonLinstener onHttpRepsonLinstener)
	{
		String json = "";
		try
		{
			final URL url = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.setRequestProperty("accept", "*/*");
			urlConnection.setRequestProperty("connection", "Keep-Alive");
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			urlConnection.setRequestProperty("charset", "utf-8");
			urlConnection.setUseCaches(false);
			// 濡傛灉鏄疨OST璇锋眰蹇呴』浣跨敤杩欎袱椤�
			// urlConnection.setDoOutput(true);
			// urlConnection.setDoInput(true);

			// OutputStream outputStream = urlConnection.getOutputStream();
			// String data = action;
			// outputStream.write(data.getBytes());
			// outputStream.flush();
			//
			// urlConnection.setReadTimeout(1000);
			// urlConnection.setConnectTimeout(1000);
			// urlConnection.setRequestMethod("GET");
			// urlConnection.setRequestProperty("accept", "*/*");
			// urlConnection.setRequestProperty("connection", "Keep-Alive");
			int code = urlConnection.getResponseCode();
			if (code == 200)
			{
				InputStream inputStream = urlConnection.getInputStream();

				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];
				while ((len = inputStream.read(buf)) != -1)
				{
					byteArrayOutputStream.write(buf, 0, len);
				}
				byteArrayOutputStream.flush();
				final String jsonString = byteArrayOutputStream.toString();
				Log.e("TAG", "user:" + jsonString);
				aactivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						onHttpRepsonLinstener.onGetString(jsonString);
					}
				});

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public interface OnHttpRepsonLinstener
	{
		void onGetString(String json);
	}
}
