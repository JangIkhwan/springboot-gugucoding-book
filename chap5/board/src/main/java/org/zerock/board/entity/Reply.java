package org.zerock.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude="board") // 연관관계 사용 시, 참조하는 엔티티는 제외하는 것이 좋음
public class Reply extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    private String replyer;
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}
