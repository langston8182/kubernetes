FROM openjdk:8-jdk-alpine

COPY target/kubernetes-0.0.1-SNAPSHOT.jar /Users/cyril/kubernetes-docker/

EXPOSE 8080

WORKDIR /Users/cyril/kubernetes-docker/

ENTRYPOINT ["/usr/bin/java"]

CMD ["-jar", "kubernetes-0.0.1-SNAPSHOT.jar"]
