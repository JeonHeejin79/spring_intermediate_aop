package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * 지연조회
 * ObjectProvider 를 통해 지연해서 BEAN 을 조회할 수 있다.
 *
 * ObjectProvider 는 객체를 스프링 컨테이너에서 조회하는 것을 스프링 빈 생성 시점이 아니라 실제
 * 객체를 사용하는 시점으로 지연할 수 있다.
 * callServiceProvider.getObject() 를 호출하는 시점에 스프링 컨테이너에서 빈을 조회한다.
 * 여기서는 자기 자신을 주입 받는 것이 아니기 때문에 순환 사이클이 발생하지 않는다.
 */
@Slf4j
@Component
public class CallServiceV2 {

    // private final ApplicationContext applicationContext;
    private final ObjectProvider<CallServiceV2> callServiceProvider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider) {
        this.callServiceProvider = callServiceProvider;
    }


    public void external() {
        log.info("call external");
        // CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = callServiceProvider.getObject();
        callServiceV2.internal(); // 외부 메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
