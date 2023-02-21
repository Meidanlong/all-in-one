package com.meidl.springboot.wechat.service.workweixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mdl.common.utils.HttpUtil;
import com.meidl.springboot.wechat.domain.dto.DepartmentDTO;
import com.meidl.springboot.wechat.domain.dto.UserDTO;
import com.meidl.springboot.wechat.domain.enums.UserGenderEnum;
import com.meidl.springboot.wechat.domain.enums.UserStatusEnum;
import com.meidl.springboot.wechat.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/21 16:14
 */
@Slf4j
@Service
public class UserService {

    private final static String userDetailUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/get";
    private final static String userMobileUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserid?access_token=%s";
    private final static String batchDeleteUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token=%s";
    private final static Integer YES = 1;

    @Resource
    private AccessTokenService accessTokenService;

    /**
     * 获取用户详情
     *
     *  {@link <a href="https://developer.work.weixin.qq.com/document/path/90196"/a>}
     * @author meidanlong
     * @date 2023/2/21
     * @version 1.0.0
     * @param userId fieldDesc @required
     * @return UserDTO
     */
    public UserDTO getUserDetail(String userId){
        String accessToken = accessTokenService.getAccessToken();
        Map<String, String> param = new HashMap<>();
        param.put("access_token", accessToken);
        param.put("userid", userId);
        String result = HttpUtil.doGet(userDetailUrl, null, param);
        JSONObject jsonObject = ResultUtil.getResult(result, "企业微信获取成员信息");
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(jsonObject.getString("userid"));
        userDTO.setName(jsonObject.getString("name"));
        userDTO.setGender(UserGenderEnum.getEnum(jsonObject.getInteger("gender")));
        userDTO.setDirectLeaders(jsonObject.getJSONArray("direct_leader").toJavaList(String.class));
        userDTO.setMobile(jsonObject.getString("mobile"));
        userDTO.setEmail(jsonObject.getString("email"));
        userDTO.setAvatar(jsonObject.getString("avatar"));
        userDTO.setPosition(jsonObject.getString("position"));
        userDTO.setStatus(UserStatusEnum.getEnum(jsonObject.getInteger("status")));
        JSONArray departmentArray = jsonObject.getJSONArray("department");
        JSONArray theLeaderArray = jsonObject.getJSONArray("is_leader_in_dept");
        List<DepartmentDTO> departments = new ArrayList<>();
        if(departmentArray != null && !departmentArray.isEmpty() && theLeaderArray != null && !theLeaderArray.isEmpty()){
            Integer[] departmentArr = departmentArray.toArray(new Integer[departmentArray.size()]);
            Integer[] theLeaderArr = theLeaderArray.toArray(new Integer[theLeaderArray.size()]);
            for(int i=0; i<departmentArr.length; i++){
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setDepartmentId(departmentArr[i]);
                departmentDTO.setTheLeader(YES.equals(theLeaderArr[i]));
                departments.add(departmentDTO);
            }
        }
        userDTO.setDepartments(departments);
        return userDTO;
    }

    /**
     * 根据手机号获取用户详情
     *
     * @author meidanlong
     * @date 2023/2/21
     * @version 1.0.0
     * @param mobile fieldDesc @required
     * @return UserDTO
     */
    public UserDTO getUserDetailByMobile(String mobile){
        return getUserDetail(getUserIdByMobile(mobile));
    }

    /**
     * 批量删除用户
     *
     *  {@link <a href="https://developer.work.weixin.qq.com/document/path/90199"/a>}
     * @author meidanlong
     * @date 2023/2/21
     * @version 1.0.0
     * @param useridList fieldDesc @required
     * @return void
     */
    public void batchDeleteUsers(List<String> useridList){
        String accessToken = accessTokenService.getAccessToken();
        String url = String.format(userMobileUrl, accessToken);
        Map<String, List<String>> param = new HashMap<>();
        param.put("useridlist", useridList);
        HttpUtil.doPostJson(url, null, JSON.toJSONString(param));
    }

    /**
     * 通过手机号获取userid
     *
     *  {@link <a href="https://developer.work.weixin.qq.com/document/path/95402"/a>}
     * @author meidanlong
     * @date 2023/2/21
     * @version 1.0.0
     * @param mobile fieldDesc @required
     * @return String
     */
    private String getUserIdByMobile(String mobile){
        String accessToken = accessTokenService.getAccessToken();
        String url = String.format(userMobileUrl, accessToken);
        Map<String, String> param = new HashMap<>();
        param.put("mobile", mobile);
        String result = HttpUtil.doPostJson(url, null, JSON.toJSONString(param));
        JSONObject jsonObject = ResultUtil.getResult(result, "企业微信根据手机号获取userid");
        return jsonObject.getString("userid");
    }
}
