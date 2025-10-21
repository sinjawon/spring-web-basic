package com.codeit.springwebbasic.book.service;

import com.codeit.springwebbasic.book.dto.request.BookCreateRequestDto;
import com.codeit.springwebbasic.book.entity.Book;
import com.codeit.springwebbasic.book.entity.BookStatus;
import com.codeit.springwebbasic.book.repository.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor //의존성 자동 주입
public class BookService {

    private final BookRepository bookRepository;

    public Book createBook(BookCreateRequestDto requestDto) {
        //isbn중복체크
        Optional<Book> byIsbn = bookRepository.findByIsbn(requestDto.getIsbn());
        if(byIsbn.isPresent()){
            throw new IllegalArgumentException("이미 있는 isbn인걸료:"+requestDto.getIsbn());
        }
 //초기화  순서를ㄹ 내 마음대로 지정해도 상관없다
        //원하는 필드만 골라서 초기화 하는것이 가능하다
        Book book = Book.builder()
                .isbn(requestDto.getIsbn())
                .title(requestDto.getTitle())
                .author(requestDto.getAuthor())
                .publisher(requestDto.getPublisher())
                .author(requestDto.getAuthor())
                .status(BookStatus.AVAILABLE)
                .build();


    return bookRepository.save(book);



    }

    public Book getBook(@Valid Long id) {
      return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("도서를 찾을수가없다"));
    }
}
