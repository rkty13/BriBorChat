import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ChatGUI extends JFrame{
    private static final long serialVersionUID = 1L;
    private static JTextField textBox;
    private static JButton sendMessage;
    
    public ChatGUI(){
        super("BBChat");
        textBox = new JTextField(20);
        sendMessage = new JButton();
        sendMessage.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(final ActionEvent ae){
                send();
            }
        });
        
        
        final JPanel textPanel = new JPanel();
        textPanel.add(textBox);
        
        final Container mainPanel = getContentPane();
        
        mainPanel.add(textPanel);
        
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(450, 450));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    
    public static void send(){
        String message = textBox.getText().trim();
    }
    
    public static void main(String[]args){
        new ChatGUI();
    }
    
}