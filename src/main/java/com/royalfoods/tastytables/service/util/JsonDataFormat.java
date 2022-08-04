package com.royalfoods.tastytables.service.util;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.royalfoods.tastytables.data.vo.*;

import java.nio.charset.*;

public class JsonDataFormat extends DataFormat{
    final String TYPE = "JSON";
    @Override
    protected FormattedDataVo format(Object obj) {
        FormattedDataVo dataVo = FormattedDataVo.builder().type(TYPE).build();
        try {
            String jsonData = new ObjectMapper().writeValueAsString(obj);
            dataVo.setData(jsonData);
            dataVo.setSize(Long.valueOf(jsonData.getBytes(StandardCharsets.UTF_8).length));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return dataVo;
    }
}
