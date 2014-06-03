package com.ams.bean;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.annotation.column.IntegerColumn;

@Table(name = Notice.TABLE_NAME)
public class Notice extends AmsBaseEntity {
	public static final String PUBLISH_END_DATE = "publishEndDate";


	public static final String PRIORITY = "priority";

	public static final String ATTACH_FILE_URL = "attachFileUrl";

	public static final String CONTENT = "content";

	public static final String PUBLISH_DATE = "publishDate";

	public static final String PUBLISHER = "publisher";

	public static final String TITLE = "title";

	public static final String TABLE_NAME = "Notice";

	@Column(name = TITLE)
	public String title;

	@Column(name = PUBLISH_DATE)
	public Date publishDate;

	@Column(name = PUBLISH_END_DATE)
	public Date publishEndDate;
	
	
	@Column(name = CONTENT)
	public String content;

	@Column(name = ATTACH_FILE_URL)
	public String attachFileUrl;

	@Column(name = PRIORITY)
	@IntegerColumn
	public Integer priority;
	
	
	public String publisher;
	
	public String deleteAttachFile;
	

	
	public String getDeleteAttachFile() {
		return deleteAttachFile;
	}

	public void setDeleteAttachFile(String deleteAttachFile) {
		this.deleteAttachFile = deleteAttachFile;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

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
	
	

	public Date getPublishEndDate() {
		return publishEndDate;
	}

	public void setPublishEndDate(Date publishEndDate) {
		this.publishEndDate = publishEndDate;
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
