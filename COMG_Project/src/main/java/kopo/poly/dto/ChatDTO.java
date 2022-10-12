package kopo.poly.dto;

import lombok.Data;

@Data
public class ChatDTO {

    public enum MessageType{
        ENTER,COMM
    } //열겨헝 아직 모르겟음 일단 저장

    private String roomKey ; // 방번호
    private String user_name ; // 유저이름
    private String user_no ; // 유저번호
    private String msg ; // 메세지
    private String dateTime ; // 전송시간
    private String user_other ; // 상대방이름 저장
    private String type ; // admin / user / who

}
