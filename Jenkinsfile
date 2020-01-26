pipeline {
  agent none
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
        emailext(subject: '$DEFAULT_SUBJECT', attachLog: true, body: '$DEFAULT_CONTENT ', saveOutput: true, to: '851561330@qq.com zlchldjyy@gmail.com')
      }
    }

  }
}