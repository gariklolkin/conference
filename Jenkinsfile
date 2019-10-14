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
                            step("Build Conference Microservice") {
                                sh script: '''
                                    ./gradlew -b ./build.gradle clean build test
                                '''
                            }
                        }
                    },
                    discount: {
                        dir("discount") {
                            step("Build Discount Microservice") {
                                sh script: '''
                                    ./gradlew -b ./build.gradle clean build test
                                '''
                            }
                        }
                    },
                    notification: {
                        dir("notification") {
                            step("Build Notification Microservice") {
                                sh script: '''
                                    ./gradlew -b ./build.gradle clean build test
                                '''
                            }
                        }
                    },
                    payment: {
                        dir("payment") {
                            step("Build Payment Microservice") {
                                sh script: '''
                                    ./gradlew -b ./build.gradle clean build test
                                '''
                            }
                        }
                    },
                    registration: {
                        dir("registration") {
                            step("Build Registration Microservice") {
                                sh script: '''
                                    ./gradlew -b ./build.gradle clean build test
                                '''
                            }
                        }
                    },
                    sagateway: {
                        dir("sa-gateway") {
                            step("Build Gateway Microservice") {
                                sh script: '''
                                    ./gradlew -b ./build.gradle clean build test
                                '''
                            }
                        }
                    },
                    sponsorship: {
                        dir("sponsorship") {
                            step("Build Sponsorship Microservice") {
                                sh script: '''
                                    ./gradlew -b ./build.gradle clean build test
                                '''
                            }
                        }
                    },
                    submittal: {
                        dir("submittal") {
                            step("Build Submittal Microservice") {
                                sh script: '''
                                    ./gradlew -b ./build.gradle clean build test
                                '''
                            }
                        }
                    }, failFast: false
                )
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
