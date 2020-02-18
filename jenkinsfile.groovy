pipeline {
    agent any
	tools
    {
        maven 'Jenkins-Maven'
        jdk 'Jenkins-Java'
    }
    stages {
		stage ('Initialize'){
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        stage('Build') {
            steps {
                echo 'Building 2..'
            }
        }
        stage('Scan') {
            steps {
                echo 'Testing2..'
            }
        }
        stage('upload') {
            steps {
                echo 'Deploying2....'
            }
        }
    }
}
