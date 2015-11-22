package cf.imxqd.LetWeightFly.Internet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cf.imxqd.LetWeightFly.Model.Web.Article;
import cf.imxqd.LetWeightFly.Model.Web.Food;
import cf.imxqd.LetWeightFly.Model.Web.SearchRes;

/**
 * Created by Henry on 2015/6/24.
 */
public class PullFromWeb {
    public static boolean IMAGE_ZOOM = true;
    public static String getContentHtmlFrom39jianFei(String url) throws IOException,NullPointerException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)")
                .get();
        Element test2 =  doc.getElementById("contentText");
        if(test2 == null)
        {
            throw new IOException();
        }
        if(IMAGE_ZOOM)
        {
            Elements imgs = test2.select("img");
            System.out.println(imgs);
            for (Element i:imgs)
            {
                String tmp = i.attr("alt");
                if(tmp.contains("微信") || tmp.contains("关注")||tmp.contains("39减肥"))
                {
                    i.remove();
                    continue;
                }
                i.removeAttr("width");
                i.attr("width", "100%");
            }
        }
        return test2.toString();
    }

    public static String getContentHtmlFromJianFei(String url) throws IOException,NullPointerException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)")
                .get();
        Element test2 =  doc.getElementById("content");
        if(test2 == null)
        {
            throw new IOException();
        }
        return test2.toString();
    }

    protected static int Max_Page_Of_Last_Mode = 1;

    //39减肥可以获取准确值 减肥网只能获取当前页最大页码
    public static synchronized int getMax_Page_Of_Last_Mode() {
        return Max_Page_Of_Last_Mode;
    }

    public static synchronized void setMax_Page_Of_Last_Mode(int value)
    {
        Max_Page_Of_Last_Mode = value;
    }

    public static final String Search_URL
            = "http://fitness.39.net/tzgj/search/query?keyword=";
    public static final String Search_URL2
            = "http://www.boohee.com/food/search?keyword=";

    public static Food getSearchContent(String url) throws IOException {

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)")
                .get();
        Food food = new Food();

        Elements content = doc.select("span[class=\"ju\"] p").not("p span");
        food.advantages = content.first().text();
        System.out.println(food.advantages);

        food.disadvantages = content.last().text();
        System.out.println(food.disadvantages);

        content = doc.select("span[id=\"food_to_sport\"] td");
        food.walk = content.get(1).text();
        food.running = content.get(3).text();
        food.skipping = content.get(5).text();
        food.aerobics = content.get(7).text();
        System.out.println(food.walk);
        System.out.println(food.running);
        System.out.println(food.skipping);
        System.out.println(food.aerobics);


        content = doc.select("div[id=\"chengfen\"] li");
        for(Element i:content)
        {
            food.chengfenTab.titles.add(i.child(0).text());
            food.chengfenTab.values.add(i.child(1).text());
        }

        for(int i = 0; i < food.chengfenTab.titles.size(); i++)
        {
            System.out.println(food.chengfenTab.titles.get(i));
            System.out.println(food.chengfenTab.values.get(i));
        }

        return food;
    }

    public static String getSearchContentHtml(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows Phone 10.0; Android 4.2.1; DEVICE INFO) AppleWebKit/537.36 (KHTML, " +
                        "like Gecko) Chrome/39.0.2171.71 Mobile Safari/537.36 Edge/12.0")
                .get();
        doc.head().select("link").remove();
        doc.head().appendElement("style").append(StaticData.SearchCSS);
        System.out.println(doc.head());
        doc.select("script").remove();
        doc.select("header").remove();
        doc.select("div[class=\"title3 flexbox\"]").remove();
        doc.select("div[class=\"search\"]").remove();
        doc.select("div[class=\"hotkey\"]").remove();
        doc.select("footer").remove();
        return doc.html();
    }

    public static ArrayList<SearchRes> search(String key) throws IOException,NullPointerException {
        Document doc = Jsoup.connect(Search_URL + URLEncoder.encode(key, "utf8"))
                .get();
        Elements res = doc.select("div[class=\"mapCon\"] a");
        int size = res.size();
        ArrayList<SearchRes> list = new ArrayList<SearchRes>();
        for(int i = 0; i < size; i++)
        {
            SearchRes tmp = new SearchRes();
            tmp.title = res.get(i).attr("title");
            tmp.Url = res.get(i).attr("href");
            tmp.describe = res.get(i).child(0).child(0).text();
            try{
                tmp.calory = Float.valueOf
                        (res.get(i).child(0).child(0).child(0).text());
            }catch (Exception e)
            {
                tmp.calory = 0f;
            }
            if(tmp.Url.contains("exercise"))
            {
                tmp.calory = -tmp.calory;
            }
            list.add(tmp);
        }
        return list;
    }

    public static final String MODE_39JianFei_YinShiJianFei
            = "http://fitness.39.net/jfff/ysjf/index_";

    public static final String MODE_39JianFei_YingYangChangShi
            = "http://fitness.39.net/jfsp/yacs/zf/index_";

    public static final String MODE_39JianFei_YunDongJianFei
            = "http://fitness.39.net/jfff/ydjf/index_";

    public static final String MODE_39JianFei_JianFeiQiaoMen
            = "http://fitness.39.net/jfff/shqm/index_";

    public static final String MODE_39JianFei_AnMoDianXue
            = "http://fitness.39.net/jfff/zyjf/am/index_";

    public static final String MODE_39JianFei_ZhuanJiaJianFei
            = "http://fitness.39.net/jfzj/zjtjf/index_";

    //从39减肥网 MODE 栏目获取文章列表
    public static ArrayList<Article> getArticleListFrom39jianFei(int page,String MODE)
            throws IOException
    {
        String url = MODE +page+".html";
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)")
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


    //饮食减肥
    public static final String MODE_JianFei_JianFeiLingShi
            = "http://www.jianfei.com/jianfeilingshi/list58_";
    public static final String MODE_JianFei_JianFeiShiPu
            = "http://www.jianfei.com/jianfeishipu/list56_";
    //减肥常识
    public static final String MODE_JianFei_JianFeiYuanYin
            = "http://www.jianfei.com/jianfeiyuanyin/list50_";
    public static final String MODE_JianFei_JianFeiYuJiBing
            = "http://www.jianfei.com/jianfeiyujibing/list51_";
    public static final String MODE_JianFei_JianFeiYuFang
            = "http://www.jianfei.com/jianfeiyufang/list52_";
    public static final String MODE_JianFei_JianFeiWuQu
            = "http://www.jianfei.com/jianfeiwuqu//list53_";
    //运动减肥
    public static final String MODE_JianFei_YuJiaJianFei
            = "http://www.jianfei.com/yujiajianfei/list60_";
    public static final String MODE_JianFei_ShouShenDongZhuo
            = "http://www.jianfei.com/shoushendongzuo/list61_";
    public static final String MODE_JianFei_YunDongJianFei
            = "http://www.jianfei.com/yundongjianfei/list62_";


    //从减肥网  MODE栏目获取文章列表
    public static List<Article> getArticleListFromjianFei(int page, String MODE)
            throws IOException {
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

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}