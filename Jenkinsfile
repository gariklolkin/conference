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

        stage('Docker') {
            parallel {
                stage('api-gateway') {
                    environment {
                        registry = "kyriconf/api-gateway"
                        registryCredential = 'conference_dockerhub'
                    }
                    agent any
                    steps {
                        dir("sa-gateway") {
                            sh script: '''
                                # Build API Gateway Microservice Jar
                                ./gradlew -b ./build.gradle bootJar
                            '''
                        }
                        script {
                            dockerImage = docker.build("${registry}:${env.GIT_COMMIT}", "./sa-gateway")
                            withDockerRegistry([ credentialsId: registryCredential, url: "" ]) {
                                dockerImage.push()
                            }
                        }
                    }
                }
                stage('sponsorship') {
                    environment {
                        registry = "kyriconf/sponsorship"
                        registryCredential = 'conference_dockerhub'
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
                            dockerImage = docker.build("${registry}:${env.GIT_COMMIT}", "./sponsorship")
                            withDockerRegistry([ credentialsId: registryCredential, url: "" ]) {
                                dockerImage.push()
                            }
                        }
                    }
                }
                stage('conference') {
                    environment {
                        registry = "kyriconf/conference"
                        registryCredential = 'conference_dockerhub'
                    }
                    agent any
                    steps {
                        dir("conference") {
                            sh script: '''
                                # Build Conference Microservice Jar
                                ./gradlew -b ./build.gradle bootJar
                            '''
                        }
                        script {
                            dockerImage = docker.build("${registry}:${env.GIT_COMMIT}", "./conference")
                            withDockerRegistry([ credentialsId: registryCredential, url: "" ]) {
                                dockerImage.push()
                            }
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
