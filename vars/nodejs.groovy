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
//              sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.89.203:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true'
                sh 'echo Code Quality'
            }
          }
          stage ('Unit Test Cases') {
            steps {
              sh 'echo Unit Test Cases'
//                sh 'npm test'
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
          stage ('Release Application') {
             when {
               expression {
                  env.TAG_NAME ==~ ".*"
               }
             }
             steps {
                 sh 'env'
                 sh 'echo $TAG_NAME >VERSION'
                 sh 'zip -r ${component}-${TAG_NAME}.zip node_module server.js VERSION'
                 sh 'curl -v -u admin:admin123 --upload-file ${component}-${TAG_NAME}.zip server.js http://172.31.81.130:8081/repository/{component}/${component}-${TAG_NAME}.zip'
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