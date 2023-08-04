package org.zerock.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{
    private final GuestbookRepository guestbookRepository;

    @Override
    public Long register(GuestbookDTO dto) {

        log.info("DTO-----------------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        guestbookRepository.save(entity);

        return entity.getGno();
    }
}
