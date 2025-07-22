import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.devoops.client.OdsayRouteClient;
import com.devoops.exception.OdsayBadRequestException;
import com.devoops.exception.OdsayClosestPlaceException;
import com.devoops.exception.OdsayUtilException;
import com.devoops.vo.Coordinates;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest(classes = {OdsayRouteClient.class})
public class OdsayClientTest {

    String apiKey;

    @Autowired
    OdsayRouteClient odsayRouteClient;

    @DisplayName("길찾기 api 요청에 성공할 시, 올바른 대중교통 소요시간을 반환한다")
    @Test
    public void test1() {
        Coordinates origin = new Coordinates("37.505419", "127.050817");
        Coordinates target = new Coordinates("37.515253", "127.102895");

        long minutes = odsayRouteClient.calculateRouteMinutes(apiKey, origin, target);

        assertThat(minutes).isEqualTo(15);
    }

    @DisplayName("도착지와 출발지가 700m 이내일 때, OdsayClosestException을 반환한다")
    @Test
    public void test2() {
        Coordinates origin = new Coordinates("37.505419", "127.050817");
        Coordinates target = new Coordinates("37.505419", "127.050817");

        assertThatThrownBy(() -> odsayRouteClient.calculateRouteMinutes(apiKey, origin, target))
                .isInstanceOf(OdsayClosestPlaceException.class);
    }

    @DisplayName("잘못된 api-key로 요청 시, OdsayUtilException을 반환한다")
    @Test
    public void test3() {
        Coordinates origin = new Coordinates("37.505419", "127.050817");
        Coordinates target = new Coordinates("37.515253", "127.102895");

        assertThatThrownBy(() -> odsayRouteClient.calculateRouteMinutes("wrongKey", origin, target))
                .isInstanceOf(OdsayUtilException.class);
    }

    @DisplayName("그 외의 에러의 경우 OdsayBadRequestException을 반환한다")
    @Test
    public void test4() {
        Coordinates origin = new Coordinates("37.505419", "127.050817");
        Coordinates target = new Coordinates("35.548756", "139.780203"); // 도쿄 국제공항

        assertThatThrownBy(() -> odsayRouteClient.calculateRouteMinutes(apiKey, origin, target))
                .isInstanceOf(OdsayBadRequestException.class);
    }
}
