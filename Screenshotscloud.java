import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import java.util.Formatter;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Screenshotscloud {
    private String apiKey;
    private String apiSecret;

    public Screenshotscloud(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();

        for (byte b: bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    public static String calculateRFC2104HMAC(String data, String key)
    throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        return toHexString(mac.doFinal(data.getBytes()));
    }

    public String parameterString(Map<String, Object> options) throws UnsupportedEncodingException {
        String results = "";

        for (Map.Entry <String, Object> entry: options.entrySet()) {
            String parameter = entry.getValue().toString();
            String encodedParameter = URLEncoder.encode(parameter, "UTF-8");

            results += (results != "" ? "&" : "") + entry.getKey() + "=" + encodedParameter;
        }

        return results;
    }

    public String screenshotUrl(Map<String, Object> options) throws Exception {
        String parameters = this.parameterString(options);
        String token = calculateRFC2104HMAC(parameters, this.apiSecret);
        String result = String.format("https://api.screenshots.cloud/v1/screenshot/%s/%s?%s", this.apiKey, token, parameters);
        return result;
    }
}