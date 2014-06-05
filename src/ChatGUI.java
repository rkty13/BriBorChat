import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public class ChatGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private static JTextField MessageBox;
	private static JButton sendButton, aboutButton;
	private static JTextArea chatBox;
	private static JLabel n = chatRoomGUI.name;
	//private static Random rand;
	//private static Color randCol;
	//private static float r, g, b;
	public static String senderName;
	
	public Client client;
	
	public ChatGUI(Client client) {
		super(chatRoomGUI.chatRoomName);
		this.client = client;
		MessageBox = new JTextField(20);
		chatBox = new JTextArea(20, 50);
		DefaultCaret caret = (DefaultCaret)MessageBox.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		sendButton = new JButton("Send");
		aboutButton = new JButton("About");
		/*
		rand = new Random();
		r = rand.nextFloat();
		g = rand.nextFloat();
		b = rand.nextFloat();
		randCol = new Color(r, g, b);
		 */
		
		JLabel messageLabel = new JLabel("Message:");
		messageLabel.setDisplayedMnemonic('M');
		messageLabel.setLabelFor(MessageBox);

		JScrollPane scrollPane = new JScrollPane(chatBox);
		chatBox.setEditable(false);

		sendButton.setMnemonic(KeyEvent.VK_S);
		ImageIcon buttonImage = new ImageIcon("resources/HydraIcon.jpg");
		sendButton.setIcon(buttonImage);

		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent ae) {
				sendMessage();
			}
		});

		aboutButton.setMnemonic(KeyEvent.VK_A);
		aboutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent ae) {
				ImageIcon img = new ImageIcon("resources/HydraIcon.jpg");
				JOptionPane
				        .showMessageDialog(
				                null,
				                "This project is an instant messenger program written in Java by Eric Kong, Parth Mistry, and Robert Kim.\n"
				                        + "For this project, we have used Java GUI's and Java ServerSockets.\n"
				                        + "We hope you enjoy our program!",
				                "About", JOptionPane.INFORMATION_MESSAGE, img);
			}
		});

		JRootPane rootPane = getRootPane();
		rootPane.setDefaultButton(sendButton);

		final JPanel chatPanel = new JPanel();
		chatPanel.add(scrollPane);

		final JPanel textPanel = new JPanel();
		textPanel.add(messageLabel);
		textPanel.add(MessageBox);
		textPanel.add(sendButton);

		final JPanel aboutPanel = new JPanel();
		aboutPanel.add(aboutButton);

		final Container mainPanel = getContentPane();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(chatPanel, BorderLayout.NORTH);
		mainPanel.add(textPanel, BorderLayout.CENTER);
		mainPanel.add(aboutPanel, BorderLayout.SOUTH);

		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public void sendMessage() {
		String message = MessageBox.getText();
		if (message.equals("")) {
		} else {
			MessageBox.setText("");
			//chatBox.setFont(new Font("ar bonnie", Font.PLAIN, 15));
			//chatBox.setForeground(randCol);
			Client.send(message);
			chatBox.append(n.getText() + ": " + message);
			chatBox.append("\n");
		}
	}
	
	public static void recieveMessage(String message){
		if(message.equals("")){
		} else {
			//chatBox.setFont(new Font("ar bonnie", Font.PLAIN, 15));
			//chatBox.setForeground(randCol);
			chatBox.append(n.getText() + ": " + message);
			chatBox.append("\n");
		}
	}


}