package ru.itis.antonov.chat.server;

import ru.itis.antonov.chat.utils.Message;
import ru.itis.antonov.chat.utils.Protocol;
import ru.itis.antonov.chat.utils.ProtocolInputStream;
import ru.itis.antonov.chat.utils.ProtocolOutputStream;

import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {

    Socket socket;

    Server server;

    String nick;

    public Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            ProtocolInputStream in = new ProtocolInputStream(socket.getInputStream());
            ProtocolOutputStream out = new ProtocolOutputStream(socket.getOutputStream());
            server.getChat().addEventListener((e -> {
                try {
                    out.writeMessage(new Message(Protocol.MESSAGE_RECEIVE, e.getLastMessage().getBytes()));
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }));
            Message m;
            while ((m = in.readMessage()) != null) {
                if (m.getType() == Protocol.JOIN_REQUEST) {
                    String nick1 = m.getDataAsString();
                    if (server.register(nick1)) {
                        this.nick = nick1;
                        out.writeMessage(new Message(Protocol.JOIN_RESPONSE));
                        System.out.println(nick);
                    } else {
                        out.writeMessage(new Message(Protocol.SEND_ERROR));
                    }
                } else if (m.getType() == Protocol.MESSAGE_SEND) {
                    if (nick == null) {
                        out.writeMessage(new Message(Protocol.SEND_ERROR));
                    } else {
                        server.getChat().addMessage(nick, m.getDataAsString());
                    }
                } else if(m.getType() == Protocol.END_SESSION){
                    in.close();
                    out.close();
                    break;
                }else {
                    out.writeMessage(new Message(Protocol.SEND_ERROR));
                }
            }
            socket.close();
            this.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
