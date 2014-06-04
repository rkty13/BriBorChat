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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ChatGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private static JTextField MessageBox, namePrompt;
    private static JButton sendButton, nameButton, aboutButton;
    private static JTextArea chatBox;
    private static String n = chatRoomGUI.name;

    public ChatGUI(){
        super("BBChat");
        MessageBox = new JTextField(20);
        chatBox = new JTextArea(20, 50);
        sendButton = new JButton("Send");
        aboutButton = new JButton("About");
        
        
        /*namePrompt = new JTextField(15);
        nameButton = new JButton("Enter");
        
        JLabel nameLabel = new JLabel("Enter Name:");
        nameLabel.setDisplayedMnemonic('N');
        nameLabel.setLabelFor(namePrompt);
        
        final JPanel nameBox = new JPanel();
        nameBox.add(nameLabel);
        nameBox.add(namePrompt);
        
        nameButton.setMnemonic('E');
        nameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				name = namePrompt.getText();
				if (name.equals("")) {
					System.err.println("A name must be enterred in order to use the BBChat servers.");
					//JOptionPane.showMessageDialog(this, name, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
        
        final Container namePanel = getContentPane();
        namePanel.setLayout(new BorderLayout());
        namePanel.add(nameBox, BorderLayout.NORTH);
        namePanel.add(nameButton, BorderLayout.SOUTH);
        
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        */
        
        JLabel messageLabel = new JLabel("Message:");
        messageLabel.setDisplayedMnemonic('M');
        messageLabel.setLabelFor(MessageBox);
        
        JScrollPane scrollPane = new JScrollPane(chatBox);
        chatBox.setEditable(false);
        
        sendButton.setMnemonic(KeyEvent.VK_S);
        ImageIcon buttonImage = new ImageIcon("resources/HydraIcon.jpg");
        sendButton.setIcon(buttonImage);
         
        sendButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(final ActionEvent ae){
                sendMessage();
            }
        });
        
        aboutButton.setMnemonic(KeyEvent.VK_A);
        aboutButton.addActionListener(new ActionListener(){
          @Override  
            public void actionPerformed(final ActionEvent ae){
              ImageIcon img = new ImageIcon("resources/HydraIcon.jpg");  
              JOptionPane.showMessageDialog(null, "This project is an instant messenger program.\n"
                      + "To do this we have used Java GUI's and Java ServerSockets.","About",JOptionPane.INFORMATION_MESSAGE, img);
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void sendMessage() {
        String message = MessageBox.getText();
        if (message.equals("")) {
        } else {
            MessageBox.setText("");
            chatBox.append(n + ": " + message);
            chatBox.append("\n");
        }
    }

    public static void main(String[] args) {
        new ChatGUI();
    }

}