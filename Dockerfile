FROM jenkins/jenkins:2.143-slim

USER root
RUN apt-get update && apt-get install -y vim
USER jenkins

# Disable plugin banner on startup
RUN echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state

#COPY jenkins.war /usr/share/jenkins/jenkins.war
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
COPY init-docker-registry-env.groovy /usr/share/jenkins/ref/init.groovy.d/init-docker-registry-env.groovy

RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt
#COPY plugins/*.jpi /usr/share/jenkins/ref/plugins/

