package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;

@SpringBootTest
public class BoardServiceTests {
    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        BoardDTO boardDTO = BoardDTO.builder()
                .title("TEST..")
                .content("TEST..")
                .writerEmail("user55@aa.com")
                .build();

        boardService.register(boardDTO);
    }

    @Test
    public void testGetList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> pageResult = boardService.getList(pageRequestDTO);

        for(BoardDTO dto : pageResult.getDtoList()){
            System.out.println(dto);
        }
    }

    @Test
    public void testGet(){
        Long bno = 100L;

        BoardDTO dto = boardService.get(100L);

        System.out.println(dto);
    }

    @Test
    public void testRemove(){
        Long bno = 1L;
        boardService.removeWithReplies(bno);
    }

    @Test
    public void testModify(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("change Title")
                .content("change Content")
                .build();

        boardService.modify(boardDTO);
    }
}
