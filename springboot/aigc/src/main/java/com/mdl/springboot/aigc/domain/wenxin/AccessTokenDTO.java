package com.mdl.springboot.aigc.domain.wenxin;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONType;
import com.mdl.common.domain.BaseObject;
import lombok.Data;

@Data
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class AccessTokenDTO extends BaseObject {

    //{
    //  "refresh_token": "25.a497fe3b2ebc15d5ff7d3461db7879fd.315360000.2002672944.282335-32920967",
    //  "expires_in": 2592000,
    //  "session_key": "9mzdCynbgzQRWCM+KhIE535SxDHbbEtrUanR/kZO6pWcNcmkB+mHG4W6cmATY6BJ8ldhehJT1vdojDzF33Z0h4liPAqdsw==",
    //  "access_token": "24.01455200cd41d2fb8d6d94e016af1f15.2592000.1689904944.282335-32920967",
    //  "scope": "brain_rpc_ernievilg_v2 public brain_all_scope brain_ernievilg_txt2img wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test权限 vis-classify_flower lpq_开放 cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base smartapp_mapp_dev_manage iop_autocar oauth_tp_app smartapp_smart_game_openapi oauth_sessionkey smartapp_swanid_verify smartapp_opensource_openapi smartapp_opensource_recapi fake_face_detect_开放Scope vis-ocr_虚拟人物助理 idl-video_虚拟人物助理 smartapp_component smartapp_search_plugin avatar_video_test b2b_tp_openapi b2b_tp_openapi_online smartapp_gov_aladin_to_xcx",
    //  "session_secret": "be25eb4003b5fb8586c28d576d20dac7"
    //}

    private String refreshToken;
    private Integer expiresIn;
    private String sessionKey;
    private String accessToken;
    private String scope;
    private String sessionSecret;
}
