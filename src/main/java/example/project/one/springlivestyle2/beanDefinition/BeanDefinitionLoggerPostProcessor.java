package example.project.one.springlivestyle2.beanDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class BeanDefinitionLoggerPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(BeanDefinitionLoggerPostProcessor.class);

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
/**
 * Этот метод вызывается самым первым, сразу после загрузки всех определений, здесь можно получить
 * все BeanDefinition с помощью дефолтного сканера и ридера
 */
        log.info(">>> Начало логирования всех BeanDefinitions в BeanDefinitionRegistryPostProcessor:");
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
//        for (String beanName : beanDefinitionNames) {
//            BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
//            log.info(">>> BeanName: {}, BeanClass: {}", beanName, beanDefinition.getBeanClassName());
//        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // Этот метод вызывается после postProcessBeanDefinitionRegistry,
        // но до вызова обычных BeanFactoryPostProcessor.
        // Здесь тоже можно логировать, но чаще работают с самим BeanFactory.
        log.info(">>> Логирование завершено, всего бинов зарегистрировано: {}", beanFactory.getBeanDefinitionCount());
    }
}