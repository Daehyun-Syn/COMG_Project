package kopo.poly.controller;

import kopo.poly.dto.CAssignmentDTO;
import kopo.poly.dto.CGroupDTO;
import kopo.poly.service.ICGroupService;
import kopo.poly.service.IKakaoService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@Controller
public class KakaoController {

    @Resource(name="KakaoService")
    private IKakaoService kaKaoService;

    @Resource(name = "CGroupService")
    private ICGroupService cGroupService;

    @ResponseBody
    @GetMapping  (value = "kakaoSend")
    public int kakaoSend(HttpServletRequest request, ModelMap modelMap, HttpSession session, @RequestParam Map<String,String> map) throws Exception {
        log.info(this.getClass().getName()+"카카오메시지 발송 컨트롤러 시작!");
        int res = 0;

        String group_seq = map.get("group_seq");
        log.info("양성인 키트 결과 가져온 그룹 seq  : " + group_seq);
        CGroupDTO aDTO = new CGroupDTO();
        aDTO.setGroup_seq(group_seq);
        CGroupDTO bDTO = new CGroupDTO();
        bDTO = cGroupService.getGroupInfo(aDTO);
        if(bDTO == null){
            bDTO = new CGroupDTO();
        }else {
            aDTO.setGroup_msg(bDTO.getGroup_msg());
        }
        String result = kaKaoService.sendKaKao(aDTO);

        if(result.equals("success")){
            res = 1;
        }else {
            res = 2;
        }


        log.info(this.getClass().getName()+"카카오메시지 발송 컨트롤러 종료!");

        return res;
    }
}

