FROM scratch
WORKDIR /resources/resource
COPY ingressResource /resources/resource
RUN kubectl apply -f /resources/resource


