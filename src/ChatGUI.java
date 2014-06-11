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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
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
    private static String names;
    private static Random rand;
    private static Color randCol1, randCol2;
    private static float r, g, b, r2, g2, b2;
    public static String senderName;
    private static ArrayList<String> styleList;
    private static int currentSize = 15;
    private static String currentStyle = "eras light itc";
    public static JComboBox<String> listOfClients;

    private static AudioInputStream stream, stream2;
    private static AudioFormat x, x2;
    private static Clip clip, clip2;

    private DataInputStream in;
    private DataOutputStream out;
    private ObjectInputStream serverIn;

    public ChatGUI() {

        super(chatRoomGUI.chatRoomName);

        Username userClass = new Username(n.trim());

        Socket socket = null;
        try {
            socket = new Socket("67.81.222.76", 18304);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            serverIn = new ObjectInputStream(socket.getInputStream());

            ObjectOutputStream user = new ObjectOutputStream(
                    socket.getOutputStream());
            user.writeObject(userClass.username);
            user.flush();

            if (!in.readBoolean()) {
                JOptionPane
                        .showMessageDialog(null,
                                "Error: Username already taken. Please choose another one.");

                System.exit(1);
            }

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
        chatBox.setFont(new Font("calibri", Font.PLAIN, 15));

        rand = new Random();
        r = rand.nextFloat();
        g = rand.nextFloat();
        b = rand.nextFloat();
        randCol1 = new Color(r, g, b);
        r2 = rand.nextFloat();
        g2 = rand.nextFloat();
        b2 = rand.nextFloat();
        randCol2 = new Color(r2, g2, b2);

        /**
         * HERE ARE THE FONT STYLES //styleList.add("ar blanca");
         * //styleList.add("arial"); //styleList.add("calibri");
         * //styleList.add("eras light itc"); //styleList.add("impact");
         * //styleList.add("magneto"); //styleList.add("microsoft himalaya");
         * //styleList.add("times new roman");
         */

        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_O);
        menubar.add(optionsMenu);

        JMenu fontAction = new JMenu("Font");
        fontAction.setMnemonic(KeyEvent.VK_M);

        JMenu fontStyleAction = new JMenu("Style");
        ButtonGroup b = new ButtonGroup();
        JRadioButtonMenuItem arial = new JRadioButtonMenuItem("Arial");
        JRadioButtonMenuItem calibri = new JRadioButtonMenuItem("Calibri");
        JRadioButtonMenuItem eras = new JRadioButtonMenuItem("Eras Light ITC");
        JRadioButtonMenuItem impact = new JRadioButtonMenuItem("Impact");
        JRadioButtonMenuItem magneto = new JRadioButtonMenuItem("Magneto");
        JRadioButtonMenuItem microsoft = new JRadioButtonMenuItem(
                "Microsoft Himalaya");
        JRadioButtonMenuItem times = new JRadioButtonMenuItem("Times New Roman");
        b.add(arial);
        b.add(calibri);
        b.add(eras);
        b.add(impact);
        b.add(magneto);
        b.add(microsoft);
        b.add(times);
        fontStyleAction.add(arial);
        fontStyleAction.add(calibri);
        fontStyleAction.add(eras);
        fontStyleAction.add(impact);
        fontStyleAction.add(magneto);
        fontStyleAction.add(microsoft);
        fontStyleAction.add(times);
        fontAction.add(fontStyleAction);

        JMenu fontSizeAction = new JMenu("Size");
        ButtonGroup bg = new ButtonGroup();
        JRadioButtonMenuItem size12 = new JRadioButtonMenuItem("12");
        JRadioButtonMenuItem size13 = new JRadioButtonMenuItem("13");
        JRadioButtonMenuItem size14 = new JRadioButtonMenuItem("14");
        JRadioButtonMenuItem size15 = new JRadioButtonMenuItem("15");
        JRadioButtonMenuItem size16 = new JRadioButtonMenuItem("16");
        bg.add(size12);
        bg.add(size13);
        bg.add(size14);
        bg.add(size15);
        bg.add(size16);
        fontSizeAction.add(size12);
        fontSizeAction.add(size13);
        fontSizeAction.add(size14);
        fontSizeAction.add(size15);
        fontSizeAction.add(size16);
        fontAction.add(fontSizeAction);

        optionsMenu.add(fontAction);

        arial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chatBox.setFont(new Font("arial", Font.PLAIN, currentSize));
                currentStyle = "arial";
            }
        });

        calibri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chatBox.setFont(new Font("calibri", Font.PLAIN, currentSize));
                currentStyle = "calibri";
            }
        });

        eras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chatBox.setFont(new Font("eras light itc", Font.PLAIN,
                        currentSize));
                currentStyle = "eras light itc";
            }
        });

        impact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chatBox.setFont(new Font("impact", Font.PLAIN, currentSize));
                currentStyle = "impact";
            }
        });

        magneto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chatBox.setFont(new Font("magneto", Font.PLAIN, currentSize));
                currentStyle = "magneto";
            }
        });

        microsoft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chatBox.setFont(new Font("microsoft himalaya", Font.PLAIN,
                        currentSize));
                currentStyle = "microsoft himalaya";
            }
        });

        times.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chatBox.setFont(new Font("times new roman", Font.PLAIN,
                        currentSize));
                currentStyle = "times new roman";
            }
        });

        size12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chatBox.setFont(new Font(currentStyle, Font.PLAIN, 12));
                currentSize = 12;
            }
        });

        size13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chatBox.setFont(new Font(currentStyle, Font.PLAIN, 13));
                currentSize = 13;
            }
        });

        size14.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chatBox.setFont(new Font(currentStyle, Font.PLAIN, 14));
                currentSize = 14;
            }
        });

        size15.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chatBox.setFont(new Font(currentStyle, Font.PLAIN, 15));
                currentSize = 15;
            }
        });

        size16.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                chatBox.setFont(new Font(currentStyle, Font.PLAIN, 16));
                currentSize = 16;
            }
        });

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
        listOfClients = new JComboBox<String>();
        listOfClients.setBorder(lineBorder);

        final JPanel clients = new JPanel();
        clients.setLayout(new BorderLayout());
        clients.add(new JLabel("Users on this server:"), BorderLayout.CENTER);
        clients.add(listOfClients, BorderLayout.SOUTH);

        final Container mainPanel = getContentPane();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(chatPanel, BorderLayout.NORTH);
        mainPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(aboutPanel, BorderLayout.SOUTH);
        mainPanel.add(clients, BorderLayout.EAST);

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

        Thread receivingServer = new Thread() {
            public void run() {
                while (true) {
                    receiveServer();
                }
            }
        };
        receivingServer.start();
    }

    public void sendMessage() {
        String message = MessageBox.getText().trim();
        if (message.trim().equals("")) {
        } else {
            MessageBox.setText("");
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
        if (message.equals("")) {
        } else {
            // chatBox.setForeground(randCol2);
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

    public void receiveServer() {
        try {
            String input = serverIn.readUTF();
            String[] command = input.split(":");
            if (command[0].equalsIgnoreCase("updateUserList")) {
                updateUserList(command[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUserList(String userlist) {
        listOfClients = new JComboBox<String>(userlist.split(" "));
    }
}