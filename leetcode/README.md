# 配置
## 文件名称
```text
$!velocityTool.camelCaseName(${question.titleSlug})
```

## 代码模版
```text
package com.meidl.leetcode.editor.cn;

// [${question.frontendQuestionId}] ${question.title}
public class $!velocityTool.camelCaseName(${question.titleSlug}){
  public static void main(String[] args) {
       Solution solution = new $!velocityTool.camelCaseName(${question.titleSlug})().new Solution();
  }
    
${question.code}
}
```