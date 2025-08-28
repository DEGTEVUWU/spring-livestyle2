package example.project.one.springlivestyle2.env;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

//@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        // Мы можем получить Environment из BeanFactory
        ConfigurableEnvironment env = (ConfigurableEnvironment) beanFactory.getBean(ConfigurableEnvironment.class);

        System.out.println("\n=== [BeanFactoryPostProcessor] ИЕРАРХИЯ PropertySources ===");
        MutablePropertySources sources = env.getPropertySources();
        sources.forEach(ps -> System.out.println(" - " + ps.getName()));

        // Проверим, загрузились ли уже свойства из application-prod.properties
        String appMessage = env.getProperty("app.message");
        System.out.println("app.message на этапе BeanFactoryPostProcessor: " + appMessage);
    }
}