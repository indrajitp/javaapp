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
					sh '''
					cp $WORKSPACE/target/*.war ./
					'''
						veracode applicationName: "GNAP", criticality: 'High', sandboxName: 'transactionApi', pHost: 'analysiscenter.veracode.com', scanName: "javaapp-${BUILD_NUMBER}",uploadIncludesPattern: '*.*ar', useIDkey: true, vid: '575998590c9d79338d32d35eaaa1b13d', vkey: 'e5353cc0736e2783fa2c454cc7c728304adf79de661267d0e9d33e4d1d8491e3ff72244020bc3d33fd98c82868fc1423918be777e016920dcd1eaccb549377ec'
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
