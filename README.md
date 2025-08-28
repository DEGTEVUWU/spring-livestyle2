## Демонстрация жизненного цикла Spring

>Из-за проблем с idea я не смог форкнуть и открыть оригинальный репо.
>[Ссылка на оригинал](https://github.com/LordDetson/spring-lifecycle)  
>Я слегка доработал проект, добавил больше деталей в начальную инициализацию спринг-контекста и тд.  
>Ниже я приведу оригинальный README.md и далее - добалвю свои логи и общую выжимку по последовательности работы спринга.

## Руководство пользователя  
Этот проект имеет maven-wrapper и использует spring-boot-maven-plugin.

Поэтому вам следует открыть корневой каталог проекта и запустить:

``` 
mvnw spring-boot:run
```

<details>
<summary>Оригинальное описание (нажмите, чтобы развернуть)</summary>
  
И консоль выводит журнал, который наглядно показывает жизненный цикл Spring с использованием инструментов интеграции, предоставляемых Spring Framework.
```
b.b.e.s.TestBeanFactoryPostProcessor     : BeanFactoryPostProcessor constructor
b.b.e.s.TestBeanFactoryPostProcessor     : BeanFactoryPostProcessor#postProcessBeanFactory method
b.b.e.s.TestBeanPostProcessor            : BeanPostProcessor constructor
b.b.e.s.TestBeanPostProcessor            : @PostConstruct annotated BeanPostProcessor method
b.b.example.springlifecycle.TestBean     : Static initialization block
b.b.example.springlifecycle.TestBean     : Non-static initialization block
b.b.example.springlifecycle.TestBean     : Bean constructor
b.b.example.springlifecycle.TestBean     : Setter-base Dependency Injection
b.b.example.springlifecycle.TestBean     : The standard set of *Aware interfaces
b.b.e.s.TestBeanPostProcessor            : BeanPostProcessor#postProcessBeforeInitialization() method - beanName = testBean
b.b.example.springlifecycle.TestBean     : @PostConstruct annotated method
b.b.example.springlifecycle.TestBean     : InitializingBean#afterPropertiesSet() method
b.b.example.springlifecycle.TestBean     : initMethod registered method
b.b.e.s.TestBeanPostProcessor            : BeanPostProcessor#postProcessAfterInitialization() method - beanName = testBean
b.b.e.s.TestSmartLifecycle               : SmartLifecycle#isRunning method
b.b.e.s.TestSmartLifecycle               : SmartLifecycle#start method
b.b.e.s.SpringContextListener            : context refreshed
b.b.e.s.SpringContextListener            : context closed
b.b.e.s.TestSmartLifecycle               : SmartLifecycle#isRunning method
b.b.e.s.TestSmartLifecycle               : SmartLifecycle#stop method
b.b.e.springlifecycle.TestLifecycle      : Lifecycle#isRunning method
b.b.example.springlifecycle.TestBean     : @PreDestroy annotated method
b.b.example.springlifecycle.TestBean     : TestBean#destroy() method
b.b.example.springlifecycle.TestBean     : destroyMethod registered method
b.b.e.s.TestBeanPostProcessor            : @PreDestroy annotated BeanPostProcessor method
```
По умолчанию проект не вызывает методы ApplicationContext#startи ApplicationContext#stop.

Чтобы увидеть, как в этом случае меняется жизненный цикл, вы можете выполнить:
```
mvnw spring-boot:run -D spring-boot.run.arguments=--triggerStartAndStopContext=true
```
Команда добавит в вывод логи:
```
b.b.e.s.SpringContextListener            : context started
b.b.e.s.SpringContextListener            : context stopped
```

Если вы хотите подробнее рассмотреть, как вызываются методы обратного вызова, то вам следует выполнить:
```
mvnw spring-boot:run -D spring-boot.run.arguments=--logging.level.by.babanin.example.springlifecycle=TRACE
```
Консоль выводит лог со стектрейсом вызова метода. Например:
```
b.b.e.s.TestBeanFactoryPostProcessor     : BeanFactoryPostProcessor constructor

by.babanin.example.springlifecycle.TestException: Reached
        at by.babanin.example.springlifecycle.LogUtils.infoWithStacktrace(LogUtils.java:12) ~[classes/:na]
        at by.babanin.example.springlifecycle.TestBeanFactoryPostProcessor.<init>(TestBeanFactoryPostProcessor.java:19) ~[classes/:na]
        at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method) ~[na:na]
        at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:77) ~[na:na]
        at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45) ~[na:na]
        at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:499) ~[na:na]
        at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:480) ~[na:na]
        at org.springframework.beans.BeanUtils.instantiateClass(BeanUtils.java:197) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:87) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateBean(AbstractAutowireCapableBeanFactory.java:1300) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1198) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:561) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:521) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:326) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:324) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:205) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:199) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors(AbstractApplicationContext.java:745) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:565) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:730) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:432) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:308) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1302) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1291) ~[spring-boot-3.0.2.jar:3.0.2]
        at by.babanin.example.springlifecycle.SpringLifecycleApplication.main(SpringLifecycleApplication.java:11) ~[classes/:na]
```
</details>

## Мои доработки
Добавляют следующий вывод логов
```
=== ANALYSIS OF @SpringBootApplication DEFAULTS ===
🎯 @SpringBootApplication found
🔧 Default exclude filters in @SpringBootApplication:
 - Type: CUSTOM
   Classes: [class org.springframework.boot.context.TypeExcludeFilter]
   Pattern: []
 - Type: CUSTOM
   Classes: [class org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter]
   Pattern: []
=== TYPE EXCLUDE FILTER REGISTRATION ===
Found 1 TypeExcludeFilter(s):
 - example.project.one.springlivestyle2.classFilter.LoggingTypeExcludeFilter
>>> Application is starting...

=== [EnvironmentPostProcessor] РАННЯЯ ИЕРАРХИЯ PROPERTYSOURCES ===
1. configurationProperties
2. commandLineArgs
3. systemProperties
4. systemEnvironment
   Пример свойства: JAVA_HOME = null
5. random
6. applicationInfo
7. Config resource 'class path resource [application-prod.properties]' via location 'optional:classpath:/'
8. Config resource 'class path resource [application.properties]' via location 'optional:classpath:/'
=== [EnvironmentPostProcessor] АКТИВНЫЕ ПРОФИЛИ НА ЭТОМ ЭТАПЕ ===
[prod]

=== [ApplicationListener] ИЕРАРХИЯ ИЗ СОБЫТИЯ ApplicationEnvironmentPreparedEvent ===
MutablePropertySource - configurationProperties
MutablePropertySource - commandLineArgs
MutablePropertySource - systemProperties
MutablePropertySource - systemEnvironment
MutablePropertySource - random
MutablePropertySource - applicationInfo
MutablePropertySource - Config resource 'class path resource [application-prod.properties]' via location 'optional:classpath:/'
MutablePropertySource - Config resource 'class path resource [application.properties]' via location 'optional:classpath:/'
Свойство 'user.name' из Environment: root
Активные профили: [prod]
>>> ApplicationEnvironmentPreparedEvent caught! Environment готов, но Context еще нет
>>> Active profiles: prod
>>> Значение свойства app.message: Production profile is active!
>>> Added custom property!

[ИЗОБРАЖЕНИЕ БАННЕРА]

MyCustomContextInitializer выполнил свою работу!
Добавлено свойство app.message: Переопределенное свойство app.message
Изменен активный профиль на: 1
>>> ApplicationContext is prepared. Context создан и BeanDefinitions загружены, но до refresh()
e.p.o.s.SpringLivestyle2Application      : Starting SpringLivestyle2Application using Java 21.0.8 with PID 172727 (/home/ivan/proj/spring-livestyle2/target/classes started by root in /home/ivan/proj/spring-livestyle2)
e.p.o.s.SpringLivestyle2Application      : The following 1 profile is active: "dev"
>>> ApplicationContext is loaded. BeanDefinitions загружены в контекст, но до refresh()
.o.s.b.BeanDefinitionLoggerPostProcessor : >>> Начало логирования всех BeanDefinitions в BeanDefinitionRegistryPostProcessor:
.o.s.b.BeanDefinitionLoggerPostProcessor : >>> Логирование завершено, всего бинов зарегистрировано: 192
e.p.o.s.TestBeanFactoryPostProcessor     : BeanFactoryPostProcessor constructor
e.p.o.s.TestBeanFactoryPostProcessor     : Environment has been set via EnvironmentAware interface
e.p.o.s.TestBeanFactoryPostProcessor     : BeanFactoryPostProcessor#postProcessBeanFactory method
e.p.o.s.TestBeanFactoryPostProcessor     : >>> BFPP: Password property has been set for TestBean
e.p.o.s.BPP.TestBeanPostProcessor        : BeanPostProcessor constructor
e.p.o.s.BPP.TestBeanPostProcessor        : @PostConstruct annotated BeanPostProcessor method
e.p.o.s.BPP.TestBeanPostProcessor        : BeanPostProcessor#postProcessBeforeInitialization() - найден бин с аннотацией Profiled до инициализации биновbeanToInject
e.p.o.s.BPP.TestBeanPostProcessor        : BeanPostProcessor#postProcessAfterInitialization() - создан прокси-бин для аннотированных Profiled после инициализации биновbeanToInject
e.project.one.springlivestyle2.TestBean  : Static initialization block
e.project.one.springlivestyle2.TestBean  : Non-static initialization block
e.project.one.springlivestyle2.TestBean  : Bean constructor
e.project.one.springlivestyle2.TestBean  : Setter-base Dependency Injection - здесь вызывается метод другого бина - BeanToInject
e.p.o.s.BPP.TestBeanPostProcessor        : Profiling: запущен метод testMethod
e.p.one.springlivestyle2.BeanToInject    : Первичный лог в бине с @Profiled - имитируем внутреннюю логику: 2+2*2= 6
e.p.o.s.BPP.TestBeanPostProcessor        : Profiling: метод testMethod выполнился за 154447 наносекунд
e.project.one.springlivestyle2.TestBean  : The standard set of *Aware interfaces
e.p.o.s.BPP.TestBeanPostProcessor        : BeanPostProcessor#postProcessBeforeInitialization() method - beanName = testBean
e.project.one.springlivestyle2.TestBean  : @PostConstruct annotated method
e.project.one.springlivestyle2.TestBean  : @PostConstruct annotated method Проверка значения env app.message: Переопределенное свойство app.message
e.project.one.springlivestyle2.TestBean  : @PostConstruct annotated method Проверка текущего профиля prod
e.project.one.springlivestyle2.TestBean  : InitializingBean#afterPropertiesSet() method
e.project.one.springlivestyle2.TestBean  : initMethod registered method
e.project.one.springlivestyle2.TestBean  : Вывод измененного пароля в BFPP в инит-методе. Пароль: 1234567890
e.p.o.s.BPP.TestBeanPostProcessor        : BeanPostProcessor#postProcessAfterInitialization() method - beanName = testBean
e.p.o.s.TestSmartLifecycle               : SmartLifecycle#isRunning method
e.p.o.s.TestSmartLifecycle               : SmartLifecycle#start method
e.p.o.s.SpringContextListener            : context refreshed
e.p.o.s.SpringLivestyle2Application      : Started SpringLivestyle2Application in 1.167 seconds (process running for 1.597)
>>> Application started in 1167 ms.
>>> Application is ready!
e.p.one.springlivestyle2.TestLifecycle   : Lifecycle#isRunning method
e.p.one.springlivestyle2.TestLifecycle   : Lifecycle#start method
e.p.o.s.TestSmartLifecycle               : SmartLifecycle#isRunning method
e.p.o.s.SpringContextListener            : context started
e.p.o.s.TestSmartLifecycle               : SmartLifecycle#isRunning method
e.p.o.s.TestSmartLifecycle               : SmartLifecycle#stop method
e.p.one.springlivestyle2.TestLifecycle   : Lifecycle#isRunning method
e.p.one.springlivestyle2.TestLifecycle   : Lifecycle#stop method
e.p.o.s.SpringContextListener            : context stopped
e.p.o.s.SpringContextListener            : context closed
e.p.o.s.TestSmartLifecycle               : SmartLifecycle#isRunning method
e.p.one.springlivestyle2.TestLifecycle   : Lifecycle#isRunning method
e.project.one.springlivestyle2.TestBean  : @PreDestroy annotated method
e.project.one.springlivestyle2.TestBean  : TestBean#destroy() method
e.project.one.springlivestyle2.TestBean  : destroyMethod registered method
e.p.o.s.BPP.TestBeanPostProcessor        : @PreDestroy annotated BeanPostProcessor method
```
Добавлено логирование: 
- какие дефолтные фильтры добавлены в аннотацию SpringBootApplication
- кастомный фильтр, реализующий TypeExcludeFilter(не добавлен в сам код, работает только с аннотацией ComponentScan)
- листенер, реализующий ApplicationListener, который может слушать,нахождение и использование кастомных фильтров
- SpringApplicationRunListener - для отлова событий старта контекста. Например, starting() - для отлова ивентов по старту контекста(до внедрения env), environmentPrepared() - после внедрения env и др.
- BPP для EnvironmentPostProcessor и листенер ApplicationListener<ApplicationEnvironmentPreparedEvent> для мониторинга использвоания. Возможность менять env до старта контекста
- Баннер через META-INF.spring.factories
- реализация ApplicationContextInitializer - для отлова инициализации контекста(создание контекста, загрузка BeanDefinitions, но до refresh())
- реализация BeanDefinitionRegistryPostProcessor для отлова сканирования и чтения BeanDefinition(до BFPP), там можно создать кастомный
- реализация проксирования бина с аннотацией через BPP
