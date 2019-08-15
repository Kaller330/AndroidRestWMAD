package Operations;

import java.net.HttpURLConnection;
import java.net.URL;

public class Delete {
    public Delete(URL url) throws Exception{
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);
        connection.disconnect();
    }
}
