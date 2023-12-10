package com.sparta.stairs.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@MappedSuperclass
public class BaseEntity {

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    protected ZonedDateTime createdAt = ZonedDateTime.now();

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    protected ZonedDateTime modifiedAt = ZonedDateTime.now();
}
