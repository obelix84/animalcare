package ru.animalcare.service.imp;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;


//OTP(одноразовый код)
@Service
public class OTPService {

    private static final Integer EXPIRE_MINS = 3;
    private LoadingCache otpCache;

    public OTPService() {
        super();
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader() {
                    public Integer load(Object key) {
                        return 0;
                    }
                });
    }

    public int generateOTP(Object key) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        otpCache.put(key, otp);
        return otp;
    }

    //TODO нужно проверить эту функцию
    public int getOtp(Object key) {
        try {
            return (int) otpCache.get(key.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public void clearOTP(String key) {
        otpCache.invalidate(key);
    }
}
