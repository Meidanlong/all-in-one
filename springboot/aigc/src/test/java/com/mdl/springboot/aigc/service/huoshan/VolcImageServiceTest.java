package com.mdl.springboot.aigc.service.huoshan;

import com.mdl.common.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author meidanlong
 * @date 2024年07月22日
 * @version: 1.0
 */
@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class VolcImageServiceTest {

    private final static String img_path = "src/test/java/com/mdl/springboot/aigc/service/huoshan/images/%s_%d.png";
    @Resource
    private IVolcImageService volcImageService;

    @Test
    public void test() {
        // 3d动画
//        String pre = "3D动画电影海报, 迪士尼动画风格, ";
        // 线稿
//        String pre = "b&w line drawing, no color, ";
        // 写实
//        String pre = "高分辨率4K照片，现实主义电影风格, ";
        // 动漫
//        String pre= "新海诚动画电影海报, 日本动漫风格, ";
        // 远景
//        pre += "long shot, ";
        // 中景
//        pre += "medium shot, ";
        // 近景
//        pre += "close-up shot, ";
        String prompt = "A young Chinese woman wearing a classic red team training uniform, black sports shorts, white sports shoes, and a white wristband stands in front of a ping pong table, swinging a paddle with sweat flying off her. The setting is a ping pong training hall with a blue floor and white walls adorned with several ping pong match posters. The atmosphere is intense and focused.";
//        String suf = " 画面要求人物面部清晰五官端正，不要出现扭曲。";
//        String suf = " THERE MUST BE NO BAD FACE OR BAD HAND OR DISTORTED FACIAL EXPRESSION OR REDUNDANT OR IRRELEVANT CHARACTERS OR ANY CONTRARY-TO-REALITY IN THE PICTURE!";
//        String roleAFace = "https://ts1.cn.mm.bing.net/th/id/R-C.301756a216f6dfd7fe0c32f5853383d6?rik=mtPhRcVGwG%2bTpA&riu=http%3a%2f%2fpic3.nipic.com%2f20090624%2f2896835_221801012_2.jpg&ehk=GbYy8yUcdyKNn4r%2bdWuaHECPuw5LaiIsJamyo%2bmeEMI%3d&risl=&pid=ImgRaw&r=0";
//        String roleBFace = "https://ts1.cn.mm.bing.net/th/id/R-C.c2932d6db8ff8f3b91e437ee0b6cc789?rik=z9EFf8Gr7DdObw&riu=http%3a%2f%2fwww.yzwhcm.com%2fuFile%2f4708%2fproduct%2f2012824192042200.jpg&ehk=7NxOExcVLi0eVFJUWvWG9zUWp8VuHa8XiOBn318UkVs%3d&risl=&pid=ImgRaw&r=0";
//        String roleCFace = "https://obj.pipi.cn/basicdatacrawler/basicdatacrawler//54ecded7c7e923b12dc7ed44268d926f106d4.png?imageMogr2/thumbnail/2500x2500%3E";
//        volcImageService.generateAnimeImageV1_3("3D, CG", prompt);
//        String base64 = volcImageService.generateImageV1_4(pre + prompt + suf);
        String base64 = volcImageService.generateImageV1_4(prompt);
        System.out.println("base64:");
        System.out.println(base64);
        FileUtil.saveBase64Image(base64, String.format(img_path, "generate", System.currentTimeMillis()));
//        System.out.println(base64);
//        base64 = volcImageService.enhancePhoto(base64);
//        FileUtil.saveBase64Image(base64, String.format(img_path, "enhance", System.currentTimeMillis()));
//        String base64 = volcImageService.outPainting(prompt, imgUrl);
//        FileUtil.saveBase64Image(base64, String.format(img_path, "outpainting", System.currentTimeMillis()));
//        List<String> roleFaces = new ArrayList<>(Arrays.asList(roleBFace));
//        String modelImage = "https://p9-aiop-sign.byteimg.com/tos-cn-i-vuqhorh59i/202407181542176540DE73E43C078986FF/output-image-0~tplv-vuqhorh59i-image.jpeg?rk3s=7f9e702d&x-expires=1721374952&x-signature=ddIZJ4TZTtJM%2F0PhfxSVLnllsXM%3D";
//        String base64 = volcImageService.faceSwap(modelImage, roleFaces);
//        System.out.println(base64);
//        volcImageService.generateImageAndSwapFace(prompt, roleFaces);
    }
}
