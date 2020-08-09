# Account Service
kubectl apply -f deploy/account-service-deployment.yaml 
sleep 2s
kubectl apply -f service/account-service-service.yaml 
sleep 2s

# Cart service
kubectl apply -f deploy/cart-service-deployment.yaml 
sleep 2s
kubectl apply -f service/cart-service-service.yaml 
