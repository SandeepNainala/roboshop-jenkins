def call() {
    pipeline {

        agent {
            node {
                label 'workstation'
            }
        }

        options {
          ansiColor ('xterm')
        }


        stages {

          stage ('Code Quality'){
            steps {
              sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.89.203:9000'
            }
          }
          stage ('Unit Test Cases') {
            steps {
              sh 'echo Unit Test Cases'
            }
          }
          stage ('CheckMarx SAST Scan') {
            steps {
              sh 'echo CheckMarx SAST Scan '
            }
          }
          stage ('CheckMarx SCA Scan'){
            steps {
              sh 'echo CheckMarx SCA Scan'
            }
          }

        }

        post {
            always {
                cleanWs()
            }
        }


    }
}