package com.example.guestbook.repository;

import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {
    @Autowired
    GuestbookRepository repository;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1, 300).forEach(i ->{
            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("content...." + i)
                    .writer("writer" + (i % 10))
                    .build();
            System.out.println(repository.save(guestbook));
        });
    }

    @Test
    public void updateTest(){
        Optional<Guestbook> result = repository.findById(300L);
        if(result.isPresent()){
            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title....");
            guestbook.changeContent("Changed Content....");

            repository.save(guestbook);
        }
    }

    @Test
    public void testQuery1(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";
        BooleanExpression expression = qGuestbook.title.contains(keyword);
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(expression);
        Page<Guestbook> result = repository.findAll(builder, pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    @Test
    public void testQuery2(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;
        BooleanBuilder builder = new BooleanBuilder();
        String keyword = "1";
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll);
        builder.and(qGuestbook.gno.gt(0L));
        Page<Guestbook> result = repository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
}
