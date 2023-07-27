package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV1Test {

    @Autowired CallServiceV1 callServiceV1; // 프록시

    @Test
    void external() {
        // AOP
        // log.info("target={}", callServiceV0.getClass());
        callServiceV1.external();
    }

    @Test
    void internal() {
        // AOP
        callServiceV1.internal();
    }
}