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
			script
				{
				sh '''
                mvn clean install
				'''

				}
            }
        }
        stage('Scan') {
            steps {
                script {
					echo "Executing VeraCode Security Scan..."
						veracode applicationName: "App Engine Application", criticality: 'High', sandboxName: '', pHost: 'analysiscenter.veracode.com', scanName: "javaapp-${BUILD_NUMBER}",uploadIncludesPattern: '*.*ar', useIDkey: true, vid: 'indrajit.patil3@globalpay.com', vkey: 'IP@Jan2020'
				}
            }
        }
        stage('upload') {
            steps {
				script
				{
				def pom = readMavenPom file: 'pom.xml'
				def version = pom.version
				println "Version: ${version}"
				
				def server = Artifactory.server 'artifactory'
                def uploadSpec = """{
                "files": [
                    {
                        "pattern": "$WORKSPACE/target/*.war",
                        "target": "libs-snapshot-local/com/bitwiseglobal/javaapp/${version}"
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
