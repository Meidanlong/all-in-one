package com.mdl.springboot.aigc.service.kling;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 可灵服务
 *
 * @author meidanlong
 * @date 2024年11月14日
 * @version: 1.0
 */
@Service
public class KlingService {

    static String ak = "";
    static String sk = "";

    public static void main(String[] args) {
        String token = sign(ak, sk);
        System.out.println(token);
    }

    static String sign(String ak, String sk) {
        try {
            // 有效时间，此处示例代表当前时间+1800s(30min)
            Date expiredAt = new Date(System.currentTimeMillis() + 1800 * 1000);
            //开始生效的时间，此处示例代表当前时间-5秒
            Date notBefore = new Date(System.currentTimeMillis() - 5 * 1000);
            Algorithm algo = Algorithm.HMAC256(sk);
            Map<String, Object> header = new HashMap<>();
            header.put("alg", "HS256");
            return JWT.create()
                    .withIssuer(ak)
                    .withHeader(header)
                    .withExpiresAt(expiredAt)
                    .withNotBefore(notBefore)
                    .sign(algo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
