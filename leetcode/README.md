# 配置
## 文件名称
```text
$!velocityTool.camelCaseName(${question.titleSlug})
```

## 代码模版
```text
package com.meidl.leetcode.editor.cn;
import com.meidl.leetcode.editor.cn.helper.ListNode;
import com.meidl.leetcode.editor.cn.helper.TreeNode;
import java.util.List;

${question.code}
```

### 模版常量
```text
${question.title}	question title	ex:Two Sum
${question.titleSlug}	question title slug 	ex:two-sum
${question.frontendQuestionId}	question serial number
${question.content}	question content
${question.code}	question code
$!velocityTool.camelCaseName(str)	transform str big camel case
$!velocityTool.smallCamelCaseName(str)	transform str small camel case
$!velocityTool.snakeCaseName(str)	transform str snake case
$!velocityTool.leftPadZeros(str,n)	pad sting with zero make str length at least n.
$!velocityTool.date()	The current time
```