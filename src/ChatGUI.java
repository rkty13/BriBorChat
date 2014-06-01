import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ChatGUI extends JFrame{
    private static final long serialVersionUID = 1L;
    private static JTextField textBox;
    private static JButton sendButton;
    
    public ChatGUI(){
        super("BBChat");
        JTextField jtf = new JTextField(20);
        textBox = new JTextField(20);
        sendButton = new JButton();
        sendButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(final ActionEvent ae){
                sendMessage();
            }
        });
        
        sendButton.setText("Send");
        
        GhostText gt = new GhostText(textBox, "Message");
        
        final JPanel textPanel = new JPanel();
        textPanel.add(new JLabel("Message:"));
        //textPanel.add(jtf);
        textPanel.add(textBox);
        
        final JPanel sendButtonPanel = new JPanel();
        sendButtonPanel.add(sendButton);
        
        final Container mainPanel = getContentPane();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(sendButtonPanel, BorderLayout.EAST);
        
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(450, 450));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    public static void sendMessage(){
        String message = textBox.getText().trim();
    }
    
    public static void main(String[]args){
        new ChatGUI();
    }
    
}