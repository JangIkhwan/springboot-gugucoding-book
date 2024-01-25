package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoards(){
        IntStream.rangeClosed(1, 100).forEach(i->{
            Member member = Member.builder().email("user" + i + "@aa.com").build();

            Board board = Board.builder()
                    .content("content.." + i)
                    .title("title.." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

    // 지연로딩을 통해서 참조하는 엔티티의 로딩을 미루는 것을 확인
    @Transactional // 지연로딩 이용 시 데이터베이스와의 연결이 끊어지는 것을 막기 위해 트랜잭션으로 처리
    @Test
    public void testRead(){
        Optional<Board> result = boardRepository.findById(20L);

        if(result.isPresent()){
            Board board = result.get();

            System.out.println(board);
            System.out.println(board.getWriter());
        }
    }

    @Test
    public void testGetBoardWithWriter(){
        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testGetBoardWithReply(){
        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        System.out.println("=================");
        for(Object[] boardWithReply : result){
            System.out.println(Arrays.toString(boardWithReply));
        }
    }

    @Test
    public void testGetBoardWithReplyCount(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        // 스트림을 사용?
        result.get().forEach(row->{
            Object[] arr = (Object[]) row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testGetBoardByBno(){
        Object result = boardRepository.getBoardByBno(100L);
        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testSearch1(){
        boardRepository.search1();
    }

    @Test
    public void testSearchPages(){
        boardRepository.searchPages("t", "1",
                PageRequest.of(1, 10, Sort.by("bno").descending()));
    }

}
