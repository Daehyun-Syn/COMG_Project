package kopo.poly.service.impl;


import kopo.poly.dto.CUserDTO;
import kopo.poly.dto.ChatDTO;
import kopo.poly.persistance.mapper.CustomerInserMapper;
import kopo.poly.persistance.mapper.ICustomerMapper;
import kopo.poly.service.ICustomerService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service("CustomerService")
public class CustomerService implements ICustomerService {

    ICustomerMapper customerMapper;
    public CustomerService(ICustomerMapper customerMapper) { this.customerMapper = customerMapper; }


    public void insertChat(ChatDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getRoomList Start!");

        // 채팅 내용 저장
        if (customerMapper.insertChat(pDTO) == 1) {
            log.info("chatMapper.insertChat Success!");

            // 데이터 만료시간 정의(채팅 이후 5분까지만 데이터 저장)
            customerMapper.setTimeOutHour(CmmUtil.nvl(pDTO.getRoomKey()), 5);

        } else {
            log.info("chatMapper.insertChat Fail!");

        }

    }

    public List<ChatDTO> getChat(ChatDTO pDTO) throws Exception{
        log.info(this.getClass().getName() + ".getChat Start!");
        return customerMapper.getChat(pDTO);
    }
}
