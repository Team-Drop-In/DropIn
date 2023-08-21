package teamdropin.server.domain.member.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberValidationTest {
    @Test
    public void testPasswordRegex() {

        assertTrue("rdkdksflA1!@".matches(MemberValidation.PASSWORD_REGEX));
        assertFalse("abcd123!".matches(MemberValidation.PASSWORD_REGEX));
    }

    @Test
    public void testNameRegex() {
        assertTrue("test".matches(MemberValidation.NAME_REGEX));
        assertTrue("이름".matches(MemberValidation.NAME_REGEX));
        assertFalse("test123".matches(MemberValidation.NAME_REGEX));
        assertFalse("test123!".matches(MemberValidation.NAME_REGEX));
        assertFalse("testtesttestestestes".matches(MemberValidation.NAME_REGEX));
    }

    @Test
    public void testNicknameRegex() {
        assertTrue("john123".matches(MemberValidation.NICKNAME_REGEX));
        assertTrue("john".matches(MemberValidation.NICKNAME_REGEX));
        assertFalse("john#".matches(MemberValidation.NICKNAME_REGEX));
        assertFalse("john#".matches(MemberValidation.NICKNAME_REGEX));
    }
}