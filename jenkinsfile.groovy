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
				sh '''
                mvn clean install
				'''
            }
        }
        stage('Scan') {
            steps {
                echo 'Testing2..'
            }
        }
        stage('upload') {
            steps {
                sh '''
                    echo "Need to add upload code here"
                '''
            }
        }
    }
}
