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

check_url=${1}

online=false


PID=$(ps -ef | grep infinity-1.0.0-SNAPSHOT | grep -v grep | awk '{print $2}')

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
sleep 10
echo "Check service with one Get request."

for (( i=1; i<=${check_attempts}; i++ ))
do
  echo "Test URL: $check_url"
  code=`curl -sL --connect-timeout 20 --max-time 30 -w "%{http_code}\\n" "${check_url}" -o /dev/null`
  echo "The http status code: $code"
  if [ "${code}" = "200" ]; then
    online=true
    break
  else
    echo "Can not get response, now wait ${check_timeout} seconds to retry."
    sleep ${check_timeout}
  fi
done
if $online; then
  echo "Service is normal."
  PID=$(ps -ef | grep infinity-1.0.0-SNAPSHOT | grep -v grep | awk '{print $2}')
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
