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

        stage('Microservice') {
            parallel {
                stage('Sponsorship') {
                    stages {
                        stage('Build') {
                            steps {
                                dir("sponsorship") {
                                    sh script: '''
                                        # Build Sponsorship Microservice
                                        ./gradlew -b ./build.gradle clean build test
                                    '''
                                }
                            }
                        }
                        stage('Sonar') {
                            steps {
                                withSonarQubeEnv(credentialsId: 'Conference_sonar', installationName: 'SonarQube') {
                                    sh script: '''
                                    # Sponsorship Microservice
                                    ./sponsorship/gradlew -b ./sponsorship/build.gradle sonarqube -Dsonar.projectKey=sponsorship -Dsonar.organization=kyribamstraining -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=bbc606de8949bdabde5cb4f88bf29931c736d2b9
                                    '''
                                }
                                sleep(30)
                                timeout(time: 3, unit: 'MINUTES') {
                                    waitForQualityGate abortPipeline: true
                                }
                            }
                        }
                        stage('Docker') {
                            environment {
                                registry1 = "kyriconf/sponsorship"
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
                                    dockerImage2 = docker.build("${registry1}:${env.GIT_COMMIT}", "./sponsorship")
                                    withDockerRegistry([ credentialsId: registryCredential, url: "" ]) {
                                        dockerImage2.push()
                                        dockerImage2.push('latest')
                                    }
                                }
                            }
                        }
                    }
                }

                stage('Conference') {
                    stages {
                        stage('Build') {
                            steps {
                                dir("conference") {
                                    sh script: '''
                                        # Build Conference Microservice
                                        ./gradlew -b ./build.gradle clean build test
                                    '''
                                }
                            }
                        }
                        stage('Sonar') {
                            steps {
                                withSonarQubeEnv(credentialsId: 'Conference_sonar', installationName: 'SonarQube') {
                                    sh script: '''
                                    # Conference Microservice
                                    ./conference/gradlew -b ./conference/build.gradle sonarqube -Dsonar.projectKey=conference -Dsonar.organization=kyribamstraining -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=bbc606de8949bdabde5cb4f88bf29931c736d2b9
                                    '''
                                }
                                sleep(30)
                                timeout(time: 3, unit: 'MINUTES') {
                                    waitForQualityGate abortPipeline: true
                                }
                            }
                        }
                        stage('Docker') {
                            environment {
                                registry2 = "kyriconf/conference"
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
                                    dockerImage2 = docker.build("${registry2}:${env.GIT_COMMIT}", "./conference")
                                    withDockerRegistry([ credentialsId: registryCredential, url: "" ]) {
                                        dockerImage2.push()
                                        dockerImage2.push('latest')
                                    }
                                }
                            }
                        }
                    }
                }

                stage('API Gateway') {
                    stages {
                        stage('Build') {
                            steps {
                                dir("sa-gateway") {
                                    sh script: '''
                                        # Build API Gateway Microservice
                                        ./gradlew -b ./build.gradle clean build test
                                    '''
                                }
                            }
                        }
                        stage('Sonar') {
                            steps {
                                withSonarQubeEnv(credentialsId: 'Conference_sonar', installationName: 'SonarQube') {
                                    sh script: '''
                                    # Conference Microservice
                                    ./sa-gateway/gradlew -b ./sa-gateway/build.gradle sonarqube -Dsonar.projectKey=sa-gateway -Dsonar.organization=kyribamstraining -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=bbc606de8949bdabde5cb4f88bf29931c736d2b9
                                    '''
                                }
                                sleep(30)
                                timeout(time: 3, unit: 'MINUTES') {
                                    waitForQualityGate abortPipeline: true
                                }
                            }
                        }
                        stage('Docker') {
                            environment {
                                registry3 = "kyriconf/api-gateway"
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
                                    dockerImage2 = docker.build("${registry3}:${env.GIT_COMMIT}", "./sa-gateway")
                                    withDockerRegistry([ credentialsId: registryCredential, url: "" ]) {
                                        dockerImage2.push()
                                        dockerImage2.push('latest')
                                    }
                                }
                            }
                        }
                    }
                }

                stage('Submittal') {
                    stages {
                        stage('Build') {
                            steps {
                                dir("submittal") {
                                    sh script: '''
                                        # Build Submittal Microservice
                                        ./gradlew -b ./build.gradle clean build test
                                    '''
                                }
                            }
                        }
                        stage('Sonar') {
                            steps {
                                withSonarQubeEnv(credentialsId: 'Conference_sonar', installationName: 'SonarQube') {
                                    sh script: '''
                                    # Submittal Microservice
                                    ./submittal/gradlew -b ./submittal/build.gradle sonarqube -Dsonar.projectKey=submittal -Dsonar.organization=kyribamstraining -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=bbc606de8949bdabde5cb4f88bf29931c736d2b9
                                    '''
                                }
                                sleep(30)
                                timeout(time: 3, unit: 'MINUTES') {
                                    waitForQualityGate abortPipeline: true
                                }
                            }
                        }
                    }
                }

                stage('Notification') {
                    stages {
                        stage('Build') {
                            steps {
                                dir("notification") {
                                    sh script: '''
                                        # Build Notification Microservice
                                        ./gradlew -b ./build.gradle clean build test
                                    '''
                                }
                            }
                        }
                        stage('Sonar') {
                            steps {
                                withSonarQubeEnv(credentialsId: 'Conference_sonar', installationName: 'SonarQube') {
                                    sh script: '''
                                    # Notification Microservice
                                    ./notification/gradlew -b ./notification/build.gradle sonarqube -Dsonar.projectKey=notification -Dsonar.organization=kyribamstraining -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=bbc606de8949bdabde5cb4f88bf29931c736d2b9
                                    '''
                                }
                                sleep(30)
                                timeout(time: 3, unit: 'MINUTES') {
                                    waitForQualityGate abortPipeline: true
                                }
                            }
                        }
                    }
                }

                stage('Payment') {
                    stages {
                        stage('Build') {
                            steps {
                                dir("payment") {
                                    sh script: '''
                                        # Build Payment Microservice
                                        ./gradlew -b ./build.gradle clean build test
                                    '''
                                }
                            }
                        }
                        stage('Sonar') {
                            steps {
                                withSonarQubeEnv(credentialsId: 'Conference_sonar', installationName: 'SonarQube') {
                                    sh script: '''
                                    # Payment Microservice
                                    ./payment/gradlew -b ./payment/build.gradle sonarqube -Dsonar.projectKey=payment -Dsonar.organization=kyribamstraining -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=bbc606de8949bdabde5cb4f88bf29931c736d2b9
                                    '''
                                }
                                sleep(30)
                                timeout(time: 3, unit: 'MINUTES') {
                                    waitForQualityGate abortPipeline: true
                                }
                            }
                        }
                    }
                }

                stage('Registration') {
                    stages {
                        stage('Build') {
                            steps {
                                dir("registration") {
                                    sh script: '''
                                        # Build Registration Microservice
                                        ./gradlew -b ./build.gradle clean build test
                                    '''
                                }
                            }
                        }
                        stage('Sonar') {
                            steps {
                                withSonarQubeEnv(credentialsId: 'Conference_sonar', installationName: 'SonarQube') {
                                    sh script: '''
                                    # Registration Microservice
                                    ./registration/gradlew -b ./registration/build.gradle sonarqube -Dsonar.projectKey=registration -Dsonar.organization=kyribamstraining -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=bbc606de8949bdabde5cb4f88bf29931c736d2b9
                                    '''
                                }
                                sleep(30)
                                timeout(time: 3, unit: 'MINUTES') {
                                    waitForQualityGate abortPipeline: true
                                }
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
