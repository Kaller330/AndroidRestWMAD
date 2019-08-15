package Operations;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;



public class Put {

    public Put(URL url, String jsonStr, StringBuilder builder) throws Exception{


        System.out.println("[JSON SENDER!]: " + jsonStr);
        builder.append(jsonStr + "\n");

        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        con.setRequestMethod("PUT");

        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");

        out.write(jsonStr);
        out.flush();
        out.close();
        builder.append("Response code: " + con.getResponseCode() + "\n\n");
        con.disconnect();
    }
}
