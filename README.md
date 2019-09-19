https://myagile.myjetbrains.com/youtrack/issues
-> track the issue

Mapstruct
-> should use componentModel = "spring"
-> mvn clean compile

java -jar target/mywebserviceapp-0.0.1-SNAPSHOT.jar
mvn spring-boot:run

sonaq
-> 
open terminal and cd project path
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.projectVersion=liang
->
http://localhost:9000/projects?sort=-analysis_date
the prerequisite is start the sonaq docker