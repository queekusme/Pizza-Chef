node("docker") {
    checkout scm

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