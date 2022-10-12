package kopo.poly.service;

import kopo.poly.dto.ChatDTO;

import java.util.List;

public interface ICustomerService {
    void insertChat(ChatDTO pDTO) throws Exception ;

    List<ChatDTO> getChat(ChatDTO pDTO) throws Exception ;


}
