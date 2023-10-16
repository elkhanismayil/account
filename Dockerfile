FROM openjdk:11 AS build
ENV APP_HOME=account
WORKDIR $APP_HOME

COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle gradle
RUN ./gradlew build || return 0
COPY . .
RUN ./gradlew build


FROM openjdk:11
ENV ARTIFACT_NAME=account-0.0.1-SNAPSHOT.jar
ENV APP_HOME=account
WORKDIR $APP_HOME
COPY --from=build $APP_HOME/build/libs/$ARTIFACT_NAME app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "app.jar"]
