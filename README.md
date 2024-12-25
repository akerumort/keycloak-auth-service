# keycloak-auth-service

docker-compose.yml for Keycloak:
```bash
version: '3.9'

services:
keycloak:
image: quay.io/keycloak/keycloak:22.0.1
container_name: keycloak
environment:
KEYCLOAK_ADMIN: admin
KEYCLOAK_ADMIN_PASSWORD: admin
command: start-dev
ports:
- "8180:8080"
volumes:
- keycloak_data:/opt/keycloak/data
restart: unless-stopped

volumes:
keycloak_data:
```

