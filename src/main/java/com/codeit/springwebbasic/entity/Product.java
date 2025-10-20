package com.codeit.springwebbasic.entity;


import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long serialNo;
    private String name;
    private  int price;

}
