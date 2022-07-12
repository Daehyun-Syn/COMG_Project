package kopo.poly.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlUtil {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * API 내용 문자열로 변환하기
     */
    private String readAll(Reader rd) {
        log.info("readAll Start!!");

        StringBuilder sb = null;

        try {
            sb = new StringBuilder();
            int cp = 0;

            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
        } catch (Exception e) {
            log.info("readAll Exception : " + e.toString());
        }
        log.info("readAll End !!!");

        return sb.toString();
    }

    /**
     * URL로부터 JSON 받아서 객체 생성하기
     *
     * @param url
     * @return
     * @throws IOException
     */
    public String urlReadforString(String url) throws IOException {
        log.info("urlReadforString Start!!");

        log.info("urlReadforString url : " + url);

        BufferedReader rd = null;
        InputStream is = null;

        String res = "";

        try {
            is = new URL(url).openStream();

            // URL 결과 내용이 많을수 있기 때문에 BufferedReader를 통해 조금씩 가져오도록 설정
            rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            // 결과 가져오기
            res = readAll(rd);
        } catch (Exception e) {
            log.info("urlReadforString Exception : " + e.toString());
        } finally {

            if (is != null) {

                is.close();
                is = null;
            }
            rd = null;
        }
        log.info("urlReadforString End !!");

        return res;
    }


}
