package com.royalfoods.tastytables.data.vo;

import lombok.*;

@Builder
@Data
public class FormattedDataVo {
    private String data;
    private String type;
    private Long size;
}
