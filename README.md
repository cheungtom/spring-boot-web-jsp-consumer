1. Build:
mvn clean install

2. Eclipse project:
mvn eclipse:eclipse

3. Local test
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