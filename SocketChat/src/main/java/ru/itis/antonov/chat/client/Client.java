package ru.itis.antonov.chat.client;

import ru.itis.antonov.chat.utils.Message;
import ru.itis.antonov.chat.utils.Protocol;
import ru.itis.antonov.chat.utils.ProtocolInputStream;
import ru.itis.antonov.chat.utils.ProtocolOutputStream;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket s = null;
        try {
            s = new Socket(InetAddress.getLocalHost(), Protocol.PORT);
        } catch (IOException e) {
            System.out.println("Неизвестный адрес");
            System.exit(-1);
        }
        try {
            ProtocolOutputStream out = new ProtocolOutputStream(s.getOutputStream());
            ProtocolInputStream in = new ProtocolInputStream(s.getInputStream());
            Scanner sc = new Scanner(System.in);
            boolean register = false;
            while(!register) {
                System.out.print("Введите имя: ");
                String nick = sc.nextLine();
                out.writeMessage(new Message(Protocol.JOIN_REQUEST, nick.getBytes()));
                Message m = in.readMessage();
                if(m.getType() == Protocol.JOIN_RESPONSE){
                    System.out.println("Успешная регистрация");
                    register = true;
                }
                else {
                    System.out.println("Error");
                }
            }
            Runnable sending = () -> {
                String message;
                while (true) {
                    message = sc.nextLine();
                    try {
                        out.writeMessage(new Message(Protocol.MESSAGE_SEND, message.getBytes()));
                    } catch (IOException e) {
                        System.out.println("Ошибка соединения");
                    }
                }
            };
            Runnable receiving = () -> {
                Message message;
                while (true) {
                    try {
                        message = in.readMessage();
                        if(message.getType() == Protocol.MESSAGE_RECEIVE){
                            System.out.println(message.getDataAsString());
                        }
                    } catch (IOException e) {
                        System.out.println("Ошибка соединения");
                    }
                }
            };
            Thread outThread = new Thread(sending);
            Thread inThread = new Thread(receiving);
            outThread.start();
            inThread.start();
        } catch (IOException e) {
            System.out.println("Ошибка соединения");
            System.exit(-1);
        }
    }
}
