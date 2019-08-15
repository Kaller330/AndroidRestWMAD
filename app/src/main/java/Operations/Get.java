package Operations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Get {

    private String result;

    public Get(URL url, StringBuilder builder) throws Exception{
        BufferedReader reader = null;

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("GET");

        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line = "";
        while ((line = reader.readLine()) != null) {
            builder.append(line + "\n");
        }
        builder.append("\n");
        if (reader != null) {
            reader.close();
        }
        con.disconnect();

        this.result = builder.toString();
    }

    public String getResult() {
        return result;
    }
}
