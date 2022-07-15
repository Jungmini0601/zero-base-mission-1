package com.zerobase.kimjungmin.http;

import com.google.gson.JsonArray;
import com.zerobase.kimjungmin.json.JsonUtils;
import com.zerobase.kimjungmin.domain.WifiInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpClient {

    private final String baseUrl =
            "http://openapi.seoul.go.kr:8088/4f6f70726a6a756e35344970676d58/json/TbPublicWifiInfo/";
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final JsonUtils jsonUtils = new JsonUtils();

    public List<WifiInfo> getAllWifiInfo() throws Exception{
        int total = getListTotalCount();
        List<WifiInfo> wifiInfoList = new ArrayList<>();
        for (int start = 1; start <= total; start += 1000) {
            String url = baseUrl + start + "/" + (start + 999);
            Response response = sendRequest(url, okHttpClient);
            ResponseBody body = checkResponse(response);
            JsonArray rows = jsonUtils.getRows(body.string());

            for (int i = 0; i < rows.size(); i++) {
                WifiInfo wifiInfo = jsonUtils.wifiInfoFromJson(rows.get(i));
                wifiInfoList.add(wifiInfo);
            }
        }

        return wifiInfoList;
    }

    private ResponseBody checkResponse(Response response) throws Exception{
        if (!response.isSuccessful() || response.body() == null) throw new Exception("잘못된 응답!");
        return response.body();
    }

    private int getListTotalCount() throws Exception{
        String url = baseUrl + "1/5";
        Response response = sendRequest(url, okHttpClient);
        ResponseBody body = checkResponse(response);
        return jsonUtils.getTotal(body.string());
    }

    private Response sendRequest(String url, OkHttpClient okHttpClient) throws IOException {
        Request.Builder requestBuilder = new Request.Builder().url(url).get();
        Request request = requestBuilder.build();
        return okHttpClient.newCall(request).execute();
    }
}
