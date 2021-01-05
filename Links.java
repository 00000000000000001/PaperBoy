public class Links {
    String html = "";
	public String toString() {
		return html;
	}
	public String getHtml() {
		return html;
	}
    public void setHtml(String html) {
		this.html = html;
	}
	public Links(String html) {
		this.html = html;
	}
	public boolean hasNext() {
		return html.contains("<a target=\"_blank\" rel=\"noopener\" class=\"link-gruen bold\" href=\"");
	}
	public String next() {
		String link = "";
		String key = "<a target=\"_blank\" rel=\"noopener\" class=\"link-gruen bold\" href=\"";
		int von;
		von = html.indexOf(key) + key.length();
		int bis = von;
		while (bis < html.length() && html.charAt(bis) != '"') {
			++bis;
		}
		link = html.substring(von, bis);
		html = html.replaceFirst("<a target=\"_blank\" rel=\"noopener\" class=\"link-gruen bold\" href=\"", "");
		return link;
    }
}