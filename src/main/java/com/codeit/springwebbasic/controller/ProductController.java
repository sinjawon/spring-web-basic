package com.codeit.springwebbasic.controller;

import com.codeit.springwebbasic.entity.Product;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//이넘은 타임리프같은 -> 뷰를 핸들링하는 컨트롤러 응답페이지를 페이지가 직접관장하는거
//@Controller
@RestController//JSON을 리턴하는 컨트롤러
@RequestMapping("products")
public class ProductController {
    //db가 없으니 가상의 메모리상품 저장소 선언

    private Map<Long,Product> productMap = new HashMap<>();

    //상품의 시리얼넘버를 순차 생성
    private long nextId = 1;

    public ProductController() {
        productMap.put(nextId,new Product(nextId,"에어컨",1000000));
        nextId++;
        productMap.put(nextId,new Product(nextId,"세탁기",1500000));
        nextId++;
        productMap.put(nextId,new Product(nextId,"콤퓨타",2000000));
        nextId++;
    }

    //1.쿼리스트링 읽기
    //get방식은 간단한 정보를 url에뭍혀서 줄수있다
    //
    //방식
    //
    //url?name=value&name=value&……  ?다음 & 또또 이런식
    //?id=몰랑 & price= 몰랑
//    @Getmapping은 나중에 나왔다
//
//원형은 @ReuestMapping()

    //헨들러메서드  요청을 받아서 처리할수있는 메서드
    //스프링4버전까지
   /* @RequestMapping(value = "/products",method = RequestMethod.GET)
    public Product getProduct(){

    }
*/
 /*   @GetMapping("products")
    public Product getProduct(HttpServletRequest request){
        String id = request.getParameter("id");
        String price = request.getParameter("price");
        System.out.println("id:"+id);
        System.out.println("price:"+price);
   return productMap.get(Long.parseLong(id));
    }*/

    @GetMapping
    public Product getProduct(
            @RequestParam("id")Long id,
            @RequestParam(value = "price",required = false ,defaultValue = "5000") int price) {
        System.out.println("id =" +id);
        System.out.println("price ="+price);
        return productMap.get(id);
    }

    //localhost:8081/products/1 -> 1번상품조회
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        System.out.println("id =" +id);
        return productMap.get(id);
    }

}
