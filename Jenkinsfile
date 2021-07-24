@Library('forge-shared-library')_

pipeline {
    agent {
        label 'docker'
    }

    stages {
        stage('Setup') {
            steps {
                script {
                    env.MYVERSION = sh(returnStdout: true, script: './gradlew properties -q | grep "^version:" | awk "{print $2}"').trim()
                }
            }
        }
        stage('Changelog') {
            steps {
                writeChangelog(currentBuild, 'build/changelog.txt')
            }
        }
        stage("Build Mod")
        {
            steps {
                withCredentials([string(credentialsId: 'CURSEFORGE_API_UPLOAD', variable: 'TOKEN')])
                {
                    withEnv([
                        "CURSEFORGE_API_UPLOAD=${TOKEN}"
                    ])
                    {
                        docker.image('gradle:jdk16-hotspot').inside()
                        {
                            sh './gradlew curseforge'
                        }
                    }
                }
            }
        }
    }
}