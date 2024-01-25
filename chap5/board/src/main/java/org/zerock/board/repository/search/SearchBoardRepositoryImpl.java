package org.zerock.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.QBoard;
import org.zerock.board.entity.QMember;
import org.zerock.board.entity.QReply;

import java.util.List;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {
    public SearchBoardRepositoryImpl(){
        super(Board.class);
    }

    @Override
    public Board search1() {
        log.info("search1....");

        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board);
        query.where(board.bno.eq(1L));
        query.select(board);

        log.info("----------------");
        log.info(query);
        log.info("----------------");

        List<Board> list = query.fetch();

        return null;
    }

    @Override
    public Page<Object[]> searchPages(String type, String keyword, Pageable pageable) {
        log.info("searchPages....");

        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(member).on(member.eq(board.writer));
        query.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tupleQuery = query.select(board, member.email, reply.count());

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);
        builder.and(expression);
        if(type != null){
            BooleanBuilder searchBuilder = new BooleanBuilder();
            if(type.contains("t")){
                searchBuilder.or(board.title.contains(keyword));
            }
            if(type.contains("w")){
                searchBuilder.or(member.email.contains(keyword));
            }
            if(type.contains("c")){
                searchBuilder.or(board.content.contains(keyword));
            }
            builder.and(searchBuilder);
        }
        tupleQuery.where(builder);
        tupleQuery.groupBy(board);

        log.info("-------------------");
        log.info(tupleQuery);
        log.info("-------------------");

        List<Tuple> result = tupleQuery.fetch();

        log.info(result);

        return null;
    }
}
