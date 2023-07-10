package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {
        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember() {}

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object args1 = joinPoint.getArgs()[0];
            log.info("[logArgs1]{}, args = {}", joinPoint.getSignature(), args1);
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg,..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2]{}, args = {}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg, ..)")
        public void logArgs3(String arg) {
            log.info("[logArgs3] arg={}", arg);
        }

        @Before("allMember() && this(obj)") // 컨테이너에 있는 객체
        public void thisArgs(JoinPoint joinpoint, MemberService obj) {
            log.info("[this]{}, obj={}", joinpoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && target(obj)") // 프록시가 호출할 실제 대상
        public void targetArgs(JoinPoint joinpoint, MemberService obj) {
            log.info("[target]{}, obj={}", joinpoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && @target(annotation)") // 어노테이션정보
        public void atTargetArgs(JoinPoint joinpoint, ClassAop annotation) {
            log.info("[@target]{}, obj={}", joinpoint.getSignature(), annotation);
        }

        @Before("allMember() && @within(annotation)")  // 어노테이션정보
        public void atWithin(JoinPoint joinpoint, ClassAop annotation) {
            log.info("[@within]{}, obj={}", joinpoint.getSignature(), annotation);
        }

        @Before("allMember() && @annotation(annotation)")  // 어노테이션의 값
        public void atWithin(JoinPoint joinpoint, MethodAop annotation) {
            log.info("[@annotation]{}, annotationValue={}", joinpoint.getSignature(), annotation.value());
        }
    }
}
