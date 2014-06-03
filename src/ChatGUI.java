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

public class ChatGUI extends JFrame{
    private static final long serialVersionUID = 1L;
    private static JTextField MessageBox;
    private static JButton sendButton, aboutButton;
    private static JTextArea chatBox;
    
    public ChatGUI(){
        super("BBChat");
        MessageBox = new JTextField(20);
        chatBox = new JTextArea(20, 50);
        sendButton = new JButton("Send");
        aboutButton = new JButton("About");
        
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
                ImageIcon icon = new ImageIcon("resources/HydraIcon.jpg");
                JOptionPane.showMessageDialog(null, "   The BBChat Project was created by Sophomores: Eric Kong, Parth Mistry, and Robert Kim.\n"
                        + "This program was designed to be an instant messenger program using Java GUI's and Java ServerSockets.\n"
                        + "Robert Kim has contributed the least work to this Project.", "About", JOptionPane.INFORMATION_MESSAGE, icon);
            }
        });
        
        JRootPane rootPane = getRootPane();
        rootPane.setDefaultButton(sendButton);
        
        final JPanel chatPanel = new JPanel();
        chatPanel.add(scrollPane);
        
        final JPanel aboutPanel = new JPanel();
        aboutPanel.add(aboutButton);
        
        final JPanel textPanel = new JPanel();
        textPanel.add(messageLabel);
        textPanel.add(MessageBox);
        textPanel.add(sendButton);
   
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

    public static void sendMessage(){
        String message = MessageBox.getText();
        if(message.equals("")){
        } else {
            MessageBox.setText("");
            chatBox.append(message);
            chatBox.append("\n");
        }
    }
    
    public static void main(String[]args){
        new ChatGUI();
    }
}