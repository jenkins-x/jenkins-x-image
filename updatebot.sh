#!/bin/bash

VERSION=$1
ORG=jenkinsxio
APP_NAME=jenkinsx

updatebot push-version --kind helm $ORG/$APP_NAME ${VERSION}
updatebot push-version --kind docker $ORG/$APP_NAME ${VERSION}
updatebot push-regex -r "\s+ImageTag: \"(.*)\"" -v ${VERSION} --previous-line "\s+Image: \"jenkinsxio/jenkinsx\"" values.yaml
