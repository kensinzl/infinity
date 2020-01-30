pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
      args '-v=/Users/zhaoliang/.m2:/root/.m2 -p 8080:8080 --net=docker_default'
    }

  }
  stages {
    stage('Test') {
      steps {
        sh 'mvn clean test'
      }
    }

    stage('Build') {
      steps {
        sh '''mvn clean package -DskipTests
'''
      }
    }

    stage('Run') {
      environment {
        check_url = 'http://localhost:8080/employee'
        version = 'infinity-1.0.0-SNAPSHOT'
      }
      steps {
        sh '''#!/bin/bash

check_attempts=3
check_timeout=3

echo "Installing jq." 
wget -O jq https://github.com/stedolan/jq/releases/download/jq-1.6/jq-linux64
chmod +x ./jq
cp jq /usr/bin

check_url=${check_url}
version=${version}

online=false

PID=$(ps -ef | grep $version | grep -v grep | awk '{print $2}')

echo "Before starting service, check whether the PID existed or not."

# STRING the length is 0 then true
if [ -z "$PID" ]; then
  echo "No existed PID runing."
else 
  echo "Killing PID: $PID before starting service." 
  kill -9 $PID
fi

nohup java -jar ./target/infinity-1.0.0-SNAPSHOT.jar >/dev/null &

echo "Service is starting."
# wait 10 seconds to wait finish the service setting up
sleep 10
echo "Check service with one POST & Get request."


echo "POST Test URL: $check_url"
# POST with payload
postCode=$(curl -s -X POST -H "Content-type:application/json" -d '{
  "id": null,
  "employeeName": "Matthew",
  "moviePOs": [
      {
          "id": null,
          "movieName": "japan",
          "price": 50,
          "author": "mkyong"
          
      }, 
      {
          "id": null,
          "movieName": "china",
          "price": 60,
          "author": "mkyong"
          
      }
  ]
}' -w "%{http_code}\\n" -o /dev/null http://localhost:8080/employee)

if [ "${postCode}" = "201" ]; then
  echo "The POST request is successful, http status code: $postCode."
else
  echo "The POST request is failed."
fi



for (( i=1; i<=${check_attempts}; i++ ))
do
  echo "GET Test URL: $check_url"
  code=`curl -sL --connect-timeout 20 --max-time 30 -w "%{http_code}\\n" "${check_url}" -o /dev/null`
  echo "The http status code: $code"
  if [ "${code}" = "200" ]; then
    # check the content of GET
    result=$(curl -s -X GET http://localhost:8080/employee)
    movieName1=$(echo $result | jq '.[0]'| jq '.moviePOs'|jq '.[0]'| jq .movieName)
    movieName2=$(echo $result | jq '.[0]'| jq '.moviePOs'|jq '.[1]'| jq .movieName)
    if [ "${movieName1}" = "japan" && "${movieName2}" = "china" ]; then
      echo "The GET content test pass."
      online=true
    else
      echo "The GET content test does not pass."
      online=false
    fi
    break
  else
    echo "Can not get response, now wait ${check_timeout} seconds to retry."
    sleep ${check_timeout}
  fi
done
if $online; then
  echo "Service is normal."
  PID=$(ps -ef | grep $version | grep -v grep | awk '{print $2}')
  if [ -z "$PID" ]; then
    echo "Service has been killed."
  else 
    echo "Killing PID: $PID after testing service." 
    kill -9 $PID
  fi
  # success exist
  exit 0
else
  # fail exist
  echo "Service is failed."
  exit 1
fi
'''
      }
    }

  }
  post {
    always {
      emailext(subject: '$DEFAULT_SUBJECT', attachLog: true, body: '$DEFAULT_CONTENT ', saveOutput: true, to: '851561330@qq.com zlchldjyy@gmail.com')
    }

  }
}
