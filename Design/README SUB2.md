| **팀명**        | Devengers                              |
| --------------- | -------------------------------------- |
| **구성원**      | 고병권, 박성진, 박정주, 서용재, 손경수 |
| **프로젝트 명** | SSAFY 작은 도서관                      |
| **일정**        | 2020.01.20 ~ 2020.02.21                |

# 목표

1. SSAFY작은 도서관 활성화로 자기주도학습 장려 및 프로젝트 수행 보조
2. 보유하고 있는 도서와 교보재 현황 시각화로 업무 보조 및 정보 제공



# 임무편성

팀장: 박정주

| 작업            | 담당자         | 내용                                    |
| --------------- | -------------- | --------------------------------------- |
| 기획            | 손경수, 박성진 | 프로젝트 기획 및 계획                   |
| DB              | 박정주, 서용재 | DB구축                                  |
| 배포            | 서용재, 손경수 | AWS 배포                                |
| User Front End  | 고병권, 박성진 | 모바일에 최적합한 페이지 구성           |
| User Back End   | 서용재, 박정주 |                                         |
| Admin Front End | 박정주, 박성진 | 현황파악 및 관리에 최적합한 페이지 구성 |
| Admin Back End  | 손경수         |                                         |
| 외부 API 적용   | 서용재, 박정주 | QR코드, 네이버 API 등 구현 및 적용      |
| TEST            | 박성진, 서용재 |                                         |



## 기획의도

1. 고객 편의성 개선

   기존 도서관의 특성상 실시간 도서 현황 파악 및 이용에 불편함이 있어 이를 개선한다.

   ![forclient](https://lab.ssafy.com/webmobile1-sub2/s02p12b206/raw/develop/asset/images/forclient.png)

2. 관리자 업무 혁신

   수작업으로 이루어지고 있는 대여/반납 시스템 및 현황 파악 시스템을 디지털화 하여 업무를 혁신한다.

   ![foradmin](https://lab.ssafy.com/webmobile1-sub2/s02p12b206/raw/develop/asset/images/foradmin.png)

# 진행상황

2020.01.31기준 Sub2 스프린트 완료

현재 전체적으로 DB 80%, Backend 75%, Frontend 30%가 진행된 상태. 

### DB

- DB구축 및 AWS배포, workbench connection

![db_aws](https://lab.ssafy.com/webmobile1-sub2/s02p12b206/raw/develop/asset/images/db_aws.png)

### Backend

- 대여/반납을 제외한 고객 페이지 주요 기능 구현  

![umldiagram](https://lab.ssafy.com/webmobile1-sub2/s02p12b206/raw/develop/asset/images/umldiagram.png)

### Frontend

- 고객 페이지 구현 

![client_page](https://lab.ssafy.com/webmobile1-sub2/s02p12b206/raw/develop/asset/images/client_page.png)

# 주요기능

### 도서 현황파악

![booknow](https://lab.ssafy.com/webmobile1-sub2/s02p12b206/raw/develop/asset/images/booknow.png)

### 도서 신청

![requestbook](https://lab.ssafy.com/webmobile1-sub2/s02p12b206/raw/develop/asset/images/requestbook.png)

### 게시판 관리

![notice](https://lab.ssafy.com/webmobile1-sub2/s02p12b206/raw/develop/asset/images/notice.png)

### 도서 대여 및 반납

![developing](https://lab.ssafy.com/webmobile1-sub2/s02p12b206/raw/develop/asset/images/developing.png)

# 이슈 해결

### 로그인/로그아웃

### ![loginissue](https://lab.ssafy.com/webmobile1-sub2/s02p12b206/raw/develop/asset/images/loginissue.png)

### mybatis 

![mybatisissue](https://lab.ssafy.com/webmobile1-sub2/s02p12b206/raw/develop/asset/images/mybatisissue.png)

# 향후계획

### 모바일 최적화 디자인

![moblieoptimize](https://lab.ssafy.com/webmobile1-sub2/s02p12b206/raw/develop/asset/images/moblieoptimize.png)

### SHARE BOOK

![sharebook](https://lab.ssafy.com/webmobile1-sub2/s02p12b206/raw/develop/asset/images/sharebook.png)

# 시스템 아키텍처

![image-20200207172423545](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20200207172423545.png)

# 기능 명세서

![명세서_기능구현](C:\Users\multicampus\git\s02p13b206\asset\images\명세서_기능구현.png)



# 진행상황

## USER FRONT

![userfront](C:\Users\multicampus\git\s02p13b206\asset\images\userfront.png)



## ADMIN FRONT

![adminfrontoverview](C:\Users\multicampus\git\s02p13b206\asset\images\adminfrontoverview.png)



# 이슈

## camera issue

![cameraissue](C:\Users\multicampus\git\s02p13b206\asset\images\cameraissue.png)

## token issue



![tokenissue](C:\Users\multicampus\git\s02p13b206\asset\images\tokenissue.png)





2020-02-07 회의

5주차 계획

User

Front
 - about us 소개 글 등의 내용채우기
 - 팀 프로필 사진
 - 메인페이지 추천도서
 - 버튼 디자인 
 - 챗봇 환경 디자인
 - 관리 탭 대여/반납 - 일련번호 입력 페이지
 - id 중복확인
 - 프로필 사진 업로드 기능
 - 토큰 만료 처리
Back
 - 카카오톡 승인 기다리는 중.
 - 추천 도서 알고리즘.
 - 희망도서 신청.
 - 챗봇 완성.
 - 도서 리뷰.

Admin

Front
 - 반납률, 대여율, 연체율
 - 메뉴바 로그인 프로필 사진 로그아웃 버튼
 - 관리자 본인 프로필 수정
 - 관리자 권한 수정
 - 회원관리 -> 대여 상태별 알림 발송
 - 신청도서 관련 기능 구현(리스트 추출)
 - 관리자 권한별 기능 분배

Back


교보재
 - table구성, 설계

배포


PWA


기능
대여 가능 리스트
대여 불가능 한 책 표시 -> 알림 발송 설정.


배포



