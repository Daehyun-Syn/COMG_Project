package kopo.poly.persistance.mapper;

import kopo.poly.dto.CUserDTO;
import kopo.poly.dto.ChatDTO;


import java.util.List;

public interface ICustomerMapper {

    // 인터페이스 선언된 변수는 상수로 선언(final과 동일)되면, 한번 메모리에 로딩된 후 값은 변경할 수 없음
    String roomInfoKey = "myRoomKey";


    // 채팅 대화 저장하기
    int insertChat(ChatDTO pDTO) throws Exception;

    // 데이터 저장 유효시간을 시간 단위로 설정
    boolean setTimeOutHour(String roomKey, int hours) throws Exception;


    //채팅방 목록 가져오기
    List<ChatDTO> getChat(ChatDTO pDTO)throws Exception;



}
