package ru.itis.antonov.chat.utils;

public class Protocol {
    public static final short PORT = 903;

    public static final byte JOIN_REQUEST = 0;
    public static final byte JOIN_RESPONSE = 1;
    public static final byte MESSAGE_SEND = 2;
    public static final byte MESSAGE_RECEIVE = 3;
    public static final byte END_SESSION = 4;
    public static final byte INCORRECT_MESSAGE = 5;
    public static final byte SEND_ERROR = 5;

    public static final int MAX_MESSAGE_LENGTH = 10000;

}
