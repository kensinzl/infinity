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


->
Employee(Main Table)
@OneToMany

Email(Key Holding Table)
@ManyToOne

```
{
	"id": 0,
	"employeeName": "Matthew",
	"emails": [
		{
			"id": 0, 
			"emailAddress": "zlchldjyy@gmail.com"
			
		}, 
		{
			"id": 0, 
			"emailAddress": "japanlife@gmail.com"
			
		}
	]
}
```
-> If put Employee.id as 0, the persistence context must not exist 
then execute the SQL to fetch the database.

#### Note: @OneToMany generate the LEFT JOIN
```
SELECT employee0_.employee_id   AS employee1_1_1_, 
       employee0_.employee_name AS employee2_1_1_, 
       emails1_.employee_id     AS employee3_0_3_, 
       emails1_.email_id        AS email_id1_0_3_, 
       emails1_.email_id        AS email_id1_0_0_, 
       emails1_.email_address   AS email_ad2_0_0_, 
       emails1_.employee_id     AS employee3_0_0_ 
FROM   employee employee0_ 
       LEFT OUTER JOIN email emails1_ 
                    ON employee0_.employee_id = emails1_.employee_id 
WHERE  employee0_.employee_id = ? 
```

-> If put Email.id as 0, it also operate the similar steps.
```
SELECT email0_.email_id      AS email_id1_0_0_, 
       email0_.email_address AS email_ad2_0_0_, 
       email0_.employee_id   AS employee3_0_0_ 
FROM   email email0_ 
WHERE  email0_.email_id = ? 
```

###### The above id if not put 0 or over 0 but put `null`, 
###### the result is still persist. 
###### But do not generate the above both select sql and directly generate inset.

