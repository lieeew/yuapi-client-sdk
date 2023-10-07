package com.yupi.yuapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yupi.yuapiclientsdk.model.User;
import com.yupi.yuapiclientsdk.utils.SecretKeyUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leikooo
 * @create 2023-09-24 20:58
 * @Package com.yupi.yuapiinterface.client
 * @Description 模拟前端
 */
public class YupiApiClient {
    private final String secretKey;
    private final String accessKey;
    private final String interfaceInfoName;
    public static final String REMOTE_PORT = "http://localhost:8090";

    public YupiApiClient(String secretKey, String accessKey, String interfaceInfoName) {
        this.secretKey = secretKey;
        this.accessKey = accessKey;
        this.interfaceInfoName = interfaceInfoName;
    }

    private Map<String, String> getResponseMap(String body) {
        Map<String, String> map = new HashMap<>();
        map.put("accessKey", accessKey);
        // 一定不用直接发给后端！
        // map.put("secretKey", secretKey);
        map.put("body", body);
        map.put("nonce", RandomUtil.randomNumbers(4));
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("sign", SecretKeyUtils.getSign(body, secretKey));
        map.put("interfaceInfoName", interfaceInfoName);
        return map;
    }

    public String getNameByGet(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        // GET请求
        String content = HttpUtil.get(REMOTE_PORT + "/api", map);
        System.out.println("GET name = " + content);
        return content;
    }

    public String getNameByPost(String name) {
        // POST请求
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        String result = HttpUtil.post(REMOTE_PORT + "/api/name", map);
        System.out.println("POST result = " + result);
        return result;
    }

    public String getUsernameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse execute = HttpRequest.post(REMOTE_PORT + "/api/user")
                // 添加请求头
                .addHeaders(getResponseMap(json))
                .body(json)
                .execute();
        System.out.println(json);
        String body = execute.body();
        System.out.println("body = " + body);
        return body;
    }
}
