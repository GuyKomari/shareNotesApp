FROM openjdk:8-jdk-alpine

ARG PROJECT=app
ARG PROJECT_DIR=/opt/${PROJECT}
RUN mkdir -p $PROJECT_DIR

