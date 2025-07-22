package com.devoops.dto;

import com.devoops.des.OdsayResponseDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Optional;
import java.util.OptionalLong;

@JsonDeserialize(using = OdsayResponseDeserializer.class)
public record OdsayResponse(
        Optional<String> code,
        Optional<String> message,
        OptionalLong minutes
) {

}

