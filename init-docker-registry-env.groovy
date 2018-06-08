import hudson.slaves.EnvironmentVariablesNodeProperty
import hudson.EnvVars
import jenkins.model.Jenkins

def KEY = "DOCKER_REGISTRY"
def HOST_ENV = "JENKINS_X_DOCKER_REGISTRY_SERVICE_HOST"
def PORT_ENV = "JENKINS_X_DOCKER_REGISTRY_SERVICE_PORT"


def globals = Jenkins.get().getGlobalNodeProperties().get(EnvironmentVariablesNodeProperty.class)
if (globals == null) {
  globals = new EnvironmentVariablesNodeProperty()
  Jenkins.get().getGlobalNodeProperties().add(globals)
}

def envVars = globals.getEnvVars();
if (envVars.get(KEY) == null || envVars.get(KEY).length() == 0) {
  // is there a system environment
  def host = System.getenv(HOST_ENV);
  def port = System.getenv(PORT_ENV);
  if (host == null || port == null) {
      println "Warning missing host/port environment variables ${HOST_ENV} =${host}, ${PORT_ENV} = ${port}"
  } else {
    def value = host + ":" + port
    println "Adding docker registry ${KEY} = ${value}"
    envVars.put(KEY, value)
  }  
}