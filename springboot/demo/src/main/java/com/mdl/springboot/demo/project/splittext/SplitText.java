package com.mdl.springboot.demo.project.splittext;


import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * 拆分文字
 */
public class SplitText {

    private final static int DEFAULT_WORD_COUNT_STANDARD = 10;
    private final static int DEFAULT_SENTENCE_COUNT_STANDARD = 2;
    private final static int GPT_MAX_TOKEN = (int) (500 * (3d/4d));
    private final static List<Character> END_MARKS = Arrays.asList('.', '。', '!', '！', '?', '？', '\n');
    private final static String NEW_LINE = "\n";


    public List<String> splitToSceneTextList(String text, Integer expectSceneCount){
        // 1、第一次扫描文本->获取文章拆分最佳字数和句子数
        List<SentenceDetail> sentenceDetailList = preProcessor(text);
        Integer totalSentenceCount = sentenceDetailList.size();
        int maxSentenceCount = expectMaxSceneSentenceCount(totalSentenceCount, expectSceneCount);
        Integer totalWordCount = sentenceDetailList.stream().mapToInt(SentenceDetail::getWordCount).sum();
        int maxWordCount = expectMaxSceneWordCount(totalWordCount, expectSceneCount);
        // 2、扫描预处理结果对文本进行拆分
        return postProcessor(sentenceDetailList, maxSentenceCount, maxWordCount);
    }

    public List<SentenceDetail> preProcessor(String text){
        char[] textCharArray = text.toCharArray();
        List<SentenceDetail> sentenceList = new ArrayList<>();
        // 自然段
        int sectionNum = 1;
        // 句子拼接
        StringBuilder sentenceBuilder = new StringBuilder();
        // 句尾标识
        boolean sentenceEnd = false;
        for (char word : textCharArray){
            // 文字结尾：sentenceEnd == true && isEndOfSentence == false;
            if(sentenceEnd && !isEndOfSentence(word)){
                boolean sectionEnd = false;
                // 对上句话进行处理
                SentenceDetail sentenceDetail = new SentenceDetail();
                sentenceDetail.setSectionNum(sectionNum);
                String sentence = sentenceBuilder.toString();
                if(sentence.contains(NEW_LINE)){
                    sectionEnd = true;
                    sentence = sentence.replaceAll(NEW_LINE, Strings.EMPTY);
                }
                sentenceDetail.setSentence(sentence);
                sentenceDetail.setWordCount(sentence.length());
                sentenceList.add(sentenceDetail);
                // 重置字段
                if(sectionEnd){
                    sectionNum++;
                }
                sentenceBuilder = new StringBuilder();
                sentenceEnd = false;
            }
            if(isEndOfSentence(word)){
                sentenceEnd = true;
            }
            sentenceBuilder.append(word);
        }
        return sentenceList;
    }

    public List<String> postProcessor(List<SentenceDetail> list, int maxSentenceCount, int maxWordCount){
        int headIndex = 0, tailIndex = list.size()-1;
        List<String> headList = new ArrayList<>(), tailList = new ArrayList<>();
        while(headIndex < tailIndex){
            StringBuilder headScene = new StringBuilder();
            int headWordCount = 0,  headSentenceCount = 0;
            while(headSentenceCount<maxSentenceCount && headWordCount<maxWordCount){
                SentenceDetail sentenceDetail = list.get(headIndex);
                headWordCount += sentenceDetail.getWordCount();
                headSentenceCount += 1;
                headScene.append(sentenceDetail.sentence);
                headIndex++;
            }
            headList.add(headScene.toString());

            StringBuilder tailScene = new StringBuilder();
            int tailWordCount = 0, tailSentenceCount = 0;
            while(tailSentenceCount<maxSentenceCount && tailWordCount<maxWordCount){
                SentenceDetail sentenceDetail = list.get(tailIndex);
                tailWordCount += sentenceDetail.getWordCount();
                tailWordCount += 1;
                tailScene.append(sentenceDetail.sentence);
                tailIndex--;
            }
            tailList.add(tailScene.toString());
        }
        Collections.reverse(tailList);
        headList.addAll(tailList);
        return headList;
    }

    private int expectMaxSceneWordCount(Integer totalWordCount, Integer expectSceneCount){
        return Math.min((totalWordCount/expectSceneCount)+1, GPT_MAX_TOKEN);
    }

    private int expectMaxSceneSentenceCount(Integer totalSentenceCount, Integer expectSceneCount){
        return (totalSentenceCount/expectSceneCount)+1;
    }

    private static boolean isEndOfSentence(char c) {
        return END_MARKS.contains(c);
    }


    @Data
    class SentenceDetail{

        /**
         * 句子文本
         */
        private String sentence;
        /**
         * 句子字数
         */
        private int wordCount;
        /**
         * 归属自然段
         */
        private int sectionNum;
    }

    public static void main(String[] args) {
        SplitText splitText = new SplitText();
        String text = "郑能量经营着一间“郑能量烧烤店”，并靠一己之力经营了二十年，也是靠这间烧烤店养育了儿子郑言。如今郑言已经开了自己的公司，成为小有成就的老板，考虑到年迈父亲的身体健康，希望父亲放弃烧烤店，去海边的房子安享晚年。但父亲郑能量却怎么也不同意，并扬言除非烧烤店没有顾客了，否则不会放弃经营了这么多年的事业。孝顺的郑言不得不使用下策，启用公司求职者李炎燚，一个有着曾干黄5个项目的“傲人\n" +
                "履历”的人，如果他能够用正当商业手段抢走“郑能量烧烤”的顾客，就同意他入职。\n" +
                "“郑能量烧烤“虽店面不大，但依靠着多年的口味和口碑，已然成为了有名气的网红店铺，每天生意都很火爆。李炎燚首先找到了”郑能量烧烤街对面的“刘小天烧烤”，一个依靠着“郑能量烧烤”排队捡漏存活的小店。李炎燚与刘小天达成共识，并很快启动了一系列营销手段，打价格战、将小吃街火爆的招牌小吃腰子姐、生蚝王等收入麾下，确实抢来了不少客人，眼见顾客一天天变少，郑能量不服，也开始打价格战，将老朋友田螺叔召回整特色。价格战拉锯导致郑能量将所有积蓄拿出还入不敷出，但还是不服，去找儿子郑言借钱要继续，郑言秘书将计就计跟老爷子编造公司运营出现危机，希望能刺激老爷子放弃烧烤店。郑能量心疼儿子，果然放弃了开店，并将店外兑的所有钱都留给了儿子郑言。\n" +
                "谁知“郑能量烧烤”关门前的最后一桌客人给郑能量录制的视频在网上疯传，视频中郑能量道出了烧烤的温暖、对烧烤店的不舍，对儿子的体谅和抱歉，看着关门后的父亲每日落寞与孤独，郑言决定将李炎燚已经盘活的“刘小天烧烤”继续经营下去，由李炎燚和刘小天负责，并将腰子姐、生蚝王都召回，郑能量担任菜品总监，生意越做越红火，越来越壮大，最后正式更名，烧烤之王。";
        List<String> sceneTextList = splitText.splitToSceneTextList(text, 5);
        System.out.println(JSON.toJSONString(sceneTextList));
    }
}
