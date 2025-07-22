import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.devoops.client.OdsayRouteClient;
import com.devoops.config.SpringConfig;
import com.devoops.exception.OdsayUtilException;
import com.devoops.vo.Coordinates;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestClient;

//@Import(OdsayRouteClient.class)
@SpringBootTest(classes = {OdsayRouteClient.class, SpringConfig.class})
public class OdsayClientTest {

    @Autowired
    RestClient.Builder restClientBuilder;

    @Autowired
    OdsayRouteClient odsayRouteClient;

    @DisplayName("길찾기 api 요청에 성공할 시, 올바른 대중교통 소요시간을 반환한다")
    @Test
    public void test1() {


    }

    @DisplayName("도착지와 출발지가 700m 이내일 때, OdsayClosestException을 반환한다")
    @Test
    public void test2() {

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

    }


}
