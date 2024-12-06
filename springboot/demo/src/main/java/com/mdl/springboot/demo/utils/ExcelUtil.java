package com.mdl.springboot.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author meidanlong
 * @date 2024年12月05日
 * @version: 1.0
 */
public class ExcelUtil {
    private final static String CSV_SPLIT_BY = ",";

    public static JSONArray readCsv(String csvName){
        JSONArray jsonArray = new JSONArray();

        String line;
        List<String> headers = new ArrayList<>();
        try (InputStream inputStream = ExcelUtil.class.getClassLoader().getResourceAsStream(csvName);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            // 读取第一行(列名)
            if ((line = br.readLine()) != null) {
                String[] headerArray = line.split(CSV_SPLIT_BY);
                for (String header : headerArray) {
                    headers.add(header.trim());
                }
            }

            // 读取数据行
            while ((line = br.readLine()) != null) {
                String[] values = line.split(CSV_SPLIT_BY);
                JSONObject jsonObject = new JSONObject();

                for (int i = 0; i < headers.size(); i++) {
                    if (i < values.length) {
                        jsonObject.put(headers.get(i), values[i].trim());
                    } else {
                        jsonObject.put(headers.get(i), "");
                    }
                }

                jsonArray.add(jsonObject);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public static JSONArray subtractJsonArrays(JSONArray whiteJson, JSONArray applyJson) {
        Set<String> applyUserIds = new HashSet<>();
        for (int i = 0; i < applyJson.size(); i++) {
            JSONObject applyObj = applyJson.getJSONObject(i);
            applyUserIds.add(applyObj.getString("user_id"));
        }

        JSONArray resultArray = new JSONArray();
        for (int i = 0; i < whiteJson.size(); i++) {
            JSONObject whiteObj = whiteJson.getJSONObject(i);
            String myUserId = whiteObj.getString("my_user_id");
            if (!applyUserIds.contains(myUserId) && !myUserId.equals("0")) {
                resultArray.add(whiteObj);
            }
        }

        return resultArray;
    }

    public static String generateBatchInsertSQL(JSONArray result) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO agi_test_apply_record (")
                .append("user_id, phone, career, use_purpose, team_name, ")
                .append("influential_personal_account, passed, deleted, create_time, update_time")
                .append(") VALUES ");

        for (int i = 0; i < result.size(); i++) {
            JSONObject obj = result.getJSONObject(i);
            Long userId = obj.getLong("my_user_id");
            // 跳过 user_id 为 '0' 的记录
            if (userId <= 0) {
                continue;
            }
            String name = obj.getString("name").replace(",", "");
            sql.append("(")
                    .append(userId).append(", ")
                    .append("AES_ENCRYPT('").append(obj.getString("phone")).append("', 'soda'), ")
                    .append("'影视导演、编剧', ")
                    .append("'制作分镜/故事板，提升项目各方沟通效率', ")
                    .append("'").append(name).append("', ")
                    .append("'原白名单用户', ")
                    .append("1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP")
                    .append(")");

            if (i < result.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(";");

        return sql.toString();
    }

    public static void main(String[] args) {
        JSONArray whiteJson = ExcelUtil.readCsv("1733386279014.csv");
        JSONArray applyJson = ExcelUtil.readCsv("1733389294447.csv");

        JSONArray result = subtractJsonArrays(whiteJson, applyJson);
//        System.out.println(result.size());
//        System.out.println(JSON.toJSONString(result, true));
        List<String> nameList = result.stream().map(r -> {
            JSONObject js = (JSONObject) r;
            return js.getString("name");
        }).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(nameList));
        String batchInsertSQL = generateBatchInsertSQL(result);
        System.out.println(batchInsertSQL);
    }

}
