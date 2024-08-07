pipeline {
    agent none

    stages {
        stage('Test') {
            agent { docker 'adoptopenjdk/openjdk11:jdk-11.0.9.1_1' }

            steps {
                sh './mvnw test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }            
        }
        stage('Build') {
            agent { docker 'adoptopenjdk/openjdk11:jdk-11.0.9.1_1' }
            steps {
                sh './mvnw package'
            }
        }
        stage('Docker Build') {
            agent any
            steps {
                script {
                    dockerImage = docker.build "efrans88/ops:0.0.$BUILD_NUMBER"                   
                }
            }
        }
        stage('Docker Deploy') {
            agent any
            steps {
              script {
                    docker.withRegistry( 'https://registry-1.docker.io', 'docker_hub' ) {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Deployt servidor') {
            agent any
            steps {
                sh "ssh ecoscard@172.17.0.1 ./update_ops.sh 0.0.$BUILD_NUMBER"
            }
        }
    }
}