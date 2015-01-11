package cn.bvin.app.samiteholiday;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import cn.bvin.app.samite_holiday.R;

public class DetailsActivity extends Activity {

	String className  = "single f_l t_shadow ption_r";
	WebView mWebView;
	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		SamiteHolidayMeta meta = (SamiteHolidayMeta) getIntent().getSerializableExtra("extra");
		//new ParseTask().execute(meta.link);
		mWebView = ((WebView)findViewById(R.id.wv));
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.loadUrl(meta.link);
	}
	
	final class MyWebViewClient extends WebViewClient{ 
        public boolean shouldOverrideUrlLoading(WebView view, String url) {  
            view.loadUrl(url);  
            return true;  
        } 
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("WebView","onPageStarted");
            super.onPageStarted(view, url, favicon);
        }   
        public void onPageFinished(WebView view, String url) {
            Log.d("WebView","onPageFinished ");
            view.loadUrl("javascript:window.local_obj.showSource('<head>'+" +
                    "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            super.onPageFinished(view, url);
        }
    }
	
	final class InJavaScriptLocalObj {
        public void showSource(String html) {
        	((TextView)findViewById(R.id.tv)).setText(html);
            //Log.e("HTML", html);
        }
        public String toString() { return "local_obj"; }
    }
	
	class ParseTask extends AsyncTask<String, Integer, String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Log.e("ParseTask", "onPreExecute");
		}
		
		@Override
		protected String doInBackground(String... params) {
			Element single_text = null;
			String html = null;
			Log.e("url:", params[0]);
			try {
				
				Document document = Jsoup.connect(params[0]).userAgent("android").get();
				/*Element mainPtion_a = document.getElementsByAttributeValue("class", className).first();
				Element content = mainPtion_a.select("content").first();
				content.getElementsByAttributeValue("class", className).first();*/
				single_text = document.select("div.single_text").first();
				for (Element image : single_text.select("img")) {
					if (image.attr("file").startsWith("/")) {
						String img = image.attr("file");
						String pref = "/../../../../..";
						image.attr("file", "http://www.jinxiujiaqi.com/"+img.substring(img.indexOf(pref)));
					}
					image.attr("src", image.attr("file"));
				}
				single_text.attr("src", single_text.attr("file"));
				String tmlhtml =  single_text.html();
				if (tmlhtml.contains("更多资源信息：")) {
					html = single_text.html().substring(0, tmlhtml.indexOf("更多资源信息："));
				}else {
					html = single_text.html().substring(0, tmlhtml.indexOf("<div"));
				}
				String wrap = "<html lang=\"zh-CN\"><head>\r\n" + 
						"    <meta charset=\"UTF-8\">\r\n" + 
						"    <meta name=\"viewport\" content=\"target-densitydpi=device-dpi, initial-scale=1.0, user-scalable=0, "
						+ "minimum-scale=1.0, maximum-scale=1.0\"></head>";
				html = wrap+html+"</html>";
				
			} catch (IOException e) {
				e.printStackTrace();
				Log.e("IOException", e.getLocalizedMessage());
			}
			return html;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result!=null) {
				Log.e("onPostExecute", result);
				
				((WebView)findViewById(R.id.wv)).loadData(result, "text/html; charset=UTF-8", "utf-8");
			}
			
		}
		
		
		
	}
}
