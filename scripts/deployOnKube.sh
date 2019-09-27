# set -e

# # deploy to cluster
# export IBMCLOUD_API_KEY=${kubeApikey}
# ibmcloud login -a ${bluemixApiEndpoint} -r ${region}
# export varToBeExported=`ibmcloud cs cluster-config ${kubeClusterName} | awk -F'export' '{print $2}'`
# export ${varToBeExported}

# if [ -d ${deployStage}/cluster/ingress ]; then
#     cd ${deployStage}/cluster/ingress
#     listOfIngressFiles=`ls`
#     listOfIngressFiles=( $listOfIngressFiles )
#     for ingressfile in "${listOfIngressFiles[@]}"
#     do
#         echo "applying ${ingressfile}"
#         kubectl apply -f ${ingressfile} --record
#     done
#     cd ..
# fi