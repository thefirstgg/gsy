package gaojbackendmodel.model.dto.questionsubmit;


import com.gsy.gaojbackendcommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/gsy">gsy</a>
 * @from <a href="https://yupi.icu">ggssyy项目</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {
    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;
    /**
     * 用户 id
     */
    private Long userId;
    private static final long serialVersionUID = 1L;
}