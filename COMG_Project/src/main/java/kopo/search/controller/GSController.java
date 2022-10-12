package kopo.search.controller;

import com.google.gson.Gson;
import kopo.poly.dto.CGroupDTO;
import kopo.poly.util.CmmUtil;
import kopo.search.service.IGSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class GSController {

    @Resource(name = "GSService")
    private IGSService gsService;

    @GetMapping (value = "COMG/SearchGroupName")
    public String SearchGroupName(HttpServletRequest request, ModelMap model) throws Exception {

        log.info("GSController : SearchGroupName 시작 ! ");

        CGroupDTO gDTO = null;
        List<CGroupDTO> rList = null;

        String group_name = CmmUtil.nvl(request.getParameter("searchGroupName"));
        String user_name = CmmUtil.nvl(request.getParameter("user_name"));
        String student_id = CmmUtil.nvl(request.getParameter("student_id"));
        String user_profile = CmmUtil.nvl(request.getParameter("user_profile"));
        log.info(user_profile);

        log.info("JSP에서 받아온 group_name : " + group_name);

        try {

            gDTO = new CGroupDTO();
            gDTO.setGroup_name(group_name);

            rList = gsService.getGroupListForAjax(gDTO);

            if (rList == null) {
                log.info("GSController : 그룹 검색 실패");
                rList = new ArrayList<>();
                log.info("rList가 null이라 강제로 메모리에 올림");
                model.addAttribute("user_name", user_name);
                model.addAttribute("student_id", student_id);
                model.addAttribute("user_profile", user_profile);
            } else {
                log.info("GSController : 그룹 검색 성공");
                model.addAttribute("user_name", user_name);
                model.addAttribute("student_id", student_id);
                model.addAttribute("user_profile", user_profile);
                model.addAttribute("rList", rList);
                for(int i = 0; i < rList.size(); i++){
                    log.info(rList.get(i).getGroup_name());
                }
            }

        } catch (Exception e) {
            log.info("ajax 로직이 실패하였습니다" + e.toString());
            e.printStackTrace();
        }



        log.info("GSController : SearchGroupName 끝 ! ");
        return "/COMGgroup/GroupSearch";
    }



    @ResponseBody
    @GetMapping (value = "COMG/AutoComplete")
    public List<CGroupDTO> Auto(HttpServletRequest request, @RequestParam(value = "form", required = false) String form) throws Exception {

        log.info("GSController : SearchGroupName 시작 ! ");

        CGroupDTO gDTO = null;
        List<CGroupDTO> rList = null;
        String json = null;

        log.info("JSP에서 받아온 group_name : " + form);


        try {

            gDTO = new CGroupDTO();
            gDTO.setGroup_name(form);

            rList = gsService.getAutoGroupName(gDTO);

            if (rList == null) {
                log.info("GSController : 그룹 검색 실패");
                rList = new ArrayList<>();
                log.info("rList가 null이라 강제로 메모리에 올림");
            } else {
                log.info("GSController : 그룹 검색 성공");
                for(int i = 0; i < rList.size(); i++){
                    log.info(rList.get(i).getGroup_name());
                }
                json = new Gson().toJson(rList);
                System.out.println("제이슨 결과 = " + json);
            }

        } catch (Exception e) {
            log.info("ajax 로직이 실패하였습니다" + e.toString());
            e.printStackTrace();
        }

        log.info("GSController : SearchGroupName 끝 ! ");
        return rList;
    }
}