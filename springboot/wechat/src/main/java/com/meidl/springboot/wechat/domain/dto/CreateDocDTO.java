package com.meidl.springboot.wechat.domain.dto;

import com.mdl.common.domain.BaseObject;
import com.mdl.common.validation.annotation.EnumValue;
import com.meidl.springboot.wechat.domain.enums.DocTypeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/16 18:48
 */
@Data
public class CreateDocDTO extends BaseObject {

    /**
     * 空间spaceid
     */
    private String spaceId;
    /**
     * 父目录fileid, 在根目录时为空间spaceid
     */
    private String fatherId;
    /**
     * 文档类型, 3:文档 4:表格
     */
    @EnumValue(enumClass = DocTypeEnum.class)
    private DocTypeEnum docType;
    /**
     * 文档名字（注意：文件名最多填255个字符, 超过255个字符会被截断）
     */
    @NotEmpty(message = "文档名字不能为空")
    @Length(max = 255)
    private String docName;
    /**
     * 管理员userid数组
     */
    private List<String> adminUsers;
}
