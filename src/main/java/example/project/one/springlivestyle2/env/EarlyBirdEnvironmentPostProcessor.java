package example.project.one.springlivestyle2.env;

import example.project.one.springlivestyle2.LogUtils;
import example.project.one.springlivestyle2.TestBeanFactoryPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

// Важно: регистрируем через META-INF, а не через @Component!
public class EarlyBirdEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.println("\n=== [EnvironmentPostProcessor] РАННЯЯ ИЕРАРХИЯ PROPERTYSOURCES ===");

        MutablePropertySources propertySources = environment.getPropertySources();
        int i = 1;
        for (PropertySource<?> ps : propertySources) {
            System.out.println(i++ + ". " + ps.getName());
            // Для демонстрации: посмотрим на случайное свойство из systemEnvironment
            if (ps.getName().equals("systemEnvironment")) {
                System.out.println("   Пример свойства: JAVA_HOME = " + ps.getProperty("JAVA_HOME"));
            }
        }

        System.out.println("=== [EnvironmentPostProcessor] АКТИВНЫЕ ПРОФИЛИ НА ЭТОМ ЭТАПЕ ===");
        System.out.println(java.util.Arrays.toString(environment.getActiveProfiles()));
    }
}