package com.codeit.springwebbasic.book.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;


public class BannedWordValidator implements ConstraintValidator<NoBannedWord, String> {

    //기존의 옵션을 사용해서 집어넣는용도 였다
    //이니셜라이즈

    private final List<String> bannedWords =List.of("멍청이","바보","메롱");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if (value == null){
            return true;
        }
        for (String word : bannedWords) {
            if(value.contains(word)){
                return false;
            }
        }

        return false;
    }
}
