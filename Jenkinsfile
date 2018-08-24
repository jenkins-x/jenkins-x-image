pipeline {
    agent {
        label "jenkins-jx-base"
    }
    environment {
        ORG         = 'jenkinsxio'
        APP_NAME    = 'jenkinsx'
    }
    stages {
        stage('CI Build and push snapshot') {
            when {
                branch 'PR-*'
            }
            steps {
                container('jx-base') {
                    sh "docker build --no-cache -t docker.io/$ORG/$APP_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER ."
                    sh "docker push docker.io/$ORG/$APP_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER"
                }
            }
        }
    
        stage('Build and Push Release') {
            when {
                branch 'master'
            }
            steps {
                container('jx-base') {
                    sh "./jx/scripts/release.sh"
                }
            }
        }
    }
}
