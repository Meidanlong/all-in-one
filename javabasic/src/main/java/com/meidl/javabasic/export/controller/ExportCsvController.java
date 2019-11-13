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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Meidanlong
 * @version 1.0
 * @date 2019/11/13 10:57
 */

@RestController
public class ExportCsvController {

    @GetMapping("exportcsv")
    public void csv(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String fileName = new FileName().getFileName(request, "测试数据.csv");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");

        LinkedHashMap<String, Object> header = new LinkedHashMap<>();
        LinkedHashMap<String, Object> body = new LinkedHashMap<>();
        header.put("1", "姓名");
        header.put("2", "年龄");
        List<LinkedHashMap<String, Object>> data = new ArrayList<>();
        body.put("1", "小明");
        body.put("2", "小王");
        data.add(header);
        data.add(body);
        data.add(body);
        data.add(body);
        FileCopyUtils.copy(ExportUtil.exportCSV(data), response.getOutputStream());
    }
}
