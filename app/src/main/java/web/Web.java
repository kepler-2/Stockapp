package web;

import android.util.Log;

import com.game.stock.stockapp.Login;
import com.game.stock.stockapp.Register;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Web {

	public void requestGet(String url, IResponse r, int i) {
		HttpClient client = new DefaultHttpClient();

		HttpGet get = new HttpGet(url);

		try {
			HttpResponse response = client.execute(get);
			InputStream in = response.getEntity().getContent();
			String result = getString(in);
			r.onComplete(result, i);
//			Log.i("hello",result+"...check");
			Log.d("tag", result);
			// parser(result);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void requestPostStringData(String url, List<NameValuePair> data,
                                     Login r, int i) {
			
		HttpClient client = new DefaultHttpClient();
		

		HttpPost post = new HttpPost(url);
		try {
			post.setEntity(new UrlEncodedFormEntity(data));
			HttpResponse response = client.execute(post);
			InputStream in = response.getEntity().getContent();
			String result = getString(in);
			Log.d("tag", result);
//			AppLog.logshow(result);
	//		r.onComplete(result, i);
			

			// parser(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
	public void requestPostStringData(String url, List<NameValuePair> data,
									  Register r, int i) {

		HttpClient client = new DefaultHttpClient();


		HttpPost post = new HttpPost(url);
		try {
			post.setEntity(new UrlEncodedFormEntity(data));
			HttpResponse response = client.execute(post);
			InputStream in = response.getEntity().getContent();
			String result = getString(in);
			Log.d("tag", result);
//			AppLog.logshow(result);
	//		r.onComplete(result, i);


			// parser(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
	
	private static String getString(InputStream in) {
		int c = -1;
		StringBuffer r = new StringBuffer();

		try {
			while ((c = in.read()) != -1)
				r.append((char) c);

			return r.toString();
		} catch (Exception e) {
		}

		return null;
	}




	public String postData(String url, File f, List<NameValuePair> data,
                           IResponse r, int i )
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try{

			FileBody bin = new FileBody(f);
			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			entity.addPart("image", bin);
			entity.addPart("age_of_bird",new StringBody(data.get(0).getValue()));
			entity.addPart("infected_bird",new StringBody(data.get(1).getValue()));
			entity.addPart("capacity_of_farm",new StringBody(data.get(2).getValue()));
			entity.addPart("type_of_bird",new StringBody(data.get(3).getValue()));
			entity.addPart("other_detail",new StringBody(data.get(4).getValue()));
			entity.addPart("medicine_detail",new StringBody(data.get(5).getValue()));
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			InputStream in = response.getEntity().getContent();
			String result = getString(in);
			r.onComplete(result,i);
			Log.i("tag", result);
			return result;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public String postDataWithMultipart(String url, MultipartEntity entity,
                                        IResponse r, int i )
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try{
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			InputStream in = response.getEntity().getContent();
			String result = getString(in);
			r.onComplete(result,i);
			Log.i("tag", result);
			return result;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}


}
