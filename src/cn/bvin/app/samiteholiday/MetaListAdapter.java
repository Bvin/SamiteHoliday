package cn.bvin.app.samiteholiday;

import java.util.List;
import cn.bvin.app.samite_holiday.R;
import com.squareup.picasso.Picasso;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MetaListAdapter extends BaseAdapter{

	List<SamiteHolidayMeta> listMeta;
	LayoutInflater inflater;
	Picasso picasso;
	public MetaListAdapter(Context context,List<SamiteHolidayMeta> listMeta) {
		super();
		this.listMeta = listMeta;
		picasso = Picasso.with(context);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listMeta.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder mViewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item_layout, null);
			mViewHolder = new ViewHolder();
			mViewHolder.tvItemTitle = (TextView) convertView.findViewById(R.id.tvItemTitle);
			mViewHolder.ivItemCover = (ImageView) convertView.findViewById(R.id.ivItemCover);
			mViewHolder.tvItemMetaInfo = (TextView) convertView.findViewById(R.id.tvItemMetaInfo);
			mViewHolder.tvItemSummary = (TextView) convertView.findViewById(R.id.tvItemSummary);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		SamiteHolidayMeta meta = listMeta.get(position);
		mViewHolder.tvItemTitle.setText(meta.title);
		picasso.load(meta.img).into(mViewHolder.ivItemCover);
		String metaInfo = meta.time+" "+meta.category+" "+meta.author+" "+meta.visitTimes+" "+meta.tag;
		mViewHolder.tvItemMetaInfo.setText(metaInfo);
		mViewHolder.tvItemSummary.setText(meta.content);
		return convertView;
	}

	class ViewHolder{
		
		TextView tvItemTitle;
		ImageView ivItemCover;
		TextView tvItemMetaInfo;
		TextView tvItemSummary;
	}
}
