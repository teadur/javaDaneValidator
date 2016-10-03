import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by georg on 28.09.2016.

public class HelloWorld {
    public static void main(String args[]) {
        System.out.println("Hello World!");
        try {
            URL url = new URL("https://data.internet.ee");
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
        } catch (IOException i) {
            i.printStackTrace();
            return -1;
        }
    }
    }
}
 */