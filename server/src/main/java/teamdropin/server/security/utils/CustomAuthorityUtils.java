package teamdropin.server.security.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthorityUtils {

    private final List<GrantedAuthority> MANAGER_ROLES = AuthorityUtils.createAuthorityList("ROLE_MANAGER", "ROLE_HOST");
    private final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
    private final List<String> MANAGER_ROLES_STRING = List.of("MANAGER", "USER");
    private final List<String> USER_ROLES_STRING = List.of("USER");

    public List<GrantedAuthority> createAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    public List<String> createUserRoles() {
        return USER_ROLES_STRING;
    }

    public List<String> createManagerRoles() {
        return MANAGER_ROLES_STRING;
    }

}
