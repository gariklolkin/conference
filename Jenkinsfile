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
                   # Build Conference Microservice
                   ./conference/gradlew -b ./conference/build.gradle clean build test
                '''
                
                sh script: '''
                   # Build Discount Microservice
                   ./discount/gradlew -b ./discount/build.gradle clean build test
                '''
                
                sh script: '''
                   # Build Notification Microservice
                   ./notification/gradlew -b ./notification/build.gradle clean build test
                '''
                
                sh script: '''
                   # Build Payment Microservice
                   ./payment/gradlew -b ./payment/build.gradle clean build test
                '''
                
                sh script: '''
                   # Build Registration Microservice
                   ./registration/gradlew -b ./registration/build.gradle clean build test
                '''
          
                sh script: '''
                   # Build sa-gateway Microservice
                   ./sa-gateway/gradlew -b ./sa-gateway/build.gradle clean build test
                '''
                
                sh script: '''
                   # Build Sponsorship Microservice
                   ./sponsorship/gradlew -b ./sponsorship/build.gradle clean build test
                '''
                
                sh script: '''
                   # Build Submittal Microservice
                   ./submittal/gradlew -b ./submittal/build.gradle clean build test
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
