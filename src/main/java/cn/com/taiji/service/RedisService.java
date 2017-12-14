package cn.com.taiji.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import cn.com.taiji.domain.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisService {
@Autowired
RedisTemplate redisTemplate;
@Autowired
StringRedisTemplate stringRedisTemplate;

public String test() {
	String uuid =UUID.randomUUID().toString();
//	
//	stringRedisTemplate.opsForValue();
	redisTemplate.opsForValue().set("uuid", "测试",1000);
//	redisTemplate.opsForValue().set("ttt", "t1234");
	Map<String, Object> map =new HashMap<String,Object>();
	map.put("name", "llj");
	map.put("age", "22");
	map.put("sex", "男");
	redisTemplate.opsForHash().putAll("user:002", map);
	stringRedisTemplate.opsForHash().putAll("user:005", map);
//	try(Jedis jedis = JedisPool.getResource()){
//		jedis.setex(uuid, 1000, "测试");
//		
//	} 
//	User user = new User("llj","男","22");
	
	
//	Map<String, Object> map =new HashMap<String,Object>();
//	map.put("name", "llj");
//	map.put("age", "22");
//	map.put("sex", "男");
//	redisTemplate.opsForHash().put("user:006", "name", "test");
//	stringRedisTemplate.opsForHash().put("user:007", "name", "test");
	return  uuid;
	
	

}

@SuppressWarnings("all")
public String serialize(User u) {
	redisTemplate.opsForValue().set("user:"+u.getId(), u);
	return u.toString();
}

public User getUser(String id) {
	User user = (User) redisTemplate.opsForValue().get(id);
	return user;
}
	
}

