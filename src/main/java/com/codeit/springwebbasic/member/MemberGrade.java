package com.codeit.springwebbasic.member;

public enum MemberGrade {
  BRONZE(0),
    SILVER( 5),
    GOLD( 15),
    PLATINUM( 30);
    private final int requiredRentals;

    MemberGrade(int requiredRentals)
    {
        this.requiredRentals = requiredRentals;
    }
    public MemberGrade upgrade(){
         return  switch (this){
             case BRONZE -> SILVER;
             case SILVER -> GOLD;
             case GOLD -> PLATINUM;
             case PLATINUM -> PLATINUM;
         };
    }
}
