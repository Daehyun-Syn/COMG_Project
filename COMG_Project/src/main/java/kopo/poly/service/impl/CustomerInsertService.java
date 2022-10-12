package kopo.poly.service.impl;


import kopo.poly.dto.CUserDTO;
import kopo.poly.dto.ChatDTO;
import kopo.poly.dto.CustomerDTO;
import kopo.poly.persistance.mapper.CustomerInserMapper;
import kopo.poly.persistance.mapper.ICustomerMapper;
import kopo.poly.service.ICustomerInsertService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service("CustomerInsertService")
public class CustomerInsertService implements ICustomerInsertService {


    CustomerInserMapper customerInserMapper;
    public CustomerInsertService(CustomerInserMapper customerInserMapper) { this.customerInserMapper = customerInserMapper; }



    public String insertChatRoom(CUserDTO uDTO) {
        CUserDTO cDTO = new CUserDTO();
        cDTO = customerInserMapper.selectChatRoom(uDTO);
        if (cDTO.getCustRoomCheck().equals("0")){
            customerInserMapper.insertChatRoom(uDTO);
            log.info("0에 들어옴");
        }
        log.info("0에 안 들어옴");

        return "ok";
    }

    @Override
    public List<CustomerDTO> getAdminList() {


        return customerInserMapper.selectAdminRoom();
    }
}
