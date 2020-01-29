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
      steps {
        sh '''#!/bin/bash

check_attempts=3
check_timeout=3

mvn spring-boot:run

online=false

echo "Check Service with one Get request."

for (( i=1; i<=${check_attempts}; i++ ))
do
  code=`curl -sL --connect-timeout 20 --max-time 30 -w "%{http_code}\\n" http://localhost:8080/employee -o /dev/null`
  echo "The http status code：$code"
  if [ "${code}" = "200" ]; then
    echo "Test URL：${check_url}"
    sleep 10
    online=true
    break
  else
    echo "Can not get response, now wait ${check_timeout} to retry."
    sleep ${check_timeout}
  fi
done
if $online; then
  echo "Service is normal."
  exit 0
else
  echo "Service is failed."
  exit 1
fi'''
      }
    }

  }
  post {
    always {
      emailext(subject: '$DEFAULT_SUBJECT', attachLog: true, body: '$DEFAULT_CONTENT ', saveOutput: true, to: '851561330@qq.com zlchldjyy@gmail.com')
    }

  }
}
