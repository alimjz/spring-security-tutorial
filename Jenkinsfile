pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6'   // Name as configured in Jenkins > Global Tool Configuration
        jdk 'jdk-17'          // Name as configured, e.g., 'jdk-17'
    }

        environment {
            IMAGE_NAME = "ali/springbootdemo"
            CONTAINER_NAME = "springbootdemo"
            JAR_FILE = "target/*.jar"
        }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/alimjz/spring-security-tutorial.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml' // Collect test results
                }
            }
        }
        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
        // Optional: Add steps to deploy, dockerize, etc.
    }

    post {
        always {
            cleanWs()
        }
    }
}
