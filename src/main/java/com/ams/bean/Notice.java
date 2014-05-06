package com.ams.bean;

import java.util.Date;
import java.util.UUID;

import com.eweblib.bean.BaseEntity;

public class Notice extends BaseEntity {

	public String title;

	public String publisher;

	public Date publishDate;

	public String content;
	
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
