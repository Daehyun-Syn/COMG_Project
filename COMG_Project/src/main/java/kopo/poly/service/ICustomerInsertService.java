package kopo.poly.service;

import kopo.poly.dto.CUserDTO;
import kopo.poly.dto.CustomerDTO;

import java.util.List;

public interface ICustomerInsertService {
    String insertChatRoom(CUserDTO uDTO);

    List<CustomerDTO> getAdminList();
}
