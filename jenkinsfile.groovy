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
				def pom = readMavenPom file: 'pom.xml'
				def version = pom.version
				println "Version: ${version}"
            }
        }
        stage('Scan') {
            steps {
                echo 'Testing2..'
            }
        }
        stage('upload') {
            steps {
				script
				{
				def server = Artifactory.server 'artifactory'
                def uploadSpec = """{
                "files": [
                    {
                        "pattern": "$WORKSPACE/target/*.war",
                        "target": "libs-snapshot-local/com/bitwiseglobal/javaapp/{$version}"
                    }
                ]
                }"""
                server.upload(uploadSpec)
                def buildInfo = server.upload uploadSpec
                server.publishBuildInfo buildInfo
				}
                
            }
        }
    }

}
