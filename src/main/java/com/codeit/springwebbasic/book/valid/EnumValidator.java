package com.codeit.springwebbasic.book.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;

//어노테이션 사용 시 이 클래스에서 검증 로직 실행
// ConstraintValidator<ValidEnum, 검사하고자하는 값의 타입>
//스트링으로 한 이유는
public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private ValidEnum annotation;//어노테이션에 설정된 값을 저장할 변수

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        //Dto에 붙여놓은 어테이션 정보를 가져와서 멤버변수에 저장하는 역할
        this.annotation = constraintAnnotation;
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //값이 우리가 사용하려는 이넘값에 맞는것이냐니까
        //@notnull이나 @notblank가 처리하도록
        if(value == null){return true;}

        //이넘도 하고 오브잭트도하고
        //사용자가 지정한 enum 클래스를 annotation 변수에서 꺼내기
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if(enumValues != null){
            for(Object enumValue : enumValues){

                //이행위는
                //사용자가 요청과 함께 전달한 값(value)vs enum 상수 이름 (enumValue.toString())
                //만약 어노테이션에 ignoreCse를 true로 주었다면 equalsIgnoreCase로 대소문자 구분없ㅇ ㅣ비교
                if(value.equals(enumValue.toString())
                        || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))){
                    return true;
                }
            }
        }
         //마지막에는 뽈스
        return false;
    }
}
