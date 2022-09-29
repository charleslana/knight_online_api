package com.charles.knightonline.commons.annotations.user;

import com.charles.knightonline.utils.UserConfigUtils;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = RunWithMockCustomUserSecurityContextFactory.class)
public @interface RunWithMockCustomUser {

    String userName = UserConfigUtils.USER_NAME;
    String password = UserConfigUtils.PASSWORD;
    String[] authorities() default {};
}
