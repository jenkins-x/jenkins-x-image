pipeline {
    agent {
        label "jenkins-jx-base"
    }
    environment {
        GH_CREDS = credentials('jenkins-x-github')
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
                    sh "docker build -no-cache -t docker.io/$ORG/$APP_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER ."
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
                    // ensure we're not on a detached head
                    sh "git checkout master"

                    // until we switch to the new kubernetes / jenkins credential implementation use git credentials store
                    sh "git config credential.helper store"

                    // so we can retrieve the version in later steps
                    sh "echo \$(jx-release-version) > VERSION"
                    sh "git add VERSION"
                    sh "git commit -m \"release \$(cat VERSION)\""
                    sh "git tag -fa v\$(cat VERSION) -m \"Release version \$(cat VERSION)\""
                    sh "git push origin v\$(cat VERSION)"

                    sh "docker build -no-cache -t docker.io/$ORG/$APP_NAME:\$(cat VERSION) ."
                    sh "docker push docker.io/$ORG/$APP_NAME:\$(cat VERSION)"
                }
            }
        }
    }
}
