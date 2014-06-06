import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

public class ChatGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private static JTextField MessageBox;
	private static JButton sendButton, aboutButton;
	private static JTextArea chatBox;
	private static String n = chatRoomGUI.name.getText();
	private static String listOfNames = "";
	private static Random rand;
	private static Color randCol1, randCol2;
	private static float r, g, b, r2, g2, b2;
	public static String senderName;

	private static AudioInputStream stream, stream2;
	private static AudioFormat x, x2;
	private static Clip clip, clip2;

	private DataInputStream in;
	private DataOutputStream out;

	public ChatGUI() {

		super(chatRoomGUI.chatRoomName);

		Username userClass = new Username(n.trim());

		Socket socket = null;
		try {
			socket = new Socket("67.81.222.76", 18304);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			ObjectOutputStream user = new ObjectOutputStream(
					socket.getOutputStream());
			user.writeObject(userClass.username);
			user.flush();

		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(null,
							"Error: Could not connect to server. Please try again later.");
			System.exit(1);
		}

		MessageBox = new JTextField(20);
		chatBox = new JTextArea(20, 50);
		DefaultCaret caret = (DefaultCaret) MessageBox.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		chatBox.setLineWrap(true);
		sendButton = new JButton();
		aboutButton = new JButton("About");
		
		rand = new Random();
		r = rand.nextFloat();
		g = rand.nextFloat();
		b = rand.nextFloat();
		randCol1 = new Color(r, g, b);
		r2 = rand.nextFloat();
		g2 = rand.nextFloat();
		b2 = rand.nextFloat();
		randCol2 = new Color(r2, g2, b2);

		JLabel messageLabel = new JLabel("Message:");
		messageLabel.setDisplayedMnemonic('M');
		messageLabel.setLabelFor(MessageBox);

		JScrollPane scrollPane = new JScrollPane(chatBox);
		chatBox.setEditable(false);

		sendButton.setMnemonic(KeyEvent.VK_S);
		ImageIcon buttonImage = new ImageIcon("resources/bb.jpg");
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
				ImageIcon img = new ImageIcon("resources/bb.jpg");
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
		
		Border lineBorder = BorderFactory.createLineBorder(Color.black);
		final JPanel listOfClients = new JPanel();
		listOfClients.setBorder(lineBorder);
		listOfClients.add(new JLabel("Connected Users:\n" + listOfNames));

		final Container mainPanel = getContentPane();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(chatPanel, BorderLayout.NORTH);
		mainPanel.add(textPanel, BorderLayout.CENTER);
		mainPanel.add(aboutPanel, BorderLayout.SOUTH);
		mainPanel.add(listOfClients, BorderLayout.EAST);

		chatRoomGUI.setDefaultUI();
		pack();
		setIconImage(new ImageIcon("src/resources/bb2.jpg").getImage());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		Thread receiving = new Thread() {
			public void run() {
				while (true) {
					receive();
				}
			}
		};
		receiving.start();
	}
	
	public void sendMessage() {
		String message = MessageBox.getText().trim();
		if (message.trim().equals("")) {
		} else {
			MessageBox.setText("");
			chatBox.setFont(new Font("ar bonnie", Font.PLAIN, 16));
			//chatBox.setForeground(randCol1);

			if (message.charAt(0) == '!' && message.length() != 1) {
				if (message.trim().equalsIgnoreCase("!music")) {
					File file = new File("src/resources/rickroll.wav");
					try {
						stream2 = AudioSystem.getAudioInputStream(file);
						x2 = stream2.getFormat();
					} catch (UnsupportedAudioFileException | IOException e1) {
						e1.printStackTrace();
					}
					DataLine.Info info = new DataLine.Info(Clip.class, x2);
					try {
						clip2 = (Clip) AudioSystem.getLine(info);
						clip2.open(stream2);
					} catch (LineUnavailableException e1) {
						e1.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					clip2.loop(100);
				}
				if (message.trim().equalsIgnoreCase("!StopMusic")) {
					clip2.stop();
				}
			} else {
				send(message);
				chatBox.append(n + ": " + message);
				chatBox.append("\n");
			}
		}
	}

	public void recieveMessage(String message) {
		String[] list = message.split(" ");
		if (message.equals("")) {
		} else if (list[1]
				.equals("82a0ca8043d31417a307bb3627ec135b74f36d0b7f41a8410616fb593fdf6c42")) {
			listOfNames = list[1];
		} else {
			chatBox.setFont(new Font("ar bonnie", Font.PLAIN, 16));
			//chatBox.setForeground(randCol2);
			chatBox.append(message);
			chatBox.append("\n");
			File file = new File("src/resources/chatsound.WAV");
			try {
				stream = AudioSystem.getAudioInputStream(file);
				x = stream.getFormat();
			} catch (UnsupportedAudioFileException | IOException e1) {
				e1.printStackTrace();
			}
			DataLine.Info info = new DataLine.Info(Clip.class, x);
			try {
				clip = (Clip) AudioSystem.getLine(info);
				clip.open(stream);
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			clip.start();
		}
	}

	public void send(String message) {
		try {
			out.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receive() {
		try {
			recieveMessage(in.readUTF());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}