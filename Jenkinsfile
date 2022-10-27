#!/usr/bin/env groovy

// Jenkins pipeline declarative version

pipeline {
	agent any

	triggers {
		pollSCM ''
	}

	stages {
		stage('Checkout SCM') {
			when { anyOf { branch 'devel'; branch 'master'; branch 'feature/*' } }
			steps {
				git credentialsId: 'git_ssh_key',
				url: 'ssh://git@git.arbeglanretsc.com:2222/david/WebCalculator.git'
			}
		}

		stage('Gradle clean') {
			when { anyOf { branch 'devel'; branch 'master'; branch 'feature/*' } }
			steps { sh 'gradle clean --no-daemon' }
		}

		stage('Gradle check') {
			when { anyOf { branch 'devel'; branch 'master'; branch 'feature/*' } }
			steps { sh 'gradle check --no-daemon' }
		}

		stage('Gradle build') {
			when { anyOf { branch 'devel'; branch 'master' } }
			steps { sh 'gradle build --no-daemon' }
		}

		stage('Build test container') {
			when {
				allOf {
					expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' };
					branch 'devel'
				}
			}
			steps {
				script {
					docker.withRegistry('https://registry.arbeglanretsc.com', 'docker_reg_key') {
						def image = docker.build('webcalculator:latest', '--no-cache .')
						image.push('latest')
					}
					sh 'docker image prune -f'
				}
			}
		}

		stage('Run test container on test environment') {
			when {
				allOf {
					expression { currentBuild.result == null || currentBuild.result == 'SUCCESS'};
					branch 'devel'
				}
			}
			steps {
				script {
					docker.withServer('utility.arbeglanretsc.com:2376', 'tls_client_cert') {
						sh '''
							if docker container ls | grep -q webcalculator; then
								docker container stop webcalculator
								docker container rm -f webcalculator
								docker image rm -f registry.arbeglanretsc.com/webcalculator:latest
							fi
						'''
						docker.withRegistry('https://registry.arbeglanretsc.com', 'docker_reg_key') {
							def image = docker.image('registry.arbeglanretsc.com/webcalculator:latest')
							image.pull()
							image.run(['--publish 8081:8081 --name webcalculator'])
						}
					}
				}
			}
		}

		stage('Build prod container') {
			when {
				allOf {
					expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' };
					branch 'master'
				}
			}
			steps {
				script {
					docker.withRegistry('https://registry.arbeglanretsc.com', 'docker_reg_key') {
						def image = docker.build("webcalculator:rev${env.BUILD_ID}", '--no-cache .')
						image.push("rev${env.BUILD_ID}")
					}
					sh 'docker image prune -f'
				}
			}
		}
	}
}
