#Post Install
#	kubectl create deploy nginx --image=nginx
#	kubectl create service nodeport nginx --tcp=80:80

#To FIx CrashLoopBackOff of coredns
#	$ kubectl edit cm coredns -n kube-system 
#		delete ‘loop’ ,save and exit restart master node. It was work for me.
#	https://stackoverflow.com/questions/53559291/kubernetes-coredns-in-crashloopbackoff



# Install dashboard
kubectl apply -f ./kubernetes/_setup/dashboard-2.0.0.yaml

# kubectl proxy --address='0.0.0.0' --accept-hosts='.*'
# http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/
# Token: > kubectl -n kube-system describe secret $(kubectl -n kube-system get secret | awk '/^deployment-controller-token-/{print $1}') | awk '$1=="token:"{print $2}'
kubectl -n kube-system describe secret $(kubectl -n kube-system get secret | awk '/^deployment-controller-token-/{print $1}') | awk '$1=="token:"{print $2}'

kubectl create -f ./kubernetes/_setup/prometheus-grafana/manifests/setup
sleep 10

until kubectl get servicemonitors --all-namespaces ; do date; sleep 1; echo ""; done

kubectl create -f ./kubernetes/_setup/prometheus-grafana/manifests/

#Prometheus
#$ kubectl --namespace monitoring port-forward svc/prometheus-k8s 9090
#Then access via http://localhost:9090

#Grafana
#$ kubectl --namespace monitoring port-forward svc/grafana 3000
#Then access via http://localhost:3000 and use the default grafana user:password of admin:admin.

#Alert Manager
#$ kubectl --namespace monitoring port-forward svc/alertmanager-main 9093
#Then access via http://localhost:9093