package ru.itis.antonov.chat.server;

import ru.itis.antonov.chat.listeners.ChatListener;
import ru.itis.antonov.chat.utils.Protocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private List<String> nickNames;

    private List<Connection> connections;

    private Chat chat;

    private ServerSocket server;

    public Server(){
        try {
            server = new ServerSocket(Protocol.PORT);
            nickNames = new ArrayList<>();
            connections = new ArrayList<>();
            chat = new Chat();
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void start(){
        while (true){
            try {
                Socket s = server.accept();
                Connection c = new Connection(s, this);
                connections.add(c);
                c.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean register(String nick){
        for (String s : nickNames){
            if(s.equals(nick)){
                return false;
            }
        }
        nickNames.add(nick);
        return true;
    }

    public void release(String nick){
        for (String s : nickNames){
            if(s.equals(nick)){
                nickNames.remove(s);
                return;
            }
        }
    }

    public void initExit(){
        System.exit(0);
    }

    public List<String> getNickNames() {
        return nickNames;
    }

    public Chat getChat() {
        return chat;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

}
