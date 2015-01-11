package cn.bvin.app.samiteholiday;

import java.io.Serializable;

public class SamiteHolidayMeta implements Serializable{


	private static final long serialVersionUID = 1L;

	public String title;
	public String link;
	
	public String img;
	
	public String time;
	public String category;
	public String author;
	public String visitTimes;
	
	public String tag;
	
	public String content;

	@Override
	public String toString() {
		return "SamiteHolidayMeta [title=" + title + ", link=" + link + ", img=" + img + ", time="
				+ time + ", category=" + category + ", author=" + author + ", visitTimes="
				+ visitTimes + ", tag=" + tag + ", content=" + content + "]";
	}

	
}
