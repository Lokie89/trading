# 요구사항 정의서
## 코드 개발 후 재 정의, 리팩토링을 목적으로 함
### 기본 순서

#### 1. 업비트 API 를 통하여 시세 데이터를 가져온다.
- 외부와의 통신 ( Tunnel )
  - 통신 시 요청 수 제한
  - 통신 시 JWT Token 생성
  - 통신 시 Body, Param 데이터 생성
    - 객체 -> QueryString
    - 객체 -> JsonString
- 시세 데이터 ( Candle )
  - Tunnel 객체 사용
  - 1분마다 시세 데이터 요청
  - Request, Response
#### 2. 시세 데이터를 통해 추가적인 내용을 기입한다. ( 볼린더 밴드, RSI 등등 )
- 차트 생성 ( Chart )
  - Candle 객체 사용
  - 기본적으로 Cache 사용
  - 주기적으로 DB 저장
    - Batch Update
  - 일급 컬렉션 사용하여 차트 생성
    - 차트화 객체 생성
#### 3. 전체 데이터를 돌며 요구에 맞는 코인명을 찾는다.
- 요구 매칭 데이터 검색 ( Match )
  - Chart 객체 사용
  - User 객체 사용
#### 4. 해당 코인의 가격으로 정해진 만큼의 금액을 주문한다.
- 주문한다 ( Order )
  - Tunnel 객체 사용
  - Request, Response
#### 5. 주문 한 내용을 기록한다.
- 기록한다 ( Record )
  - 저장한다.
  - 보여준다.

### 시뮬레이션
#### 1. 업비트 API 를 통하여 시세 데이터를 가져온다.
- 외부와의 통신 ( Tunnel )
  - 통신 시 요청 수 제한
  - 통신 시 JWT Token 생성
  - 통신 시 Body, Param 데이터 생성
    - 객체 -> QueryString
    - 객체 -> JsonString
- 시세 데이터 ( Candle )
  - Tunnel 객체 사용
  - 1분마다 시세 데이터 요청
  - Request, Response
#### 2. 시세 데이터를 통해 추가적인 내용을 기입한다. ( 볼린더 밴드, RSI 등등 )
- 차트 생성 ( Chart )
  - Candle 객체 사용
  - 기본적으로 Cache 사용
  - 주기적으로 DB 저장
    - Batch Update
  - 일급 컬렉션 사용하여 차트 생성
    - 차트화 객체 생성
#### 3. 전체 데이터를 돌며 요구에 맞는 코인명을 찾는다.
- 요구 매칭 데이터 검색 ( Match )
  - Chart 객체 사용
  - Simulate User 또는 User 객체 사용
#### 4. 주문 할 내용을 기록한다.
- 기록한다 ( Record )
  - 저장한다.
  - 보여준다.

### 메시지 토픽
- QUOTATION_REQUEST
- EXCHANGE_REQUEST
- CHART_REQUEST
- EXCHANGE_REQUEST