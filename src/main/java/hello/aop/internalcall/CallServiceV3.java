package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

/**
 * 구조를 변경 분리
 * internal 호출하는 부분을 별도의 클래스로 분리
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {

    private final InternalService internalService;

    public void external() {
        log.info("call external");
        this.internal();
    }

    public void internal() {
        internalService.internal();
    }
}
