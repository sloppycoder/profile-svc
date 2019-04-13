## Testing monitoring with Prometheus Operator on GKE.


### Files
* [namespace_monitoring.json](namespace_monitoring.json) is used to create new monitoring namespace.
* [deploy.yaml](deploy.yaml) creates deployment and it's realted service object.
* [servicemonitor.yml](servicemonitor.yaml) creates the service monitor object. 

Service Monitor object will trigger Prometheus Operator to update Prometheus configuration to start scraping the pod's metrics endpoint. In order for this to work the service monitor object must have label ```k8s-app```, as configured by prometeus object. For more details, run the following command and check the service monitor selector configured.
```bash 
kubectl describe prometheus -n monitoring 
```


### Install Prometheus Operator

```bash

git clone https://github.com/coreos/prometheus-operator.git

cd prometheus-operator
git checkout release-0.29 # I used this branch for testing
kubectl apply -f namespace_monitoring.json
kubectl -f -R contrib/kube-prometheus/manifests/

```


### Create deployment and service
Creating deployment and service in monitoring namespace

```bash

kubectl apply -f deploy.yaml -n monitoring
kubectl apply -f servicemonitor.yaml -n monitoring

```

Then check Prometheus console and check if configuration is automatically updated to scrape the prometheus metrics endpoint, it should contains something like this:

```text

- job_name: monitoring/profile-svc/0
  honor_labels: true
  scrape_interval: 15s
  scrape_timeout: 10s
  metrics_path: /actuator/prometheus
  scheme: http
  kubernetes_sd_configs:
  - api_server: null
    role: endpoints
    namespaces:(https://github.com/coreos/prometheus-operator/issues/2557
      names:
      - monitoring
  relabel_configs:
    - blah
    - blah
    

```


However, Prometheus cannot detect the correct endpoint if the service is deployed into a different name space.

```bash

kubectl apply -f deploy.yaml -n my-project
kubectl apply -f servicemonitor.yaml -n monitoring

```

The configuration is generated but Prometheus does not scrape my metrics endpoint. I created [an issue](https://github.com/coreos/prometheus-operator/issues/2557) in Prometheus Operator project on GitHub].
