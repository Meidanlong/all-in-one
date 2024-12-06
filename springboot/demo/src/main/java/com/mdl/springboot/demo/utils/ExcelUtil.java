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
 * CSV/Excel工具类
 *
 * CSV需保证一列内无英文逗号，否则解析出错
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

}
