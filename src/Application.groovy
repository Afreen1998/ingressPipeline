import groovy.transform.Field

@Field final String DEPENDENT_SERVICE_CREDENTIAL='DEPENDENT_SERVICE_CREDENTIAL'
@Field final String BLUEMIX_CREDENTIAL='BLUEMIX_CREDENTIAL'
@Field final String BLUEMIX_USERID='BLUEMIX_USERID'
@Field final String BLUEMIX_PASSWORD='BLUEMIX_PASSWORD'
@Field final String BLUEMIX_APIKEY='BLUEMIX_APIKEY'
@Field final String KUBE_ORG='KUBE_ORG'
@Field final String KUBE_SPACE='KUBE_SPACE'
@Field final String KUBE_SPACE_GUID='KUBE_SPACE_GUID'
@Field final String KUBE_APIKEY='KUBE_APIKEY'
@Field final String KUBE_NAMESPACE='KUBE_NAMESPACE'
@Field final String KUBE_CLUSTERNAME='KUBE_CLUSTERNAME'

def deploy(deployStage)
{
    // if (!isDeployBuild()) {
    //     return
    // }
    try {
        //vaultSecrets=getSecretsFromVaultForDeployment(projectName, deployStage)
       // withEnv(vaultSecrets){
             deployToKube(deployStage)
            echo "success" + deployStage;
       // }
    } catch (err) {
        throw err
    }
}

def deployToKube(deployStage)
{
     try{
        timeout(time:30, unit:'MINUTES') {
            // handleNpmrc(deployStage)
            // getGlobalEnvPropFiles()

            def propsDir = getEnvPropsFileDir(deployStage)
            echo "${propsDir}";
            //IBM cloud details

            //def bluemixApiKey = getBluemixApiKey(deployStage)
            //def bluemixUserName = getBluemixUserName(deployStage)
            //def bluemixUserPassword = getBluemixUserPassword(deployStage)
            //def bluemixApiEndpoint = getBMApiEndpoint(deployStage)
            //def region = getKubeClusterRegionString(deployStage)
           // def region = "us-south";
            //kube details

            //def kubeApikey = getDestinationKubeProperties(deployStage, KUBE_APIKEY)
            //def kubeNamespace = getDestinationKubeProperties(deployStage, KUBE_NAMESPACE)
           // def kubeClusterName = getDestinationKubeProperties(deployStage, KUBE_CLUSTERNAME)

//             // if (projectName.contains('bms-push') && (deployStage.contains('-yp') || deployStage.contains('-prod'))) {
//             //     def dependentServiceSecrets = setDependentServiceSecrets(projectName, kubeNamespace, deployStage, serviceName)
//             //     if (dependentServiceSecrets == false) {
//             //         println "Failed Creating secrets for Dependent service"
//             //             throw new hudson.AbortException('setDependentServiceSecrets(): failed with non-zero return code. Exiting')
//             //     }
//             // }
//              withEnv(['deployStage=' + deployStage, 'propsDir=' + propsDir,'bluemixApiEndpoint=' + bluemixApiEndpoint, 'kubeNamespace=' + kubeNamespace, 'kubeClusterName=' + kubeClusterName,
//             'kubeApikey=' + kubeApikey, 'region=' + region]) {
//                 sshagent([env.BLUEMPUS_SSHAGENT_KEY]) {
//                 sh "git clone git@github.ibm.com:afkatage/ingress_pipeline.git pipeline"
//                 }
//                 def statusCode = sh(script: 'pipeline/scripts/deployOnKube.sh', returnStatus: true)

//                 print "deploy script statusCode=" + statusCode
                
//                 if(statusCode!=0) {
//                     currentBuild.result = 'FAILURE'
//                     throw new hudson.AbortException('deployOnK8s.sh failed with non-zero return code. Exiting')
//                 } else {
//                     print "Current Build Status Success"
//                 }
//             }
            
          }
     } catch(deployError) {
        
         throw deployError
    }
 }


 def getEnvPropsFileDir(deployStage) {
    
     return deployStage + '/cluster'
 }

// def getBluemixApiKey(deployStage='not set') {
//     // return if its located in vault and set as an environment variable
//     if(env.BLUEMIX_APIKEY!=null){
//         println "BLUEMIX_APIKEY from Vault "
//         def api = sh script: 'set +x ; bdeploy decrypt -k ${bms_decrypt} -s ' + env.BLUEMIX_APIKEY + ' ; set -x', returnStdout: true
//         api = api.trim()
//         return api
//         }
//     def propsDir = getEnvPropsFileDir()
//     println "BLUEMIX_APIKEY not set through env"
//     def api = sh script: 'bdeploy parse -k ${bms_decrypt} -s ' + propsDir + '/config.json -p cf_apikey', returnStdout: true
//     api = api.trim()
//     return api
// }

// def getBluemixUserName(deployStage='not set') {
//     if(env.BLUEMIX_USERID!=null){
//         println "BLUEMIX_USERID from Vault "
//         return env.BLUEMIX_USERID
//     }
//     def propsDir = getEnvPropsFileDir()
//     println "BLUEMIX_USERID not set through env"
//     def username = sh script: 'bdeploy parse -s ' + propsDir + '/config.json -p cf_email', returnStdout: true
//     username = username.trim()
//     return username
// }

// def getBluemixUserPassword(deployStage='not set') {
//     if(env.BLUEMIX_PASSWORD!=null){
//         println "BLUEMIX_PASSWORD from Vault "
//         def password = sh script: 'set +x ; bdeploy decrypt -k ${bms_decrypt} -s ' + env.BLUEMIX_PASSWORD + ' ; set -x', returnStdout: true
//         password = password.trim()
//         return password
//     }
//     def propsDir = getEnvPropsFileDir()
//     println "BLUEMIX_PASSWORD not set through env"
//     def password = sh script: 'bdeploy parse -k ${bms_decrypt} -s ' + propsDir + '/config.json -p cf_password', returnStdout: true
//     password = password.trim()
//     return password
// }

// def getBMApiEndpoint(deployStage){
//     // ANK: For testing purpose return dallas yp
//     return "https://cloud.ibm.com"
//     switch(deployStage) {
//         case "dallas-yp": return "https://api.ng.bluemix.net"
//         case "dallas-yp-2": return "https://api.ng.bluemix.net"
//         case "dallas-yp-3": return "https://api.ng.bluemix.net"
//         case "dallas-ys1-dev": return "https://api.ng.bluemix.net"
//         case "dallas-ys1": return "https://api.ng.bluemix.net"
//         //case "london-yp": return "https://api.eu-gb.bluemix.net"
//         //case "london-ys1": return "https://api.stage1.eu-gb.bluemix.net"
//         //case "frankfurt-yp": return "https://api.eu-de.bluemix.net"
//         //case "sydney-yp": return "https://api.au-syd.bluemix.net"
//         case "washington-yp": return "https://api.us-east.bluemix.net"
//     }
// }

// def getDestinationKubeProperties(deployStage, property) {
//     def propsDir = getEnvPropsFileDir()

//     switch(property){
//         case KUBE_ORG:
//             def kube_org = sh script: 'bdeploy parse -k ${bms_decrypt} -s ' + propsDir + '/config.json -p kube_org', returnStdout: true
//             kube_org = kube_org.trim()
//             return kube_org
//         case KUBE_SPACE:
//             def kube_space = sh script: 'bdeploy parse -k ${bms_decrypt} -s ' + propsDir + '/config.json -p kube_space', returnStdout: true
//             kube_space = kube_space.trim()
//             return kube_space
//         case KUBE_SPACE_GUID:
//             def kube_space_guid = sh script: 'bdeploy parse -k ${bms_decrypt} -s ' + propsDir + '/config.json -p kube_space_guid', returnStdout: true
//             kube_space_guid = kube_space_guid.trim()
//             return kube_space_guid
//         case KUBE_APIKEY:
//             // Its kept in vault and set as env variable in the Jenkinsfile
//             if(env.BLUEMIX_APIKEY!=null){
//                 return getBluemixApiKey(deployStage)
//             }
//             def kube_apikey = sh script:'bdeploy parse -k ${bms_decrypt} -s ' + propsDir + '/config.json -p kube_apikey', returnStdout: true
//             kube_apikey = kube_apikey.trim()
//             if(kube_apikey==''){
//                 print "kube_apikey is empty, taking value from cf_apikey"
//                 kube_apikey = sh script: 'bdeploy parse -k ${bms_decrypt} -s ' + propsDir + '/config.json -p cf_apikey', returnStdout: true
//             }
//             return kube_apikey
//         case KUBE_NAMESPACE:
//             def kube_namespace = sh script: 'bdeploy parse -k ${bms_decrypt} -s ' + propsDir + '/config.json -p kube_namespace', returnStdout: true
//             kube_namespace = kube_namespace.trim()
//             return kube_namespace
//         case KUBE_CLUSTERNAME:
//             def kube_clusterName = sh script:  'bdeploy parse -k ${bms_decrypt} -s ' + propsDir + '/config.json -p kube_cluster_name', returnStdout: true
//             kube_clusterName = kube_clusterName.trim()
//             return kube_clusterName
//     }

// }

// // def getKubeClusterRegionString(deployStage) {
// //     switch(deployStage){
// //         case "dallas-ys1": return "us-south"
// //         case "dallas-yp": return "us-south"
// //         case "dallas-yp-2": return "us-south"
// //         case "dallas-yp-3": return "us-south"
// //         case "dallas-ys1-dev": return "us-south"
// //         case "dallas-ys1-test": return "us-south"
// //         case "london-yp": return "eu-gb"
// //         case "london-ys1": return "eu-gb"
// //         case "frankfurt-yp":
// //             if ( env.Component != null ) {
// //                 if ( Component.contains("bms-mobile-proxy") ) {
// //                     println "secret path points to us-south"
// //                     return "us-south"
// //                 } else {
// //                     println "secret path points to eu-de"
// //                     return "eu-de"
// //                 }
// //             } else {
// //                 println " else secret path points to eu-de"
// //                 return "eu-de"
// //             }
// //         case "sydney-yp": return "au-syd"
// //         case "dallas-prod": return "us-south"
// //         case "london-prod": return "eu-gb"
// //         case "frankfurt-prod":
// //             if ( env.Component != null ) {
// //                 if ( Component.contains("bms-mobile-proxy") ) {
// //                     println "secret path points to us-south"
// //                     return "us-south"
// //                 } else {
// //                     println "secret path points to eu-de"
// //                     return "eu-de"
// //                 }
// //             } else {
// //                 println "else secret path points to eu-de"
// //                 return "eu-de"
// //             }
// //         case "sydney-prod": return "au-syd"
// //         case "washington-yp": return "us-east"
// //         case "washington-prod": return "us-east"
// //         case "tokyo-yp": return "jp-tok"
// //     }
// // }

// // def isPrBuild() {
// //     def matcher = env.JOB_NAME =~ 'PR-\\d+'
// //     def isPrBuild = matcher ? true : false
// //     matcher = null
// //     return isPrBuild
// // }

// // def isDeployBuild() {
// //     echo "JOB_NAME = ${env.JOB_NAME}"
// //     if (isPrBuild()) {
// //         return false
// //     }
// //     if (env.JOB_NAME.startsWith('bmd-codegen') || env.JOB_NAME.startsWith('bmd-pman')) {
// //         if (env.BRANCH_NAME.equals('development')
// //          || env.BRANCH_NAME.equals('master')
// //          || env.JOB_NAME.endsWith('-any-branch')
// //          || env.JOB_NAME.endsWith('-deploy')) {
// //             return true
// //         } else {
// //             return false
// //         }
// //     }
// //     return true
// // }

return this;