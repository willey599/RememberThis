Repo to store my custom helm chart for hosting remember-this locally through k3s. Accessed through remember-this-app.app

IMPORTANT: This chart includes Cloudflare Tunnel, but you need to install ingress-nginx in order to connect to remember-this-app.app. 
The repo is ingress-nginx/ingress-nginx, and it should be installed in the ingress-nginx namespace. The command is: 

helm install ingress-nginx ingress-nginx/ingress-nginx -n ingress-nginx --create-namespace

This will install the latest version, which is surrently 4.14.0 as of 11/19/2025