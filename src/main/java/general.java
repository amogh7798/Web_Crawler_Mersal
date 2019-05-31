import java.io.*;
import java.util.*;

public class general {

    public static void create_project_dir(String dir){
        File directory = new File("./"+dir);
        if(!directory.exists())
        {
            System.out.println("creating directory: " + directory);
            directory.mkdir();
        }
        else{
            System.out.println("project already exists");
        }
    }

    public static void create_data_files(String project_name,String base_url){
        String queue_s = "./"+ project_name + "/queue.txt";
        String crawled_s = "./"+ project_name + "/crawled.txt";
        File queue = new File(queue_s);
        File crawled = new File(crawled_s);

       try{
           if(!queue.exists())
               queue.createNewFile();
           if(!crawled.exists())
               crawled.createNewFile();
       }catch(IOException e)
       {
           throw(new RuntimeException(e));
       }
    }

    public static void delete_file_contents(String path){
        try {
            FileWriter write = new FileWriter("./" + path + ".txt");
            write.write("");
            write.close();
        }catch(IOException e)
        {
            throw(new RuntimeException(e));
        }
    }

    public static void write_file(String filename,String data){
        try {
            FileWriter writer = new FileWriter("./"+filename + ".txt");
            writer.write(data);
            writer.close();
        }catch(IOException e){
            throw(new RuntimeException(e));
        }
    }

    public static Set<String> file_to_set(String filename){
        Set<String> results = new HashSet<String>();
        try {
            Scanner scan = new Scanner(new File("./" + filename + ".txt"));
            while (scan.hasNextLine()) {
                results.add(scan.nextLine());
            }
            scan.close();
        }catch(FileNotFoundException e){
            throw(new RuntimeException(e));
        }
        return results;
    }

    public static void set_to_file(Set<String> set, String filename){
        try {
            FileWriter writer = new FileWriter("./"+filename + ".txt");
            for (String x:set)
                writer.write(x + "\n");
            writer.close();
        }catch(IOException e){
            throw(new RuntimeException(e));
        }
    }

}
