package kopo.poly.service.impl;

import kopo.poly.dto.CMailDTO;
import kopo.poly.service.ICMailService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Service("CMailService")
public class CMailService implements ICMailService {

    final String host = "smtp.gmail.com";
    final String user = "shindaepal@gmail.com";
    final String password = "cxoyrwyfkuwuvhzy";
    final int port = 465;

    @Override
    public int doSendmail(CMailDTO pDTO) throws Exception {

        // 메일발송 성공: 1, 실패 0
        int res = 1;
        log.info("CMailService : doSendmail(메일발송 서비스) 시작! ");

        // 받아오는 pDTO 널처리
        if(pDTO == null) {
            pDTO = new CMailDTO();
        }

        String toMail = CmmUtil.nvl(pDTO.getToMail());

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", 587);



        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));

            // 메일 받는 사람
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
            log.info("메일 받는 사람 : " + toMail);

            // 메일 제목
            message.setSubject(CmmUtil.nvl(pDTO.getTitle()));
            log.info("받아온 메일 제목 : " + pDTO.getTitle());

            // 메일 내용
            message.setText(CmmUtil.nvl(pDTO.getContents()));
            log.info("받아온 메일 내용 : " + pDTO.getContents());

            // 메일 발송
            Transport.send(message);

        }catch(MessagingException e) {
            res = 0;
            log.info("[ERROR]" + this.getClass().getName() + ".doSendMail : " + e);
        }catch(Exception e) {
            res = 0;
            log.info("[ERROR]" + this.getClass().getName() + ".doSendMail : " + e);
        }

        log.info("CMailService : doSendmail(메일발송 서비스) 끝! ");
        return res;
    }
}
