package example.project.one.springlivestyle2;

import example.project.one.springlivestyle2.BPP.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Profiled
public class BeanToInject {
    private static final Logger log = LoggerFactory.getLogger(BeanToInject.class);

    public void testMethod() {
        int result = 2+2*2;
        LogUtils.infoWithStacktrace(log, "Первичный лог в бине с @Profiled - имитируем внутреннюю логику: 2+2*2= " + result);
    }
}