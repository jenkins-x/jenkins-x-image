pipeline {
    agent {
        label "jenkins-jx-base"
    }
    environment {
        GH_CREDS = credentials('jenkins-x-github')
        ORG         = 'rawlingsj'
        APP_NAME    = 'jenkinsx'
    }
    stages {
        stage('CI Build and push snapshpt') {
            when {
                branch 'PR-*'
            }
            steps {
                container('jx-base') {
                    sh "docker build -t docker.io/$ORG/$APP_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER ."
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
                    // until kubernetes plugin supports init containers https://github.com/jenkinsci/kubernetes-plugin/pull/229/
                    sh 'cp /root/netrc/.netrc ~/.netrc'

                    // so we can retrieve the version in later steps
                    sh "echo \$(jx-release-version) > VERSION"
                    sh "git add VERSION"
                    sh "git commit -m \"release \$(cat VERSION)\""
                    sh "git tag -fa v\$(cat VERSION) -m \"Release version \$(cat VERSION)\""
                    sh "git push origin v\$(cat VERSION)"

                    sh "docker build -t docker.io/$ORG/$APP_NAME:\$(cat VERSION) ."
                    sh "docker push docker.io/$ORG/$APP_NAME:\$(cat VERSION)"
                }
            }
        }
    }
}
