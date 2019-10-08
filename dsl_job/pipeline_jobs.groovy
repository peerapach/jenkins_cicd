folder('01-BACKEND') {
    displayName('Backend')
    description('Folder for Backend PROD')
}

folder('01-FRONTEND') {
    displayName('Frontend')
    description('Folder for frontend')
}

folder('01-BACKEND/PROD') {
    displayName('PROD')
    description('Folder for Backend PROD')
}

folder('/01-FRONTEND/PROD') {
    displayName('PROD')
    description('Folder for PROD')
}

pipelineJob('01-BACKEND/job_Pipeline_1') {

  def repo = 'https://github.com/peerapach/node-jx.git'

  triggers {
    scm('H/15 * * * *')
  }
  description("Pipeline for $repo")

  definition {
    cpsScm {
      scm {
        git {
          remote { url(repo) }
          branches('master', '**/feature*')
          scriptPath('misc/Jenkinsfile')
          extensions { }  // required as otherwise it may try to tag the repo, which you may not want
        }
      }
    }
  }
}

pipelineJob('01-BACKEND/PROD/job_Pipeline_prod') {
  definition {
    cps {
      script('''
        pipeline {
            agent any
                stages {
                    stage('Stage 1') {
                        steps {
                            echo 'logic'
                        }
                    }
                    stage('Stage 2') {
                        steps {
                            echo 'logic'
                            echo 'add new step'
                        }
                    }
                }
            }
        }
      '''.stripIndent())
      sandbox()     
    }
  }
}

pipelineJob('/01-FRONTEND/PROD/Pipeline_frontend_prod') {
  parameters {
    stringParam('myParameterName', 'my default stringParam value', 'my description')
  }
  definition {
    cps {
      script(readFileFromWorkspace('pipelines/jenkinsfile1.groovy'))
      sandbox()     
    }
  }
}

pipelineJob('/01-FRONTEND/PROD/Pipeline_frontend_prod_2') {
  def repo = 'https://github.com/peerapach/node-jx.git'
  parameters {
    stringParam('myParameterName', 'my default stringParam value', 'my description')
  }    
  definition {
    cpsScm {
      scm {
        git {
          remote { url(repo) }
          branches('master', '**/feature*')
          scriptPath('misc/Jenkinsfile')
          extensions { }  // required as otherwise it may try to tag the repo, which you may not want
        }
      }
    }
  }
}

