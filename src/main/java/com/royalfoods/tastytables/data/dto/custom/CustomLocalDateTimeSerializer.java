package com.royalfoods.tastytables.data.dto.custom;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.std.*;

import java.io.*;
import java.time.*;
import java.time.format.*;

public class CustomLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public CustomLocalDateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }
    public CustomLocalDateTimeSerializer(){
        this(null);

    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeRawValue(formatter.format(value));
    }
}
