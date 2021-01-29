package ru.geekbrains.java_two.chat.client;

import ru.geekbrains.java_two.chat.library.Protocol;
import ru.geekbrains.java_two.network.SocketThread;
import ru.geekbrains.java_two.network.SocketThreadListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class ClientGUI extends JFrame implements ActionListener,
        Thread.UncaughtExceptionHandler, SocketThreadListener {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static final String WINDOW_TITLE = "Chat client";
    private final DateFormat DATE_FORMAT = new SimpleDateFormat("[HH:mm:ss] ");

    private final JTextArea log = new JTextArea();

    private final JPanel panelTop = new JPanel(new GridLayout(2, 1));
    private final JTextField tfIPAddress = new JTextField("127.0.0.1");
    private final JTextField tfPort = new JTextField("8189");
    private final JCheckBox cbAlwaysOnTop = new JCheckBox("Always on top");
    private final JTextField tfLogin = new JTextField("test");
    private final JPasswordField tfPassword = new JPasswordField("123");
    private final JButton btnLogin = new JButton("Login");

    private final JButton btnChangeNickname = new JButton("Submit");
    private final JTextField tfNewNickname = new JTextField("NewNickname");

    private final JPanel panelLogin = new JPanel(new GridLayout(1, 0));
    private final JPanel panelIp = new JPanel(new GridLayout(1, 0));
    private final JPanel panelNickname = new JPanel(new GridLayout(1, 0));
    private final JPanel panelIpAndNickname = new JPanel(new GridLayout(1, 0));

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JButton btnDisconnect = new JButton("<html><b>Disconnect</b></html>");
    private final JTextField tfMessage = new JTextField();
    private final JButton btnSend = new JButton("Send");

    private final JList<String> userList = new JList<>();
    private boolean shownIoErrors = false;
    private SocketThread socketThread;

    private Logger logger = new Logger();

    private ClientGUI() {
        Thread.setDefaultUncaughtExceptionHandler(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setTitle(WINDOW_TITLE);
        log.setEditable(false);
        log.setLineWrap(true);
        JScrollPane scrollLog = new JScrollPane(log);
        JScrollPane scrollUsers = new JScrollPane(userList);
        scrollUsers.setPreferredSize(new Dimension(100, 0));
        cbAlwaysOnTop.addActionListener(this);
        btnSend.addActionListener(this);
        tfMessage.addActionListener(this);
        btnLogin.addActionListener(this);
        btnDisconnect.addActionListener(this);
        btnChangeNickname.addActionListener(this);

        panelIp.add(tfIPAddress);
        panelIp.add(tfPort);
        panelNickname.add(tfNewNickname);
        panelNickname.add(btnChangeNickname);
        panelLogin.add(tfLogin);
        panelLogin.add(tfPassword);
        panelLogin.add(btnLogin);
        panelIpAndNickname.add(cbAlwaysOnTop);
        panelIpAndNickname.add(panelIp);
        panelTop.add(panelIpAndNickname);
        panelTop.add(panelLogin);
        panelBottom.add(btnDisconnect, BorderLayout.WEST);
        panelBottom.add(tfMessage, BorderLayout.CENTER);
        panelBottom.add(btnSend, BorderLayout.EAST);
        panelBottom.setVisible(false);

        add(scrollLog, BorderLayout.CENTER);
        add(scrollUsers, BorderLayout.EAST);

        add(panelTop, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbAlwaysOnTop) {
            setAlwaysOnTop(cbAlwaysOnTop.isSelected());
        } else if (src == btnSend || src == tfMessage) {
            sendMessage();
        } else if (src == btnLogin) {
            connect();
        } else if (src == btnDisconnect) {
            socketThread.close();
        } else if (src == btnChangeNickname) {
            socketThread.sendMessage(Protocol.getChangeNickname(tfNewNickname.getText(), tfLogin.getText(), new String(tfPassword.getPassword())));
        } else {
            throw new RuntimeException("Undefined source: " + src);
        }
    }

    private void connect() {
        try {
            Socket s = new Socket(tfIPAddress.getText(), Integer.parseInt(tfPort.getText()));
            socketThread = new SocketThread(this, "Client", s);
        } catch (IOException e) {
            e.printStackTrace();
            showException(Thread.currentThread(), e);
        }
    }

    private void sendMessage() {
        String msg = tfMessage.getText();
        if ("".equals(msg)) return;
        tfMessage.setText(null);
        tfMessage.grabFocus();
        socketThread.sendMessage(Protocol.getUserBroadcast(msg));
    }

    private void restoreMessagesFromLogFile() {
        int quantityOfRestoredMessages = 100;
        List<String> messages = logger.getMessagesFromLogFile(quantityOfRestoredMessages);
        for (int i = 0; i < messages.size(); i++) {
            putLog(messages.get(i));
        }
    }

    private void putLog(String msg) {
        if ("".equals(msg)) return;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.append(msg + "\n");
                log.setCaretPosition(log.getDocument().getLength());
            }
        });
    }

    private void showException(Thread t, Throwable e) {
        String msg;
        StackTraceElement[] ste = e.getStackTrace();
        if (ste.length == 0)
            msg = "Empty Stacktrace";
        else {
            msg = String.format("Exception in thread \"%s\" %s: %s\n\tat %s",
                    t.getName(), e.getClass().getCanonicalName(), e.getMessage(), ste[0]);
            JOptionPane.showMessageDialog(this, msg, "Exception", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        showException(t, e);
        System.exit(1);
    }

    /**
     * Socket thread listener methods implementation
     */

    @Override
    public void onSocketStart(SocketThread thread, Socket socket) {
        putLog("Socket created");
    }

    @Override
    public void onSocketStop(SocketThread thread) {
        putLog("Socket stopped");
        panelBottom.setVisible(false);
        panelIpAndNickname.remove(panelNickname);
        panelIpAndNickname.add(panelIp);
        panelLogin.add(btnLogin);
        panelTop.revalidate();
        panelTop.repaint();
        setTitle(WINDOW_TITLE);
        userList.setListData(new String[0]);
    }

    @Override
    public void onSocketReady(SocketThread thread, Socket socket) {
        putLog("Socket ready");
        socketThread.sendMessage(Protocol.getAuthRequest(
                tfLogin.getText(), new String(tfPassword.getPassword())));
    }

    @Override
    public void onReceiveString(SocketThread thread, Socket socket, String msg) {
        handleMessage(msg);
    }

    private void handleMessage(String msg) {
        String[] arr = msg.split(Protocol.DELIMITER);
        String msgType = arr[0];
        switch (msgType) {
            case Protocol.AUTH_ACCEPT:
                setTitle(WINDOW_TITLE + " nickname: " + arr[1]);
                panelBottom.setVisible(true);
                panelIpAndNickname.remove(panelIp);
                panelIpAndNickname.add(panelNickname);
                panelLogin.remove(btnLogin);
                panelTop.revalidate();
                panelTop.repaint();
                logger.setUser(arr[1]);
                restoreMessagesFromLogFile();
                break;
            case Protocol.AUTH_DENIED:
                putLog("Authorization failed");
                break;
            case Protocol.MSG_FORMAT_ERROR:
                putLog(msg);
                socketThread.close();
                break;
            case Protocol.TYPE_BROADCAST:
                String message = String.format("%s%s: %s",
                        DATE_FORMAT.format(Long.parseLong(arr[1])),
                        arr[2], arr[3]);
                putLog(message);
                logger.putMsgToLogFile(message);
                break;
            case Protocol.USER_LIST:
                String users = msg.substring(Protocol.USER_LIST.length() +
                        Protocol.DELIMITER.length());
                String[] usersArr = users.split(Protocol.DELIMITER);
                Arrays.sort(usersArr);
                userList.setListData(usersArr);
                break;
            default:
                throw new RuntimeException("Unknown message type: " + msg);

        }
    }

    @Override
    public void onSocketException(SocketThread thread, Exception exception) {
        exception.printStackTrace();
    }
}
