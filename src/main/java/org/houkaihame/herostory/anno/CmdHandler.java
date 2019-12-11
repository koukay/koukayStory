package org.houkaihame.herostory.anno;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CmdHandler {
    Class<?> name();
}
