{{- define "rememberthis.v1.labels"}}
    labels:
      app: remember-this
      tier: backend
      chart: {{ .Chart.Name }}
{{- end}}

{{- define "rememberthis.v1.frontend-pod-info"}}
metadata: 
  name: frontend-pod
  namespace: {{ .Values.namespace }}
  labels: {{- include "rememberthis.v1.frontend-pod-labels" . | indent 4 }} 
spec: 
  containers:
    - name: frontend-pod
      ports: 
        - containerPort: 80
      image: willey599/frontend:605bfbf54db42b5d2c222108f3d3934b7702294f
      resources: 
        requests: 
          cpu: "100m"
          memory: "128Mi"
        limits:
          cpu: "200m"
          memory: "256Mi"
{{- end}}

{{- define "rememberthis.v1.backend-pod-info"}}
metadata: 
  name: backend-pod
  namespace: {{ .Values.namespace }}
  labels: 
{{- include "rememberthis.v1.backend-pod-labels" . | indent 4}} 
spec: 
  containers:
    - name: "backend-pod"
      ports: 
        - containerPort: 8080
      image: willey599/backend:e953aa70c6e4895004199a2d22bebc2d15b96bdd
      resources: 
        requests: 
          cpu: "100m"
          memory: "128Mi"
        limits:
          cpu: "200m"
          memory: "256Mi"
{{- end}}

{{- define "rememberthis.v1.database-pod-info" }}
containers:
  - name: mysql
    image: "willey599/mysql:1.1"
    ports: 
      - containerPort: 3306
    env:
      - name: MYSQL_ROOT_PASSWORD
        valueFrom: 
          secretKeyRef:
            name: secrets
            key: mysql-root-password
      - name: MYSQL_DATABASE
        valueFrom: 
          secretKeyRef:
            name: secrets
            key: mysql-database-name
      - name: MYSQL_USER
        valueFrom:
          secretKeyRef:
            name: secrets
            key: mysql-user
      - name: MYSQL_PASSWORD
        valueFrom:
          secretKeyRef:
            name: secrets
            key: mysql-password
    volumeMounts:
      - name: mysql-data
        mountPath: /var/lib/mysql
{{- end }}

{{- define "rememberthis.v1.frontend-pod-labels" }}

app: remember-this
tier: frontend

{{- end }}

{{- define "rememberthis.v1.backend-pod-labels"}}
app: remember-this
tier: backend
{{- end }}

{{- define "rememberthis.v1.database-pod-labels"}}
app: "remember-this"
tier: "database"
{{- end }}
