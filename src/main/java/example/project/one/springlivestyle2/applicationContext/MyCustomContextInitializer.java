package example.project.one.springlivestyle2.applicationContext;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import java.util.HashMap;
import java.util.Map;

public class MyCustomContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // Получаем доступ к Environment
        ConfigurableEnvironment env = applicationContext.getEnvironment();

        // Программно создаем и добавляем свойства
        Map<String, Object> customProperties = new HashMap<>();
        customProperties.put("app.message", "Переопределенное свойство app.message");

        // Добавляем свой PropertySource в Environment
        env.getPropertySources().addFirst(
                new MapPropertySource("myCustomPropertySource", customProperties)
        );

        // Можем также активировать профили
        env.setActiveProfiles("dev");

        // Здесь можно произвести любые другие манипуляции с applicationContext
        System.out.println("MyCustomContextInitializer выполнил свою работу!");
        System.out.println("Добавлено свойство app.message: " + env.getProperty("app.message"));
        System.out.println("Изменен активный профиль на: " + env.getActiveProfiles().length);

    }
}