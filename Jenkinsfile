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

        stage('Publish docker') {
            steps {
                parallel {
                    stage('Sponsorship docker') {
                        environment {
                            registry = "kyriconf/sponsorship"
                            registryCredential = 'conference_dockerhub'
                            dockerImage = ''
                        }
                        agent any
                        steps {
                            dir("sponsorship") {
                                sh script: '''
                                    # Build Sponsorship Microservice Jar
                                    ./gradlew -b ./build.gradle bootJar
                                '''
                            }
                            script {
                                dockerImage = docker.build( "kyriconf/sponsorship:${env.GIT_COMMIT}", "./sponsorship")
                                dockerImage.push()
                            }
                        }
                    },
                    stage('Submittal docker') {
                        dir("submittal") {
//                             sh script: '''
//                                 # Build Submittal Microservice
//                                 ./gradlew -b ./build.gradle clean build test
//                             '''
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
