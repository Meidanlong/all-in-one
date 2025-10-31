package com.mdl.springai.mcp.server.tools;

import com.alibaba.fastjson.JSON;
import com.mdl.common.utils.HttpUtil;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 电影工具
 *
 * @author meidanlong
 * @date 2025年08月14日
 * @version: 1.0
 */
@Slf4j
@Component
public class MovieTool {

    private static final String MOVIE_API_URL = "https://maliang.st.maoyan.com/chatglm/movie/autoCartesian/daily/boxRank/mcp?token=aigc-maliang-mcp";

    @Data
    @ToString
    public static class MovieTargetRequest {
        // 必填参数
        @ToolParam(description = "结合上下文历史消息转换用户想询问的精确问题，该参数提取结果不能为空！要求：1、问题中存在模糊的信息要求精确化，是一句有主谓宾的完整的问题，尤其要明确主语是什么！2、转换后的问题应包含解析出来的其他参数provinces、cities、cityTiers、dates、schedule的结果。示例1：已知今天是2024年4月1号，问明天的票房是多少？则应该转化问题为：20240402的票房是多少？示例2：用户在和你聊电影《哈利波特》，问题是“这部电影评价怎样？”，则应该转换为：《哈利波特》评价怎样？示例3:用户说“好的/我知道了”等，说明次轮对话告一段落，则不需要转换问题，直接返回用户的原始对话。")
        private String userIntent;
        @ToolParam(description = "结合上下文历史消息提取问题中查询电影的指标，问题中可能存在多个电影指标，请依次返回。例如“日票房,上座率”。" +
                "全部电影指标列表为：[\"当日票房\",\"累计票房\",\"预测票房\",\"场次\",\"票房占比\",\"排片占比\",\"上座率\",\"场均人次\",\"场均人数\",\"黄金场次占比\",\"黄金场次\",\"观影人次\",\"观影人数\",\"平均票价\",\"人次占比\",\"排座占比\",\"网售票房占比\",\"网售人次占比\",\"大盘退票人次\",\"大盘退票率\",\"排场效益(B值)\",\"一线城市票房占比\",\"二线城市票房占比\",\"三线城市票房占比\",\"四线城市票房占比\"]。注意：如果用户提起票房且明确了某一天则应选择该指标，否则应选择“累计票房”"
        )
        private String target;
        @ToolParam(description = "结合上下文历史消息提取问题中的时间纬度，默认是“day”（天纬度），其他可选纬度有：“week”（周纬度）、“month”（月纬度）、“schedule”（档期纬度）、“year”（年纬度）。要求仅返回时间纬度对应的英文，不要返回不在要求内的英文或其他文字。")
        private String dateType;

        // 选填参数
        @ToolParam(description = "结合上下文历史消息提取问题中的电影名称，问题中可能存在多个电影名称，请依次返回。例如：“碟中谍7（上）,哈利波特与死亡圣器”",
                required = false)
        private String movies;
        @ToolParam(description = "结合上下文历史消息提取问题中的电影档期，问题中可能存在多个档期，请按照“年份#档期名”的格式依次返回。例如“2024#跨年档,2023#国庆档”。注意年份为yyyy数字不可缺失，默认为今年",
                required = false)
        private String schedule;
        @ToolParam(description = "结合上下文历史消息提取问题中的开始日期和结束日期，问题中可能存在多个时间范围，按照参数“dateType”（时间纬度）进行划分（如：dateType=year且你判断开始日期至结束日期为20230101-20241231，则应该返回“20230101-20231231,20240101-20241231”。其他时间纬度以此类推）。该参数不可为空，默认为今天日期。若问题中只有一个时间信息，则开始日期等于结束日期。若用户问的是某个月份，则开始日期应为该月份第一天的日期。如用户问的是某一年，则开始日期应为该年份第一天的日期。请以“yyyyMMdd”的格式返回，例如“20240401-20240402,20240501-20240505”",
                required = false)
        private String dates;
        @ToolParam(description = "结合上下文历史消息提取问题中的省份，问题中可能可以提取多个省份，请依次返回。例如“北京,上海”。如果问题中存在省份简称，要求返回该省份全称。如问题中出现“京津冀”，应返回“北京,天津,河北”",
                required = false)
        private String provinces;
        @ToolParam(description = "结合上下文历史消息提取问题中城市，问题中可能可以提取多个城市，请依次返回。例如“北京,上海”。如果问题中存在城市简称，要求返回该城市全称。如问题中出现“京沪”，应返回“北京,上海”",
                required = false)
        private String cities;
        @ToolParam(description = "结合上下文历史消息提取问题中城市等级，问题中可能可以提取多个城市等级，请依次返回。如问题中出现“一二线城市”，应拆分为“一线城市,二线城市”",
                required = false)
        private String cityTiers;
    }

    @Tool(description = "电影指标查询详情首选工具")
    public String getMovieTargetData(MovieTargetRequest request) {
        log.info("========== getMovieTargetData request:{}==========", JSON.toJSONString(request));
        // 构建请求参数并调用API
        Map<String, String> params = buildRequestParams(request);
        String movieData = HttpUtil.doPostJson(MOVIE_API_URL, null, JSON.toJSONString(params));
        log.info("========== getMovieTargetData movieData:{}==========", movieData);
        return movieData;
    }

    /**
     * 构建请求参数，移除空值
     */
    private Map<String, String> buildRequestParams(MovieTargetRequest request) {
        Map<String, String> params = JSON.parseObject(JSON.toJSONString(request), Map.class);
        params.entrySet().removeIf(entry ->
                entry.getValue() == null || entry.getValue().trim().isEmpty());
        return params;
    }
}
