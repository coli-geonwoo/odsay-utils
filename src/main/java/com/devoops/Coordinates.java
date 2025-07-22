package com.devoops;

import com.devoops.exception.OdsayUtilException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Coordinates {

    private String latitude;

    private String longitude;

    public Coordinates(String latitude, String longitude) {
        validateNull(latitude);
        validateNull(longitude);
        this.latitude = formatForDoubleConversion(latitude);
        this.longitude = formatForDoubleConversion(longitude);
    }

    private void validateNull(String value) {
        if (value == null) {
            throw new OdsayUtilException("들어온 좌표가 null 값입니다");
        }
    }

    private String formatForDoubleConversion(String rawCoordinate) {
        try {
            BigDecimal coordinate = new BigDecimal(rawCoordinate);
            return coordinate.setScale(7, RoundingMode.HALF_UP).toString();
        } catch (NumberFormatException exception) {
            throw new OdsayUtilException("Double로 변환 불가능한 좌표입니다.");
        }
    }
}

