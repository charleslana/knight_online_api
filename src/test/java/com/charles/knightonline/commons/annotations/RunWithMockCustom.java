package com.charles.knightonline.commons.annotations;

import com.charles.knightonline.model.dto.UserDetailsDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

public interface RunWithMockCustom {

    default SecurityContext renderSecurityContext(String userName, String password, String[] authorities) {
        UserDetailsDTO user = createUser(userName, password, authorities);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        user.getPassword(),
                        user.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }

    default UserDetailsDTO createUser(String userName, String password, String[] authorities) {
        List<SimpleGrantedAuthority> roles = Arrays.stream(authorities)
                .map(SimpleGrantedAuthority::new).toList();
        return new UserDetailsDTO(roles, password, userName);
    }
}
