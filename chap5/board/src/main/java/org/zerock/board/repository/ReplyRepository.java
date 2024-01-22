package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Modifying // delete나 update 연산시에 이 어노테이션 필요
    @Query(value="DELETE FROM Reply r WHERE r.board.bno = :bno")
    public void deleteByBno(@Param("bno") Long bno);
}
