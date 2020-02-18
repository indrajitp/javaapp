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
post {  
         always {  
             sh '''
			 echo "Executing POST execution notification"
			 '''  
         }  
         success {  
             sh '''
			 echo -e "Jenkins build compeleted for below services \n `cat ~/file_to_be_build` "|  mailx -r JekinsJobAlerts@globalpay.com -s "Jenkins CI SUCCESS" sanjeevkumarsingh@globalpay.com indrajit.patil3@globalpay.com
			 '''  
         }  
         failure {  
             sh '''
			 echo -e "Jenkins build Failed for any of the below services \n `cat ~/file_to_be_build` "|  mailx -r JekinsJobAlerts@globalpay.com -s "Jenkins CI Failed" sanjeevkumarsingh@globalpay.com indrajit.patil3@globalpay.com
			 '''
         }  

    }
}
