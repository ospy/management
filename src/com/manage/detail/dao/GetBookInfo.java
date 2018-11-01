
/**
 * 
 */
/**
 * @author admin
 *
 */
package com.manage.detail.dao;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;












import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.manage.entity.BookAbstract;
import com.manage.utils.RsToJson;
import com.sun.mail.handlers.text_html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class GetBookInfo {
private static final String PREFIX = "\\u";

public static String getDD(String url) {
	
	String bookid="";
	String regex_id = "(?<=/)([0-9]*)(?=.html)";
	Pattern Pattern_id = Pattern.compile(regex_id);
    Matcher Matcher_id = Pattern_id.matcher(url);

    while (Matcher_id.find()) {
    	bookid=Matcher_id.group(0).trim().toString();
    }
   
    
		String str,str1;
		String result = "";
		ArrayList<BookAbstract> list=new ArrayList<BookAbstract>();
		BookAbstract bookabstract = new BookAbstract();
        //创建一个webclient

        
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);  
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(5000);
        
        //获取页面
        HtmlPage page;
       
		try {
			page = webClient.getPage(url);			
			  //获取页面的文本
	        str = page.asXml();	       
	        //关闭webclient
	        webClient.close();
	    
		    Document doc = null;
			doc = Jsoup.parse(str);
			
			bookabstract.setBookTitle(doc.select("div.name_info h1").text());
			Elements ele1_2= doc.select("#author a");
			String authors="";
			 for (Element lineInfo : ele1_2) { 				    
		            authors += lineInfo.text().trim()+"，";  
		        } 
			 bookabstract.setAuthorName(authors.substring(0,authors.length() - 1));
			 bookabstract.setPressName(doc.select("a[dd_name=出版社]").text());
			 bookabstract.setPicUrl(doc.select("#largePic").attr("src")); // 所有 png 的图片 );
			String pubdate= doc.select("span.t1:contains(出版时间)").text();
			pubdate = pubdate.substring(pubdate.indexOf(":")+1);
			bookabstract.setPublishTime(pubdate);
			String ddprice= doc.select("#original-price").text();
			bookabstract.setPrice(ddprice.substring(ddprice.indexOf("¥")+1).trim());
			Elements ele2= doc.select("#detail_describe ul");
                     ele2.select("li.fenlei").remove(); 
         	//版权信息        
			String temp2 = ele2.html().trim();
			String regex2 = "(?<=<li>)(.*)(?:：)(.*)(?=</li>)";
	        Pattern mPattern2 = Pattern.compile(regex2);
	        Matcher mMatcher2 = mPattern2.matcher(temp2);

	        while (mMatcher2.find()) {
	        	
	        	String name2=mMatcher2.group(1).trim().toString();
	        	
	        	switch(name2){  

	            case "版 次":  
	            	bookabstract.setEdition(mMatcher2.group(2)); 
	                break;  
	            case "页 数":  
	            	bookabstract.setPages(mMatcher2.group(2));
	                break;  
	            case "字 数":  
	            	bookabstract.setWordNumber(mMatcher2.group(2));
	                break;  
	            case "印刷时间":  
	            	bookabstract.setPrintingTime(mMatcher2.group(2));
	                break; 
	            case "开 本":  
	            	bookabstract.setBookSize(mMatcher2.group(2));
	                break; 
	            case "包 装":  
	            	bookabstract.setPack(mMatcher2.group(2));
	                break; 
	            case "纸 张":  
	            	bookabstract.setPaper(mMatcher2.group(2));
	                break; 
	            case "国际标准书号ISBN":  
	            	bookabstract.setISBN(mMatcher2.group(2));
	                break; 
	            case "丛书名":  
	            	bookabstract.setSeriesName(mMatcher2.group(2));
	                break; 
	            
	            }     	
	        
	        }
	    
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		try {
			 String url_detail="http://product.dangdang.com/index.php?r=callback%2Fdetail&productId="+bookid+"&templateType=publish&describeMap=&shopId=0&categoryPath=01.56.41.04.00.00";
			 //获取页面的文本
			 page = webClient.getPage(url_detail);			
			  //获取页面的文本
	        str1 = page.asXml();
	        str1 = StringEscapeUtils.unescapeHtml(str1);
	       
	        str1= ascii2Native(str1);
	        str1= str1.replace("\\\"", "");
	        str1= str1.replace("\\n", "");
	        str1= str1.replace("=\"\"", "\"");
	        str1= str1.replace("\\", "");
	        
	        Document doc = null;
			doc = Jsoup.parse(str1);
			
	        //关闭webclient
	        webClient.close();
				bookabstract.setContentIntro(doc.select("#content .descrip").html().trim());// 内容简介
				bookabstract.setAuthorIntro(doc.select("#authorIntroduction .descrip").html().trim()); // 作者简介
				bookabstract.setContentIndex(doc.select("#catalog .descrip").html().trim()); // 目录
		} catch (Exception e) {
			// TODO: handle exception
		}
		list.add(bookabstract); 
		JSONArray jsonArray = JSONArray.fromObject(list);
		
		result = jsonArray.toString();
		
		return result;
  }
	
public static String getJD(String url) {
	
	String bookid="";
	String regex_id = "(?<=/)([0-9]*)(?=.html)";
	Pattern Pattern_id = Pattern.compile(regex_id);
    Matcher Matcher_id = Pattern_id.matcher(url);

    while (Matcher_id.find()) {
    	bookid=Matcher_id.group(0).trim().toString();
    }
   
    
		String str,str1;
		String result = "";
		ArrayList<BookAbstract> list=new ArrayList<BookAbstract>();
		BookAbstract bookabstract = new BookAbstract();
        //创建一个webclient

        
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);  
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(10000);
        
        //获取页面
        HtmlPage page;
       
		try {
			page = webClient.getPage(url);			
			  //获取页面的文本
	        str = page.asXml();	
	       
	        //关闭webclient
	        webClient.close();
	    
		    Document doc = null;
			doc = Jsoup.parse(str);
			
			bookabstract.setBookTitle(doc.select("#name h1").text());
			Elements ele1_2= doc.select("#p-author a");
			String authors="";
			 for (Element lineInfo : ele1_2) { 				    
		            authors += lineInfo.text().trim()+"，";  
		        } 
			 bookabstract.setAuthorName(authors.substring(0,authors.length() - 1));
			 
			 bookabstract.setPicUrl(doc.select("#spec-n1 img").attr("src")); // 所有 png 的图片 );
			 bookabstract.setAuthorIntro(doc.select("#detail-tag-id-4 .book-detail-content").html());//内容简介 
			bookabstract.setContentIntro(doc.select("#detail-tag-id-3 .book-detail-content").html());//内容简介
			bookabstract.setContentIndex(doc.select("#detail-tag-id-6 .book-detail-content").html());//目录
			
			
			String jdprice= doc.select("#jd-price").text();
			bookabstract.setPrice(jdprice.substring(jdprice.indexOf("￥")+1).trim());
			
         	//版权信息        
			Elements ele2= doc.select("#parameter2");
            
			String temp2 = ele2.html().trim();
			String regex2 = "(?<=>)(.*)(?:：)(.*)(?=</li>)";
	        Pattern mPattern2 = Pattern.compile(regex2);
	        Matcher mMatcher2 = mPattern2.matcher(temp2);

	        while (mMatcher2.find()) {
	        	
	        	String name2=mMatcher2.group(1).trim().toString();
	        	
	        	switch(name2){  
	        	
	        	case "出版社":  
	        		Element publish = Jsoup.parse(mMatcher2.group(2));	        		
	            	bookabstract.setPressName(publish.text()); 
	                break;
	            case "版次":  
	            	bookabstract.setEdition(mMatcher2.group(2)); 
	                break;  
	            case "页数":  
	            	bookabstract.setPages(mMatcher2.group(2));
	                break;  
	            case "字数":  
	            	bookabstract.setWordNumber(mMatcher2.group(2));
	                break;  
	            case "出版时间":  
	            	bookabstract.setPublishTime(mMatcher2.group(2));
	                break; 
	            case "印刷时间":  
	            	bookabstract.setPrintingTime(mMatcher2.group(2));
	                break; 
	            case "开本":  
	            	bookabstract.setBookSize(mMatcher2.group(2));
	                break; 
	            case "包装":  
	            	bookabstract.setPack(mMatcher2.group(2));
	                break; 
	            case "用纸":  
	            	bookabstract.setPaper(mMatcher2.group(2));
	                break; 
	            case "ISBN":  
	            	bookabstract.setISBN(mMatcher2.group(2));
	                break; 
	            case "丛书名":  
	            	Element series = Jsoup.parse(mMatcher2.group(2));
	            	bookabstract.setSeriesName(series.text());
	                break; 
	            case "正文语种":  
	            	bookabstract.setLanguage(mMatcher2.group(2));
	                break;

	            }     	
	        
	        }
	    
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		list.add(bookabstract); 
		JSONArray jsonArray = JSONArray.fromObject(list);
		
		result = jsonArray.toString();
		
		return result;
}

public static String getAMAZON(String url) {
	
		String str,ASIN = null;
		String result = "";
		ArrayList<BookAbstract> list=new ArrayList<BookAbstract>();
		BookAbstract bookabstract = new BookAbstract();
        //创建一个webclient

        
        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);  
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(10000);
        
        //获取页面
        HtmlPage page;
       
		try {
			page = webClient.getPage(url);			
			  //获取页面的文本
	        str = page.asXml();	       
	        //关闭webclient
	        webClient.close();
	    
		    Document doc = null;
			doc = Jsoup.parse(str);
		
			String booktitle= doc.select("#productTitle").text();
			Elements ele1_2= doc.select("#byline a");
			String authors="";
			 for (Element lineInfo : ele1_2) { 				    
		            authors += lineInfo.text().trim()+"，";  
		        } 
			 bookabstract.setAuthorName(authors.substring(0,authors.length() - 1));
			 String imgurl=doc.select("#imgBlkFront").attr("data-a-dynamic-image");
			  imgurl+=doc.select("#ebooksImgBlkFront").attr("data-a-dynamic-image");
			 String regeximg = "(https://.*?\\.jpg)";
		        Pattern mPattern1 = Pattern.compile(regeximg);
		        Matcher mMatcher1 = mPattern1.matcher(imgurl);

		        while (mMatcher1.find()) {	        	
		        	bookabstract.setPicUrl(mMatcher1.group(1));
		        	}
			 
			
			 //bookabstract.setPicUrl(doc.select("#imgBlkFront").attr("data-a-dynamic-image")); // 所有 png 的图片 );
			
			String ddprice= doc.select("#buyBoxInner .a-color-secondary").text();
			bookabstract.setPrice(ddprice.substring(ddprice.indexOf("￥")+1).trim());
			
			
                    
         	//版权信息 
			Elements ele2= doc.select("#detail_bullets_id .content ul");
			ele1_2.remove("#SalesRank");
			ele1_2.remove(".bucket");
			
			String temp2 = ele2.html().replace("：", ":");
			temp2=temp2.replace("<b>","").replace("</b>", "");
			String regex2 = "(?<=<li>)(.*)(?::)(.*)(?=</li>)";
	        Pattern mPattern2 = Pattern.compile(regex2);
	        Matcher mMatcher2 = mPattern2.matcher(temp2);

	        while (mMatcher2.find()) {
	        	
	        	String name2=mMatcher2.group(1).replace(" ","").replace("：", ":");
	        	
	        	switch(name2){ 
	        	case "外文书名":  
	        		Element foreign = Jsoup.parse(mMatcher2.group(2));
	        		booktitle =booktitle+foreign.text(); 
	                break;
	        	case "出版社":
	        		String press = mMatcher2.group(2);
	        		String regex2_1 = "(.*);(.*)\\((.*)\\)";
	    	        Pattern mPattern2_1 = Pattern.compile(regex2_1);
	    	        Matcher mMatcher2_1 = mPattern2_1.matcher(press);
	    	        while (mMatcher2_1.find()) {
	            	bookabstract.setPressName(mMatcher2_1.group(1)); 
	            	bookabstract.setEdition(mMatcher2_1.group(2));
	            	bookabstract.setPublishTime(mMatcher2_1.group(3));
	    	        }
	                break;
	            case "版 次":  
	            	bookabstract.setEdition(mMatcher2.group(2)); 
	                break;  
	            case "平装":  
	            	bookabstract.setPack(mMatcher2.group(1));
	            	bookabstract.setPages(mMatcher2.group(2).trim());//页数
	                break; 
	            case "精装":  
	            	bookabstract.setPack(mMatcher2.group(1).trim());
	            	bookabstract.setPages(mMatcher2.group(2));//页数
	                break;  
	            case "字 数":  
	            	bookabstract.setWordNumber(mMatcher2.group(2));
	                break;  
	            case "印刷时间":  
	            	bookabstract.setPrintingTime(mMatcher2.group(2));
	                break; 
	            case "语种":  
	            	bookabstract.setLanguage(mMatcher2.group(2));
	                break; 
	            case "开本":  
	            	bookabstract.setBookSize(mMatcher2.group(2));
	                break; 
	            case "包 装":  
	            	bookabstract.setPack(mMatcher2.group(2));
	                break; 
	            case "纸 张":  
	            	bookabstract.setPaper(mMatcher2.group(2));
	                break; 
	            case "ISBN":  
	            	bookabstract.setISBN(mMatcher2.group(2));
	                break; 
	            case "丛书名":  
	            	Element series =  Jsoup.parse(mMatcher2.group(2));
	            	bookabstract.setSeriesName(series.text());
	                break; 
	            case "ASIN":  
	            	ASIN =  mMatcher2.group(2).trim();
	            
	                break; 

	            }     	
	        
	        }
	        
	        //摘要目录
	        String contenturl="https://www.amazon.cn/gp/product-description/ajaxGetProuductDescription.html?ref_=dp_apl_pc_loaddesc&asin="+ASIN+"&deviceType=web";
	        page = webClient.getPage(contenturl);			
			  //获取页面的文本
	        str = page.asXml();	       
	        //关闭webclient
	        webClient.close();
	    
		    Document doc1 = null;
			doc1 = Jsoup.parse(str);
	        
	        String content= doc1.select(".s-contents").html();
	        String recommend ="";
           
	        String regex3 = "<h3>(.*?)(?:</h3>)(.*?)</div>";
	        Pattern mPattern3 = Pattern.compile(regex3,Pattern.MULTILINE | Pattern.DOTALL);
	        Matcher mMatcher3 = mPattern3.matcher(content);

	        while (mMatcher3.find()) {
	        	
	        	String name3=mMatcher3.group(1).replace(" ","");
	        	
	        	switch(name3){ 
	        	case "编辑推荐":  
	        		recommend = mMatcher3.group(2);
	        		 
	                break;
	        	case "作者简介":        		
	            	bookabstract.setAuthorIntro(mMatcher3.group(2)); 
	                break;
	            case "目录":  
	            	bookabstract.setContentIndex(mMatcher3.group(2)); 
	                break;  
	        	        }
	            }     	
	        bookabstract.setContentIntro(doc.select("#iframeContent").text()+recommend);
	        
	        bookabstract.setBookTitle(booktitle);
	    
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		list.add(bookabstract); 
		JSONArray jsonArray = JSONArray.fromObject(list);
		
		result = jsonArray.toString();
		
		return result;
}


    

    public static String ascii2Native(String str) {

        StringBuilder sb = new StringBuilder();

        int begin = 0;

        int index = str.indexOf(PREFIX);

        while (index != -1) {

            sb.append(str.substring(begin, index));

            sb.append(ascii2Char(str.substring(index, index + 6)));

            begin = index + 6;

            index = str.indexOf(PREFIX, begin);

        }

        sb.append(str.substring(begin));

        return sb.toString();

    }

    private static char ascii2Char(String str) {

        if (str.length() != 6) {

            throw new IllegalArgumentException("Ascii string of a native character must be 6 character.");

        }

        if (!PREFIX.equals(str.substring(0, 2))) {

            throw new IllegalArgumentException("Ascii string of a native character must start with \"\\u\".");

        }

        String tmp = str.substring(2, 4);

        int code = Integer.parseInt(tmp, 16) << 8;

        tmp = str.substring(4, 6);

        code += Integer.parseInt(tmp, 16);

        return (char) code;

    }

}
