# JEOGI_BE
# 🌍 저기어때 - 실시간 여행 계획 플랫폼

[![Java](https://img.shields.io/badge/Java-21-brightgreen.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen.svg)](https://spring.io/projects/spring-boot)

저기어때는 사용자가 여행지를 검색하고, 실시간으로 소통하며, 주차장 예약까지 가능한 종합 여행 계획 플랫폼입니다.

## 📋 목차

- [🌟 주요 기능](#-주요-기능)
- [🛠 기술 스택](#-기술-스택)
- [📊 시스템 아키텍처](#-시스템-아키텍처)
- [📡 API 문서](#-api-문서)
- [💬 실시간 채팅](#-실시간-채팅)
- [🅿️ 주차장 예약 시스템](#️-주차장-예약-시스템)
- [🔐 보안](#-보안)

## 🌟 주요 기능

### 🔍 여행지 검색
- **지역별 검색**: 시/도, 구/군 단위로 여행지 필터링
- **카테고리별 검색**: 관광지, 문화시설, 레포츠, 쇼핑 등 다양한 카테고리
- **위치 기반 검색**: 현재 위치 또는 특정 좌표 기준 반경 검색
- **고성능 검색**: QueryDSL을 활용한 동적 쿼리 최적화

### 💬 실시간 채팅
- **WebSocket 기반**: STOMP 프로토콜을 통한 실시간 양방향 통신
- **채팅방 관리**: 여행지별 채팅방 생성 및 참여
- **메시지 캐싱**: Redis를 활용한 채팅 메시지 캐시 관리
- **메시지 이력**: 데이터베이스 저장 및 조회 기능

### 🅿️ 주차장 예약
- **실시간 예약**: 동시성 제어를 통한 안전한 예약 시스템
- **예약 가능 공간 조회**: 특정 시간대 주차 가능 공간 실시간 확인
- **예약 관리**: 개인 예약 내역 조회 및 취소
- **중복 예약 방지**: 동일 시간대 중복 예약 차단

### 🔐 사용자 인증
- **회원가입/로그인**: Spring Security 기반 세션 인증
- **JSON 로그인**: 커스텀 필터를 통한 JSON 형태 로그인 지원
- **세션 관리**: 안전한 세션 기반 상태 관리

## 🛠 기술 스택

### Backend
| Category | Technology | Version |
|----------|------------|---------|
| **Language** | Java | 21 |
| **Framework** | Spring Boot | 3.4.5 |
| **Security** | Spring Security | 6.x |
| **Data Access** | Spring Data JPA | 3.x |
| **Query Builder** | QueryDSL | 5.0.0 |
| **WebSocket** | Spring WebSocket | 3.4.5 |
| **Database** | H2 (Dev), MySQL (Prod) | - |
| **Cache** | Redis | - |
| **API Documentation** | Swagger/OpenAPI | 2.8.6 |
| **Build Tool** | Gradle | 8.x |

## 📊 시스템 아키텍처

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Vue.js SPA    │    │  Spring Boot    │    │     Database    │
│                 │    │   Application   │    │                 │
│  ┌───────────┐  │    │  ┌───────────┐  │    │  ┌───────────┐  │
│  │   Router  │  │◄──►│  │    Web    │  │◄──►│  │    H2     │  │
│  │   Store   │  │    │  │   Layer   │  │    │  │   MySQL   │  │
│  │Components │  │    │  └───────────┘  │    │  └───────────┘  │
│  └───────────┘  │    │  ┌───────────┐  │    └─────────────────┘
│                 │    │  │  Service  │  │    
│  ┌───────────┐  │    │  │   Layer   │  │    ┌─────────────────┐
│  │WebSocket  │  │◄──►│  └───────────┘  │    │      Redis      │
│  │  Client   │  │    │  ┌───────────┐  │◄──►│                 │
│  └───────────┘  │    │  │Repository │  │    │  ┌───────────┐  │
└─────────────────┘    │  │   Layer   │  │    │  │   Cache   │  │
                       │  └───────────┘  │    │  │  Session  │  │
┌─────────────────┐    │                 │    │  │   Chat    │  │
│   Map Service   │◄──►│  ┌───────────┐  │    │  └───────────┘  │
│  (Kakao Maps)   │    │  │WebSocket  │  │    └─────────────────┘
└─────────────────┘    │  │  Handler  │  │
                       │  └───────────┘  │
                       └─────────────────┘
```

## 📡 API 문서

### 🔐 인증 API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | 회원가입 |
| POST | `/api/auth/login` | 로그인 |
| POST | `/api/auth/logout` | 로그아웃 |
| GET | `/api/auth/check` | 로그인 상태 확인 |

#### 회원가입 요청
```json
{
  "id": "user123",
  "password": "password123",
  "name": "홍길동",
  "email": "user@example.com"
}
```

#### 로그인 요청
```json
{
  "id": "user123",
  "password": "password123"
}
```

### 🗺 여행지 검색 API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/search/condition` | 조건별 여행지 검색 |
| GET | `/api/search/local` | 지역 목록 조회 |
| GET | `/api/search/contents-type` | 콘텐츠 타입 조회 |
| GET | `/api/attractions/{id}` | 특정 여행지 상세 조회 |

#### 여행지 검색 요청
```bash
GET /api/search/condition?metropolitanCode=1&localCode=1&contentTypeId=12&isRangeSearch=true&latitude=37.5665&longitude=126.9780&range=5.0
```

#### 검색 응답
```json
{
  "message": "검색 성공",
  "length": 25,
  "fetchTime": 0.123,
  "attractions": [
    {
      "id": 1,
      "title": "경복궁",
      "address": "서울특별시 종로구 사직로 161",
      "latitude": 37.5796,
      "longitude": 126.9770,
      "parkingLotCount": 100,
      "contentTypes": 12,
      "metropolitanCode": 1,
      "localCode": 1
    }
  ]
}
```

### 🅿️ 주차장 예약 API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/parking-reservations/{parkingLotId}` | 예약 가능 공간 조회 |
| POST | `/api/v1/parking-lots/{parkingLotId}/reservation` | 주차장 예약 |
| GET | `/api/v1/parking-reservations/me` | 내 예약 내역 조회 |
| DELETE | `/api/v1/parking-reservations/{reservationId}` | 예약 취소 |

#### 주차장 예약 요청
```json
{
  "startDateTime": "2024-12-25T10:00:00",
  "endDateTime": "2024-12-25T18:00:00"
}
```

#### 예약 응답
```json
{
  "reservationId": 123,
  "parkingLotId": 1,
  "username": "홍길동",
  "attractionName": "경복궁",
  "reservationPeriod": {
    "startDateTime": "2024-12-25T10:00:00",
    "endDateTime": "2024-12-25T18:00:00"
  },
  "createdAt": "2024-12-20T15:30:00"
}
```

## 💬 실시간 채팅

### WebSocket 연결
```javascript
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const client = new Client({
  webSocketFactory: () => new SockJS('/ws'),
  connectHeaders: {},
  debug: (str) => console.log(str),
  onConnect: (frame) => {
    console.log('Connected: ' + frame);
    
    // 채팅방 구독
    client.subscribe('/topic/chat/room/1', (message) => {
      const chatMessage = JSON.parse(message.body);
      console.log('Received:', chatMessage);
    });
  }
});

client.activate();
```

### 메시지 전송
```javascript
const sendMessage = (roomId, content) => {
  client.publish({
    destination: '/app/chat/message',
    body: JSON.stringify({
      roomId: roomId,
      content: content,
      type: 'TALK'
    })
  });
};
```

### 채팅 메시지 구조
```json
{
  "id": 1,
  "roomId": 1,
  "content": "안녕하세요! 경복궁 방문 계획 중입니다.",
  "type": "TALK",
  "sender": "홍길동",
  "createdAt": "2024-12-20T15:30:00"
}
```

## 🅿️ 주차장 예약 시스템

### 동시성 제어
- **분산 락**: Redis 기반 분산 락으로 동시 예약 요청 제어
- **트랜잭션**: `@Transactional`을 통한 데이터 일관성 보장
- **중복 예약 방지**: 동일 사용자의 중복 시간대 예약 차단

### 예약 가능 공간 계산
```java
@Transactional(readOnly = true)
public int getAvailableParkingSpaces(int parkingLotId, ParkingReservationRequest request) {
    ParkingLots parkingLot = parkingLotsRepository.findById(parkingLotId)
        .orElseThrow(() -> new InvalidRequestException("존재하지 않는 주차장입니다."));
    
    int reservationCount = parkingReservationRepository
        .countParkingReservationInTimeRange(
            parkingLotId, 
            request.getStartDateTime(), 
            request.getEndDateTime()
        );
    
    return parkingLot.getTotalCount() - reservationCount;
}
```

### 예약 프로세스
1. **예약 가능 공간 확인**: 요청 시간대의 잔여 공간 조회
2. **중복 예약 검증**: 동일 사용자의 시간 중복 확인
3. **락 획득**: 주차장별 분산 락 획득
4. **예약 저장**: 데이터베이스에 예약 정보 저장
5. **락 해제**: 분산 락 해제

## 🔐 보안

### Spring Security 설정
- **세션 기반 인증**: 쿠키-세션 방식의 상태유지 인증
- **CORS 설정**: 프론트엔드 도메인에 대한 CORS 허용
- **CSRF 보호**: API 엔드포인트에 대한 CSRF 토큰 검증
- **비밀번호 암호화**: BCrypt를 통한 비밀번호 해싱


## 🧪 테스트

### 주요 테스트 케이스
- **주차장 예약 동시성 테스트**: 100명이 동시에 5개 자리 예약 시도
  ``` java
  @DisplayName("5칸 남은 주차장에 200명이 동시에 예약을 시도하면 성공한 예약은 최대 5건이다.")
      @Test
      void tryReserveParkingSpace_concurrently() throws InterruptedException {
          // ── given ────────────────────────────────────────────────────────────────────
          int THREAD_COUNT = 200;
          int PARKING_CAPACITY = 5;   // parkingLot.totalCount
  
          // 1) 주차장-메타 데이터 준비
          ContentTypes contentsType = createContentType("관광지");
          contentTypesRepository.save(contentsType);
  
          Metropolitans metropolitan = createMetropolitan(1, "서울특별시");
          metropolitansRepository.save(metropolitan);
  
          Locals local = createLocal(metropolitan.getCode(), 1, "노원구");
          localsRepository.save(local);
  
          Attractions attraction = new Attractions(
                  metropolitan, contentsType, local,
                  "제목", null, null, 123, 123, null, null, null);
          attractionsRepository.save(attraction);
  
          ParkingLots parkingLot = createParkingLot(attraction, PARKING_CAPACITY);
          parkingLotsRepository.save(parkingLot);
  
          // 2) 10명의 회원 생성
          List<Members> members = IntStream.rangeClosed(1, THREAD_COUNT)
                  .mapToObj(i -> createMember(String.valueOf(i)))
                  .collect(Collectors.toList());
          membersRepository.saveAll(members);
  
          // 3) 공통 예약 파라미터
          LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
          LocalDateTime endTime = startTime.plusHours(2);
          ParkingReservationRequest request = createParkingReservationRequest(
                  startTime, endTime);
  
          // ── when  ───────────────────────────────────────────────────────────
          CountDownLatch startLatch = new CountDownLatch(1);      // 동시에 출발
          CountDownLatch doneLatch = new CountDownLatch(THREAD_COUNT); // 종료 대기
          ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
  
          for (Members m : members) {
              executor.execute(new ReservationWorker(
                      parkingLot.getId(), m.getId(), request, startLatch, doneLatch));
          }
  
          startLatch.countDown();
          doneLatch.await();
          executor.shutdown();
  
          // ── then ─────────────────────────────────────────────────────────────────────
          int reservations =
                  parkingReservationRepository.countParkingReservationInTimeRange(parkingLot.getId(), startTime, endTime);
  
          assertThat(reservations).isEqualTo(PARKING_CAPACITY);   // 실제 저장된 건수 = 최대 수용인원(5)
      }
  ```
- **중복 예약 방지 테스트**: 동일 시간대 중복 예약 차단
  

### 커밋 컨벤션
```
feat: 새로운 기능 추가
fix: 버그 수정
docs: 문서 수정
style: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
refactor: 코드 리팩토링
test: 테스트 코드, 리팩토링 테스트 코드 추가
chore: 빌드 업무 수정, 패키지 매니저 수정
```

---

**저기어때** - 혁신적인 여행 계획의 시작 🌍✈️
