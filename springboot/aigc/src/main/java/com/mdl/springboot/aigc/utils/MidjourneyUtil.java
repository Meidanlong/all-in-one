package com.mdl.springboot.aigc.utils;

public class MidjourneyUtil {

    private static final String PROMPT_MARKER = "**";
    private static final String VERSION_MARKER = " --v";
    private static final String SPACE = " ";
    private static final String DOUBLE_SPACE = SPACE + SPACE;

    /**
     * 从MJ消息中获取原prompt
     * @param messageContent
     * @param removeVersion
     * @return
     */
    public static String getReturnPrompt(String messageContent, boolean removeVersion){
        String resultPrompt = messageContent.substring(messageContent.indexOf(PROMPT_MARKER) + PROMPT_MARKER.length(), messageContent.lastIndexOf(PROMPT_MARKER));
        if(removeVersion){
            int vIndex = resultPrompt.indexOf(VERSION_MARKER);
            if(vIndex != -1) {
                resultPrompt = resultPrompt.substring(0,vIndex);
            }
        }
        return resultPrompt;
    }

    /**
     * 格式化prompt
     * @param prompt
     * @return
     */
    public static String formatPrompt(String prompt){
        return prompt.replaceAll(DOUBLE_SPACE, SPACE).trim();
    }
}
