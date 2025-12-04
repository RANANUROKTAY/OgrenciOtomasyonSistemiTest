pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Prepare ') {
      steps {
        script {
          if (isUnix()) {
            // Ensure wrapper is executable on Unix agents
            sh 'chmod +x gradlew'
          }
        }
      }
    }

    stage('Build') {
      steps {
        script {
          if (isUnix()) {
            sh './gradlew clean build'
          } else {
            bat '.\\gradlew.bat clean build'
          }
        }
      }
    }

    stage('Test') {
      steps {
        script {
          if (isUnix()) {
            sh './gradlew test'
          } else {
            bat '.\\gradlew.bat test'
          }
        }
      }
      post {
        always {
          // publish test results to Jenkins (if any)
          junit 'build/test-results/test/*.xml'
        }
      }
    }

    stage('Archive') {
      steps {
        archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
      }
    }
  }

  post {
    always {
      echo "Pipeline finished: ${currentBuild.currentResult}"
    }
    success {
      echo 'Build and tests succeeded.'
    }
    failure {
      echo 'Build or tests failed.'
    }
  }
}
