package com.codeit.springwebbasic.book.repository;

import com.codeit.springwebbasic.book.entity.Book;
import com.codeit.springwebbasic.member.MemberGrade;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Repository
public class BookRepository {
    //ConcurrentHashMap<>(); 기능자체는 동일한데  왜 사용
    //멀티 스레드에서도 안전하게 사용할 수 있는 Hashmap 사용
    //여러개의 작을 하나의 해쉬맵으로 스랟도
    private final Map<Long, Book> store =new ConcurrentHashMap<>();
    private final AtomicLong squence = new AtomicLong(1);

    //도서저장
    public Book save(Book book) {
        if(book.getId()==null) {
            book.setId(squence.getAndIncrement());//ㅏㄱㅄ을 얻고 나서 값을 하나 증가
        }
        store.put(book.getId(), book);
        return book;
    }
    //도서조회
    public List<Book> findAll() {
        //멥에서 value만 전부 꺼낸 후List로 반환
        return new ArrayList<>(store.values());

    }

    //한번 검증할수이는
    //id에 따라 조회가 안될 수도 있다 -> 조회가 안되면null 리턴 ->npe발생가능성높다
    //optinal로 포장해서 호풀한 쪽에서 충분한 검증후에 book 객체를 사용하도록 유도한다
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    //isbn으로 북 조회  파악
    public Optional<Book> findByIsbn(String isbn) {
        return store.values().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();
    }


    public void findByTitleContaining(String title) {
        store.values().stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }



}
