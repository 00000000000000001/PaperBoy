import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class HTTP {
  private static HttpURLConnection con;
  private static final String USER__AGENT = "Mozilla/5.0";
  public static HttpURLConnection connect(String url) throws Exception{
    URL obj = new URL(url);
    con = (HttpURLConnection) obj.openConnection();
    con.setRequestProperty("User-Agent", USER__AGENT);
    return con;
  }
  public static String sendGet(String url) throws Exception{
    connect(url);
    int responseCode = con.getResponseCode();
    BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
    return response.toString();
  }
}