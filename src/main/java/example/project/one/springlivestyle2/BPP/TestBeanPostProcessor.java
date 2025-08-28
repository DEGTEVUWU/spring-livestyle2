package example.project.one.springlivestyle2.BPP;

import example.project.one.springlivestyle2.LogUtils;
import example.project.one.springlivestyle2.SpringLifecycleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.springframework.cglib.proxy.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class TestBeanPostProcessor implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(TestBeanPostProcessor.class);
    private final Map<String, Class<?>> beansToProfile = new HashMap<>();

    @Value("${showTestBeanOnly:true}")
    private boolean showTestBeanOnly;

    public TestBeanPostProcessor() {
        LogUtils.infoWithStacktrace(log, "BeanPostProcessor constructor");
    }

    @PostConstruct
    public void init() {
        LogUtils.infoWithStacktrace(log, "@PostConstruct annotated BeanPostProcessor method");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(shouldShowBean(beanName)) {
            LogUtils.infoWithStacktrace(log, "BeanPostProcessor#postProcessBeforeInitialization() method - beanName = " + beanName);
        }
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiled.class)) {
            beansToProfile.put(beanName, beanClass);
            LogUtils.infoWithStacktrace(log, "BeanPostProcessor#postProcessBeforeInitialization() - найден бин с аннотацией Profiled до инициализации бинов" + beanName);
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(shouldShowBean(beanName)) {
            LogUtils.infoWithStacktrace(log, "BeanPostProcessor#postProcessAfterInitialization() method - beanName = " + beanName);
        }
        Class<?> beanClass = beansToProfile.get(beanName);
        if (beanClass != null) {

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(beanClass);
            enhancer.setCallback((InvocationHandler) (proxy, method, args) -> invokeWithProfiling(bean, method, args));
            Object proxy = enhancer.create();
            LogUtils.infoWithStacktrace(log, "BeanPostProcessor#postProcessAfterInitialization() - создан прокси-бин для аннотированных Profiled после инициализации бинов" + beanName);
            return proxy;
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @PreDestroy
    public void destroy() {
        LogUtils.infoWithStacktrace(log, "@PreDestroy annotated BeanPostProcessor method");
    }

    private boolean shouldShowBean(String beanName) {
        return !showTestBeanOnly || SpringLifecycleConfiguration.isTestBean(beanName);
    }

    private Object invokeWithProfiling(Object bean, Method method, Object[] args) throws Throwable {
        LogUtils.infoWithStacktrace(log, "Profiling: запущен метод " + method.getName());
        long start = System.nanoTime();

        try {
            return method.invoke(bean, args);
        } finally {
            long end = System.nanoTime();
            LogUtils.infoWithStacktrace(log, "Profiling: метод " + method.getName() +
                    " выполнился за " + (end - start) + " наносекунд");
        }
    }
}