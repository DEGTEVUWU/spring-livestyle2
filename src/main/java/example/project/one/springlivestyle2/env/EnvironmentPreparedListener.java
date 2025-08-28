package example.project.one.springlivestyle2.env;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

public class EnvironmentPreparedListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment env = event.getEnvironment();
        System.out.println("\n=== [ApplicationListener] ИЕРАРХИЯ ИЗ СОБЫТИЯ ApplicationEnvironmentPreparedEvent ===");

        MutablePropertySources sources = env.getPropertySources();
        sources.forEach(ps -> System.out.println("MutablePropertySource - " + ps.getName()));

        System.out.println("Свойство 'user.name' из Environment: " + env.getProperty("user.name"));
        System.out.println("Активные профили: " + java.util.Arrays.toString(env.getActiveProfiles()));
    }
}