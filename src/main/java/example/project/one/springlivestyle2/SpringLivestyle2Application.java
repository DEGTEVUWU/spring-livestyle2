package example.project.one.springlivestyle2;

import example.project.one.springlivestyle2.applicationContext.MyCustomContextInitializer;
import example.project.one.springlivestyle2.classFilter.DefaultFilterAnalyzer;
import example.project.one.springlivestyle2.classFilter.LoggingTypeExcludeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SpringLivestyle2Application {

	private static final Logger log = LoggerFactory.getLogger(SpringLivestyle2Application.class);

	public static void main(String[] args) {
		//посмотреть, какие фильтры добавлены по дефолту в аннотацию SpringBootApplication
		DefaultFilterAnalyzer.analyzeSpringBootApplication(SpringLivestyle2Application.class);

		SpringApplication springApplication = new SpringApplication(SpringLivestyle2Application.class);
		springApplication.addInitializers(new MyCustomContextInitializer());

		ConfigurableApplicationContext context = springApplication.run(args);

		if(Boolean.parseBoolean(context.getEnvironment().getProperty("triggerStartAndStopContext", "false"))) {
			context.start();
			context.stop();

		}


	}
}
