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

public class chatRoomGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private static JButton selectNameButton, chatRoom1, chatRoom2;
    private static JTextField nameField;
    public static String name;

    public chatRoomGUI() {
        super("BBChat");
        nameField = new JTextField(10);

        selectNameButton = new JButton("Submit");
        selectNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent ae) {
                selectName();
            }
        });

        chatRoom1 = new JButton("Enter Chat Room # 1");
        chatRoom2 = new JButton("Enter Chat Room # 2");

        chatRoom1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent ae) {
                enterChat1();
            }
        });
        /*
         * chatRoom2.addActionListener(new ActionListener() {
         * 
         * @Override public void actionPerformed(final ActionEvent ae) {
         * 
         * } });
         */
        final JPanel inputPanel = new JPanel();
        inputPanel.add(nameField);
        inputPanel.add(selectNameButton);

        final JPanel chatRoomButtons = new JPanel();
        chatRoomButtons.add(chatRoom1);
        chatRoomButtons.add(chatRoom2);

        final Container mainPanel = getContentPane();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        mainPanel.add(chatRoomButtons, BorderLayout.CENTER);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void selectName() {
        String temp = nameField.getText().trim();
        if (temp.equals("")) {
            JOptionPane.showMessageDialog(null, "Your name cannot be blank!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            name = temp;
            JOptionPane.showMessageDialog(null, name);
        }
        nameField.setText("");

    }

    private void enterChat1() {
        if (name == null) {
            JOptionPane.showMessageDialog(null,
                    "Please enter your name before entering the chat.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            new ChatGUI();
        }
    }

    public static void main(String[] args) {
        new chatRoomGUI();
    }
}
