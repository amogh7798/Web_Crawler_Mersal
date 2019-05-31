import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkFinder {

    String base_url;
    String page_url;
    Set<String> links;

    LinkFinder(String base_url,String page_url){
        this.base_url = base_url;
        this.page_url = page_url;
        this.links = new HashSet<String>();
    }

    public void feed(String html_String){
         Document doc = Jsoup.parse(html_String);
         Elements url = doc.getElementsByTag("a");
         try {
             for (Element x : url)
                 this.links.add(new URL(new URL(base_url),x.attr("href")).toString());
         }catch(MalformedURLException e){
             throw(new RuntimeException(e));
         }
    }

    public Set<String> page_links(){
        return this.links;
    }
}
