package kopo.poly.service.impl;

import kopo.poly.dto.CAssignmentDTO;
import kopo.poly.dto.CGroupDTO;
import kopo.poly.persistance.mapper.ICAssignmentMapper;
import kopo.poly.service.IKakaoService;
import kopo.poly.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

@Slf4j
@EnableScheduling
@Service("KakaoService")
public class KakaoService implements IKakaoService {
    private final ICAssignmentMapper cAssignmentMapper;


    @Autowired
    public KakaoService(ICAssignmentMapper cAssignmentMapper) {
        this.cAssignmentMapper = cAssignmentMapper;
    }



    @Override
    public String sendKaKao(CGroupDTO aDTO) throws Exception {
        log.info(this.getClass().getName() + "카카오톡 메시지 전송 서비스 시작!");

        if (aDTO == null){
            aDTO = new CGroupDTO();
        }

        log.info("DB에서 찍어온 그룹별 컨텐츠 데이터는 ? " + aDTO.getGroup_msg());

        UrlUtil uu = new UrlUtil();
        String url = "http://localhost:8000"; // 파이썬 포트 5005
        String api = "/kakaoFriend";  // 파이썬 app.route 명
        String data = "?content=";
        String content_data = aDTO.getGroup_msg();

        String fullPath = uu.urlReadforString(url + api + data + URLEncoder.encode(content_data,"UTF-8"));

        log.info("파이썬에서 넘어온 메시지 전송 결과는 ? " + fullPath);
        log.info(this.getClass().getName() + "카카오톡 메시지 전송 서비스 종료!");



        return fullPath;
    }

    @Scheduled(cron = "0 30 3 * * *")   // 매일 00시 정각
    public void send_Lee()throws Exception{
        log.info(this.getClass().getName()+"매일 아침 교수님께 발송하는 과제 제출 현황 시작!");

        CAssignmentDTO pDTO = cAssignmentMapper.proKakaoSend();

        UrlUtil uu = new UrlUtil();

        String url = "http://localhost:8000"; // 파이썬 포트 5005
        String api = "/kakaoFriend";  // 파이썬 app.route 명
        String data = "?content=";
        String content_data = "이협건 교수님 현재 과제 제출 현황입니다\n"+ "과제명 : "+pDTO.getAssignment_room_name()+"\n"+"제출인원 : " + pDTO.getCnt_work()+"명 입니다.";

        String fullPath = uu.urlReadforString(url + api + data + URLEncoder.encode(content_data,"UTF-8"));
        log.info("교수님 카카오 파이썬 전송 결과는 ? " + fullPath);

        log.info(this.getClass().getName()+"매일 아침 교수님께 발송하는 과제 제출 현황 종료!");

    }

}
