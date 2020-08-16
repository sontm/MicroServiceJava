kubectl delete -f ../kubernetes/service/account-service-service.yaml 
sleep 1
kubectl delete -f ../kubernetes/deploy/account-service-deployment.yaml
sleep 1

kubectl apply -f ../kubernetes/deploy/account-service-deployment.yaml
sleep 1
kubectl apply -f ../kubernetes/service/account-service-service.yaml 
