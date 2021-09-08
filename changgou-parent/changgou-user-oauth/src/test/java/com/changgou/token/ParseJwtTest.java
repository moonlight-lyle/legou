package com.changgou.token;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

/*****
 * @Author: www.itheima
 * @Date: 2019/7/7 13:48
 * @Description: com.changgou.token
 *  使用公钥解密令牌数据
 ****/
public class ParseJwtTest {

    /***
     * 校验令牌
     */
    @Test
    public void testParseToken(){
        // 令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IlJPTEVfVklQLFJPTEVfVVNFUiIsIm5hbWUiOiJpdGhlaW1hIiwiaWQiOiIxIn0.nJxVxMb6-weRym5F5THXdRTZqoU9wlZPRpNK0O6BVbGDcbhcBqdm_6eP8fMU4H-GbEQnFK-mwISEipllmPF4ObwWMyYGo3fpQFoMsDJJJ4NyLK5Faz6uHgLxQHjq3P3LoA5uBlCNWjYtITT5XSD2XnK9Egb3ZXwf28Y1tD_G3lfFu96ID63aSQEaPFr8VuSsiQdQvr_zImEOs2PoWoqTvjokesGq5ej4YwbMme0oRtHBbCHwQXeuVNsb_vatQDjjlNrn9-bMmI4luQG1E7W0rFzyjaOPf6SLVbuaVulVdLuEvaCw5BsJBM9uA2d-91EYyzktyneoBrOkQggG_zPnOw";

        // 公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs0RP/0bOrGvBbx8P/eJoZgrts+Gqx9wSWGcWwFAEz+kouRTxu/TQ934Ndii3yLERxJQUn29Yzu0ahm0ZEqE17q7JUbACpEG66I2rTRfvDEY09F3+vGbi+7TFZtuUmfVoIvKQAvUJanPxK26FktPprXyftkGdj0yvMouc2UcukEWPs8iDVvfT8eGpZO9WB+VcJbEBAjf9HC11Xh9Q8ojs1rYo8NKQhgJ8N1s+bYP5kdqfOykRL3CkxNlTaxVTTeU9+A1nrVan7ONHDy+BqBUSDVKMdJu8qGWjbZsVtnvd2vTuQzCMciFaICNiUO/JDudv6l4WIsg0Mtzt8txF+oEBPwIDAQAB-----END PUBLIC KEY-----";

        // 校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        // 获取Jwt原始内容
        String claims = jwt.getClaims();
        System.out.println(claims);
        // jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }
}
