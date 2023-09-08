package teamdropin.server.global.exception;

import lombok.Getter;

public enum ExceptionCode {


    //존재 하지 않는 포스트
    POST_NOT_FOUND(404, "Post not found"),

    //존재 하지 않는 댓글
    COMMENT_NOT_FOUND(404, "Comment not found"),

    //이미 존재하는 아이디(이메일)
    USERNAME_EXISTS(400, "Username already exists"),

    //존재하지 않는 아이디
    USER_NOT_FOUND(404, "User not found"),

    //이미 존재하는 닉네임
    NICKNAME_EXISTS(400, "Nickname already exists"),

    // 패스워드 불일치
    PASSWORD_MISMATCH(403, "Password Mismatch"),

    // 회원가입 이메일 코드 불일치
    CODE_MISMATCH(403,"Code MisMatch"),

    // 회원 권한이 없음
    USER_NOT_AUTHORIZED(401, "User not Authorized"),

    //파일 컨버트에 실패
    FILE_CONVERT_FAILED(500, "MultipartFile cannot convert to file" ),

    //토큰 기한 만료
    TOKEN_EXPIRED(401, "Token is expired"),

    //토큰 없을 때
    TOKEN_NOT_FOUND(401, "Token is exists"),

    //유효하지 않는 토큰
    TOKEN_INVALID(401, "Token is invalid"),
    LIKE_NOT_FOUND(404, "Like not found" ),
    BOX_NOT_FOUND(404, "BOX not found" );
    @Getter
    private final int status;
    @Getter
    private final String message;

    ExceptionCode(int status, String message){
        this.status = status;
        this.message = message;
    }
}
