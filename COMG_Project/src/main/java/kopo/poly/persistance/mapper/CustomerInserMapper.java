package kopo.poly.persistance.mapper;

import kopo.poly.dto.CUserDTO;
import kopo.poly.dto.ChatDTO;
import kopo.poly.dto.CustomerDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CustomerInserMapper {

    CUserDTO selectChatRoom(CUserDTO uDTO);

    void insertChatRoom(CUserDTO uDTO);

    List<CustomerDTO> selectAdminRoom();
}
