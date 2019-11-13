package com.meidl.javabasic.export.controller;

import com.meidl.javabasic.export.util.ExportUtil;
import com.meidl.javabasic.export.util.FileName;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author Meidanlong
 * @version 1.0
 * @date 2019/11/13 10:53
 */

@RestController
public class ExportXlsxController {

    @GetMapping("/exportxlsx")
    public void xlsx(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String fileName = new FileName().getFileName(request, "测试数据.xlsx");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");

        List<LinkedHashMap<String, Object>> datas = new ArrayList<>();
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("1", "姓名");
        data.put("2", "年龄");
        datas.add(data);
        for (int i = 0; i < 5; i++) {
            data = new LinkedHashMap<>();
            data.put("1", "小青");
            data.put("2", "小白");
            datas.add(data);
        }

        Map<String, List<LinkedHashMap<String, Object>>> tableData = new HashMap<>();
        tableData.put("日报表", datas);
        tableData.put("周报表", datas);
        tableData.put("月报表", datas);

        FileCopyUtils.copy(ExportUtil.exportXlsx(tableData), response.getOutputStream());
    }
}
