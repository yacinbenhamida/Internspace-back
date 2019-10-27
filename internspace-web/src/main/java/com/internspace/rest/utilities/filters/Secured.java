package com.internspace.rest.utilities.filters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.ws.rs.NameBinding;
import java.lang.annotation.*;
// if you wish to secure your api you can proceed by calling this annotation on top of your ws method such as @Secured
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })

public @interface Secured {
}
