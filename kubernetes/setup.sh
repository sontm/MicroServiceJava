# Account Service
kubectl apply -f kubernetes/deploy/account-service-deployment.yaml 
sleep 2s
kubectl apply -f kubernetes/service/account-service-service.yaml 
sleep 2s

# Cart service
kubectl apply -f kubernetes/deploy/cart-service-deployment.yaml 
sleep 2s
kubectl apply -f kubernetes/service/cart-service-service.yaml 
