package com.codeit.springwebbasic.practice;

import org.springframework.web.bind.annotation.*;

import java.util.Map;











@RestController//JSON을 리턴하는 컨트롤러
@RequestMapping("practice/api/v1")
public class PracticeController {

    // 문제 1
    // 요청 url: /practice/api/v1/welcome
    // 요청 데이터: X
    // 리턴: "Welcome to Spring Web!"
    @GetMapping("/welcome")
    public String getProductMap(){
           return "welcome to Spring Web";
    }
    // 문제 2
    // 요청 url: /practice/api/v1/product/{id}
    // 요청 데이터: 아이디가 url에 포함되어 옴
    // 리턴: "Product ID: ~~~"
    @GetMapping("/product/{id}")
    public String getProductMap(@PathVariable("id")String id){

           return "Product  ID" + id;
    }

    // 문제 3
    // 요청 url: /practice/api/v1/books
    // 요청 데이터: 쿼리 스트링으로 author, genre
    // 리턴: "Author: ???, Genre: ???"
    @GetMapping("/books")
    public String getProductMap(@RequestParam("Authort")String Authort,@RequestParam("Genre")String Genre){
      return "Authort"+ Authort+", Genre"+Genre;
    }
    // 문제 4
    // 요청 url: /practice/api/v1/search
    // 요청 데이터: 쿼리 스트링으로 query(검색어), page(페이지, 값이 오지 않는다면 기본값 1)
    // 리턴: "검색어: ???, 요청 페이지: ???"
    @GetMapping("/search")
    public String getProductMap(
             @RequestParam("query")String query
            ,@RequestParam(value = "page",required = false ,defaultValue = "1") int page){
                 return "검색어: "+ query +"페이지: " +page;

    }
}
