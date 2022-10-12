package kopo.poly;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TravelPrjApplicationTests {

    @LocalServerPort
    private int port;

    @Test
    void contextLoads() {
        System.out.println(port);
        assertNotEquals(port, 0);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    // Do some test...

}