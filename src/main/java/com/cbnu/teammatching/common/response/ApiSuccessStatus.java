package com.cbnu.teammatching.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApiSuccessStatus {
    SIGNUP_SUCCESS(HttpStatus.CREATED,"회원가입 성공"),
    SIGNIN_SUCCESS(HttpStatus.ACCEPTED, "로그인 성공"),
    PROFILE_SAVE(HttpStatus.CREATED,"프로필 등록 성공"),
    RETRIEVAL_SUCCESS(HttpStatus.OK,"조회 성공"),
    POST_CREATE_SUCCESS(HttpStatus.CREATED,"게시글 작성 성공"),
    MEMBER_APPLY_SUCCESS(HttpStatus.CREATED,"회원 지원 성공"),
    APPLICATION_PROCESS_SUCCESS(HttpStatus.OK,"지원 처리 완료");

    private final HttpStatus httpStatus;
    private final String message;
}
