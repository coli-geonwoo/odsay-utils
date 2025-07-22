package com.devoops.client;

import com.devoops.dto.OdsayResponse;
import com.devoops.exception.OdsayUtilException;
import com.devoops.mapper.OdsayResponseMapper;
import com.devoops.vo.Coordinates;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class OdsayRouteClient {

    private static final String BASE_URL = "https://api.odsay.com/v1/api/searchPubTransPathT";

    private final String apiKey;
    private final RestClient restClient;

    public OdsayRouteClient(String apiKey, RestClient.Builder builder) {
        this(apiKey, builder.build());
    }

    public long calculateRouteMinutes(Coordinates origin, Coordinates target) {
        OdsayResponse response = getOdsayResponse(origin, target);
        return responseToRouteTime(response);
    }

    private OdsayResponse getOdsayResponse(Coordinates origin, Coordinates target) {
        OdsayResponse response = restClient.get()
                .uri(makeURI(origin, target))
                .retrieve()
                .body(OdsayResponse.class);
        return Objects.requireNonNullElseGet(response, () -> {
            throw new OdsayUtilException("서버 에러");
        });
    }

    private URI makeURI(Coordinates origin, Coordinates target) {
        String uri = BASE_URL
                + "?SX=" + origin.getLongitude()
                + "&SY=" + origin.getLatitude()
                + "&EX=" + target.getLongitude()
                + "&EY=" + target.getLatitude()
                + "&apiKey=" + apiKey;
        try {
            return new URI(uri);
        } catch (URISyntaxException exception) {
            throw new OdsayUtilException(exception.getMessage());
        }
    }

    private long responseToRouteTime(OdsayResponse response) {
        return OdsayResponseMapper.mapMinutes(response);
    }
}

