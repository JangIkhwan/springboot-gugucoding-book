package org.zerock.board.service;

import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;


public interface BoardService {
    public Long register(BoardDTO dto);

    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO requestDTO);

    public BoardDTO get(Long bno);

    public void remove(Long bno);
    default public Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder().email(dto.getWriterEmail()).build();
        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member).
                build();
        return board;
    }

    default public BoardDTO entityToDto(Board board, Member member, Long replyCount){
        BoardDTO dto = BoardDTO.builder()
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .title(board.getTitle())
                .content(board.getContent())
                .bno(board.getBno())
                .modDate(board.getModDate())
                .regDate(board.getRegDate())
                .replyCount(replyCount.intValue())
                .build();
        return dto;
    }
}
