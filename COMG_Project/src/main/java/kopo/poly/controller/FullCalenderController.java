package kopo.poly.controller;


import kopo.poly.dto.ScheduleDTO;
import kopo.poly.dto.ScheduleReqDTO;
import kopo.poly.service.ICBoardService;
import kopo.poly.service.impl.FullCalenderService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class FullCalenderController {

    FullCalenderService fullCalenderService;
    public FullCalenderController(FullCalenderService fullCalenderService) {
        this.fullCalenderService = fullCalenderService;
    }

    @GetMapping(value = "/fullCalender")
    public String Calender(ModelMap model, HttpServletRequest request) {
        int groupSeq = Integer.parseInt(CmmUtil.nvl(request.getParameter("groupSeq")));
        ScheduleReqDTO reqDTO = null;
        reqDTO = new ScheduleReqDTO();
        reqDTO.setGroupSeq(groupSeq);

        List<ScheduleDTO> eList = null;
        eList = new ArrayList<>();

        eList = fullCalenderService.getScheduleList(reqDTO);
        if(eList == null) {
            eList = new ArrayList<>();
        }else {
            for(int i = 0; i<eList.size(); i++){
                log.info(eList.get(i).getAssignmentRoomName());
                log.info(eList.get(i).getAssignmentRegDate());
                log.info(eList.get(i).getAssignmentDeadLine());
            }

            model.addAttribute("eList",eList);

        }

        return "/calender/fullcalender";
    }

}
