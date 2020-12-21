package com.talent.six.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Clock;
import java.util.Date;

public class JwtUtil {

	/**
	 * @Title: createJWT
	 * @Description: 创建jwt
	 * @Author:ln
	 * @Version:1.0
	 * @Create:2018-6-21
	 */
	public static String createJWT(String subject, long ttlMillis) throws Exception {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		//生成签名的时候使用的秘钥secret
		SecretKey key = generalKey();
		JwtBuilder builder = Jwts.builder()
				.setIssuedAt(new Date())    //iat: jwt的签发时间
				.setSubject(subject);       //sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串
		if (ttlMillis >= 0) {
			long expMillis = Clock.systemDefaultZone().millis() + ttlMillis;
			builder.setExpiration(new Date(expMillis));
		}
		builder.signWith(signatureAlgorithm, key);//设置签名使用的签名算法和签名使用的秘钥
		return builder.compact();
	}

	/**
	 * @Title: parseJWT
	 * @Description: 对token进行解码，如果验证失败返回null
	 * @Author:ln
	 * @Version:1.0
	 * @Create:2018-6-21
	 */
	public static Claims parseJWT(String jwt) {
		SecretKey key = generalKey();  //签名秘钥
		Claims claims = null;

		try {
			claims = Jwts.parser()  //得到DefaultJwtParser
					.setSigningKey(key)    //设置签名的秘钥
					.parseClaimsJws(jwt).getBody();//设置需要解析的jwt
		} catch (Exception e) {
			claims = null;
		}

		return claims;
	}

	/**
	 * @Title: generalKey
	 * @Description: 通过AES制造密钥
	 * @Author:ln
	 * @Version:1.0
	 * @Create:2018-6-21
	 */
	public static SecretKey generalKey() {
		//本地配置文件中加密的密文
		String stringKey = Constant.JWT_SECRET;
		byte[] encodedKey = Base64.decodeBase64(stringKey);
		// 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。（后面的文章中马上回推出讲解Java加密和解密的一些算法）
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}
}
