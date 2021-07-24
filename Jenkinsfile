@Library('forge-shared-library')_

node("docker")
{
    checkout scm

    stage("Generate Changelog")
    {
        writeChangelog(currentBuild, 'build/changelog.txt')
    }

    withCredentials([string(credentialsId: 'CURSEFORGE_API_UPLOAD', variable: 'TOKEN')])
    {
        withEnv(['CURSEFORGE_API_UPLOAD=$TOKEN', stage("Build Mod")
        {
            docker.image('gradle:jdk16-hotspot').inside()
            {
                sh '''
                    chmod +x gradlew
                    ./gradlew curseforge
                '''
            }
        }
    }
}