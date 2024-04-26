package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import static com.example.day18.Message.*;

public class ChatThread extends Thread {
    private String nickname;
    private Socket socket;
    private List<ChattingRoom> chattingRooms;
    private ChattingRoom currentChattingRoom;
    private final Map<String, PrintWriter> clients;
    private PrintWriter out;
    private BufferedReader in;


    public ChatThread(Socket socket, List<ChattingRoom> chattingRooms, Map<String, PrintWriter> clients) throws IOException {
        this.socket = socket;
        this.chattingRooms = chattingRooms;
        this.clients = clients;
        currentChattingRoom = null;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        nickname = in.readLine();
        synchronized (clients){
            clients.put(nickname, out);
        }
    }

    @Override
    public void run() {
        out.println(WELCOME);
        out.println(REQUEST_COMMAND);
        String chatting;
        try {
            while ((chatting = in.readLine()) != null) {
                if ("/list".equalsIgnoreCase(chatting)) {
                    viewChattingRoom();
                    out.println(REQUEST_COMMAND);
                } else if ("/create".equalsIgnoreCase(chatting)) {
                    createChattingRoomAndJoin();
                } else if (chatting.matches("/join+\\s+\\d+$")) {
                    joinChattingRoom(chatting);
                } else if ("/exit".equalsIgnoreCase(chatting)) {
                    exitChattingRoom();
                    out.println(REQUEST_COMMAND);
                } else if ("/bye".equalsIgnoreCase(chatting)){
                    out.println(QUIT_APPLICATION);
                } else {
                    out.println("올바른 명령어가 아닙니다.");

                }
            }
        } catch (IOException e){
            throw new IllegalArgumentException();
        }
    }


    private void viewChattingRoom() {
        if (chattingRooms.size() == 0){
            out.println(ROOM_NOT_EXIST);
        }
        for (ChattingRoom chattingRoom : chattingRooms) {
            out.println(String.format(VIEW_ROOM_FORMAT, chattingRoom.getRoomId(), chattingRoom.getRoomName()));
        }
    }
    private void createChattingRoomAndJoin() throws IOException {
        out.println(REQUEST_ROOM_NAME);
        String roomName = in.readLine();
        ChattingRoom chattingRoom = new ChattingRoom(roomName);
        chattingRooms.add(chattingRoom);
        currentChattingRoom = chattingRoom;
        alertClientJoin(nickname);
        startChatting();
    }
    private void joinChattingRoom(String chatting) throws IOException {
        int roomNumber = Integer.parseInt(chatting.split(" ")[1]);
        if (roomNumber > ChattingRoom.getTotalRoomCount()){
            System.out.println(NOT_EXIST_ROOM_NUMBER);
            return;
        }
        currentChattingRoom = chattingRooms.get(roomNumber - 1);
        alertClientJoin(nickname);
        startChatting();
    }
    private void exitChattingRoom() {
        currentChattingRoom = null;
        System.out.println(REQUEST_COMMAND);
    }

    private void startChatting() throws IOException {
        out.println(CHATTING_START);
        String chatting;
        while (!(chatting = in.readLine()).equalsIgnoreCase("/exit")){
            synchronized (clients){
                final String input = chatting;
                clients.forEach((k,v) -> v.println(input));
            }
        }
    }

    public void alertClientJoin(String nickname) {
        synchronized (clients){
            clients.forEach((k,v) -> v.println(String.format(CLIENT_JOIN_CHATTING, nickname)));
        }
    }
}
