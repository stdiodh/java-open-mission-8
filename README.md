# 우아한테크코스 8기 오픈 미션 - 프리코스 미션 재구현

> 본 프로젝트는 우아한테크코스 8기 프리코스 기간 동안 진행했던 1~3주차 미션(문자열 덧셈 계산기, 자동차 경주, 로또)을
`Java`와 `Spring Boot`를 사용한 API 서버로 재구현하고, `React` 기반의 프론트엔드와 연동하여 실제 웹 서비스로 확장하는 오픈 미션입니다.

## 🏃‍♂️ 오픈 미션의 목표와 다짐

이번 오픈 미션의 핵심 키워드는 `도전`과 `깊이`입니다. <br>
결과에 급급하여 생성형 AI에 의존했던 과거의 학습 방식을 반성하고 <br>
'속도보다 깊이를 추구하는 개발자'가 되기 위해 다음과 같은 약속을 세웠습니다. <br>

1.  **코드 생성형 AI 사용 제한:** 스스로의 지식으로 문제를 해결하기 위해 노력합니다.<br> (단, 코드 및 커밋 메시지의 오탈자 확인 용도로만 제한적 허용)
2.  **이론의 확립:** 코드를 작성하기 전 사용하려는 기술과 로직의 이론을 확실히 학습합니다.
3.  **'왜?'에 집중하는 기록:** 막히는 과정을 두려워하지 않고<br> 문제 발생의 이유(`Why`)와 해결 과정을 상세히 기록하여 기술의 본질을 파악하려 노력합니다.

이 과정을 통해 프리코스의 경험을 일회성 지식이 아닌 지속 가능한 자산으로 만들고<br> 
백엔드 개발자로서 프론트엔드와의 협업을 공감하는 `T자형 인재`로 성장하는 것을 목표로 합니다.

## 🏗️ 시스템 아키텍처 (System Architecture)

본 프로젝트는 백엔드 API 서버와 프론트엔드 웹 애플리케이션으로 분리되어 있으며<br>
CI/CD 파이프라인을 통해 각각 AWS와 Firebase에 배포됩니다.

</br>
<img width="871" height="555" alt="스크린샷 2025-11-06 오후 5 57 03" src="https://github.com/user-attachments/assets/40f1653d-c173-4735-b115-f33d05df1c57" />
</br>

* **Backend (Spring Boot):**
    * IntelliJ에서 개발 후 Github에 Push합니다.
    * Github Actions가 CI/CD를 수행하며, 코드를 빌드하고 Docker 이미지를 생성합니다.
    * 생성된 이미지는 DockerHub에 Push됩니다.
    * AWS EC2 서버에서 Docker 이미지를 Pull 받아 Nginx, Certbot과 함께 애플리케이션을 실행합니다. (데이터베이스: MongoDB 사용)
* **Frontend (React):**
    * VS Code에서 개발 후 Github에 Push합니다.
    * Github Actions가 CI/CD를 수행하여 Firebase Hosting에 빌드 및 배포합니다.
* **Interaction:**
    * 프론트엔드(React)는 백엔드(Spring Boot) 서버에 API를 요청하고, JSON 형식의 데이터를 받아 화면을 렌더링합니다.

---

## 🚀 구현 기능 (API)

기존 1~3주차 미션의 핵심 기능을 API 엔드포인트로 구현합니다.

### 1. 문자열 덧셈 계산기 (1주차)

> 콘솔 입력을 API 요청으로 변경합니다.

* **핵심 기능:**

| 기능 | 설명 |
| :--- | :--- |
| **문자열 분리** | 기본 구분자(`,`, `:`) 및 커스텀 구분자를 기준으로 문자열을 분리합니다. |
| **입력 검증** | 입력 값(음수, 숫자 외 문자)을 검증하고 예외를 처리합니다. |
| **덧셈 계산** | 분리된 숫자들의 합을 계산하여 반환합니다. |

* **API 흐름 (Sequence Diagram):**
    ```mermaid
    sequenceDiagram
        Client->>+API Server: POST /api/v1/calculator/add (body: "text")
        Note over API Server: 1. 문자열 파싱 (구분자 식별)<br>2. 유효성 검사 (음수, 숫자 형식)<br>3. 덧셈 연산
        API Server-->>-Client: 200 OK (body: {"result": sum})
        
        Note right of Client: 예외 발생 시<br>API서버는 400 Bad Request와<br>에러 메시지 반환
    ```

### 2. 자동차 경주 (2주차)

> 콘솔 입/출력을 API 요청/응답으로 변경하여, 프론트엔드에서 경주 과정을 시각화합니다.

* **핵심 기능:**

| 기능 | 설명 |
| :--- | :--- |
| **경주 준비** | 쉼표로 구분된 자동차 이름을 입력받아 `Car` 객체를 생성합니다. |
| **경주 진행** | 시도할 횟수만큼 경주를 진행하며, 각 라운드별 자동차의 위치를 기록합니다. |
| **우승자 판별** | 최종 우승자를 판별하여 반환합니다. |

* **API 흐름 (Sequence Diagram):**
    ```mermaid
    sequenceDiagram
        Client->>+API Server: POST /api/v1/racing/start (body: {"names": "pobi,woni,jun", "tryCount": 5})
        Note over API Server: 1. 입력값 유효성 검사 (이름 5자, 중복, 횟수 등)<br>2. `Cars` 객체 생성<br>3. `tryCount`만큼 경주 시뮬레이션 실행<br>4. 매 라운드 결과 또는 최종 결과 집계
        API Server-->>-Client: 200 OK (body: {"rounds": [...], "winners": ["pobi", "jun"]})
        
        Note right of Client: API 설계에 따라<br>매 라운드 결과를 반환받거나<br>최종 결과를 한번에 받을 수 있음
    ```

### 3. 로또 발매기 (3주차)

> 구매, 당첨 번호 입력, 통계 확인 등 각 단계를 별도의 API로 분리하여 구현합니다.

* **핵심 기능:**

| 기능 | 설명 |
| :--- | :--- |
| **로또 구매** | 1,000원 단위의 구입 금액을 받아 수량만큼 로또를 자동 발행합니다. |
| **당첨 번호 입력** | 당첨 번호 6개와 보너스 번호 1개를 입력받습니다. |
| **당첨 통계** | 발행된 로또와 당첨 번호를 비교하여 1등~5등 당첨 내역과 총 수익률을 계산합니다. |

* **API 흐름 (Sequence Diagram):**
    ```mermaid
    sequenceDiagram
    Note over Client: 1. 로또 구매
    Client->>+API Server: POST /api/v1/lotto/purchase (body: {"amount": 8000})
    Note over API Server: 1. 구입 금액 검증 (1000단위, 0원 등)<br>2. 8개의 로또 생성 (LottoMachine)<br>3. 생성된 로또 목록 저장 (DB or 세션)
    API Server-->>-Client: 200 OK (body: {"count": 8, "lottos": [[...], [...]]})

    Note over Client: 2. 당첨 번호 및 결과 확인
    Client->>+API Server: POST /api/v1/lotto/result (body: {"purchasedLottos": [[...],...], "winningNumbers": [1,2,3,4,5,6], "bonusNumber": 7})
    Note over API Server: 1. 당첨/보너스 번호 유효성 검사<br>2. (전달받은 로또 목록) vs (당첨 번호) 비교<br>3. 당첨 통계 (Rank) 집계<br>4. 수익률 계산
    API Server-->>-Client: 200 OK (body: {"statistics": {"FIRST": 0, ...}, "profitRate": 62.5})
    ```

---

## 🛠️ 기술 스택 (Tech Stack)

| 구분 | 기술 |
| :--- | :--- |
| **Backend** | Java 21, Spring Boot 3.x, Gradle, JUnit5 |
| **Frontend** | React, JavaScript(ES6+), HTML5, CSS3 |
| **Infra & CI/CD** | AWS EC2, Docker, Nginx, Certbot, Github Actions, Firebase Hosting |
| **Database** | MongoDB |
| **Tools** | IntelliJ, VS Code, Git, Github |
