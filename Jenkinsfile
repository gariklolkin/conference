pipeline {
    agent {
        label 'centos7 || centos7onDemand'
    }
    options {
        disableConcurrentBuilds()
        timestamps ()
    }
    environment {
        ARTIFACTORY = credentials('artifactory.auto.user_credentials')
    }
    stages {
        stage('Build') {
            steps {
                sh script: '''
                  ls -l
                '''
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
