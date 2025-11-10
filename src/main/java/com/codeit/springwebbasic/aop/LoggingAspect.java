package com.codeit.springwebbasic.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;


@Component//빈등록할꺼다
@Aspect//나는 aop담당자다 이시키야
public class LoggingAspect {
    //
  //1,pointcut (어디서?)
    //포인트컷은
    //

    //execution 실행
    //excution([수식어|접근제한자|]리턴타입 [클래스경로.]메서드이름(파라미터)[예외]-[]는생략가능한문법이다])
    //@Pointcut("execution(* com.codeit.springwebbasic.member.controller.MemberController.createMember())")
    //모든 접근 제한자 허용 ,모든 리턴타입 허용 ,MemberController 안에 있는 모든 메서드를 대상 (매개갑은 모든 파라미터)
    //이런경로에 적합한것이 호출되면 미테 메서드가 실행되겠다
    @Pointcut("execution(* com.codeit.springwebbasic.member.controller.MemberController.*(..))")
    private  void allControllerMethod(){
        //위에 지저앟ㄴ 어디에 라는 메서드 위치에 사전에 지정해야 할 여러 설정, 사전 작어 ㅂ등을 명시한다
        //@pointcut을 생략하고,@Arond에 바로 execution을 작성해도된다
        System.out.println("allControllerMethod");
    }
    //2.advice(무엇을?)
    @Around("allControllerMethod()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable{
        //ProceedingJoinPoint: 이 aop가 적용되는 지점 (메서드)에 대한 정보를 담고 있는 객체
       //3. 공통기능
       //
     long start = System.currentTimeMillis();
     //메서드이름을 준다
        String name = joinPoint.getSignature().getName();
        Signature signature = joinPoint.getSignature();
        System.out.println(signature);
        Object[] args = joinPoint.getArgs();//메서드에 전달된 매개값들

        System.out.println("메서드이름"+name);
        System.out.println("매개값"+ Arrays.toString(args));

        //4.핵심기능 실행 (원래의 메서드의 기능을 실행해라)
      //조인포인트한테 메서드정보가 담겨져있다
        Object proceed = joinPoint.proceed();
        //5.공통기능 (종료 및 로그)
        long endTime = System.currentTimeMillis();
        System.out.println("실행시간"+(endTime-start)+"ms");

        return proceed;
    }

    //before: 핵심기능이 실행되기 직전까지만 딱 실행도ㅣㅁ
    //proceed()를 따로 호출하지않느다
    @Before("execution(* com.codeit.springwebbasic.member.controller.MemberController.*(..))")
    public void logBefore(JoinPoint joinPoint){

        //3. 공통기능
        //
        long start = System.currentTimeMillis();
        //메서드이름을 준다
        String name = joinPoint.getSignature().getName();
        Signature signature = joinPoint.getSignature();
        System.out.println(signature);
        Object[] args = joinPoint.getArgs();//메서드에 전달된 매개값들

        System.out.println("메서드이름"+name);
        System.out.println("매개값"+ Arrays.toString(args));


        HttpServletRequest request = getCurrentRequests();



        //이 메서드가 종료되면 알아서 핵심 로직이 수행된다


    }
    @AfterReturning(pointcut  = "execution(* com.codeit.springwebbasic.member.controller.MemberController.*(..))"
            ,returning = "result")
    public void logAfterReturning(JoinPoint joinPoint,Object result){
        String name = joinPoint.getSignature().getName();
        System.out.println("성공"+name);
        System.out.println("qksghksrkqt "+result);
    }

   //핵심기능 실행중에 예외가 발생했을 때 실행됩니다.
    @AfterThrowing(pointcut = "execution(* com.codeit.springwebbasic.member.controller.MemberController.*(..))"
                    ,throwing = "ex"
    )
    public void AfterThrowing(JoinPoint joinPoint, Exception ex){
        System.out.println("에러메시지" + ex.getMessage());
        System.out.println("발생된매서드"+ joinPoint.getSignature().getName());

        //나중에 메신저 (slack,discord등의 알림 전송 로직이 들어갈수도 있네
    }
    //저 위의 두 개를 한꺼번에 아우를수 있느 기능이 Around


   //현재 HTTP요청 가지고오기
    private HttpServletRequest getCurrentRequests(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        //스프링에서 제공하는 요청 관련 정보를 담아놓은 reuestcontexthorlder에게
        //요청 객체를 얻어내서 리턴
        return attributes.getRequest();
    }

}
