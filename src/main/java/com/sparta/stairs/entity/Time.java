package com.sparta.stairs.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;

@Getter
@MappedSuperclass
public abstract class Time {

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime createDateTime;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private ZonedDateTime modifiedDateTime;
}
