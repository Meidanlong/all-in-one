package com.mdl.springboot.demo.service.payment;

import com.alibaba.fastjson.JSON;
import com.mdl.common.utils.HttpUtil;
import lombok.Builder;
import lombok.Data;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 针对埃及市场调研D-Local支付方式
 *
 * @author meidanlong
 * @date 2025年05月23日
 * @version: 1.0
 */
public class DLocalService {

    private static final String API_URL = "https://sandbox.dlocal.com/api_curl/cashout_api/request_cashout";
    private static final String QUERY_PAYOUT_URL = "https://sandbox.dlocal.com/payouts";
    private static final String LOGIN = "DPDxXcXPl8";
    private static final String TRANS_KEY = "hgNcaplLSf";
    private static final String SECRET_KEY = "aJtt6FI2ClYv02ULMZUfqtC9Km1CIgUFE";

    // Method to generate HMAC-SHA256 signature
    public static String generateHMACSHA256Signature(String payload, String secretKey) throws Exception {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hashBytes = mac.doFinal(payloadBytes);

        StringBuilder hashString = new StringBuilder();
        for (byte b : hashBytes) {
            hashString.append(String.format("%02x", b));
        }
        return hashString.toString();
    }

    public static Long cashOut(CashoutRequest cashoutRequest) {
        try {
            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("login", LOGIN);
            requestBody.put("pass", TRANS_KEY);
            requestBody.put("external_id", cashoutRequest.getExternalId());
            requestBody.put("beneficiary_name", cashoutRequest.getBeneficiaryName());
            requestBody.put("beneficiary_lastname", cashoutRequest.getBeneficiaryLastname());
            requestBody.put("country", cashoutRequest.getCountry());
            requestBody.put("phone", cashoutRequest.getPhone());
            requestBody.put("amount", cashoutRequest.getAmount());
            requestBody.put("email", cashoutRequest.getEmail());
            requestBody.put("currency", cashoutRequest.getCurrency());
            requestBody.put("account_type", cashoutRequest.getAccountType());
            requestBody.put("notification_url", "https://thisisawebsite.net/payments");
            requestBody.put("type", "json");

            // 转换为JSON
            String requestPayloadJSON = JSON.toJSONString(requestBody);

            // 生成签名
            String signature = generateHMACSHA256Signature(requestPayloadJSON, SECRET_KEY);

            // 获取UTC时间戳
            String timestamp = ZonedDateTime.now(ZoneOffset.UTC)
                    .format(DateTimeFormatter.RFC_1123_DATE_TIME);

            // 设置请求头
            Map<String, String> headers = HttpUtil.commonHeaders();
            headers.put("X-Date", timestamp);
            headers.put("X-Login", LOGIN);
            headers.put("X-Trans-Key", TRANS_KEY);
            headers.put("payload-signature", signature);

            // 发送请求
            String response = HttpUtil.doPostJson(API_URL, headers, requestPayloadJSON);

            // 解析响应
            CashoutResponse cashoutResponse = JSON.parseObject(response, CashoutResponse.class);

            // 打印响应
            System.out.println("Payment request successful!");
            System.out.println("Response: " + response);

            return cashoutResponse.getCashoutId();

        } catch (Exception e) {
            System.err.println("Payment request failed: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static PayoutQueryResponse queryPayout(String payoutId, String externalId) {
        try {
            // 构建查询参数
            Map<String, String> params = new HashMap<>();
            if (payoutId != null) {
                params.put("payout_id", payoutId);
            }
            if (externalId != null) {
                params.put("external_id", externalId);
            }

            // 获取UTC时间戳
            String timestamp = ZonedDateTime.now(ZoneOffset.UTC)
                    .format(DateTimeFormatter.RFC_1123_DATE_TIME);

            // 设置请求头
            Map<String, String> headers = HttpUtil.commonHeaders();
            headers.put("X-Date", timestamp);
            headers.put("X-Login", LOGIN);
            headers.put("X-Trans-Key", TRANS_KEY);

            // 发送GET请求
            String response = HttpUtil.doGet(QUERY_PAYOUT_URL, headers, params);

            System.out.println("response: " + JSON.toJSONString(response, true));

            // 解析响应
            return JSON.parseObject(response, PayoutQueryResponse.class);

        } catch (Exception e) {
            System.err.println("Query payout failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
//        String externalId = System.currentTimeMillis() + "";
//        System.out.println("externalId: "+externalId);
//        CashoutRequest request = CashoutRequest.builder()
//                .externalId(externalId)
//                .beneficiaryName("KARIM")
//                .beneficiaryLastname("Ahan")
//                .country("EG")
//                .phone("+201014948646")
//                .amount("245.40")
//                .email("karim.ahan@gmail.com")
//                .currency("EGP")
//                .accountType("Orange")
//                .build();
//
//        Long cashOutId = cashOut(request);
//        PayoutQueryResponse response = queryPayout(cashOutId + "", request.getExternalId());
        queryPayout("1575071", "1747993605216");

    }


}

@Data
@Builder
class CashoutRequest {
    private String externalId;
    private String beneficiaryName;
    private String beneficiaryLastname;
    private String country;
    private String phone;
    private String amount;
    private String email;
    private String currency;
    private String accountType;
}

@Data
class CashoutResponse {
    private Long cashoutId;
    private String desc;
    private Integer status;

    // FastJSON 需要的字段别名
    public void setCashout_id(Long cashoutId) {
        this.cashoutId = cashoutId;
    }
}

@Data
@Builder
class PayoutQueryResponse {
    private String statusDescription;
    private Integer statusCode;
    private Integer errorCode;
    private String errorDescription;
    private String externalId;
    private Integer payoutId;
    private Double amount;
    private String currency;
    private String creationDate;
    private String country;
    private Double exchangeRate;
    private Map<String, Object> amountDetails;
    private Map<String, Object> fees;
    private BeneficiaryInfo beneficiary;
}

@Data
@Builder
class BeneficiaryInfo {
    private String documentId;
    private String documentType;
    private String beneficiaryName;
    private String beneficiaryLastname;
    private String phone;
    private String email;
}
