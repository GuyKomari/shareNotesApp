FROM openjdk:8-jdk-alpine
MAINTAINER Gabriel Munits

ARG PROJECT=notesapp
ARG PROJECT_DIR=/opt/${PROJECT}
RUN mkdir -p $PROJECT_DIR

WORKDIR ${PROJECT_DIR}

COPY ./build/libs/app-0.0.1-SNAPSHOT.jar ${PROJECT_DIR}

ENTRYPOINT ["java","-jar","app-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
