package com.sparta.stairs.global;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;


@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class Time {


    @Column(nullable = false)
    protected ZonedDateTime createdDateTime = ZonedDateTime.now();

    @Column
    protected ZonedDateTime modifiedDateTime = ZonedDateTime.now();
}
