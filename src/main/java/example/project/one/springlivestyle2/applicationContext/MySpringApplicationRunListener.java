package example.project.one.springlivestyle2.applicationContext;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.time.Duration;

public class MySpringApplicationRunListener implements SpringApplicationRunListener {

    public MySpringApplicationRunListener(SpringApplication application, String[] args) {
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        // Самое первое событие, даже до подготовки Environment
        System.out.println(">>> Application is starting...");
    }

    /**
     * Работает до создания контекста, но когда уже добавлены env
     * @param bootstrapContext
     * @param environment
     */
    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        System.out.println(">>> ApplicationEnvironmentPreparedEvent caught! Environment готов, но Context еще нет");
        System.out.println(">>> Active profiles: " + String.join(", ", environment.getActiveProfiles()));

        // Пример: можно посмотреть какое-то конкретное свойство
        String appMessage = environment.getProperty("app.message");
        System.out.println(">>> Значение свойства app.message: " + appMessage);

        // Пример: можно ДОБАВИТЬ свойство программно (мощная возможность)
//        MutablePropertySources propertySources = environment.getPropertySources();
//        propertySources.addLast(new MapPropertySource("myCustomSource", Map.of("custom.property", "value-added-dynamically")));
        System.out.println(">>> Added custom property!");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        // Context создан и BeanDefinitions загружены, но до refresh()
        System.out.println(">>> ApplicationContext is prepared. Context создан и BeanDefinitions загружены, но до refresh()");
    }

    /**
     * Аналог метода handleContextRefreshedEvent() в классе данного приложения SpringContextListener
     * @param context
     */
    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        // BeanDefinitions загружены в контекст, но до refresh()
        System.out.println(">>> ApplicationContext is loaded. BeanDefinitions загружены в контекст, но до refresh()");
    }

    /**
     * Аналог метода handleContextRefreshedEvent() в классе данного приложения SpringContextListener
     * @param context
     * @param timeTaken
     */
    @Override
    public void started(ConfigurableApplicationContext context, Duration timeTaken) {
        // Context полностью обновлен (refreshed), ApplicationRunner и CommandLineRunner вызваны.
        System.out.println(">>> Application started in " + timeTaken.toMillis() + " ms.");
    }

    @Override
    public void ready(ConfigurableApplicationContext context, Duration timeTaken) {
        // Приложение полностью готово к работе.
        System.out.println(">>> Application is ready!");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        // Запуск завершился с ошибкой.
        System.err.println(">>> Application failed to start: " + exception.getMessage());
    }
}