package kopo.poly.persistance.redis;

import kopo.poly.dto.ChatDTO;
import kopo.poly.persistance.mapper.ICustomerMapper;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component("CustomerMapper")
public class CustomerMapper implements ICustomerMapper {

    public final RedisTemplate<String, Object> redisDB;

    public CustomerMapper(RedisTemplate<String, Object> redisDB) {
        this.redisDB = redisDB;
    }


    @Override
    public int insertChat(ChatDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".insertChat Start!");

        int res = 0;

        // 대화방 키 정보
        String roomKey = CmmUtil.nvl(pDTO.getRoomKey());

        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatDTO.class)); // JSON 타입

        redisDB.opsForList().rightPush(roomKey, pDTO);

        res = 1;

        log.info(this.getClass().getName() + ".insertChat End!");

        return res;
    }

    @Override
    public boolean setTimeOutHour(String roomKey, int hours) throws Exception {
        log.info(this.getClass().getName() + ".setTimeOutHour Start!");
        return redisDB.expire(roomKey, hours, TimeUnit.HOURS);
    }

    @Override
    public List<ChatDTO> getChat(ChatDTO pDTO) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".getChat Start!");

        // Redis에서 가져온 결과 저장할 객체
        List<ChatDTO> rList = null;

        // 대화방 키 정보
        String roomKey = CmmUtil.nvl(pDTO.getRoomKey());

        redisDB.setKeySerializer(new StringRedisSerializer()); // String 타입
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatDTO.class)); // ??이게뭔지모름

        if (redisDB.hasKey(roomKey)) {

            // 저장된 전체 레코드 가져오기
            rList = (List) redisDB.opsForList().range(roomKey, 0, -1);

        }

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".getChat End!");

        return rList;
    }
}

