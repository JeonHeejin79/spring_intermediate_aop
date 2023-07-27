package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

/**
 * proxyTargetClass=false JDK 동적 프록시를 사용해서 인터페이스 기반 프록시 생성
 * proxyTargetClass=true CGLIB를 사용해서 구체 클래스 기반 프록시 생성
 */
@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // 생략시 기본적으로 JDK 프록시를 쓴다.

        // 프록시를 인터페이스로 캐스팅 할때는 성공
        MemberService proxy_service = (MemberService) proxyFactory.getProxy();

        log.info("proxy_service class = {}", proxy_service.getClass());

        // 프록시를 인터페이스 구체클래스로 캐스팅 할때는 실패
        // -> JDK 동적프록시는 인터페이스 기반으로 구현한거지 구체클래스가 뭔지는 모른다.
        // -> 그래서 구체타입으로 캐스팅은 불가능하다.
        Assertions.assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl proxy_serviceImpl = (MemberServiceImpl) proxyFactory.getProxy();
        });
    }


    @Test
    void cjlibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // CGLIB 프록시를 쓴다.

        // 프록시를 인터페이스로 캐스팅 할때는 성공
        MemberService proxy_service = (MemberService) proxyFactory.getProxy();

        log.info("proxy_service class = {}", proxy_service.getClass());

        // CGLIB 프록시를 인터페이스 구체클래스로 캐스팅 할때는 성공
        MemberServiceImpl proxy_serviceImpl = (MemberServiceImpl) proxyFactory.getProxy();

        log.info("proxy_serviceImpl class = {}", proxy_serviceImpl.getClass());
    }
}
