package com.mdl.springboot.aigc.controller;

import com.mdl.common.domain.BaseResponse;
import com.mdl.springboot.aigc.service.IMidjourneyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * midjourney控制器
 *
 * @author meidanlong
 * @date 2023-05-15T14:59:56
 **/
@RestController
@RequestMapping("/midjourney")
public class MidjourneyController {
    @Resource
    private IMidjourneyService midjourneyService;

    @RequestMapping(value = "/imagine", method = {RequestMethod.POST})
    public BaseResponse generateImage(@RequestParam String promptText) {
        return BaseResponse.success(midjourneyService.generateImage(promptText));
    }
}
