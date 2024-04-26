package org.example;

public class Message {

    public static final String WELCOME = "채팅 어플리케이션에 온 것을 환영합니다.";
    public static final String REQUEST_COMMAND =
            "이용하실 기능을 선택해 주세요\n"
            + "방 목록 보기 : /list\n"
            + "방 생성 : /create\n"
            + "방 입장 : /join [방번호]\n"
            + "방 퇴장 : /exit\n"
            + "어플 종료 : /bye\n";
    public static final String ROOM_NOT_EXIST = "생성된 방이 없습니다.";
    public static final String VIEW_ROOM_FORMAT = "%d 번 방 : %s\n";
    public static final String REQUEST_ROOM_NAME = "방 이름을 입력해 주세요.";
    public static final String REQUEST_ROOM_NUMBER = "입장하실 방의 번호를 입력해 주세요.";
    public static final String REQUEST_NICKNAME = "닉네임을 입력해주세요.";
    public static final String NOT_EXIST_ROOM_NUMBER = "존재하지 않는 방입니다.";
    public static final String CLIENT_JOIN_CHATTING = "%s 사용자가 채팅에 입장했습니다.";
    public static final String CHATTING_START = "채팅이 시작됩니다.";
    public static final String QUIT_APPLICATION = "어플리케이션을 종료합니다.";
}
