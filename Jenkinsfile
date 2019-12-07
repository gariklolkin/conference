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
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
