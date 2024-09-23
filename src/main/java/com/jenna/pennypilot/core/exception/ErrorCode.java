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
    USER_EMAIL_NOT_EXIST("USER005", "사용자 이메일이 존재하지 않습니다.", BAD_REQUEST),
    USER_PW_NOT_MATCHED("USER006", "비밀번호가 일치하지 않습니다.", BAD_REQUEST),
    USERNAME_ALREADY_EXISTS("USER007", "이미 사용중인 이름입니다.", BAD_REQUEST),
    USER_EMAIL_ALREADY_EXISTS("USER008", "이미 사용중인 이메일 주소입니다.", BAD_REQUEST),
    USER_REGISTER_ERROR("USER009", "회원가입 도중 오류가 발생했습니다. \n관리자에게 문의해주세요", INTERNAL_SERVER_ERROR),
    USER_UPDATE_ERROR("USER010", "사용자 정보를 수정하는 도중 오류가 발생했습니다. \n관리자에게 문의해주세요", INTERNAL_SERVER_ERROR),
    USER_DELETE_ERROR("USER011", "사용자 정보를 삭제하는 도중 오류가 발생했습니다. \n관리자에게 문의해주세요", INTERNAL_SERVER_ERROR),

    // accounts
    EMPTY_ACCOUNT_DATA("ACC001", "계좌 정보가 비어있습니다. 계좌 정보를 추가해주세요.", BAD_REQUEST),
    ACCOUNT_NOT_EXISTS("ACC002", "계좌 정보가 존재하지 않습니다.", BAD_REQUEST),
//    ACCOUNT_CREATE_ERROR("ACC003", "계좌 정보를 생성하는 도중 오류가 발생했습니다. \n관리자에게 문의해주세요.", INTERNAL_SERVER_ERROR),
//    ACCOUNT_UPDATE_ERROR("ACC004", "계좌 정보를 수정하는 도중 오류가 발생했습니다. \n관리자에게 문의해주세요.", INTERNAL_SERVER_ERROR),
//    ACCOUNT_DELETE_ERROR("ACC005", "계좌 정보를 삭제하는 도중 오류가 발생했습니다. \n관리자에게 문의해주세요.", INTERNAL_SERVER_ERROR),

    // transactions
    TRANSACTION_NOT_EXISTS("TRA001", "거래 정보가 존재하지 않습니다.", BAD_REQUEST),
    INVALID_TRANSACTION_DATE("TRA002", "적절하지 않은 날짜 정보입니다. 다시 확인해주세요.", BAD_REQUEST),

    // categories
    CATEGORY_NOT_EXISTS("CTG001", "카테고리(분류) 정보가 존재하지 않습니다.", BAD_REQUEST),
    CATEGORY_ALREADY_EXISTS("CTG002", "이미 존재하는 카테고리 정보입니다.", BAD_REQUEST),

    // budgets

    ;

    private final String code;
    private final String message;
    private final HttpStatus status;
}
