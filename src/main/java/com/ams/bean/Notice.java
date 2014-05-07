package com.ams.bean;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Notice.TABLE_NAME)
public class Notice extends BaseEntity {
	public static final String ATTACH_FILE_URL = "attachFileUrl";

	public static final String CONTENT = "content";

	public static final String PUBLISH_DATE = "publishDate";

	public static final String PUBLISHER = "publisher";

	public static final String TITLE = "title";

	public static final String TABLE_NAME = "Notice";

	@Column(name = TITLE)
	public String title;

	@Column(name = PUBLISHER)
	public String publisher;

	@Column(name = PUBLISH_DATE)
	public Date publishDate;

	@Column(name = CONTENT)
	public String content;

	@Column(name = ATTACH_FILE_URL)
	public String attachFileUrl;

	public String getAttachFileUrl() {
		return attachFileUrl;
	}

	public void setAttachFileUrl(String attachFileUrl) {
		this.attachFileUrl = attachFileUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static void main(String args[]) {
		Notice notice = new Notice();
		notice.setTitle("title 1");
		notice.setContent("通知");
		notice.setId(UUID.randomUUID().toString());
		notice.setPublishDate(new Date());
		notice.setPublisher("dylan");
		notice.setAttachFileUrl("http://www.baidu.com");

		System.out.println(notice.toString());
	}
}
