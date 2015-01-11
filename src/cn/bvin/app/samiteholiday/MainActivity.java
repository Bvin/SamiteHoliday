package cn.bvin.app.samiteholiday;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.bvin.app.samite_holiday.R;

public class MainActivity extends Activity {

	String className = "post ption_r";
	String url = "http://www.jinxiujiaqi.com/page/2";
	ListView lv;
	List<SamiteHolidayMeta> listData;
	MetaListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//parseHtml(url);
		listData = new ArrayList<SamiteHolidayMeta>();
		adapter = new MetaListAdapter(this, listData);
		lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				SamiteHolidayMeta selectedMeta = (SamiteHolidayMeta) parent.getAdapter().getItem(position);
				Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
				intent.putExtra("extra", selectedMeta);
				startActivity(intent);
			}
			
		});
		new ParseTask().execute(url);
	}
	
	private void parseHtml(String url){
		try {
			Document document = Jsoup.connect(url).get();
			Log.e("parse-url", url+document.title());
			Elements elements = document.getElementsByAttributeValue("class", className);
			Log.e("items", ""+elements.size());
			for (Element element : elements) {
				SamiteHolidayMeta data = new SamiteHolidayMeta();
				Element title = element.select("h1").first().getElementsByTag("a").first();
				//Log.e(title.attr("href"), ""+title.text());
				data.title = title.text();
				data.link = title.attr("href");
				Element meta = element.select("div.meta").first();
				String img = meta.select("img").attr("file");
				data.img = img;
				Element metaInfo = meta.select("p.meta_info").first();
				Elements infos = metaInfo.select("span.mr10");
				String time = infos.get(0).text();
				data.time = time;
				String category = infos.get(1).select("a").text();
				data.category = category;
				String author = infos.get(2).text();
				data.author = author;
				String visitTimes = infos.get(3).text();
				data.visitTimes = visitTimes;
				
				Element metaTag = meta.select("p.meta_tag").first().select("a").first();
				String tag = metaTag.text();
				data.tag = tag;
				
				Element metaContent = meta.select("div.meta_content").first();
				Log.i("metaTag", metaContent.text());
				data.content = metaContent.text();
				
				listData.add(data);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	class ParseTask extends AsyncTask<String, Integer, String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			parseHtml(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			adapter.notifyDataSetChanged();
		}
		
		
		
	}
}

