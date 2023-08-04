package org.zerock.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // 테이블을 생성하지 않음. 상속받은 클래스만 테이블로 만듬
@EntityListeners(value={AuditingEntityListener.class}) // 엔티티객체의 생성과 변경을 감지. 생성날짜와 변경날짜 값 지정하기 위함
@Getter
abstract public class BaseEntity {
    @CreatedDate   // JPA가 엔티티 생성시간 처리
    @Column(name="regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate // 수정시간을 처리
    @Column(name="moddate")
    private LocalDateTime modDate;
}
