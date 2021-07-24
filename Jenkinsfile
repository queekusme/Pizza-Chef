node("docker") {
    checkout scm

    stage("Build Mod")
    {
        docker.image('adoptopenjdk/openjdk16:aarch64-ubuntu-jre-16.0.1_9').inside()
        {
            sh '''
                chmod +x gradlew
                ./gradlew build
            '''
        }
    }
}