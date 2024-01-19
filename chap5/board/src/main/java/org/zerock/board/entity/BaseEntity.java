package org.zerock.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class})
@Getter
abstract public class BaseEntity {
    @Generated
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate
    @Column(name="moddate")
    private LocalDateTime modTime;
}
