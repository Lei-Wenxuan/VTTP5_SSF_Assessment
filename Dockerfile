FROM eclipse-temurin:23-noble AS compiler

ARG COMPILE_DIR=/code_folder

WORKDIR ${COMPILE_DIR}

COPY mvnw .
COPY pom.xml .

COPY .mvn .mvn
COPY src src

RUN chmod a+x ./mvnw && ./mvnw package -Dmaven.test.skip=true

FROM eclipse-temurin:23-jre-noble

LABEL MAINTAINER="leiwenxuan"
LABEL description="VTTP5 SSF Assessment"
LABEL name="vttp5_ssf_assessment"

ARG DEPLOY_DIR=/app

WORKDIR ${DEPLOY_DIR}

COPY --from=compiler /code_folder/target/vttp5_ssf_assessment-0.0.1-SNAPSHOT.jar vttp5_ssf_assessment.jar

RUN apt update && apt install -y curl

ENV NOTICEBOARD_DB_HOST=localhost NOTICEBOARD_DB_PORT=6379 NOTICEBOARD_DB_DATABASE=0
ENV NOTICEBOARD_DB_USERNAME="" NOTICEBOARD_DB_PASSWORD=""
ENV PORT=3000

EXPOSE ${PORT}

HEALTHCHECK --interval=30s --timeout=5s --start-period=5s --retries=3 \
    CMD curl -s -f http://localhost:${PORT}/status || exit 1

ENTRYPOINT SERVER_PORT=${PORT} java -jar vttp5_ssf_assessment.jar