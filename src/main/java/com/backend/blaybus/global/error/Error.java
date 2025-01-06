package com.backend.blaybus.global.error;

import lombok.Getter;

@Getter
/* 예외 상황에 대한 상태코드와 에러 메시지 정의 */
public enum Error {
    INVALID_INPUT("입력값이 유효하지 않습니다.", 400),
    ILLEGAL_ARGUMENT("잘못된 인자가 전달되었습니다.", 400),
    NO_SUCH_ELEMENT("요소를 찾을 수 없습니다.", 404),
    ACCESS_DENIED("접근이 거부되었습니다.", 403),
    INTERNAL_SERVER_ERROR("서버에 문제가 발생했습니다.", 500);

    private final String errorMessage;
    private final int state;

    Error(String errorMessage, int state) {
        this.errorMessage = errorMessage;
        this.state = state;
    }
}
