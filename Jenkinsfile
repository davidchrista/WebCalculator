#!/usr/bin/env groovy

pipeline {
	agent any

	triggers {
		pollSCM ''
	}

	stages {
		stage('Checkout') {
			steps {
				git 'git@192.168.1.203:ARetsc/WebCalculator.git'
			}
		}

		stage('Cleanup') {
			steps {
				sh 'gradle clean'
			}
		}

		stage('Gradle Build') {
			steps {
				sh 'gradle assemble'
			}
		}

		stage('Gradle Unit Tests') {
			when {
				expression {
					currentBuild.result == null || currentBuild.result == 'SUCCESS'
				}
			}
			steps {
				sh 'gradle check'
			}
		}

		stage('Clone Deploy Repo')
		{
			when {
				allOf {
					not {
						branch 'master'
					}
					expression {
						currentBuild.result == null || currentBuild.result == 'SUCCESS'
					}
				}
			}
			steps {
				git 'git@localhost:ARetsc/WebCalculator-Deploy.git'
			}
		}

		stage('Build Test Container') {
			when {
				allOf {
					not {
						branch 'master'
					}
					expression {
						currentBuild.result == null || currentBuild.result == 'SUCCESS'
					}
				}
			}
			steps {
				sh 'ls -la'
			}
		}
	}
}
