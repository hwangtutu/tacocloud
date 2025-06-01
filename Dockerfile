# ---------------------------------
# 1. Build Stage (Java 23 + Maven)
# ---------------------------------
FROM maven:3.9.9-eclipse-temurin-23-slim AS build

# ① 컨테이너 내부 작업 디렉터리를 /app으로 설정
WORKDIR /app

# ② 로컬(또는 GitHub) 프로젝트 전체를 /app으로 복사
COPY . /app

# ③ /app 경로에 pom.xml이 있어야 mvn이 인식 가능
RUN mvn clean package -DskipTests

# -----------------------------------------------
# 2. Runtime Stage (Java 23 + OpenJDK 경량 슬림)
# -----------------------------------------------
FROM openjdk:23-jdk-slim

# ④ build 스테이지에서 생성된 JAR을 /app/taco.jar으로 복사
COPY --from=build /app/target/lec25.3-0.0.1-SNAPSHOT.jar /app/taco.jar

# ⑤ 컨테이너 외부에 노출할 포트 지정
EXPOSE 8085

# ⑥ 컨테이너 실행 시 java -jar /app/taco.jar 실행
ENTRYPOINT ["java", "-jar", "/app/taco.jar"]
