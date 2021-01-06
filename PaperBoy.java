import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
public class PaperBoy extends JFrame {
	JPanel panel1 = new JPanel(new BorderLayout());
	JPanel panel2 = new JPanel(new BorderLayout());
    JTextField textField = new JTextField();
    JButton button = new JButton("Suchen");
    JList<String> list = new JList<>(Search.list);
    JScrollPane scrollPane = new JScrollPane(list);
    public static JLabel label = new JLabel();
    public PaperBoy() {
        button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Search(URLEncoder.encode(textField.getText(),
						StandardCharsets.UTF_8)).start();
			}
		});
		panel1.add(textField, BorderLayout.NORTH);
		panel1.add(button, BorderLayout.SOUTH);
		panel2.add(scrollPane, BorderLayout.CENTER);
		panel2.add(label, BorderLayout.SOUTH);
		add(panel1, BorderLayout.NORTH);
		add(panel2, BorderLayout.CENTER);
		pack();
		setSize(600, 400);
		setVisible(true);
	}
    public static void main(String[] args) {
		new PaperBoy();
	}
}