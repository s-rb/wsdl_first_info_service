# Stage 1: Build
FROM maven:3.6.3-adoptopenjdk-8-openj9 as build

ENV HOME=/home/app

RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn verify --fail-never
ADD src/main/resources/wsdl ./src/main/resources/wsdl
RUN mvn cxf-codegen:wsdl2java --fail-never

COPY src ./src

RUN mvn package -DskipTests

# Stage 2: Create a lightweight image to run the application
FROM adoptopenjdk:8u272-b10-jdk-hotspot-focal
LABEL maintainer="Roman Surkov surkov.r.b@gmail.com"

COPY --from=build /home/app/target/soap_service*.jar app.jar

# Run script ServiceMix
COPY start-system.sh start-system.sh
RUN chmod +x start-system.sh

EXPOSE 9090

CMD ["/start-system.sh"]