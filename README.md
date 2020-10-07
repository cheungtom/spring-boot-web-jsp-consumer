1.) Build:

mvn clean install

2.) Eclipse project:

mvn eclipse:eclipse

3.) Local test

mvn spring-boot:run

4.) Test at PostMan (list your file content by S3 folder Name)

http://localhost:8080/?folderName=tom-s3-poc

5.) Docker build:

docker build -t cheungtom/s3-web .

6.) Run docker container

docker run -d --name s3-web --link s3-svc:s3-svc -p 8080:8080 cheungtom/s3-web

docker ps -a

7.) Logs

docker logs -f s3-web

8.) Docker Image push

docker login

docker push cheungtom/s3-web

9.) Kubernetes deploy

kubectl apply -f s3-web-replicaset.yaml

kubectl get pods

kubectl logs -f s3-web-p6xrb

kubectl get rs

kubectl apply -f s3-web-service.yaml

kubectl get svc

http://localhost:80/?folderName=tom-s3-poc

11.a) Install OpenShift on AWS by CloudFormtaion

Follow this link:

https://aws.amazon.com/quickstart/architecture/openshift/

Web OCP Admin console link:

https://red-hat-o-openshif-19mmubql4jye5-283815188.ap-southeast-2.elb.amazonaws.com/console/catalog

Connect to ansible-configserver

ssh -A -i "sydney_key.pem" ec2-user@ec2-3-25-75-82.ap-southeast-2.compute.amazonaws.com

Admin login:

sudo -s

oc whoami


You should be cluster admin system:admin

exit

Normal user login:

oc login

Server: https://Red-Hat-O-OpenShif-19MMUBQL4JYE5-283815188.ap-southeast-2.elb.amazonaws.com:443 (openshift)

User: admin

Pwd: When you set admin password for CloudFormation template
 
oc logout

The following section assume you use normal user login

11.b) Deploy to OpenShift - Manually build

oc new-project s3

oc project s3

Deploy Web API Microservice:

oc apply -f s3-web-replicaset.yaml

oc get pods

oc get rs

oc apply -f s3-web-service.yaml

oc get svc

oc expose service/s3-web

oc get routes

oc create -f nginx-pod.yml

oc exec -it nginx bash

apt update

apt install iputils-ping

apt install curl

apt install jq

Get by Service ELB

curl http://a4dc8beaea3e611eab960065e494cc6a-497550535.ap-southeast-2.elb.amazonaws.com:80/?folderName=tom-s3-poc

Get by K8s service name

curl http://s3-web:80/?folderName=tom-s3-poc

Get by OpenShift Inbound Traffic Router

curl http://s3-web-s3.router.default.svc.cluster.local:80/?folderName=tom-s3-poc

oc delete all --all

12.) Deploy by S2I image builder (Only apply for Maven jar project, war not support)

oc new-project s3-s2i

oc project s3-s2i

S2I can deploy jar only, not war. The follow not work

Deploy Web microservice

oc new-app java~https://github.com/cheungtom/spring-boot-web-jsp-consumer.git

oc delete all --all

13.) Deploy by S2I image builder template (Only apply for Maven jar project, war not support)

oc new-project s3-ui

oc project s3-ui

We use openjdk18-web-basic-s2i template

Same as your select image builder "OpenJDK 8" at OC Admin UI

Admin UI build limit: cannot pass env var

oc process --parameters openjdk18-web-basic-s2i -n openshift

oc export template openjdk18-web-basic-s2i -n openshift

S2I can deploy jar only, not war. The follow not work

Deploy Web microservice

oc new-app --template=openjdk18-web-basic-s2i -p APPLICATION_NAME=spring-boot-web-jsp-consumer \

-p SOURCE_REPOSITORY_URL=https://github.com/cheungtom/spring-boot-web-jsp-consumer.git \

-p SOURCE_REPOSITORY_REF=master \

-p CONTEXT_DIR=

oc delete all --all
