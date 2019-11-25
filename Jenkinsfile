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
                parallel (
                    conference: {
                        dir("conference") {
                            sh script: '''
                                # Build Conference Microservice
                                ./gradlew -b ./build.gradle clean build test
                            '''
                        }
                    },
                    discount: {
                        dir("discount") {
                            sh script: '''
                                # Build Discount Microservice
                                ./gradlew -b ./build.gradle clean build test
                            '''
                        }
                    },
                    notification: {
                        dir("notification") {
                            sh script: '''
                                # Build Notification Microservice
                                ./gradlew -b ./build.gradle clean build test
                            '''
                        }
                    },
                    payment: {
                        dir("payment") {
                            sh script: '''
                                # Build Payment Microservice
                                ./gradlew -b ./build.gradle clean build test
                            '''
                        }
                    },
                    registration: {
                        dir("registration") {
                            sh script: '''
                                # Build Registration Microservice
                                ./gradlew -b ./build.gradle clean build test
                            '''
                        }
                    },
                    sagateway: {
                        dir("sa-gateway") {
                            sh script: '''
                                # Build Gateway Microservice
                                ./gradlew -b ./build.gradle clean build test
                            '''
                        }
                    },
                    sponsorship: {
                        dir("sponsorship") {
                            sh script: '''
                                # Build Sponsorship Microservice
                                ./gradlew -b ./build.gradle clean build test
                            '''
                        }
                    },
                    submittal: {
                        dir("submittal") {
                            sh script: '''
                                # Build Submittal Microservice
                                ./gradlew -b ./build.gradle clean build test
                            '''
                        }
                    }, failFast: false
                )
            }
        }
        stage("SonarQube analysis") {
            agent any
            steps {
//                 withSonarQubeEnv {
                    dir("sponsorship") {
                        sh script: '''
                            # Sponsorship Microservice
                            ./gradlew -b ./build.gradle sonarqube -Dsonar.projectKey=sponsorship -Dsonar.organization=kyribamstraining -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=bbc606de8949bdabde5cb4f88bf29931c736d2b9
                        '''
                    }
//                 }
            }
        }
        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
//         stage('Sonar') {
//             stages {
//                 stage('SonarQube analysis')
//                 withSonarQubeEnv('My SonarQube Server') {
//                     steps {
//                         dir("conference") {
//                             sh script: '''
//                                 # Conference Microservice
//                                 ./gradlew -b ./build.gradle sonarqube -Dsonar.projectKey=conference -Dsonar.organization=kyribamstraining -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=bbc606de8949bdabde5cb4f88bf29931c736d2b9
//                             '''
//                         }
//                         dir("sponsorship") {
//                             sh script: '''
//                                 # Sponsorship Microservice
//                                 ./gradlew -b ./build.gradle sonarqube -Dsonar.projectKey=sponsorship -Dsonar.organization=kyribamstraining -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=bbc606de8949bdabde5cb4f88bf29931c736d2b9
//                             '''
//                         }
//                     }
//                 }
//                 stage('Conference Quality Gate') {
//                     steps {
//                         waitForQualityGate abortPipeline: true
//                     }
//                 }
//             }
//         }
    }
    post {
        always {
            cleanWs()
        }
    }
}
