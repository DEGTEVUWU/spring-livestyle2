package example.project.one.springlivestyle2.classFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.annotation.AnnotationUtils;
import java.lang.annotation.Annotation;
import java.util.Arrays;

public class DefaultFilterAnalyzer {

    public static void analyzeSpringBootApplication(Class<?> applicationClass) {
        System.out.println("=== ANALYSIS OF @SpringBootApplication DEFAULTS ===");

        // Получаем аннотацию @SpringBootApplication
        SpringBootApplication springBootAnnotation =
                applicationClass.getAnnotation(SpringBootApplication.class);

        if (springBootAnnotation != null) {
            System.out.println("🎯 @SpringBootApplication found");

            // Анализируем @ComponentScan внутри @SpringBootApplication
            ComponentScan componentScan = AnnotationUtils.findAnnotation(
                    applicationClass, ComponentScan.class);

            if (componentScan != null) {
                System.out.println("🔧 Default exclude filters in @SpringBootApplication:");

                Arrays.stream(componentScan.excludeFilters())
                        .forEach(filter -> {
                            System.out.println(" - Type: " + filter.type());
                            System.out.println("   Classes: " + Arrays.toString(filter.classes()));
                            System.out.println("   Pattern: " + Arrays.toString(filter.pattern()));
                        });
            }
        }
    }
}
