package com.mdl.springboot.aigc.service.tencent;

import com.alibaba.fastjson.JSON;
import com.mdl.common.domain.BusinessException;
import com.mdl.common.enums.ErrorEnum;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.hunyuan.v20230901.models.QueryHunyuanImageJobRequest;
import com.tencentcloudapi.hunyuan.v20230901.models.QueryHunyuanImageJobResponse;
import com.tencentcloudapi.hunyuan.v20230901.models.SubmitHunyuanImageJobRequest;
import com.tencentcloudapi.hunyuan.v20230901.models.SubmitHunyuanImageJobResponse;
import com.tencentcloudapi.hunyuan.v20230901.models.TextToImageLiteRequest;
import com.tencentcloudapi.hunyuan.v20230901.models.TextToImageLiteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 腾讯混元
 *
 * @author meidanlong
 * @date 2024年07月22日
 * @version: 1.0
 */
@Slf4j
@Service
public class HunyuanService {

    private static final String region = "ap-guangzhou";
    // 获取方式文档：https://cloud.tencent.com/document/product/382/43194

    @Value("${hunyuan.secret_id}")
    private String SECRET_ID;
    @Value("${hunyuan.secret_key}")
    private static String SECRET_KEY;

    /**
     * 文生图轻量版接口根据输入的文本描述，智能生成与之相关的结果图。
     * 文生图轻量版默认提供3个并发任务数，代表最多能同时处理3个已提交的任务，上一个任务处理完毕后才能开始处理下一个任务。
     *
     * @param req TextToImageLiteRequest
     * @return TextToImageLiteResponse
     * @throws TencentCloudSDKException
     */
    public TextToImageLiteResponse TextToImageLite(TextToImageLiteRequest req) {
        try {
            return getHunyuanClient().TextToImageLite(req);
        } catch (TencentCloudSDKException e) {
            throw new BusinessException(ErrorEnum.SYSTEM_ERROR, e);
        }
    }

    public List<String> textToImageJob(SubmitHunyuanImageJobRequest req) {
        try {
            SubmitHunyuanImageJobResponse submitResp = getHunyuanClient().SubmitHunyuanImageJob(req);
            if (submitResp == null) {
                throw new BusinessException(ErrorEnum.SYSTEM_ERROR, "混元生图提交任务异常");
            }
            log.info(JSON.toJSONString(submitResp));
            while (true) {
                QueryHunyuanImageJobRequest queryJobReq = new QueryHunyuanImageJobRequest();
                queryJobReq.setJobId(submitResp.getJobId());
                QueryHunyuanImageJobResponse queryResp = getHunyuanClient().QueryHunyuanImageJob(queryJobReq);
                if (queryResp == null) {
                    throw new BusinessException(ErrorEnum.SYSTEM_ERROR, "混元生图查询任务异常");
                }
                log.info(JSON.toJSONString(queryResp));
                if ("4".equals(queryResp.getJobStatusCode())) {
                    // 失败
                    throw new BusinessException(ErrorEnum.SYSTEM_ERROR, queryResp.getJobErrorMsg());
                }
                if ("5".equals(queryResp.getJobStatusCode())) {
                    // 成功
                    return new ArrayList<>(Arrays.asList(queryResp.getResultImage()));
                }
                TimeUnit.SECONDS.sleep(3);
            }
        } catch (TencentCloudSDKException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private HunyuanClient getHunyuanClient() {
        Credential credential = new Credential(SECRET_ID, SECRET_KEY);
        return new HunyuanClient(credential, region);
    }

}
