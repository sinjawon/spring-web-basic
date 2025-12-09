package com.codeit.springwebbasic.book.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BannedWordValidator.class)
public @interface NoBannedWord {

   // Class<? extends Enum<?>> enumClass();

    String message() default "제목에 금지도닌 단어가 포함되어있다";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
