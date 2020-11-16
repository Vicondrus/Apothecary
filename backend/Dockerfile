FROM maven:3.6.3-jdk-11 AS builder

COPY ./src/ /root/src
COPY ./pom.xml /root/
COPY ./checkstyle.xml /root/
WORKDIR /root
RUN mvn package -e
RUN java -Djarmode=layertools -jar /root/target/apothecary-0.0.1-SNAPSHOT.jar list
RUN java -Djarmode=layertools -jar /root/target/apothecary-0.0.1-SNAPSHOT.jar extract
RUN ls -l /root

FROM openjdk:11.0.6-jre

ENV TZ=UTC
ENV DB_IP=ec2-54-217-236-206.eu-west-1.compute.amazonaws.com
ENV DB_PORT=5432
ENV DB_USER=flzgkzhtagcltj
ENV DB_PASSWORD=86b19510c6ebc1683adb783ae191c5e0331713db7ed57d900ae994f3e2d34331
ENV DB_DBNAME=d6ukt0q4cpl8k6


COPY --from=builder /root/dependencies/ ./
COPY --from=builder /root/snapshot-dependencies/ ./

RUN sleep 10
COPY --from=builder /root/spring-boot-loader/ ./
COPY --from=builder /root/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher","-XX:+UseContainerSupport -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -Xms512m -Xmx512m -XX:+UseG1GC -XX:+UseSerialGC -Xss512k -XX:MaxRAM=72m"]