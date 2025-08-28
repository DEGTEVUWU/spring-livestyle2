package example.project.one.springlivestyle2;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor, EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(TestBeanFactoryPostProcessor.class);
    private Environment environment;

    public TestBeanFactoryPostProcessor() {
        LogUtils.infoWithStacktrace(log, "BeanFactoryPostProcessor constructor");
    }

    // Never called
    @PostConstruct
    public void init() {
        LogUtils.infoWithStacktrace(log, "@PostConstruct annotated BeanFactoryPostProcessor method");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        LogUtils.infoWithStacktrace(log, "BeanFactoryPostProcessor#postProcessBeanFactory method");

        BeanDefinition bd = beanFactory.getBeanDefinition("testBean");
        MutablePropertyValues propertyValues = bd.getPropertyValues();
        String rawPassword = environment.getProperty("app.password");
        propertyValues.add("password", rawPassword);

        LogUtils.infoWithStacktrace(log, ">>> BFPP: Password property has been set for TestBean");
    }

    // Never called
    @PreDestroy
    public void destroy() {
        LogUtils.infoWithStacktrace(log, "@PreDestroy annotated BeanFactoryPostProcessor method");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        LogUtils.infoWithStacktrace(log, "Environment has been set via EnvironmentAware interface");
    }
}