
# Install dashboard
#kubectl delete -f ./kubernetes/_setup/dashboard-2.0.0.yaml


kubectl delete -f ./kubernetes/_setup/prometheus-grafana/manifests/
sleep 10s
kubectl delete -f ./kubernetes/_setup/prometheus-grafana/manifests/setup

