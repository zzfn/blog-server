package org.owoto.exception;

import org.owoto.enums.ResultEnum;

/**
 * @author chenchen
 */
public class BusinessException extends RuntimeException {
    private Integer code;

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
