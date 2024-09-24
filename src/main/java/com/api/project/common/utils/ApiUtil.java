package com.api.project.common.utils;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ApiUtil {
    private static String telegramApi;
    private static String prefix;
    private static String ip;

    public static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(2000, TimeUnit.MILLISECONDS)
            .readTimeout(2000, TimeUnit.MILLISECONDS)
            .build();

    @Value("${telegram.url}")
    public void setTelegramApi(String val) {
        telegramApi = val;
    }

    @Value("${telegram.domain}")
    public void setPrefix(String val) {
        prefix = val;
    }

    @Value("${telegram.ip}")
    public void setIp(String val) {
        ip = val;
    }

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static void sendTelegram(String msg) {
        String ipText = "[" + ip + "]";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", prefix + " " + ipText + "\n" + "Netfly API 에서 에러가 발생했습니다."  +"\n" + msg);
        String json = jsonObject.toString();

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(telegramApi)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            // 요청 실행 후 응답 코드 확인
            log.info("Telegram 메시지 전송 응답 코드: {}", response.code());
        } catch (IOException e) {
            log.error("Exception : {}{}", e, Arrays.toString(e.getStackTrace()));
        }
    }
}
