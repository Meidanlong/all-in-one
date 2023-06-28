package com.mdl.springboot.aigc.domain.openai;

import com.mdl.common.domain.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author： Luzelong
 * @Created： 2023/2/14 19:04
 */
@Data
public class CompletionError extends BaseObject {

    private String message;

    private String type;

    private String param;

    private String code;

}
