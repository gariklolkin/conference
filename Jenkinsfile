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
                                    sh script: '''# Sponsorship Microservice
                                    ./sponsorship/gradlew -b ./sponsorship/build.gradle sonarqube \
                                    -Dsonar.projectKey=sponsorship \
                                    -Dsonar.organization=kyribamstraining \
                                    -Dsonar.host.url=https://sonarcloud.io \
                                    -Dsonar.login=bbc606de8949bdabde5cb4f88bf29931c736d2b9 \
                                    -Dsonar.pullrequest.provider=GitHub \
                                    -Dsonar.pullrequest.github.repository=kyribamstraining/conference \
                                    -Dsonar.pullrequest.key=''' + env.CHANGE_ID + ''' \
                                    -Dsonar.pullrequest.branch=''' + env.CHANGE_BRANCH
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
                                    dockerImage1 = docker.build("${registry1}:${env.GIT_COMMIT}", "./sponsorship")
                                    withDockerRegistry([ credentialsId: registryCredential, url: "" ]) {
                                        dockerImage1.push()
                                        dockerImage1.push('latest')
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
                                    dockerImage3 = docker.build("${registry3}:${env.GIT_COMMIT}", "./sa-gateway")
                                    withDockerRegistry([ credentialsId: registryCredential, url: "" ]) {
                                        dockerImage3.push()
                                        dockerImage3.push('latest')
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
                    }
                }

                stage('Discount') {
                    stages {
                        stage('Build') {
                            steps {
                                dir("discount") {
                                    sh script: '''
                                        # Build Discount Microservice
                                        ./gradlew -b ./build.gradle clean build test
                                    '''
                                }
                            }
                        }
                    }
                }
            }
        }

        stage('Approve candidate') {
        	agent none
        	steps {
        		script {
        			env.IS_CANDIDATE_APPROVED = input message: 'User input required',
        			submitter: 'authenticated',
        			parameters: [
        			    choice(
        			        name: 'Approve candidate',
        			        choices: 'no\nyes',
        			        description: 'Choose "yes" if you want to update the last stable system version'
        			    )
        			]
        		}
        	}
        }

        stage('Update system version') {
            when {
            	environment name: 'IS_CANDIDATE_APPROVED', value: 'yes'
            }
            steps {
                echo 'todo: update last stable system version as configuration file in common Git repository'
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
