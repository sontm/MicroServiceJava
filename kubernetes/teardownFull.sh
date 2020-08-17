# Cart service
#kubectl delete -f deploy/cart-service-deployment.yaml 
kubectl delete -f kubernetes/service/account-service-service.yaml 
sleep 2s
kubectl delete -f kubernetes/service/rabbitmq-service.yaml
sleep 2s
kubectl delete -f kubernetes/service/postgresql-service.yaml 
sleep 2s

kubectl delete -f kubernetes/deploy/rabbitmq-deployment.yaml
    #kubectl exec -it rabbitmq-694b5b7ddc-pplq7 -- bash
sleep 2s
# Postgres
kubectl delete -f kubernetes/deploy/postgresql-deployment.yaml 
sleep 2s
# Account Service
kubectl delete -f kubernetes/deploy/account-service-deployment.yaml 
sleep 2s

kubectl delete -f kubernetes/volumes/postgresql-claim0-pvc.yaml
sleep 2s

#sleep 2s
#kubectl delete -f service/cart-service-service.yaml 