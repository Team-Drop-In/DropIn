package teamdropin.server.redis.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RedisUtil {

    private final StringRedisTemplate template;

    public String getData(String key){
        ValueOperations<String,String> valueOperations = template.opsForValue();
        return valueOperations.get(key);
    }

    public boolean existData(String key){
        return Boolean.TRUE.equals(template.hasKey(key));
    }

    @Transactional(readOnly = false)
    public void setData(String key, String value, long duration){
        ValueOperations<String,String> valueOperations = template.opsForValue();
        Duration expiredDuration = Duration.ofSeconds(duration);
        valueOperations.set(key,value,expiredDuration);

    }

    @Transactional(readOnly = false)
    public void deleteData(String key){
        template.delete(key);
    }
}
