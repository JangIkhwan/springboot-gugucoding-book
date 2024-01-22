package org.zerock.board.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {
    private List<DTO> dtoList;
    private int totalPages;
    private int page, size;
    private int start, end;
    private boolean prev, next;
    private List<Integer> pageList;
    public PageResultDTO(Page<EN> page, Function<EN, DTO> fn){
        dtoList = page.stream().map(fn).collect(Collectors.toList());
        totalPages = page.getTotalPages();
        makePageList(page.getPageable());
    }

    public void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1; //
        this.size = pageable.getPageSize();

        int tempEnd = (int) (Math.ceil(page / 10.0)) * size; //

        start = tempEnd - 9;
        end = totalPages > tempEnd ? tempEnd : totalPages;

        prev = start > 1;
        next = totalPages > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
