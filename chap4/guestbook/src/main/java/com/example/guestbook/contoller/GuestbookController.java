package com.example.guestbook.contoller;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {
    private final GuestbookService service;

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    // 목록
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list----------" + pageRequestDTO);
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    // 등록
    @GetMapping("/register")
    public void register(){
        log.info("register-------");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO guestbookDTO, RedirectAttributes redirectAttributes){
        log.info("dto = " + guestbookDTO);

        Long id = service.register(guestbookDTO);

        redirectAttributes.addFlashAttribute("msg", id);

        return "redirect:/guestbook/list";
    }

    // 조회
    @GetMapping({"/read", "/modify"})
    public void read(Long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        log.info("read------");

        GuestbookDTO dto = service.findById(gno);
        model.addAttribute("dto", dto);
    }

    // 수정
    @PostMapping("/modify")
    public String modify(GuestbookDTO dto, PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        log.info("modify post---------------");

        service.modify(dto);

        // 리다이렉트 시 이전의 정보를 유지하기 위해서 애트리뷰트를 추가
        redirectAttributes.addAttribute("gno", dto.getGno());
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        return "redirect:/guestbook/read";
    }

    // 삭제
    @PostMapping("remove")
    public String remove(Long gno, RedirectAttributes redirectAttributes){
        log.info("remove-------------");

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }
}

