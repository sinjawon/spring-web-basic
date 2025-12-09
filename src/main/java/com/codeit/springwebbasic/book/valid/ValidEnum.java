package com.codeit.springwebbasic.book.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//어노테이션의 적용위치가 어디니 메서드니 필드니 파라미터니
//@Target({ElementType.FIELD,ddd, ddd,dd,ddd,}) 가능하다
//필드와파라미터에 적용하겠따
@Target({ElementType.FIELD,ElementType.PARAMETER})
//이 어노테이션의 생명주
//런타임 - 프로그램이 실행되는동안 계속
@Retention(RetentionPolicy.RUNTIME)
//이 어노테이션이 붙은 값의 검사는 누가 담당하는가  구현체가누구일까???????????
@Constraint(validatedBy =EnumValidator.class )

//지금 @interface 틀을 만들어놓고  구현클레스를 만들어야한다
public @interface ValidEnum {

    //허용할 Enum Class를 지정하기 위해 선언
    //사용할 때 @ValidEnum(enumClass = BookType.class) .... 이런식으로 선언해서 사용
    Class<? extends Enum<?>> enumClass();

    //사용하떄 @ValidEnum(ignoreCase = true) 트루뽈스 이그노어케이스
    // 안적으면 기본값 false
    //값이 들어오면 null값 허용여부 체크
    boolean ignoreCase() default false;


    String message() default  "invalid enum value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
