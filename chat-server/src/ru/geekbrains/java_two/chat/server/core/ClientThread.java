package ru.geekbrains.java_two.chat.server.core;

import ru.geekbrains.java_two.chat.library.Protocol;
import ru.geekbrains.java_two.network.SocketThread;
import ru.geekbrains.java_two.network.SocketThreadListener;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientThread extends SocketThread {

    private String nickname;
    private boolean isAuthorized;

    public ClientThread(SocketThreadListener listener, String name, Socket socket) {
        super(listener, name, socket);
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    void authAccept(String nickname) {
        isAuthorized = true;
        this.nickname = nickname;
        sendMessage(Protocol.getAuthAccept(nickname));
    }

    void authFail() {
        sendMessage(Protocol.getAuthDenied());
        close();
    }

    void msgFormatError(String msg) {
        sendMessage(Protocol.getMsgFormatError(msg));
        close();
    }

    @Override
    public synchronized boolean sendMessage(String msg) {
        ArrayList<String> unformatted = new ArrayList<>(Arrays.asList(msg.split(Protocol.DELIMITER)));
        removeProtocolInfo(unformatted);
        msg = createFormattedMessage(unformatted);
        return super.sendMessage(msg);
    }

    private synchronized void removeProtocolInfo(ArrayList<String> arr) {
        arr.remove(Protocol.AUTH_ACCEPT);
        arr.remove(Protocol.AUTH_DENIED);
        arr.remove(Protocol.AUTH_REQUEST);
        arr.remove(Protocol.MSG_FORMAT_ERROR);
        arr.remove(Protocol.TYPE_BROADCAST);
    }

    private synchronized String createFormattedMessage(ArrayList<String> arr) {
        StringBuilder msgBuilder = new StringBuilder();
        for (int i = 0; i < arr.size(); i++) {
            msgBuilder.append(arr.get(i));
            if (i + 1 < arr.size()) msgBuilder.append(": ");
        }
        return msgBuilder.toString();
    }
}
