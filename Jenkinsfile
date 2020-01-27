pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
      args '-v=/Users/zhaoliang/.m2:/root/.m2 -p 8080:8080 --net=host'
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
        sh 'mvn spring-boot:run'
      }
    }

  }
  post {
    always {
      emailext(subject: '$DEFAULT_SUBJECT', attachLog: true, body: '$DEFAULT_CONTENT ', saveOutput: true, to: '851561330@qq.com zlchldjyy@gmail.com')
    }

  }
}