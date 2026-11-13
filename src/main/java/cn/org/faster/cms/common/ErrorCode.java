package cn.org.faster.cms.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 业务错误码。
 *
 * <p>编码规则：{@code 4xx00} 为通用错误，{@code 11xx} 起为业务错误；HTTP 状态码与之一一对应。</p>
 */
@Getter
public enum ErrorCode {

    BAD_REQUEST(40000, HttpStatus.BAD_REQUEST, "请求参数不合法"),
    UNAUTHORIZED(40100, HttpStatus.UNAUTHORIZED, "未登录或登录已过期"),
    TOKEN_INVALID(40101, HttpStatus.UNAUTHORIZED, "登录凭证无效"),
    FORBIDDEN(40300, HttpStatus.FORBIDDEN, "没有访问权限"),
    NOT_FOUND(40400, HttpStatus.NOT_FOUND, "资源不存在"),
    METHOD_NOT_ALLOWED(40500, HttpStatus.METHOD_NOT_ALLOWED, "请求方法不支持"),
    CONFLICT(40900, HttpStatus.CONFLICT, "资源状态冲突"),
    PAYLOAD_TOO_LARGE(41300, HttpStatus.PAYLOAD_TOO_LARGE, "文件超出大小限制"),
    INTERNAL(50000, HttpStatus.INTERNAL_SERVER_ERROR, "服务器内部错误"),

    CAPTCHA_INVALID(1102, HttpStatus.BAD_REQUEST, "验证码错误或已过期"),
    USER_NOT_FOUND(1100, HttpStatus.NOT_FOUND, "用户不存在"),
    PASSWORD_ERROR(1101, HttpStatus.BAD_REQUEST, "密码错误"),
    USER_DISABLED(1103, HttpStatus.FORBIDDEN, "用户已被禁用"),
    OLD_PASSWORD_ERROR(1132, HttpStatus.BAD_REQUEST, "旧密码不正确"),
    USER_EXISTS(1130, HttpStatus.CONFLICT, "账号已存在"),
    USER_PROTECTED(1133, HttpStatus.BAD_REQUEST, "内置超级管理员不可修改或删除"),

    PERMISSION_NOT_FOUND(1110, HttpStatus.NOT_FOUND, "权限不存在"),
    PERMISSION_CODE_EXISTS(1111, HttpStatus.CONFLICT, "权限编码已存在"),
    PERMISSION_PARENT_NOT_FOUND(1112, HttpStatus.BAD_REQUEST, "权限父级不存在"),

    ROLE_NOT_FOUND(1120, HttpStatus.NOT_FOUND, "角色不存在"),
    ROLE_PROTECTED(1121, HttpStatus.BAD_REQUEST, "内置超级管理员角色不可修改或删除"),
    ROLE_IN_USE(1122, HttpStatus.CONFLICT, "角色下仍有用户，无法删除"),
    ROLE_PERMISSION_PROTECTED(1140, HttpStatus.BAD_REQUEST, "超级管理员无需配置权限"),

    ARTICLE_NOT_FOUND(1150, HttpStatus.NOT_FOUND, "文章不存在"),
    ARTICLE_CODE_EXISTS(1151, HttpStatus.CONFLICT, "文章编码已存在"),

    SECTION_NOT_FOUND(1160, HttpStatus.NOT_FOUND, "栏目不存在"),
    SECTION_CODE_EXISTS(1161, HttpStatus.CONFLICT, "栏目编码已存在"),
    SECTION_PARENT_NOT_FOUND(1162, HttpStatus.BAD_REQUEST, "栏目父级不存在"),
    SECTION_HAS_CHILDREN(1163, HttpStatus.CONFLICT, "请先删除子栏目"),
    SECTION_HAS_ARTICLES(1164, HttpStatus.CONFLICT, "栏目下仍有文章，无法删除"),

    TEMPLATE_NOT_FOUND(1170, HttpStatus.NOT_FOUND, "模板不存在");

    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
