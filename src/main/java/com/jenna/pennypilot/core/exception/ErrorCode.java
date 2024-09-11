package com.jenna.pennypilot.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;


@Getter
@AllArgsConstructor
public enum ErrorCode {

    // users
    USER_NOT_EXISTS("USER001", "사용자 정보가 존재하지 않습니다.", BAD_REQUEST),
    EMPTY_USER_DATA("USER002", "사용자 데이터가 비어있습니다.", BAD_REQUEST),
    NOT_LOGGED_IN("USER003", "로그인이 필요한 작업입니다. 먼저 로그인 해주세요.", UNAUTHORIZED),
    SESSION_EXPIRED("USER004", "세션이 만료되었습니다. 다시 로그인 해주세요.", UNAUTHORIZED),
    USER_ID_NOT_EXIST("USER005", "사용자 아이디가 존재하지 않습니다.", BAD_REQUEST),
    USER_PW_NOT_MATCHED("USER006", "비밀번호가 일치하지 않습니다.", BAD_REQUEST),

    // accounts
    EMPTY_ACCOUNT_DATA("ACC001", "계좌 정보가 비어있습니다. 계좌 정보를 추가해주세요.", BAD_REQUEST),
    ACCOUNT_CREATE_ERROR("ACC002", "계좌 정보를 생성하는 도중 오류가 발생했습니다. \n관리자에게 문의해주세요.", INTERNAL_SERVER_ERROR),

    // transactions


    // categories


    // budgets

    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
