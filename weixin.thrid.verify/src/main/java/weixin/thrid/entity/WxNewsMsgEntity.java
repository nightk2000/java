package weixin.thrid.entity;

import java.util.List;

public class WxNewsMsgEntity extends WxMsgEntity {

	// 必填，图文消息个数，限制为10条以内 
	//private int articleCount;
	// 必填，多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应 
	private List<WxArticle> articles;
	//  	图文消息标题 
	private String title;
	//  	图文消息描述 
	private String description;
	//  	图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200 
	private String picUrl;
	//  	点击图文消息跳转链接 
	private String url;
	
	public int getArticleCount() {
		return this.articles.size();
	}
	
	public List<WxArticle> getArticles() {
		return articles;
	}
	public void setArticles(List<WxArticle> articles) {
		this.articles = articles;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	
	/**
	 * 
<Articles>
	<item>
		<Title><![CDATA[title1]]></Title> 
		<Description><![CDATA[description1]]></Description>
		<PicUrl><![CDATA[picurl]]></PicUrl>
		<Url><![CDATA[url]]></Url>
	</item>
	<item>
		<Title><![CDATA[title]]></Title>
		<Description><![CDATA[description]]></Description>
		<PicUrl><![CDATA[picurl]]></PicUrl>
		<Url><![CDATA[url]]></Url>
	</item>
</Articles>
	 */
}
