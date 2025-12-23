#베이스 이미지로 openjdk17-jdk-slim버전사용
#이미지 빌드 시 java 17버전이 설치된 리눅스 환경을 깔아라

FROM eclipse-temurin:17-jdk-alpine AS build

#작업폴더 지정(이제부터 컨테이너안의/app이라는 폴더에서 작업할게!)
WORKDIR /app

#이 dockerfile을 기준으로 현재 경로에 있는 모든 파일(소스코드,gradle 등등)을
#작업폴더인 /app으로 복사하겠다.
#..두개 현재도커파일 파일경로 . 앱경로
#하나하나 가능하지만 전부다니까
#전부다  .  엡 경로에다
COPY . .

#실행권한
#우리로컬은 가능하지만 이 컨테이너안에서 가능하기떄문에
# 컨테이너기준에서는  이게 당현이 있지만
#이 런이 실행되는공간은 컨테이너고  이파일을 실행할수있는 권한이 없다
#그래서 chom 권한수정 리눅스 ㅁ명령어 권한을 추가 +x == excute 추가
#컨테이너 기준에서 gradelw가 외부 파일이기 때문에 ㅇ
#권한안주면 permission denied 권한 거부
RUN chmod +x ./gradlew
#./gradlew 기존에 있던건 지우고 clean 새롭게 빌드하라
#이건 빌드는 테스트포함하니 RUN ./gradlew clean build
RUN ./gradlew clean build -x test

#############################################################################################################################
#두번째 스테이지 -> 실행 영역
#이거할꺼야
FROM eclipse-temurin:17-jre-alpine

# build라는 별칭으로 만들어진 첫번째 스테이지에서
#엡이라는폴더 안에 빌드 안에 립스안에 끝이 jar로 끝나는 파일을  app.jar로 복사해서 이미지 세팅하겠다

COPY --from=build /app/build/libs/*.jar app/jar

#타임존 설정
ENV TZ=Asia/Seoul
RUN apk add --no-cache curl tzdata

#이 이미지를기반으로한 컨테이너가 시작될 때 무조건 실행해야 하는 명령어
ENTRYPOINT ["java","-jar","app/jar"]
