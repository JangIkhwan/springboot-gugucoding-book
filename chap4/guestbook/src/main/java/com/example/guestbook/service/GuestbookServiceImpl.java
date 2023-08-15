package com.example.guestbook.service;

import com.example.guestbook.dto.GuestbookDTO;
import com.example.guestbook.dto.PageRequestDTO;
import com.example.guestbook.dto.PageResultDTO;
import com.example.guestbook.entity.Guestbook;
import com.example.guestbook.entity.QGuestbook;
import com.example.guestbook.repository.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{
    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("service----------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO pageRequestDTO){
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder search = getSearch(pageRequestDTO);

        Page<Guestbook> result = repository.findAll(search, pageable);

        Function<Guestbook, GuestbookDTO> fn = entity -> entityToDto(entity);

        return new PageResultDTO<> (result, fn);
    }

    public BooleanBuilder getSearch(PageRequestDTO requestDTO){
        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();

        BooleanBuilder builder = new BooleanBuilder();
        QGuestbook qGuestbook = QGuestbook.guestbook;

        builder.and(qGuestbook.gno.gt(0L));

        if(type == null || keyword.trim().length() == 0){
            return builder;
        }

        BooleanBuilder keywordCond = new BooleanBuilder();
        if(type.contains("t")){
            keywordCond.or(qGuestbook.title.contains(keyword));
        }
        if(type.contains("c")){
            keywordCond.or(qGuestbook.content.contains(keyword));
        }
        if(type.contains("w")){
            keywordCond.or(qGuestbook.writer.contains(keyword));
        }

        return builder.and(keywordCond);
    }

    @Override
    public GuestbookDTO findById(Long gno) {
        Optional<Guestbook> optEntity = repository.findById(gno);
        return optEntity.isPresent()? entityToDto(optEntity.get()) : null;
    }

    @Override
    public void modify(GuestbookDTO dto) {
        Optional<Guestbook> optEntity = repository.findById(dto.getGno());
        if(optEntity.isPresent()){
            Guestbook entity = optEntity.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            repository.save(entity);
        }
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }
}
