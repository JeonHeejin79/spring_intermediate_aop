package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import hello.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * JDK 동적프록시는 인터페이스에만 의존관계 주입을 해야한다. 구체클래스에 의존관계 주입은 불가능하다 .
 * CGLIB 프록시는 구체클래스를 상속받아서 만드므로 쿠체클래스와 인터페이스 둘다 의존관계 주입이 가능하다.
 * */
@Slf4j
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // JDK 동적프록시 설정 (없으면 CGLIB 프록시를 쓴다.)
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"}) //  CGLIB 프록시설정
@SpringBootTest
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService class={}", memberService.getClass());
        log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");
    }
}
