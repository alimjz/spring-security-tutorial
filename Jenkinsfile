pipeline {
    agent any

    tools {
        maven 'Maven 3.9.11'   // Name as configured in Jenkins > Global Tool Configuration
        jdk 'jdk-17'          // Name as configured, e.g., 'jdk-17'
    }

    environment {
            IMAGE_NAME = "ali/springsecurity"
            CONTAINER_NAME = "springboot-security"
            JAR_FILE = "target/*.jar"
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/alimjz/spring-security-tutorial.git',credentialsId: 'github'
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

        stage('Build Jar') {
            steps {
                sh 'mvn clean package -DskipTests=false'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }
        stage('Stop/Remove Old Container') {
            steps {
                // Stop and remove the old container if it exists
                sh '''
                  (docker stop $CONTAINER_NAME || true) && (docker rm $CONTAINER_NAME || true)
                '''
            }
        }
        stage('Run Docker Container') {
            steps {
                // Run the app (map port 8080, customize as needed)
                sh '''
                  docker run -d --name $CONTAINER_NAME -p 8080:8080 $IMAGE_NAME
                '''
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