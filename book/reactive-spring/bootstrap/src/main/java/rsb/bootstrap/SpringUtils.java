package rsb.bootstrap;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/4/28:11:33
 * @since 2022.04.0
 */
public class SpringUtils {

    public static ConfigurableApplicationContext run(Class<?> sources, String profile) {
        // <1>
        var applicationContext = new AnnotationConfigApplicationContext();

        // <2>
        if(StringUtils.hasText(profile)) {
            applicationContext.getEnvironment().setActiveProfiles(profile);
        }

        // <3>
        applicationContext.register(sources);
        applicationContext.refresh();

        // <4>
        applicationContext.start();
        return applicationContext;
    }
}
