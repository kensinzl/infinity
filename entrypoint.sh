#! /bin/bash
su - dev -c "cd /build"
mvn package

su - dev -c "cd /build/target/"

mvn spring-boot:run

#chmod 777 /build/target/infinity-1.0.0-SNAPSHOT.jar

#java -jar infinity-1.0.0-SNAPSHOT.jar