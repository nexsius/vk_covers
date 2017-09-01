pipeline {
    agent none
    stages {
        stage('get sources') {
            steps {
                git clone 'https://github.com/nexsius/vk_covers.git'
            }
    }
        stage('build') {
            steps {
                sh "/usr/bin/mvn clean compile install"
            }
    }
        stage('results') {
            steps {
               sh "echo done"
               sh 'ls -al target'
               sh 'pwd'
            }
        }
        
}
