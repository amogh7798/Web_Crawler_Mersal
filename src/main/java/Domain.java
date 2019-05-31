import java.net.MalformedURLException;
import java.net.URL;

public class Domain {
    public static String get_domain_name(String url) {
        String domain = new String();
        try {
            URL aURL = new URL(url);
            domain = aURL.getHost();
        } catch (MalformedURLException e) {
            throw (new RuntimeException(e));
        }
        return domain;
    }
}
