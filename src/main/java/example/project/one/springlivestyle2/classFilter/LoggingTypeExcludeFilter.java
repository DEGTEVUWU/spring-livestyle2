package example.project.one.springlivestyle2.classFilter;

import org.slf4j.LoggerFactory;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import java.io.IOException;
import org.slf4j.Logger;

/**
 * Не будет вызван, тк используется аннотация SpringBootApplication,
 * кастомные фильтры работают лишь в аннотацией ComponentScan
 */
public class LoggingTypeExcludeFilter extends TypeExcludeFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingTypeExcludeFilter.class);

    public LoggingTypeExcludeFilter() {
        log.info("🔧 TypeExcludeFilter constructor called");
    }

    @Override
    public boolean match(MetadataReader metadataReader,
                         MetadataReaderFactory metadataReaderFactory)
            throws IOException {

        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        String className = classMetadata.getClassName();

        log.debug("Checking class for exclusion: {}", className);

        boolean shouldExclude = className.contains("Mock")
                || className.contains("Test")
                ;

        if (shouldExclude) {
            log.info("🚫 Excluding class: {}", className);
        }

        return shouldExclude;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LoggingTypeExcludeFilter;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}