# CORS Troubleshooting

본 프로젝트는 `WebConfig`에서 WebFlux CORS 설정을 등록합니다. 프론트엔드에서 API 호출이 막히면 요청 origin과 `allowedOriginPatterns` 설정을 먼저 확인합니다.

## 현재 CORS 설정

| 항목 | 값 |
| :--- | :--- |
| Mapping | `/**` |
| Allowed origins | `http://localhost:3000`, `https://*.web.app`, `https://web.app`, `${app.server.url}` |
| Methods | `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS` |
| Headers | `*` |
| Credentials | `true` |

## 확인 순서

1. 브라우저 개발자 도구에서 차단된 요청의 `Origin` 값을 확인합니다.
2. 해당 origin이 `allowedOriginPatterns`에 포함되는지 확인합니다.
3. Firebase Hosting을 사용한다면 실제 배포 도메인이 `https://*.web.app` 패턴에 맞는지 확인합니다.
4. `SERVER_URL`을 CORS origin으로 쓰려면 `.env` 또는 환경 변수에 올바르게 설정되어 있는지 확인합니다.
5. credentials 요청이면 프론트엔드 요청과 서버 CORS 설정이 함께 credentials를 허용해야 합니다.

## 주의할 점

- `allowedOriginPatterns`와 `allowCredentials(true)`를 함께 사용하고 있으므로 origin을 넓게 열기보다 실제 프론트엔드 주소를 기준으로 관리해야 합니다.
- `app.server.url`은 Swagger server URL에도 쓰이므로, CORS만을 위한 값으로 바꾸면 Swagger 문서의 요청 대상도 같이 바뀔 수 있습니다.
- 실제 운영 도메인과 secret 값은 repository에 노출하지 않습니다.
