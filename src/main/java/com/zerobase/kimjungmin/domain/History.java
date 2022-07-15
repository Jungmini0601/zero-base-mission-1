package com.zerobase.kimjungmin.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class History {
    private Integer ID;
    private Double LAT; // x좌표
    private Double LNT; // y좌표
    private String HISTORY_AT; // 작업일자
}
