import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class main_class implements Runnable{

    public static String Project_Name;
    public static String Home_Page;
    public static String Domain_Name;
    public static String Queue_File;
    public static String Crawled_File;
    public static Integer Number_Of_Threads = 8;
    public static volatile Queue<String> queue = new LinkedList<String>();

    public void create_workers(){
        for(Integer i=0; i<Number_Of_Threads ; i++){
            Thread t = new Thread(this,i.toString() + " Thread");
            t.start();
        }
    }

    public static void create_jobs(){
        while(true) {
            if (queue.isEmpty()) {
                for (String link : general.file_to_set(Queue_File)) {
                    queue.add(link);
                }
                main_class.crawl();
            }
        }
    }

    public static void crawl(){
        Set<String> queued_links = general.file_to_set(Queue_File);
        if(queued_links.size() > 0){
            System.out.println(((Integer)queued_links.size()).toString() + " Links in the queue");
            create_jobs();
        }
    }

    public void run(){
        while(true) {
            if(!queue.isEmpty()) {
                String url = queue.remove();
                Spider.crawl_page(Thread.currentThread().getName(), url);
            }
        }
    }

    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the Project Name:");
        main_class.Project_Name = scan.nextLine();
        System.out.println("Enter the domain name");
        main_class.Home_Page = scan.nextLine();

        main_class.Domain_Name = Domain.get_domain_name(Home_Page);
        main_class.Queue_File = Project_Name + "/queue";
        main_class.Crawled_File = Project_Name + "/crawled";

        new Spider(Project_Name,Home_Page,Domain_Name);
        main_class bots = new main_class();
        bots.create_workers();
        crawl();

    }
}

