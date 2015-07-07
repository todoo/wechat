package cn.com.wechat.message;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cn.com.wechat.pojo.Article;

public class ArticleNormalWechatMessage extends NormalWecahtMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6968394571447596942L;
	
	private int ArticleCount;
	private Article mainArticle;//图文封面,第一条
	private List<Article> articles;
	
	public ArticleNormalWechatMessage() {
		this.msgType = WechatMessage.MSG_TYPE_NORMAL_NEWS;
		this.ArticleCount = 0;
		this.mainArticle = null;
		this.articles = new ArrayList<Article>();
	}
	
	public int getArticleCount() {
		return ArticleCount;
	}
	
	public List<Article> getArticles() {
		return articles;
	}

	/**
	 * 添加一条图文，最多只能添加10条，超过10条将添加失败
	 * @param article
	 * @param isMain 是否是第一条图文，当成封面
	 * @return
	 */
	public boolean addArticle(Article article, boolean isMain) {
		if (null == article) {
			return false;
		}
		
		if (this.ArticleCount >=10) {
			return false;
		}
		
		if (isMain) {
			this.mainArticle = article;
		} else {
			this.articles.add(article);
		}
		
		++this.ArticleCount;
		return true;
	}

	@Override
	public String getReplyXmlData() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.newDocument();
			//添加根节点
			Element root = document.createElement("xml");
			document.appendChild(root);
			
			//添加子节点
			Element toUserNameElement = document.createElement("ToUserName");
			toUserNameElement.setTextContent(this.toUserName);
			root.appendChild(toUserNameElement);
			
			Element fromUserNameElement = document.createElement("FromUserName");
			fromUserNameElement.setTextContent(this.fromUserName);
			root.appendChild(fromUserNameElement);
			
			Element createTimeElement = document.createElement("CreateTime");
			createTimeElement.setTextContent(this.createTime + "");
			root.appendChild(createTimeElement);
			
			Element msgTypeElement = document.createElement("MsgType");
			msgTypeElement.setTextContent(this.msgType);
			root.appendChild(msgTypeElement);
			
			Element countElement = document.createElement("ArticleCount");
			countElement.setTextContent(this.ArticleCount + "");
			root.appendChild(countElement);
			
			Element articlesElement = document.createElement("Articles");
			for (int i=0; i<this.ArticleCount; ++i) {
				Element itemElement = document.createElement("item");
				if (0 == i && this.mainArticle != null) { 
					Element titleElement = document.createElement("Title");
					titleElement.setTextContent(this.mainArticle.getTitle());
					Element descriptionElement = document.createElement("Description");
					descriptionElement.setTextContent(this.mainArticle.getDescription());
					Element picUrlElement = document.createElement("PicUrl");
					picUrlElement.setTextContent(this.mainArticle.getPicUrl());
					Element urlElement = document.createElement("Url");
					urlElement.setTextContent(this.mainArticle.getUrl());
					
					itemElement.appendChild(titleElement);
					itemElement.appendChild(descriptionElement);
					itemElement.appendChild(picUrlElement);
					itemElement.appendChild(urlElement);
				} else {
					int offset = this.mainArticle==null?0:-1;
					Element titleElement = document.createElement("Title");
					titleElement.setTextContent(this.articles.get(i+offset).getTitle());
					Element descriptionElement = document.createElement("Description");
					descriptionElement.setTextContent(this.articles.get(i+offset).getDescription());
					Element picUrlElement = document.createElement("PicUrl");
					picUrlElement.setTextContent(this.articles.get(i+offset).getPicUrl());
					Element urlElement = document.createElement("Url");
					urlElement.setTextContent(this.articles.get(i+offset).getUrl());
					
					itemElement.appendChild(titleElement);
					itemElement.appendChild(descriptionElement);
					itemElement.appendChild(picUrlElement);
					itemElement.appendChild(urlElement);
				}
				articlesElement.appendChild(itemElement);
			}
			root.appendChild(articlesElement);
			
			
			//将document转换为xml字符串
			TransformerFactory transFactory = TransformerFactory.newInstance();
	        Transformer transFormer = transFactory.newTransformer();
	        transFormer.setOutputProperty(OutputKeys.ENCODING, System.getProperty("sun.jnu.encoding"));
	        DOMSource domSource = new DOMSource(document);
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        transFormer.transform(domSource, new StreamResult(bos));
	        String xmlStr = bos.toString();
	        xmlStr = xmlStr.substring(xmlStr.indexOf("?>")>0?(xmlStr.indexOf("?>")+2):0);//去掉xml的第一行申明
	        return xmlStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getCustomSendMsgJson() {
		try {
			JSONObject articles = new JSONObject();
			for (int i=0; i<this.ArticleCount; ++i) {
				JSONObject item = new JSONObject();
				if (0 == i && this.mainArticle != null) { 
					item.put("title", this.mainArticle.getTitle());
					item.put("description", this.mainArticle.getDescription());
					item.put("url", this.mainArticle.getUrl());
					item.put("picurl", this.mainArticle.getPicUrl());
				} else {
					int offset = this.mainArticle==null?0:-1;
					item.put("title", this.articles.get(i+offset).getTitle());
					item.put("description", this.articles.get(i+offset).getDescription());
					item.put("url", this.articles.get(i+offset).getUrl());
					item.put("picurl", this.articles.get(i+offset).getPicUrl());
				}
				articles.append("articles", item);
			}

			JSONObject json = new JSONObject();
			json.put("touser", this.toUserName);
			json.put("msgtype", this.msgType);
			json.put("news", articles);
			
			return json.toString();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean setReceiveXmlData(String xmlData) {
		// TODO Auto-generated method stub
		return false;
	}

}
