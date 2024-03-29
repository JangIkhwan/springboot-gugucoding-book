package org.zerock.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Board;
import org.zerock.board.repository.search.SearchBoardRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {

    @Query(value="SELECT b, w FROM Board b LEFT JOIN b.writer w WHERE b.bno = :bno")
    public Object getBoardWithWriter(@Param("bno")Long bno);

    @Query(value="SELECT b, r FROM Board b LEFT JOIN Reply r ON b = r.board WHERE b.bno = :bno")
    public List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value="SELECT b, w, count(r) FROM Board b LEFT JOIN b.writer w"
            + " LEFT JOIN Reply r ON r.board = b GROUP BY b",
            countQuery = "SELECT count(b) FROM Board b")
    public Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query(value="SELECT b, w, count(r)"
            + " FROM Board b LEFT JOIN b.writer w"
            + " LEFT OUTER JOIN Reply r ON r.board = b WHERE b.bno = :bno")
    public Object getBoardByBno(@Param("bno") Long bno);
}
