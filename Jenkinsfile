pipeline {
  agent any

  environment {
    // gradle wrapper path; use platform-appropriate command in script blocks
    GRADLEW = '.\\gradlew.bat'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
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

