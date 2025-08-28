package example.project.one.springlivestyle2.classFilter;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Будет вызван, тк указан в META-INF файле, сделано для демонстрации(хаха, лисенеры вызыаются, ожидаемо)
 * , однако логика в самом кастомном классе-фильтре(LoggingTypeExcludeFilter) - не будет вызван
 * - см описание LoggingTypeExcludeFilter
 */
@Component
public class TypeExcludeFilterListener implements
        ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        System.out.println("=== TYPE EXCLUDE FILTER REGISTRATION ===");

        // Получаем все зарегистрированные TypeExcludeFilter
        List<TypeExcludeFilter> filters = SpringFactoriesLoader.loadFactories(
                TypeExcludeFilter.class,
                event.getSpringApplication().getClassLoader()
        );

        System.out.println("Found " + filters.size() + " TypeExcludeFilter(s):");
        filters.forEach(filter ->
                System.out.println(" - " + filter.getClass().getName()));
    }
}