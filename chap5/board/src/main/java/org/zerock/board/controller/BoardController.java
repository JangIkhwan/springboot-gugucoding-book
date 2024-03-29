package org.zerock.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.service.BoardService;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO requestDTO, Model model){
        log.info("list....." + requestDTO);

        PageResultDTO<BoardDTO, Object[]> resultDTO = boardService.getList(requestDTO);

        model.addAttribute("result", resultDTO);
    }

    @GetMapping("/register")
    public void register(){
        log.info("register get....");
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO, Model model, RedirectAttributes redirectAttributes){
        log.info("register post...." + boardDTO);

        Long bno = boardService.register(boardDTO);

        log.info("bno=" + bno);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model){
        log.info("bno=" + bno);

        BoardDTO dto = boardService.get(bno);

        log.info("dto=" + dto);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO requestDTO, BoardDTO dto, RedirectAttributes redirectAttributes){
        log.info("modify post...");
        log.info("dto=" + dto);


        boardService.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        redirectAttributes.addAttribute("bno", dto.getBno());

        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String remove(PageRequestDTO requestDTO, Long bno, RedirectAttributes redirectAttributes){
        log.info("remove.....");

        boardService.removeWithReplies(bno);

//        redirectAttributes.addFlashAttribute("page", 1);
//        redirectAttributes.addFlashAttribute("type", requestDTO.getType());
//        redirectAttributes.addFlashAttribute("page", requestDTO.getPage());

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }
}
