pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building 2..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing2..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying2....'
            }
        }
    }
}