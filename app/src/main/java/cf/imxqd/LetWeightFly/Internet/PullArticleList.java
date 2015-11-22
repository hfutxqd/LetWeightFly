package cf.imxqd.LetWeightFly.Internet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cf.imxqd.LetWeightFly.Model.Web.Article;

/**
 * Created by Henry on 2015/6/29.
 */
public class PullArticleList {
    String MODE;
    int Max_Page_Of_Last_Mode = 0;

    public PullArticleList(String MODE)
    {
        this.MODE = MODE;
    }


    public int getMaxPage() {
        return Max_Page_Of_Last_Mode;
    }

    private void setMax_Page_Of_Last_Mode(int value)
    {
        Max_Page_Of_Last_Mode = value;
    }

    //从39减肥网 MODE 栏目获取文章列表
    public synchronized ArrayList<Article> getArticleListFrom39jianFei(int page) throws IOException
    {
        String url = MODE +page+".html";
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)")
                .timeout(10000)
                .get();


        Elements list =  doc.getElementsByClass("listbox");
        if(list == null)
        {
            throw new IOException();
        }

        Elements li = list.select("ul li");
        ArrayList<Article> artList = new ArrayList<Article>();
        for (int i = 0; i < li.size(); i++)
        {
            Element article = li.get(i).select("span a").first();
            Element date = li.get(i).select("span[class=\"time\"]").first();
            Article tmp =new Article();
            tmp.url = article.attr("href");
            tmp.title = article.text();
            tmp.time = date.text();
            artList.add(tmp);
        }

        //获取最大页码数
        Element last = doc.getElementsByClass("list_page").select("a").last();
        if(last == null)
        {
            throw new IOException();
        }
        if(isNumeric(last.text()))
        {
            setMax_Page_Of_Last_Mode(Integer.valueOf(last.text()));
        }else
        {
            String tmp = last.attr("href");
            tmp = tmp.replaceFirst("index_","");
            tmp = tmp.replaceFirst(".html", "");
            setMax_Page_Of_Last_Mode(Integer.valueOf(tmp));
        }


        return artList;
    }



    //从减肥网  MODE栏目获取文章列表
    public synchronized List<Article> getArticleListFromjianFei(int page) throws IOException {
        Document doc = Jsoup.connect(MODE
                + page + ".html").get();
        Elements list = doc.getElementsByClass("news_r");
        if(list == null)
        {
            throw new IOException();
        }

        list = list.select("li");
        list = list.not("li[class=\"b1\"]");
        List<Article> artList = new ArrayList<Article>();
        for (int i = 0; i < list.size(); i++)
        {
            Element article = list.get(i).select("span a").first();
            Element date = list.get(i).select("i").first();
            Article tmp =new Article();
            tmp.url = article.attr("href");
            tmp.title = article.attr("title");
            tmp.time  = date.text();
            artList.add(tmp);
        }

        Elements listPage = doc.select("ul[class=\"pagelist\"]").select("li a");
        if (!isNumeric(listPage.last().text()))
        {
            int size = listPage.size();
            setMax_Page_Of_Last_Mode(Integer.valueOf(listPage.get(size - 2).text()));
        }else{
            setMax_Page_Of_Last_Mode(Integer.valueOf(listPage.last().text()));
        }

        return artList;
    }

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
