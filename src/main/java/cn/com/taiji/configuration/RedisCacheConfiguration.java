package cn.com.taiji.configuration;













import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;



@Configuration
@EnableCaching
@PropertySource("classpath:/redis.properties")
public class RedisCacheConfiguration extends CachingConfigurerSupport{
 Logger logger = LoggerFactory.getLogger(RedisCacheConfiguration.class);
 
 @Value("${spring.redis.host}")
 private String host;
 
 @Value("{spring.redis.database}")
 private String database;
 
 @Value("${spring.redis.port}")
 private int  port;
 
 @Value("${spring.redis.timeout}")
 private int timeout;
 
 @Value("${spring redis.pool.max.idle}")
 private int maxIdle;

 @Value("{$ spring.redis.pool.max.wait}")
 private long maxWaitMillis;
 
 @Value("{$ spring.redis.password}")
 private String password;

 
 @Bean
 public JedisConnectionFactory redisConnectionFactory() {
	 JedisConnectionFactory redisConnectionFactory =new JedisConnectionFactory();
	 //defaults
	 System.out.println("++++host"+host);
	 JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	 jedisPoolConfig.setMaxIdle(maxIdle);
	 jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
	 redisConnectionFactory.setHostName(host);
	 redisConnectionFactory.setPort(port);
	 redisConnectionFactory.setPoolConfig(jedisPoolConfig);
	return redisConnectionFactory; 
	 
 }
 
 @Bean
 public StringRedisTemplate stringRedisTemplate() {
	 StringRedisTemplate redisTemolate =new StringRedisTemplate();
	 redisTemolate.setConnectionFactory(redisConnectionFactory());
	 redisTemolate.afterPropertiesSet();
	 return redisTemolate;
 }
 
 
 @Bean
 public RedisTemplate<String, Object> redisTemplate(){
	 RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
	 redisTemplate.setConnectionFactory(redisConnectionFactory());
//	 redisTemplate.setKeySerializer(new StringRedisSerializer());
	 redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
	 redisTemplate.setValueSerializer(new StringRedisSerializer());
	return redisTemplate;
	 
 }
 
 
 @Bean
 public CacheManager cacheManager() {
	 RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
	 cacheManager.setDefaultExpiration(timeout);//设置过期时间
	 cacheManager.setLoadRemoteCachesOnStartup(true);//启动时加载进程缓存
	 cacheManager.setUsePrefix(true);//是否使用进程生成器
	 return cacheManager;
 }
 
 
}
