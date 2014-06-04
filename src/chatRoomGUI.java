import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class chatRoomGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private static JButton selectButton;

	public chatRoomGUI() {
		super("BBChat");

		selectButton = new JButton("Enter Chat Room");
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent ae) {
				//TO-DO
			}
		});
		
		final JPanel selectButtonPanel = new JPanel();
		selectButtonPanel.add(selectButton);
		
		final Container mainPanel = getContentPane();
		
		
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
	}
}
