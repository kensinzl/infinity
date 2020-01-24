pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
      args '-v=~/.m2:/root/.m2 --net=host'
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

  }
}