//package co.id.jss.jssrestapi.config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//
//@Configuration
//@EnableScheduling
//@ConfigurationProperties(prefix = "apps.scheduler")
//public class SchedulerConfig implements SchedulingConfigurer {
//
//    private String cron;
//
//    @Bean
//    public String getCron() {
//        return cron;
//    }
//
//    public void setCron(String cron) {
//        this.cron = cron;
//    }
//
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
//        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
//
//        int POOL_SIZE = 5;
//
//        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
//        threadPoolTaskScheduler.setThreadNamePrefix("govar-scheduled-task-pool-");
//        threadPoolTaskScheduler.initialize();
//
//        scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
//    }
//}
