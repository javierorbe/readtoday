FROM ubuntu:20.04 AS build
RUN apt-get -y update \
    && apt-get install -y wget \
    && cd /home \
    # Download JDK
    && wget https://github.com/AdoptOpenJDK/openjdk16-binaries/releases/download/jdk-16%2B36_openj9-0.25.0/OpenJDK16-jdk_x64_linux_openj9_16_36_openj9-0.25.0.tar.gz \
    # Checksum
    && wget -O- -q -T 1 -t 1 https://github.com/AdoptOpenJDK/openjdk16-binaries/releases/download/jdk-16%2B36_openj9-0.25.0/OpenJDK16-jdk_x64_linux_openj9_16_36_openj9-0.25.0.tar.gz.sha256.txt | sha256sum -c \
    && tar xzvf OpenJDK16-jdk_x64_linux_openj9_16_36_openj9-0.25.0.tar.gz \
    # Download Maven
    && wget https://ftp.cixug.es/apache/maven/maven-3/3.8.1/binaries/apache-maven-3.8.1-bin.tar.gz \
    && tar xzvf apache-maven-3.8.1-bin.tar.gz \
    # Install MySQL
    && apt-get -y install mysql-server
ENV JAVA_HOME=/home/jdk-16+36 PATH="/home/apache-maven-3.8.1/bin:${PATH}"
COPY src/main/sql /home/sql
# Create database schema
RUN service mysql start \
    && mysql < /home/sql/db.sql \
    && mysql readtoday < /home/sql/schema.sql
# Download Maven dependencies
COPY pom.xml /home/app/
RUN mvn -f /home/app/pom.xml dependency:go-offline
# Package app
COPY jooq-config.xml /home/app/
COPY src /home/app/src/
RUN service mysql start \
    && mvn -f /home/app/pom.xml clean package

FROM openjdk:16
COPY --from=build /home/app/target/readtoday-server-*-final.jar /usr/local/lib/readtoday-server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/readtoday-server.jar"]
