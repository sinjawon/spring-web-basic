package com.codeit.springwebbasic.book.controller;

import com.codeit.springwebbasic.book.dto.request.BookCreateRequestDto;
import com.codeit.springwebbasic.book.dto.response.BookResponseDto;
import com.codeit.springwebbasic.book.entity.Book;
import com.codeit.springwebbasic.book.repository.BookRepository;
import com.codeit.springwebbasic.book.service.BookService;
import com.codeit.springwebbasic.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor//final 변수를 초기화하는 매개값을  전달받는 생성자
                       //의존성주입은 대부분  이방식
public class BookContoller {

    private final BookService bookService;

    //프론트쪽이랑 약속을 했다
    //
      /*
      * {
      * "title :" string,
      * "author:" string,
      * "isbn:" string,
      * "publisher :" string,
      * "publishedDate :"date //자바는 대부분 로컬데이트로 해준다 스프링이 알아서해준다
      * }
      * */
    //
    @RequestMapping(value = "/api/books",method = RequestMethod.POST)
    public BookResponseDto createBook(@Valid @RequestBody BookCreateRequestDto requestDto){
        Book book = bookService.createBook(requestDto);
        return  BookResponseDto.from(book);
    }

    //조회요청
    //매서드이름 get 책 1권조회요청 id
   @GetMapping("/api/books/{id}")
    public BookResponseDto getBook(@PathVariable Long id){
       Book book = bookService.getBook(id);
       return BookResponseDto.from(book);
    }

}
