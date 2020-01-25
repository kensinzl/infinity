pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
      args '-v=/Users/zhaoliang/.m2:/root/.m2 --net=host'
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
        sh 'mvn clean package -DskipTests'
      }
    }

    stage('Mail') {
      steps {
        emailext(subject: '$Default_Subject', attachLog: true, body: '$Default_Content ', compressLog: true, replyTo: 'zlchldjyy@gmail.com', saveOutput: true)
      }
    }

  }
}