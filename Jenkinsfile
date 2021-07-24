@Library('forge-shared-library')_

// node("docker")
// {
//     checkout scm

//     stage("Generate Changelog")
//     {
//         withEnv([
//             "MYVERSION=${sh(returnStdout: true, script: './gradlew properties -q | grep "^version:" | awk "{print \$2}"').trim()}"
//         ])
//         {
//             writeChangelog(currentBuild, 'build/changelog.txt')
//             println "$currentBuild"
//             println sh(returnStdout: true, script: 'cat build/changelog.txt').trim()
//             sh 'false'
//         }
//     }

//     // stage("Build Mod")
//     // {
//     //     withCredentials([string(credentialsId: 'CURSEFORGE_API_UPLOAD', variable: 'TOKEN')])
//     //     {
//     //         withEnv([
//     //             "CURSEFORGE_API_UPLOAD=${TOKEN}"
//     //         ])
//     //         {
//     //             docker.image('gradle:jdk16-hotspot').inside()
//     //             {
//     //                 sh '''
//     //                     chmod +x gradlew
//     //                     ./gradlew curseforge
//     //                 '''
//     //             }
//     //         }
//     //     }
//     // }
// }

pipeline {
    agent {
        label 'docker'
    }

    environment {
        DISABLE_AUTH = 'true'
        DB_ENGINE    = 'sqlite'
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
                println sh(returnStdout: true, script: 'cat build/changelog.txt').trim()
                sh 'false'
            }
        }
    }
}