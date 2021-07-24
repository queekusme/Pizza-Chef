@Library('forge-shared-library')_

node("docker") {
    checkout scm

    stage("Generate Changelog")
    {
        writeChangelog(currentBuild, 'build/changelog.txt')
    }

    stage("Build Mod")
    {
        docker.image('gradle:jdk16-hotspot').inside()
        {
            sh '''
                chmod +x gradlew
                ./gradlew build
            '''
        }
    }
}