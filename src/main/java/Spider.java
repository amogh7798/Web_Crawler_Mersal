import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;

public class Spider {

    public static String project_name;
    public static String base_url;
    public static String domain_name;
    public static String queue_file;
    public static String crawled_file;
    public static volatile Set<String> queue;
    public static volatile Set<String> crawled;

    Spider(String project_name,String base_url,String domain_name){
        Spider.project_name = project_name;
        Spider.base_url = base_url;
        Spider.domain_name = domain_name;
        Spider.queue_file = Spider.project_name + "/queue";
        Spider.crawled_file = Spider.project_name + "/crawled";
        this.boot();
        Spider.crawl_page("First spider",this.base_url);
    }

    public void boot(){
        general.create_project_dir(Spider.project_name);
        general.create_data_files(Spider.project_name,Spider.base_url);
    }

    public static void crawl_page(String thread_name,String page_url) {
        Spider.queue = general.file_to_set(Spider.queue_file);
        Spider.crawled = general.file_to_set(Spider.crawled_file);
        if (!Spider.crawled.contains(page_url)) {
            System.out.println(thread_name + " now crawling " + page_url);
            System.out.println("Queue: " + new Integer(Spider.queue.size()).toString() + " | Crawled " + new Integer(Spider.crawled.size()).toString());
            Spider.add_links_to_queue(Spider.gather_links(page_url));
            Spider.queue.remove(page_url);
            Spider.crawled.add(page_url);
            Spider.update_files();
        }
    }

    public static Set<String> gather_links(String page_url) {
        String html_String = "";
        LinkFinder finder = new LinkFinder(Spider.base_url, page_url);
        try {
            URL url = new URL(page_url);
            try {
                URLConnection connection = url.openConnection();
                if (connection.getContentType().contains("text/html")) {
                    html_String = connection.getContent().toString();
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    while ((inputLine = br.readLine()) != null) {
                        html_String = html_String + inputLine;
                    }
                    finder.feed(html_String);
                }
            } catch (Exception e) {
                throw (new RuntimeException(e));
            }
        } catch (MalformedURLException e) {
            throw (new RuntimeException(e));
        }
        return finder.page_links();
    }

    public static void add_links_to_queue(Set<String> links)
    {
        for(String x : links){
            if(Spider.queue.contains(x) || Spider.crawled.contains(x))
                continue;
            if(!Spider.domain_name.equals(Domain.get_domain_name(x)))
                continue;
            Spider.queue.add(x);
        }
    }

    public static void update_files(){
        general.set_to_file(Spider.queue,Spider.queue_file);
        general.set_to_file(Spider.crawled,Spider.crawled_file);
    }
}
