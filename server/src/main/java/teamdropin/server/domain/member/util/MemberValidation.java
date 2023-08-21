package teamdropin.server.domain.member.util;

import org.springframework.beans.factory.annotation.Value;

public class MemberValidation {

    public static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()])(?!.*(.)\\1\\1|.*(abc|bcd|cde|def|efg|fgh|ghi|hij|ijk|jkl|klm|lmn|mno|nop|opq|pqr|qrs|rst|stu|tuv|uvw|vwx|wxy|xyz|ABC|BCD|CDE|DEF|EFG|FGH|GHI|HIJ|IJK|JKL|KLM|LMN|MNO|NOP|OPQ|PQR|QRS|RST|STU|TUV|UVW|VWX|WXY|123|234|345|456|567|678|789|012|([A-Za-z0-9])\\1\\1)).*$";
    public static final String NAME_REGEX = "^[a-zA-Z가-힣]*$";
    public static final String NICKNAME_REGEX = "^[a-zA-Z가-힣0-9]*$";

}
