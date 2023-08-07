package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestBookServiceTests {
    @Autowired
    private GuestbookService service;
    @Test
    public void testRegister(){
        GuestbookDTO dto = GuestbookDTO.builder()
                .title("Sample Title...")
                .content("Sample content...")
                .writer("user0")
                .build();

        System.out.println(service.register(dto));
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> result = service.getList(pageRequestDTO);

        System.out.println("Next: " + result.isNext());
        System.out.println("Prev: " + result.isPrev());
        System.out.println("Total: " + result.getTotalPages());

        System.out.println("dtoList --------------");
        for(GuestbookDTO dto : result.getDtoList()){
            System.out.println(dto);
        }

        System.out.println("pageList--------------");
        for(Integer i : result.getPageList()){
            System.out.println(i);
        }
    }
}
