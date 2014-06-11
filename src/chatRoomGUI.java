import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class chatRoomGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private static JButton selectNameButton, chatRoom1;
    private static JTextField nameField;
    public static JLabel name;
    public static String chatRoomName;

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

        chatRoom1 = new JButton("Enter Chat Room");
        chatRoom1.setMnemonic(KeyEvent.VK_E);

        chatRoom1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent ae) {
                enterChat1();
            }
        });

        JRootPane rootPane = getRootPane();
        rootPane.setDefaultButton(selectNameButton);

        final JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Please enter your name:"));
        inputPanel.add(nameField);

        final JPanel selectNamePanel = new JPanel();
        selectNamePanel.add(selectNameButton);

        final JPanel chatRoomButtons = new JPanel();
        chatRoomButtons.add(chatRoom1);

        final Container mainPanel = getContentPane();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(selectNamePanel, BorderLayout.SOUTH);
        mainPanel.add(chatRoomButtons, BorderLayout.NORTH);

        setDefaultUI();
        pack();
        setIconImage(new ImageIcon("src/resources/bb2.jpg").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void selectName() {
        String temp = nameField.getText().trim();
        if (temp.equals("") && nameField.isEditable() == true) {
            JOptionPane.showMessageDialog(null, "Your name cannot be blank!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            name = new JLabel(temp);
            nameField.setEditable(false);
        }
        nameField.setText("");
    }

    private void enterChat1() {
        if (name == null) {
            JOptionPane.showMessageDialog(null,
                    "Please enter your name before entering the chat.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            chatRoomName = "BBChat ~ Room";
            new ChatGUI();
        }
    }

    public static void setDefaultUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Cannot set default UI");
        }
    }

    public static void main(String[] args) {
        new chatRoomGUI();
    }
}
