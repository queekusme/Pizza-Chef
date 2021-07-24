@Library('forge-shared-library')_

pipeline {
    agent {
        label 'docker'
    }

    stages {
        stage('Setup') {
            steps {
                script {
                    env.MYVERSION = sh(returnStdout: true, script: './gradlew properties -q | grep "^version:" | awk "{print \$2}"').trim()
                }
            }
        }
        stage("Build Mod")
        {
            agent {
                docker { image 'gradle:jdk16-hotspot' }
            }
            steps {
                writeChangelog(currentBuild, 'build/changelog.txt')
                println sh(returnStdout: true, script: 'cat build/changelog.txt').trim()

                withCredentials([string(credentialsId: 'CURSEFORGE_API_UPLOAD', variable: 'TOKEN')])
                {
                    withEnv([
                        "CURSEFORGE_API_UPLOAD=${TOKEN}"
                    ])
                    {
                        sh './gradlew curseforge'
                    }
                }
            }
        }
    }
}