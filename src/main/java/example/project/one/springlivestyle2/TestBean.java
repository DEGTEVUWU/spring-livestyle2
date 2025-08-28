package example.project.one.springlivestyle2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;

public class TestBean implements InitializingBean, DisposableBean, BeanNameAware {

    @Value("${app.message}")
    private String message;
    private String password;

    private static final Logger log = LoggerFactory.getLogger(TestBean.class);

    private BeanToInject bean;

    // Standard JVM Class Initialization Section

    static {
        LogUtils.infoWithStacktrace(log, "Static initialization block");
    }

    {
        LogUtils.infoWithStacktrace(log, "Non-static initialization block");
    }

    public TestBean() {
        LogUtils.infoWithStacktrace(log, "Bean constructor");
    }

    //  Bean Initialization Callbacks Section

    @PostConstruct
    public void annotationInitialization() {
        LogUtils.infoWithStacktrace(log, "@PostConstruct annotated method");
        LogUtils.infoWithStacktrace(log, "@PostConstruct annotated method Проверка значения env app.message: " + message);
        LogUtils.infoWithStacktrace(log, "@PostConstruct annotated method Проверка текущего профиля " + getActiveProfile());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LogUtils.infoWithStacktrace(log, "InitializingBean#afterPropertiesSet() method");
    }

    public void init() {
        LogUtils.infoWithStacktrace(log, "initMethod registered method");
        LogUtils.infoWithStacktrace(log, "Вывод измененного пароля в BFPP в инит-методе. Пароль: " + getPassword());
    }

    //  Bean Destruction Callbacks Section

    @PreDestroy
    public void annotationCleanUp() {
        LogUtils.infoWithStacktrace(log, "@PreDestroy annotated method");
    }

    @Override
    public void destroy() throws Exception {
        LogUtils.infoWithStacktrace(log, "TestBean#destroy() method");
    }

    public void cleanUp() {
        LogUtils.infoWithStacktrace(log, "destroyMethod registered method");
    }

    // The standard set of *Aware interfaces

    @Override
    public void setBeanName(String name) {
        LogUtils.infoWithStacktrace(log, "The standard set of *Aware interfaces");
    }

    // Setter-base Dependency Injection

    @Resource
    public void setBean(BeanToInject bean) {
        LogUtils.infoWithStacktrace(log, "Setter-base Dependency Injection - здесь вызывается метод другого бина - BeanToInject");
        this.bean = bean;
        this.bean.testMethod();
    }

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    public String getActiveProfile() {
        return activeProfile;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}