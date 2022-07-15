package com.zerobase.kimjungmin.json;

import com.google.gson.*;
import com.zerobase.kimjungmin.domain.WifiInfo;

public class JsonUtils {
    public JsonArray getRows(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonObject result = jsonObject.getAsJsonObject("TbPublicWifiInfo");
        return result.get("row").getAsJsonArray();
    }

    public int getTotal(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonObject result = jsonObject.getAsJsonObject("TbPublicWifiInfo");
        return result.get("list_total_count").getAsInt();
    }

    public WifiInfo wifiInfoFromJson(JsonElement json) {
        Gson gson = new Gson();
        return gson.fromJson(json, WifiInfo.class);
    }
}
