package cn.org.faster.cms.common;

import lombok.Getter;

/**
 * 业务异常，由 {@link GlobalExceptionHandler} 统一转换为 RFC 7807 响应。
 */
@Getter
public class BizException extends RuntimeException {

    private final ErrorCode errorCode;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BizException(ErrorCode errorCode, String detail) {
        super(detail);
        this.errorCode = errorCode;
    }

    public static BizException of(ErrorCode errorCode) {
        return new BizException(errorCode);
    }

    public static BizException of(ErrorCode errorCode, String detail) {
        return new BizException(errorCode, detail);
    }
}
