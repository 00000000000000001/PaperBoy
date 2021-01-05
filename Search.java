import javax.swing.DefaultListModel;
import java.net.HttpURLConnection;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedInputStream;
public class Search extends Thread{
	public static DefaultListModel<String> list = new DefaultListModel<>();
    private String text = "";
    private Links links = new Links("");
    public Search(String text) {
		this.text = text;
    }
	public boolean addPDF(String url) {
		String link = "";
		String html = "";
		int von = 0;
		int bis = 0;
		HttpURLConnection con;
		ByteArrayOutputStream baos;
		BufferedInputStream in;
		try {
			html = HTTP.sendGet(url);
			while(html.contains("href=\"http")){
				von = html.indexOf("href=\"http") + "href=\"".length();
				bis = von;
				while (bis < html.length() && html.charAt(bis) != '"') {
					++bis;
				}
				link = html.substring(von, bis);
				PaperBoy.label.setText("Scanne: " + link);
				html = html.replaceFirst("href=\"http", "");
				con = HTTP.connect(link);
				baos = new ByteArrayOutputStream();
				in = new BufferedInputStream(con.getInputStream());
				byte[] buffer = new byte[4];
				int n;
				if ((n = in.read(buffer)) > 0) {
					baos.write(buffer,0, n);
				}
				//in.close();
				//baos.close();
				//con.disconnect();
				byte[] response = baos.toByteArray();
				if(response.length == 4 && response[0] == 37
						&& response[1] == 80
						&& response[2] == 68
						&& response[3] == 70) {
					if (!list.contains(link)) {
						list.addElement(link);
					}
					return true;
				}
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}
    public void add(Links html) {
		while (links.hasNext()) {
			addPDF(links.next());
		}
	}
    public Links nextHTML() {
		if (links.getHtml().equals("")) {			
			try {
				links.setHtml(HTTP.sendGet("https://www.base-search.net/Search/Results?lookfor="
					+ text + "&type=all&page=1&l=de&oaboost=1&refid=dcpagede"));
			} catch(Exception e) {
				System.out.println(e);
			}
		} else {
			int bis = links.getHtml().indexOf("\" title=\"weiter\"><img src=\"/interface/images/pfeil_gruen_rechts.png\" alt=\"weiter\" />");
			int von = bis;
			while (von > 0 && links.getHtml().charAt(von - 1) != '"') {
				--von;
			}
			try {
				links.setHtml(HTTP.sendGet(links.getHtml().substring(von, bis).replace("amp;", "")));
			} catch(Exception e) {
				System.out.println(e);
			}
		}
		return links;
	}
    public boolean hasNextHTML() {
		if (links.getHtml().equals("")) {
			return true;
		} else {
			return links.getHtml().contains("<img src=\"/interface/images/pfeil_gruen_rechts.png\" alt=\"weiter\" />");
		}
	}
	public void run() {
		list.clear();
        while (hasNextHTML()) {
			add(nextHTML());
		}
		PaperBoy.label.setText("Fertig");
	}
}