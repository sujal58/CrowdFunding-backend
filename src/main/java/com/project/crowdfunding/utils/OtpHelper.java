package com.project.crowdfunding.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.security.SecureRandom;

public class OtpHelper {

    private static final JedisPool jedisPool = new JedisPool("localhost", 6379);
    private static final int EXPIRY_SECONDS = 5 *60;

    private static final SecureRandom random = new SecureRandom();

    public static void storeOtp(String email, String otp){
        try(Jedis jedis = jedisPool.getResource()){
            String key = "otp:"+email;
            jedis.setex(key, EXPIRY_SECONDS,otp);
        }
    }

    public static boolean verifyOtp(String email, String otp){
        try(Jedis jedis = jedisPool.getResource()){
            String key = "otp:"+email;
            String storedOtp = jedis.get(key);
            if(storedOtp == null || storedOtp.isEmpty()){
                return false;
            }
            return storedOtp.equals(otp);
        }
    }

    public static void removeOtp(String email){
        try(Jedis jedis = jedisPool.getResource()){
            String key = "otp:"+email;
            jedis.del(key);
        }
    }

    public static String getOtp(String email){
        try(Jedis jedis = jedisPool.getResource()){
            String key = "otp:"+email;
            return jedis.get(key);
        }
    }

    public static String generateOTP() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }


}
