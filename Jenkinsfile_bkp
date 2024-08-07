pipeline {
    environment {
        registry = "inlinesoft/"
        version = "0.0.$BUILD_NUMBER"        
    }
     
    agent none
    stages {
        stage('build') {
            agent { docker 'adoptopenjdk/openjdk11:jdk-11.0.9.1_1' }
         
            steps {
                sh './mvnw package' 
            }            
        }
        stage('Build Docker') {
            agent any
            steps {
                script {
                   dockerImage = docker.build registry + "ops:$version"                   
                }
            }
        }
        stage('Deploy Docker') {
            agent any
            steps {
                script {
                    docker.withRegistry( 'https://registry-1.docker.io', 'docker_hub' ) {
                        dockerImage.push()
                        //rocketSend channel: 'jenkins', message: "Build success - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)", emoji: ':jenkins:',  color: 'green', rawMessage: true
                    }
                }
            }
            post {
                failure {
                    echo 'FAILED (in stage NotOK)'
                    rocketSend channel: 'jenkins', message: "Build FAIL - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)", emoji: ':jenkins_devil:',  color: 'red', rawMessage: true
                }
            }
        }
    }
}
