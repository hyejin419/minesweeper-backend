## 프로젝트 구조

minesweeper/

├── backend/ # Spring Boot 서버 (게임 로직, API)

│ ├── controller/ # REST API 엔드포인트

│ ├── service/ # 비즈니스 로직 (게임 규칙 처리)

│ ├── model/ # Game, Cell 데이터 구조

│ ├── dto/ # 요청/응답 객체

│ ├── util/ # 보드 생성 및 유틸리티

│ ├── config/ # CORS 설정

│ └── MinesweeperApplication.java

└── 




## 백엔드

| 역할 | 설명 |
|------|------|
| `GameController` | `/api/games` 요청 처리 (생성, 클릭, 깃발) |
| `GameService` | 실제 게임 로직 수행 |
| `Game` / `Cell` | 보드와 칸의 상태 관리 |
| `BoardGenerator` | 랜덤 지뢰 배치 및 인접 숫자 계산 |
| `CorsConfig` | React와의 CORS 연결 설정 |



**API 명세 예시:**

| Method | URL | 설명 |
|---------|-----|------|
| `POST` | `/api/games` | 새 게임 생성 |
| `POST` | `/api/games/{id}/reveal` | 칸 열기 |
| `POST` | `/api/games/{id}/flag` | 깃발 표시/해제 |







