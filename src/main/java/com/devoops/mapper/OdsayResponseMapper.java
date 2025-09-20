package com.devoops.mapper;

import com.devoops.dto.OdsayResponse;
import com.devoops.exception.OdsayBadRequestException;
import com.devoops.exception.OdsayClosestPlaceException;
import com.devoops.exception.OdsayTooManyRequestException;
import com.devoops.exception.OdsayUtilException;
import com.devoops.exception.OdsayWrongApiKeyException;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OdsayResponseMapper {

    private static final String WRONG_API_KEY_MESSAGE = "[ApiKeyAuthFailed] ApiKey authentication failed.";
    private static final String CLOSE_LOCATION_CODE = "-98"; //출발지-도착지가 700m 이내일 때
    private static final String TOO_MANY_REQUEST_CODE = "429"; //1초당 호출 가능량을 넘었을 때
    private static final String ODSAY_SERVER_ERROR = "500";

    public static long mapMinutes(OdsayResponse response) {
        log.info("Odsay response: {}", response);
        if (response == null) {
            throw new OdsayUtilException("오디세이로부터 온 응답이 null입니다");
        }

        if (isCloseLocation(response)) {
            throw new OdsayClosestPlaceException("출발지와 도착지가 700m이내입니다");
        }

        if(isTooManyRequest(response)) {
            throw new OdsayTooManyRequestException("1초당 호출 가능한 횟수를 초과하였습니다");
        }

        if (response.code().isPresent()) {
            checkOdsayException(response);
        }

        return response.minutes()
                .orElseThrow(() -> new OdsayUtilException("OdsayResponse minutes is Empty"));
    }

    private static boolean isCloseLocation(OdsayResponse response) {
        Optional<String> code = response.code();
        return code.isPresent() && CLOSE_LOCATION_CODE.equals(code.get());
    }

    private static boolean isTooManyRequest(OdsayResponse response) {
        Optional<String> code = response.code();
        return code.isPresent() && TOO_MANY_REQUEST_CODE.equals(code.get());
    }

    private static void checkOdsayException(OdsayResponse response) {
        if(isWrongApiKey(response)) {
            throw new OdsayWrongApiKeyException("오디세이 API KEY가 잘못되었거나, IP가 제대로 등록되어 있지 않습니다");
        }

        if (isServerErrorCode(response)) {
            log.error("ODsay Server Error: {}", response);
            throw new OdsayUtilException("오디세이 서버 에러");
        }

        throw new OdsayBadRequestException("ODSay BAD REQUEST Error: " + response);
    }

    private static boolean isWrongApiKey(OdsayResponse response) {
        Optional<String> message = response.message();
        return isServerErrorCode(response)
                && message.isPresent() && message.get().equals(WRONG_API_KEY_MESSAGE);
    }

    private static boolean isServerErrorCode(OdsayResponse response) {
        Optional<String> code = response.code();
        return code.isPresent() && ODSAY_SERVER_ERROR.equals(code.get());
    }
}

