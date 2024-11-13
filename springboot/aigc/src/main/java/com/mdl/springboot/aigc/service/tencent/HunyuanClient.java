package com.mdl.springboot.aigc.service.tencent;

import com.tencentcloudapi.common.AbstractClient;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.hunyuan.v20230901.models.QueryHunyuanImageJobRequest;
import com.tencentcloudapi.hunyuan.v20230901.models.QueryHunyuanImageJobResponse;
import com.tencentcloudapi.hunyuan.v20230901.models.SubmitHunyuanImageJobRequest;
import com.tencentcloudapi.hunyuan.v20230901.models.SubmitHunyuanImageJobResponse;
import com.tencentcloudapi.hunyuan.v20230901.models.TextToImageLiteRequest;
import com.tencentcloudapi.hunyuan.v20230901.models.TextToImageLiteResponse;

/**
 * 腾讯混元客户端
 *
 * @author meidanlong
 * @date 2024年07月22日
 * @version: 1.0
 */
public class HunyuanClient extends AbstractClient {
    private static final String endpoint = "hunyuan.tencentcloudapi.com";
    private static final String service = "hunyuan";
    private static final String version = "2023-09-01";

    public HunyuanClient(Credential credential, String region) {
        this(credential, region, new ClientProfile());
    }

    public HunyuanClient(Credential credential, String region, ClientProfile profile) {
        super(HunyuanClient.endpoint, HunyuanClient.version, credential, region, profile);
    }

    /**
     * 混元生图接口基于混元大模型，将根据输入的文本描述，智能生成与之相关的结果图。分为提交任务和查询任务2个接口。
     * 提交任务：输入文本等，提交一个混元生图异步任务，获得任务 ID。
     * 查询任务：根据任务 ID 查询任务的处理状态、处理结果，任务处理完成后可获得生成图像结果。
     * 并发任务数（并发）说明：并发任务数指能同时处理的任务数量。混元生图默认提供1个并发任务数，代表最多能同时处理1个已提交的任务，上一个任务处理完毕后才能开始处理下一个任务。
     *
     * @param req QueryHunyuanImageJobRequest
     * @return QueryHunyuanImageJobResponse
     * @throws TencentCloudSDKException
     */
    public QueryHunyuanImageJobResponse QueryHunyuanImageJob(QueryHunyuanImageJobRequest req) throws TencentCloudSDKException {
        req.setSkipSign(false);
        return this.internalRequest(req, "QueryHunyuanImageJob", QueryHunyuanImageJobResponse.class);
    }

    /**
     * 混元生图接口基于混元大模型，将根据输入的文本描述，智能生成与之相关的结果图。分为提交任务和查询任务2个接口。
     * 提交任务：输入文本等，提交一个混元生图异步任务，获得任务 ID。
     * 查询任务：根据任务 ID 查询任务的处理状态、处理结果，任务处理完成后可获得生成图像结果。
     * 并发任务数（并发）说明：并发任务数指能同时处理的任务数量。混元生图默认提供1个并发任务数，代表最多能同时处理1个已提交的任务，上一个任务处理完毕后才能开始处理下一个任务。
     *
     * @param req SubmitHunyuanImageJobRequest
     * @return SubmitHunyuanImageJobResponse
     * @throws TencentCloudSDKException
     */
    public SubmitHunyuanImageJobResponse SubmitHunyuanImageJob(SubmitHunyuanImageJobRequest req) throws TencentCloudSDKException {
        req.setSkipSign(false);
        return this.internalRequest(req, "SubmitHunyuanImageJob", SubmitHunyuanImageJobResponse.class);
    }

    /**
     * 文生图轻量版接口根据输入的文本描述，智能生成与之相关的结果图。
     * 文生图轻量版默认提供3个并发任务数，代表最多能同时处理3个已提交的任务，上一个任务处理完毕后才能开始处理下一个任务。
     *
     * @param req TextToImageLiteRequest
     * @return TextToImageLiteResponse
     * @throws TencentCloudSDKException
     */
    public TextToImageLiteResponse TextToImageLite(TextToImageLiteRequest req) throws TencentCloudSDKException {
        req.setSkipSign(false);
        return this.internalRequest(req, "TextToImageLite", TextToImageLiteResponse.class);
    }

}
