package kopo.poly.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import kopo.poly.dto.CUserDTO;
import kopo.poly.dto.ChatDTO;
import kopo.poly.dto.CustomerDTO;
import kopo.poly.service.ICAssignmentService;
import kopo.poly.service.ICustomerInsertService;
import kopo.poly.service.ICustomerService;
import kopo.poly.service.impl.CustomerInsertService;
import kopo.poly.service.impl.CustomerService;

import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class CustomerController {


    @Resource(name = "CustomerService")
    private ICustomerService customerService;

    @Resource(name = "CustomerInsertService")
    private ICustomerInsertService customerInsetService;


    @GetMapping(value = "/chat2")
    public String chat2() {


        return "asd";
    }
    @GetMapping(value = "/chat")
    public ModelAndView getChatViewPage(ModelAndView mv) {
        mv.setViewName("/chat/chat");

        return mv;
    }

    //채팅 메세지 아작스로 저장하기
    @PostMapping(value = "chat/msg")
    @ResponseBody
    public String msg (HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String,String> param)throws Exception{
        log.info(this.getClass().getName()+"chatController Start!");

        String name = param.get("name");
        String msg = param.get("msg");
        String date = param.get("date");
        String roomKey = param.get("roomKey");
        String other = "Admin";

        log.info(this.getClass().getName()+"name"+name);  //보내는 사람이름
        log.info(this.getClass().getName()+"msg"+msg); //보내는 메시지
        log.info(this.getClass().getName()+"date"+date); //보내는 날짜
        log.info(this.getClass().getName()+"roomKey"+roomKey); //채팅방 룸키


        List<ChatDTO> rList =null;

        if(msg.length()>0){
            ChatDTO pDTO = new ChatDTO();
            pDTO.setMsg(msg);
            pDTO.setDateTime(date);
            pDTO.setRoomKey(roomKey); //수정해야하는 부분
            pDTO.setUser_name(name);
            pDTO.setUser_other(other);
            customerService.insertChat(pDTO); //저장

            pDTO = null;

        }

        log.info(this.getClass().getName() + ".msg End!");

        return "ok";
    }


    @GetMapping(value = "/chatingroom")
    public String chattroom(ModelMap model, HttpServletRequest request, HttpSession session)throws Exception{
        log.info(this.getClass().getName() + ".getMsg Start!");

        String other = "Admin"; //관리자 이름

        CUserDTO suDTO = new CUserDTO();
        suDTO = (CUserDTO) session.getAttribute("suDTO");
        String myId = CmmUtil.nvl(suDTO.getUser_id());
        log.info("other : " + other);

        ChatDTO pDTO = new ChatDTO();

        pDTO.setRoomKey("Chat_to_Admin_from_"+myId); //채팅방이름
        pDTO.setUser_other(other); //상대방이름
        CUserDTO uDTO = new CUserDTO();
        uDTO.setCUST_ROOM("Chat_to_Admin_from_"+myId);
        uDTO.setUser_id(myId);
        customerInsetService.insertChatRoom(uDTO);



        List<ChatDTO> rList = customerService.getChat(pDTO); //채팅방기록가져오기

        if (rList == null) {
            rList = new LinkedList<>();

        }



        // 조회된 리스트 결과값 넣어주기
        model.addAttribute("pDTO",pDTO);  //채팅방에 보내기
        model.addAttribute("rList", rList);
        log.info("rList 채팅방 목록 가져오기"+rList);
        log.info(this.getClass().getName() + ".getMsg End!");
        log.info("start chattingroom");

        return"/CUSTOMER/customerUser";
    }

    @GetMapping(value = "/COMG/adminCustomerList")
    public String adminCustomerList(ModelMap model, HttpServletRequest request){
        List<CustomerDTO> rList = customerInsetService.getAdminList();

        model.addAttribute("rList", rList);
        return "/CUSTOMER/customerAdminList";
    }

    @GetMapping(value = "/COMG/adminCustomer")
    public String adminCustomer(ModelMap model, HttpServletRequest request, HttpSession session)throws Exception{
        log.info(this.getClass().getName() + ".getMsg Start!");

        String other = CmmUtil.nvl(request.getParameter("user_id")); //상대방 아이디
        String roomkey = CmmUtil.nvl(request.getParameter("room_key"));
        String userName = CmmUtil.nvl(request.getParameter("userName")); //상대방 아이디
        String profilePath = CmmUtil.nvl(request.getParameter("profilePath"));
        String myName = CmmUtil.nvl((String) session.getAttribute("user_name"));

        log.info("other : " + other);
        log.info("내 이름 : " +myName);

        ChatDTO pDTO = new ChatDTO();

        pDTO.setRoomKey(roomkey); //채팅방이름
        pDTO.setUser_other(userName); //상대방이름



        List<ChatDTO> rList = customerService.getChat(pDTO); //채팅방기록가져오기

        if (rList == null) {
            rList = new LinkedList<>();
            log.info("rList 가 널임");
        }



        // 조회된 리스트 결과값 넣어주기
        model.addAttribute("pDTO",pDTO);
        model.addAttribute("roomkey",roomkey);  //채팅방에 보내기
        model.addAttribute("myName",myName);  //채팅방에 보내기
        model.addAttribute("userName",userName);  //채팅방에 보내기
        model.addAttribute("profilePath",profilePath);  //채팅방에 보내기
        model.addAttribute("rList", rList);
        log.info("rList 채팅방 목록 가져오기"+rList);
        log.info(this.getClass().getName() + ".getMsg End!");
        log.info("start chattingroom");
        return"/CUSTOMER/customerAdmin";
    }
}
