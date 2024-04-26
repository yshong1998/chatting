package org.example;

public class ChattingRoom {
    private static int totalRoomCount = 0;
    private int roomId;
    private String roomName;


    public ChattingRoom(String roomName) {
        this.roomId = ++totalRoomCount;
        this.roomName = roomName;
    }

    public static int getTotalRoomCount() {
        return totalRoomCount;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }
}
