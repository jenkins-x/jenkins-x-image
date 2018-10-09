pipeline {
    agent any
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
                sh "docker build --no-cache -t docker.io/$ORG/$APP_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER ."
                sh "docker push docker.io/$ORG/$APP_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER"
            }
        }
    
        stage('Build and Push Release') {
            when {
                branch 'master'
            }
            steps {
                git "https://github.com/jenkins-x/jenkins-x-image"
                sh "jx step git credentials"
                sh "./jx/scripts/release.sh"
            }
        }
    }
}
