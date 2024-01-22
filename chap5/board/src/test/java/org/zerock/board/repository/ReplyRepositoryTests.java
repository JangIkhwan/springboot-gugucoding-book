package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReplys(){
        IntStream.rangeClosed(1, 300).forEach(i->{
            long bno = (long)(Math.random() * 100) + 1;

            Board board = Board.builder()
                    .bno(bno)
                    .build();

            Reply reply = Reply.builder()
                    .replyer("guest")
                    .text("reply.." + i)
                    .board(board)
                    .build();

            replyRepository.save(reply);
        });
    }

    // 즉시로딩과 지연로딩의 차이를 알아보기 위한 테스트
    @Test
    public void testRead(){
        Optional<Reply> result = replyRepository.findById(20L);

        if(result.isPresent()){
            Reply reply = result.get();

            System.out.println(reply);
            System.out.println(reply.getBoard());
        }
    }
}
