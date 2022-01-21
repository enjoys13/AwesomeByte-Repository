package co.id.jss.jssrestapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAsync
@EnableScheduling
public class AppAsyncConfig extends AsyncConfiguration {

    @Autowired
    public AppAsyncConfig(AsyncProperties asyncProperties) {
        super(asyncProperties);
    }
}
