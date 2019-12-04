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
                        registry1 = "kyriconf/api-gateway"
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
                            dockerImage1 = docker.build("${registry1}:${env.GIT_COMMIT}", "./sa-gateway")
                            withDockerRegistry([ credentialsId: registryCredential, url: "" ]) {
                                dockerImage1.push()
                                dockerImage1.push('latest')
                            }
                        }
                    }
                }
                stage('sponsorship') {
                    environment {
                        registry2 = "kyriconf/sponsorship"
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
                            dockerImage2 = docker.build("${registry2}:${env.GIT_COMMIT}", "./sponsorship")
                            withDockerRegistry([ credentialsId: registryCredential, url: "" ]) {
                                dockerImage2.push()
                                dockerImage2.push('latest')
                            }
                        }
                    }
                }
                stage('conference') {
                    environment {
                        registry3 = "kyriconf/conference"
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
                            dockerImage3 = docker.build("${registry3}:${env.GIT_COMMIT}", "./conference")
                            withDockerRegistry([ credentialsId: registryCredential, url: "" ]) {
                                dockerImage3.push()
                                dockerImage3.push('latest')
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
