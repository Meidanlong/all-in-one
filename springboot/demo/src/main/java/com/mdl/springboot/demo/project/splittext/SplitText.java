package com.mdl.springboot.demo.project.splittext;


import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 拆分文字
 */
@Slf4j
public class SplitText {

    private final static int AUTOMATIC_CALCULATE_SCENE_COUNT = 0;
    private final static int EXPECT_WORD_COUNT_OF_SINGLE_SCENE = 100;
    private final static int GPT_MAX_TOKEN = (int) (500 * (3d/4d));
    private final static List<Character> END_MARKS = Arrays.asList('.', '。', '!', '！', '?', '？', '\n');
    private final static String NEW_LINE = "\n";

    public static List<String> splitToSceneTextList(String text){
        return splitToSceneTextList(text, AUTOMATIC_CALCULATE_SCENE_COUNT);
    }

    public static List<String> splitToSceneTextList(String text, Integer expectSceneCount){
        // 1、第一次扫描文本->获取文章拆分最佳字数和句子数
        List<SentenceDetail> sentenceDetailList = preProcessor(text);
        Integer totalWordCount = sentenceDetailList.stream().mapToInt(SentenceDetail::getWordCount).sum();
        // expectSceneCount 动态计算
        if(expectSceneCount == null || expectSceneCount <= 0){
            expectSceneCount = totalWordCount/EXPECT_WORD_COUNT_OF_SINGLE_SCENE;
            if(expectSceneCount == 0){
                // 兜底
                expectSceneCount = 1;
            }
        }
        int maxWordCount = expectMaxSceneWordCount(totalWordCount, expectSceneCount);
        Integer totalSentenceCount = sentenceDetailList.size();
        int maxSentenceCount = expectMaxSceneSentenceCount(totalSentenceCount, expectSceneCount);
        log.info("===[SplitText#splitToSceneTextList] - expectSceneCount={}, totalWordCount={}, maxWordCount={}, totalSentenceCount={}, maxSentenceCount={}", expectSceneCount, totalWordCount, maxWordCount, totalSentenceCount, maxSentenceCount);
        // 2、扫描预处理结果对文本进行拆分
        return postProcessor(sentenceDetailList, maxSentenceCount, maxWordCount);
    }

    private static List<SentenceDetail> preProcessor(String text){
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

    private static List<String> postProcessor(List<SentenceDetail> list, int maxSentenceCount, int maxWordCount){
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
        // todo 中间段落补偿
        //1. 比较headList和tailList长度，长的说明多一个段落
        //2. 多的段落和另外一个list的最后一个段落进行组合
        //3. 如果字数小于gpt的token限制，则组合成为一段
        Collections.reverse(tailList);
        headList.addAll(tailList);
        return headList;
    }

    private static int expectMaxSceneWordCount(Integer totalWordCount, Integer expectSceneCount){
        return Math.min((totalWordCount/expectSceneCount)+1, GPT_MAX_TOKEN);
    }

    private static int expectMaxSceneSentenceCount(Integer totalSentenceCount, Integer expectSceneCount){
        return (totalSentenceCount/expectSceneCount)+1;
    }

    private static boolean isEndOfSentence(char c) {
        return END_MARKS.contains(c);
    }


    @Data
    static class SentenceDetail{
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
        String text = "苏佳慧是一个责编，她管理的一个作家写的小说改了好几遍她都不满意，于是亲自执笔改动。她的笔下，22岁的苏佳慧是名活力女厨师，就在她经过努力进入米其林那天，厨房发生连翻意外，苏佳慧来到周朝同样名为苏佳慧的妃子体内。\n" +
                "古代的苏佳慧，性格温婉娴静，中书令苏华严的嫡孙女，苏家与二皇子周承佑联姻，顺利助其登上帝位，苏家也本将成为周朝第一世家，奈何苏华严与嫡子苏继福意外身亡，苏家之势一朝崩塌，而封后在即的苏佳慧也暂缓封后，新帝周承佑原本喜欢的商贾女子赵婉儿，与苏家联姻迎娶苏佳慧也是其母德妃娘娘一手安排，周承佑与赵婉儿便无疾而终。苏家式微，新晋兵部尚书玉家如日中天，玉家女玉漱妃更有上位可能，苏佳慧的处境岌岌可危。\n" +
                "现代的苏佳慧醒来后，发现睡在镇边王周承乾的身边，苏佳慧的思维还停留在穿越带来的巨大惊愕中，侍女秋歌便匆忙闯入，说玉漱妃领着皇上向这边而来，该如何是好。苏佳慧还不知事态危急，周承乾淡然表示妃子出现在王爷的床榻之上，也不过人头落地而已，但他手握天下兵权，皇帝也不敢轻易动他，所以人头落地的只能是苏佳慧。苏佳慧此时并不慌乱，她已经死过一次，也不是真实的古代苏佳慧，她心态比较淡然，苏佳慧一把抱住承乾揩油，要在死之前享回艳福，因为她认为周承乾是她见过的第一美男子。苏佳慧的行为让周承乾发蒙，此时玉漱妃领着皇帝等人来到寝殿外，一名宫女确定着看见苏佳慧醉醺醺的进了此处宫殿。\n" +
                "然而最终，玉漱妃的人并没有在寝殿中搜到苏佳慧，那名宫女也因故意侮辱妃子名声而杖毙，周承乾借机嘲讽了承佑一番，原来苏佳慧已经被他从密道送走。\n" +
                "苏佳慧在回自己居住的莲华宫途中，古代苏佳慧的记忆进入她的记忆中，弄得她有些眩晕，但更令她瞠目的是皇帝周承佑此时已等在莲华宫中，周承佑不管三七二十一抱着苏佳慧丢到床榻上，粗鲁的对待她，原本周承佑认为苏佳慧会惊惧会生气，苏佳慧的反应确是欢喜得紧，原来这周承佑又是一极品美男子，苏佳慧的反应让周承佑觉得自己不会了。周承佑拿出一只香囊，质问他送给她的香囊怎会出现在周承乾的寝殿中，苏佳慧表示不小心弄掉的，周承佑负气而去，搞不清状况的苏佳慧，觉得这些古代的帅哥们脾气是一个比一个怪，但她并没往心里去，既然上天让她重活一回，她必然要活得多姿多彩……\n" +
                "然而，第二天周承佑就下旨将她禁足，任何人不得接近莲华宫，苏佳慧被变相打入冷宫，玉漱妃以为自己计谋得逞，后位即将到手。\n" +
                "出人意料得是，皇帝下的禁令，苏佳慧根本不在乎，嘴馋的她开始游串在宫中寻找美食材料，太后最喜爱的七色花也被她摘走，玉漱妃得知这个消息之后，一边认为苏佳慧脑子出了问题，一边又心中暗爽，苏佳慧越闹，倒得就越快。\n" +
                "周承佑得知此事被气得七窍生烟，太后领着后宫掌刑司的人直接前往莲华宫，此时的苏佳慧则在安静的做着糕点，面对兴师动众的一群人泰然处之，最终七色花糕俘获了太后的胃。太后更是当场撤了苏佳慧的禁足令。\n" +
                "一向严苛，又食欲不佳的太后被苏佳慧变着法的伺候着，太后就像换了一个人，整日闪耀着容光，孝顺的周承佑见状心中欢喜，对苏佳慧赐下诸多赏赐，二人间的互动也多了起来，每日一道菜品俘获帝王心，二人各种花式撒狗粮，苏佳慧心中也渐渐认可了周承佑。\n" +
                "苏式小厨房开课了，御膳房的厨子都前去听讲学习，苏佳慧也成了宫中另类红人，谁知一波未平，一波又起，玉漱妃让人在苏佳慧的食材中下毒，太后中毒卧床不起，矛头直指苏佳慧，苏佳慧被拘禁，周承佑以身试菜强力护妻，且拘禁中的苏佳慧恩宠不断，但被拘禁的苏佳慧误以为周承佑故意针对她，真相被查明，苏佳慧被释放之后，心生芥蒂。\n" +
                "苏佳慧带着侍女秋歌偷跑出宫游玩，遇险被匪徒所擒，苏佳慧机智脱险但仍被匪徒追踪，正巧被调查少女失踪案的玉面府尹楼玉岚所救，苏佳慧带着楼玉岚捣毁匪徒窝点，牵扯出某大臣拘禁少女供达官显贵作乐一事。此事幕后大佬正是玉漱妃之父兵部尚书玉衡，玉淑妃也在太监处得知皇帝已经签了封苏为后的文书，玉衡于是派人暗杀苏佳慧，危急关头周承乾英雄救美，苏佳慧表示宫中无趣事，周承乾说要带她去塞外领略风光，并成为他的女人，作为一个受过现代高等教育的她，在一番天人交战之后斩断了脚踏两只船的想法，拒绝了周承乾的建议。\n" +
                "苏佳慧和周承乾相处的这短暂时间发生了些暧昧的传闻，此事传到了周承佑耳中，醋意大发，不仅打消了准备封苏佳慧为后的打算，还下旨将‘初恋’赵婉儿迎入宫，封为嫔妃。然而他还是派人暗中保护并监视苏佳慧。与此同时，边疆起战事，西燕来犯，周承乾奉命御守。\n" +
                "苏佳慧得知周承乾纳初恋为妃，同样醋意大发，她不打算回宫，于是在京郊盘了一家店，开起了火锅客栈，生意出奇的好。楼玉岚主动派人维持秩序。周承佑带着贴身太监乔装打扮来到火锅客栈，品尝美味之后，心中醋意更胜，吩咐人来闹事，岂料楼玉岚很快将闹事者拆穿，周承乾又不想暴露身份掉价，此事只得暂时作罢。\n" +
                "周承乾一连数日盘桓于火锅客栈，其实早已被苏佳慧发现，苏佳慧在食材中做手脚让周承乾拉了几次肚子，但周仍天天来此，苏佳慧心有所动，在某种契机下，二人互相表明心迹重归于好。\n" +
                "周承佑为迎接北鲜使团特设国宴待之，国宴之上北鲜使者提出比三关，第一关为对联，北鲜以一副长联难住大周，最后苏佳慧一一副食物长联回对，受到众人追捧。第二关为骑术比赛，周承佑亲自上阵，力胜对方，然而第三关北鲜准备了三名大厨与大周的御厨比赛，御厨不敌而输掉了比赛，同时苏佳慧点评北鲜的大厨所做菜肴还有进步空间，北鲜大厨不服且讽刺苏佳慧，苏佳慧当场漏一手让北鲜大厨心悦诚服。\n" +
                "周承佑以立功为跳板，宣告天下立苏佳慧为后，一时间风起云涌，苏家已然式微，苏佳慧失了靠山还能被立为后，各宫嫔妃都有意巴结。依照律例，苏佳慧回家祭祖，心中不胜唏嘘，两世为人，直系亲属都已不在，她在墓园遇到一名老僧，老僧只言片语点出她的来历，她也说出了一直困扰的问题，她已经分不清自己是现代的苏佳慧还是古代的苏佳慧，老僧告诉她，人生就是一场梦，生命的尽头就是梦醒的时候，一场梦的结束，也是新的开始。苏佳慧若有所悟，决定珍惜眼前人。\n" +
                "另一边，玉漱妃怀恨在心，暗中怂恿单纯的赵婉儿对付苏佳慧，封后典礼前夕，太妃举办花朝会，玉漱妃打算在花朝会当天让苏佳慧当众落水，失去威仪无缘后位。苏佳慧事先准备了一批奶茶，在花朝节上受到追捧，\n" +
                "苏佳慧顺利封后，玉漱妃同时被封为四妃之一的淑妃，玉衡心有不甘，在周皇叔的拉拢下加入谋反派，积蓄力量试图谋朝篡位。\n" +
                "苏佳慧一想到皇帝能坐拥三宫六院，就十分不爽，自己的男人和她人分享实在过不去心中那道坎，近一段时间便对周承佑十分冷淡，周承佑看出苗头，便提议带着苏佳慧去江南微服私访，同时他也刚好要调查江南吏治问题，苏佳慧玩心大起，欣然应允。周皇叔等人见皇帝下江南，江南更是玉衡的老巢，遂开始谋划。\n" +
                "一路上，苏佳慧就地取材，烹制出很多美食让周承佑赞不绝口，夫妻二人更是恩爱日盛。江南歌姬颇负盛名，周承佑等人接连数日流连忘返，苏佳慧心中不悦，遂女扮男装游戏青楼，乃发现周承佑等人游戏风尘只为探听消息，夫妻二人联手震动江南风月场，一些埋藏着的阴暗之事被抖露，然而江州知府贪赃枉法毅然将周承佑下狱，好在楼玉岚及时出现道出周承佑皇帝身份，但江州知府并未表现出多少敬畏之心，同时调派兵力将周的院落围困，灌以谋反势力之名。苏佳慧带着自己暗中发展的势力救援周承佑，且战且退逃往山谷，被玉衡派来的大军围困。玉衡封山月余，以为苏、周已粮草殆尽，岂料苏佳慧搞出简易‘士力架’，大大提升了军力，双方展开拉锯战。\n" +
                "另一边，周皇叔控制太后，宣告周承佑死亡消息，开始独揽朝政。而江南这边周承佑已岌岌可危，千钧一发之际，周承乾率精锐千里奇袭救出周承佑，并护送周承佑回京，周皇叔事迹败露，双方于皇城决战，在苏佳慧的协助下，周承佑大获全胜。事后，苏佳慧得知，周承乾御守西燕、周承佑微服江南都是为了吊出以周皇叔为首的反贼，然而人算不如天算，反贼部署周详且力量强大，若非苏佳慧插科打挥，后果难料。";
        List<String> sceneTextList = SplitText.splitToSceneTextList(text);
        System.out.println(JSON.toJSONString(sceneTextList));
    }
}
