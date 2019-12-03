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
            parallel {
                stage('API Gateway') {

                    environment {
                        registry = "kyriconf/api-gateway"
                        registryCredential = 'conference_dockerhub'
                    }
                    agent any
                    steps {
                        checkout scm
                        dir("sa-gateway") {
                            sh script: '''
                                # Build API Gateway Microservice Jar
                                ./gradlew -b ./build.gradle bootJar
                            '''
                        }
                        script {
                            dockerImage = docker.build("${registry}:${env.GIT_COMMIT}", "./sa-gateway")
                            dockerImage.push("latest")
                        }
                    }
                }
                stage('Sponsorship') {

                    environment {
                        registry = "kyriconf/sponsorship"
                        registryCredential = 'conference_dockerhub'
                    }
                    agent any
                    steps {
                        checkout scm
                        dir("sponsorship") {
                            sh script: '''
                                # Build Sponsorship Microservice Jar
                                ./gradlew -b ./build.gradle bootJar
                            '''
                        }
                        script {
                            dockerImage = docker.build("${registry}:${env.GIT_COMMIT}", "./sponsorship")
                            dockerImage.push("latest")
                        }
                    }
                }
                stage('Conference') {

                    environment {
                        registry = "kyriconf/conference"
                        registryCredential = 'conference_dockerhub'
                    }
                    agent any
                    steps {
                        checkout scm
                        dir("conference") {
                            sh script: '''
                                # Build Conference Microservice Jar
                                ./gradlew -b ./build.gradle bootJar
                            '''
                        }
                        script {
                            dockerImage = docker.build("${registry}:${env.GIT_COMMIT}", "./conference")
                            dockerImage.push("latest")
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
