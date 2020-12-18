package ru.geekbrains.java_two.chat.library;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Protocol {
    //common data
    // ±
    //client-to-server
    // /auth_request±login±password

    //server-to-client(s)
    // /auth_accept±nickname
    // /auth_denied
    // /broadcast±src±msg
    // /msg_format_error

    public static final String DELIMITER = "±"; //alt+num0177
    public static final String AUTH_REQUEST = "/auth_request";
    public static final String AUTH_ACCEPT = "/auth_accept";
    public static final String AUTH_DENIED = "/auth_denied";
    public static final String MSG_FORMAT_ERROR = "/msg_format_error";
    // если мы вдруг не поняли, что за сообщение и не смогли разобрать
    public static final String TYPE_BROADCAST = "/bcast";
    // то есть сообщение, которое будет посылаться всем
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("[HH:mm:ss]");


    public static String getAuthRequest(String login, String password) {
        return AUTH_REQUEST + DELIMITER + login + DELIMITER + password;
    }

    public static String getAuthAccept(String nickname) {
        return AUTH_ACCEPT + DELIMITER + nickname;
    }

    public static String getAuthDenied() {
        return AUTH_DENIED;
    }

    public static String getMsgFormatError(String message) {
        return MSG_FORMAT_ERROR + DELIMITER + message;
    }

    public static String getTypeBroadcast(String src, String message) {
        return TYPE_BROADCAST + DELIMITER + DATE_FORMAT.format(System.currentTimeMillis()) +
                DELIMITER + src + DELIMITER + message;
    }
}
