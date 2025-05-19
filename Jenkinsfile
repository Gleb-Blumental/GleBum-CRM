pipeline {
    agent {
        docker {
            image 'maven:3.9-eclipse-temurin-17'
            args '-v $HOME/.m2:/root/.m2'
        }
    }

    environment {
        DOCKER_REGISTRY = 'your-registry-url'  // e.g., Docker Hub username or ECR URL
        IMAGE_NAME = 'crm-application'
        IMAGE_TAG = "v${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Backend') {
            steps {
                dir('backend') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Test Backend') {
            steps {
                dir('backend') {
                    sh 'mvn test'
                }
            }
            post {
                always {
                    junit 'backend/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Build Frontend') {
            agent {
                docker {
                    image 'node:18'
                }
            }
            steps {
                dir('frontend') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    // Build backend Docker image
                    dir('backend') {
                        sh 'docker build -t ${DOCKER_REGISTRY}/${IMAGE_NAME}-backend:${IMAGE_TAG} .'
                    }

                    // Build frontend Docker image
                    dir('frontend') {
                        sh 'docker build -t ${DOCKER_REGISTRY}/${IMAGE_NAME}-frontend:${IMAGE_TAG} .'
                    }
                }
            }
        }

        stage('Run Tests in Docker') {
            steps {
                sh 'docker-compose -f docker-compose.test.yml up --abort-on-container-exit'
            }
            post {
                always {
                    sh 'docker-compose -f docker-compose.test.yml down -v'
                }
            }
        }

        stage('Push Docker Images') {
            when {
                branch 'main'  // Only push images when on main branch
            }
            steps {
                withCredentials([string(credentialsId: 'docker-registry-token', variable: 'DOCKER_TOKEN')]) {
                    sh 'docker login ${DOCKER_REGISTRY} -u username -p ${DOCKER_TOKEN}'
                    sh 'docker push ${DOCKER_REGISTRY}/${IMAGE_NAME}-backend:${IMAGE_TAG}'
                    sh 'docker push ${DOCKER_REGISTRY}/${IMAGE_NAME}-frontend:${IMAGE_TAG}'
                }
            }
        }

        stage('Deploy to Development') {
            when {
                branch 'main'  // Only deploy when on main branch
            }
            steps {
                // This could be a script that updates AWS ECS service or runs docker-compose on a remote host
                sh 'echo "Deploying to development environment"'
                // Example: sh 'ssh user@dev-server "cd /app && docker-compose pull && docker-compose up -d"'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}