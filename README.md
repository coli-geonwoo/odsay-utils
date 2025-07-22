# Odsay-Utils

## 설명
오디세이 API를 활용하여 대중교통을 이용하여 두 좌표간 이동 시, 최단 시간(분)을 반환합니다.

## 사용방법

### step1. 의존성 추가

- 현재 버전에서는 `java 17` 기준 프로젝트에만 호환됩니다.
- build.gradle에서 의존성을 추가합니다.

```groovy
implementation 'io.github.coli-geonwoo:odsay-utils:1.0.1'
```

### step2. 오디세이 API 등록
- [ODsay API](https://lab.odsay.com/)에 회원가입합니다
- 애플리케이션 등록 후, Server IP에 나의 IPv4 주소를 등록합니다
![img.png](images/img.png)


### step3. OdsayRouteClient 사용
- 출발지, 도착지의 Coordinates 좌표 객체를 넘기면 대중교통 최단 소요시간(분)이 반환됩니다.
ex)
```java
    public void test() {
        Coordinates origin = new Coordinates("37.505419", "127.050817");
        Coordinates target = new Coordinates("37.515253", "127.102895");

        long minutes = odsayRouteClient.calculateRouteMinutes(apiKey, origin, target);

        assertThat(minutes).isEqualTo(15);
    }
```

### OdsayRouteClient 에러객체
| Exception                  | Description                      |
|----------------------------|----------------------------------|
| OdsayUtilException         | 오디세이 서버 자체 에러          |
| OdsayWrongApiKeyException  | apiKey 에러 or IP 미등록         |
| OdsayClosestPlaceException | 출발지와 도착지가 700m 이내일 때 |
| OdsayBadRequestException   | wrong input value                |


