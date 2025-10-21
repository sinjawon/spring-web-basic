package com.codeit.springwebbasic.member.repository;

import com.codeit.springwebbasic.member.MemberGrade;
import com.codeit.springwebbasic.member.entity.Member;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MemberRepository {

    private  final Map<Long, Member> store =new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    // 회원 저장
    public Member save(Member member) {
        if(member.getId()==null){
            member.setId(sequence.getAndIncrement());
        }
         store.put(member.getId(), member);
         return member;
    }

    // id로 회원 조회
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    // email로 회원 조회
    public Optional<Member> findByEmail(String email) {
       return  store.values()
                .stream()
                .filter(member -> member.getEmail().equals(email))
                .findFirst();//이미옵셔널로 감싸주었다
    }

    // email로 회원 존재 여부 확인
    public boolean existsByEmail(String email) {

       // return findByEmail(email).isPresent(); 이건왜안됨?

        //이게 한명이 일치하면 ture 아니면 falls로한다
     return    store.values().stream()
                .anyMatch(member -> member.getEmail().equals(email));


    }

    // 전체 회원 조회
     public  List<Member> findAll() {
         List<Member> members = new ArrayList<>(store.values());
         return members;
    }

    // 전달된 이름이 포함된 회원 조회
  public List<Member>  findByNameContaining(String name) {
      return  store.values().stream()
                .filter(m -> m.getName().contains(name))
                .collect(Collectors.toList());
    }

    // 등급별 회원 조회
   public List<Member> findByGrade(MemberGrade grade) {
       List<Member> members = store.values()
               .stream()
               .filter(m -> m.getGrade().equals(grade))
               .collect(Collectors.toList());
       return members;
   }

    // 회원 명수 조회
     public int count() {

        int size = store.values().size();
        return size;
    }

    // 회원 전체 삭제
    public void clear() {
        store.clear();
    }

}
