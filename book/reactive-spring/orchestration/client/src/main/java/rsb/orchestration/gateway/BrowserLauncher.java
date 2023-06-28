package rsb.orchestration.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.slf4j.LoggerFactory.*;

/**
 * 描述: .
 * <p>
 * Copyright © 2022 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 * </p>
 *
 * @author jiac
 * @version 2022.04.0 2023/6/6:17:17
 * @since 2022.04.0
 */
@Component
@Profile("chrome-on-macos")
public class BrowserLauncher {

    private static final Logger log = getLogger(BrowserLauncher.class);


    @EventListener
    public void webServerInitializedEvent(WebServerInitializedEvent event) throws Exception {
        var apps = new File("/Applications");
        var googleChromeAppInstallations = apps.listFiles((dir,name) -> name.contains("Google Chrome.app"));

        Assert.state(apps.exists() && googleChromeAppInstallations != null && googleChromeAppInstallations.length > 0,
                """
                        Disable this class if you're running on some other OS besides macOS and if you 
                        don't have Google Chrome installed on macOS!
                        """);
        var port = event.getWebServer().getPort();
        var url = "http://localhost:" + port + "/";
        log.info("trying to open " + url);
        var exec = Runtime.getRuntime().exec(
                new String[]{"open","-n",googleChromeAppInstallations[0].getName(),url},
                new String[]{},
                apps);

        var error = exec.getErrorStream();
        var statusCode = exec.waitFor();
        var errorString = StreamUtils.copyToString(error, Charset.defaultCharset());
        log.info("the status code is " + statusCode + " and the process output is " + errorString);
    }
}
