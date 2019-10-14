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
        parallel (
            conference: {
                dir("conference") {
                    stage("Build Conference Microservice") {
                        sh script: '''
                            ./gradlew -b ./build.gradle clean build test
                        '''
                    }
                }
            },
            discount: {
                dir("discount") {
                    stage("Build Discount Microservice") {
                        sh script: '''
                            ./gradlew -b ./build.gradle clean build test
                        '''
                    }
                }
            },
            notification: {
                dir("notification") {
                    stage("Build Notification Microservice") {
                        sh script: '''
                            ./gradlew -b ./build.gradle clean build test
                        '''
                    }
                }
            },
            payment: {
                dir("payment") {
                    stage("Build Payment Microservice") {
                        sh script: '''
                            ./gradlew -b ./build.gradle clean build test
                        '''
                    }
                }
            },
            registration: {
                dir("registration") {
                    stage("Build Registration Microservice") {
                        sh script: '''
                            ./gradlew -b ./build.gradle clean build test
                        '''
                    }
                }
            },
            sagateway: {
                dir("sa-gateway") {
                    stage("Build Gateway Microservice") {
                        sh script: '''
                            ./gradlew -b ./build.gradle clean build test
                        '''
                    }
                }
            },
            sponsorship: {
                dir("sponsorship") {
                    stage("Build Sponsorship Microservice") {
                        sh script: '''
                            ./gradlew -b ./build.gradle clean build test
                        '''
                    }
                }
            },
            submittal: {
                dir("submittal") {
                    stage("Build Submittal Microservice") {
                        sh script: '''
                            ./gradlew -b ./build.gradle clean build test
                        '''
                    }
                }
            }, failFast: false
        )
    }
    post {
        always {
            cleanWs()
        }
    }
}
