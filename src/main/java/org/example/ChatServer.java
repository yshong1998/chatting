package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatServer {
    private final int port = 12345;
    private List<ChattingRoom> chattingRooms;

    public ChatServer() {
        try (ServerSocket serverSocket = new ServerSocket(port);){
            chattingRooms = new ArrayList<>();
            System.out.println("채팅 서버 시작");
            Map<String, PrintWriter> clients = new HashMap<>();
            while (true) {
                Socket socket = serverSocket.accept();
                ChatThread chatThread = new ChatThread(socket, chattingRooms, clients);
                chatThread.start();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("포트가 이미 사용 중입니다.");
        }
    }
}