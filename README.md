# backendforfrontendservice-sox

## Runtime config model

Spring profiles are the only runtime config selector.

- `application.yml`: shared, environment-agnostic settings only
- `application-local.yml`: full non-secret local runtime topology
- `application-dev.yml`: full non-secret dev runtime topology

No workflow or shell step assembles runtime config.

Internal service-to-service auth is documented in [docs/internal-service-auth.md](docs/internal-service-auth.md).
Dev deployment assumptions and operability are documented in [docs/dev-runtime-contract.md](docs/dev-runtime-contract.md).
VM deployment flow is documented in [docs/dev-vm-deploy.md](docs/dev-vm-deploy.md).

## Supported startup modes

### Local

Officially supported local mode is root Docker Compose only.

- Start from the parent workspace root with `just infra-up auth-service site-service workspace-service bff-service spa`
- `docker-compose.yml` starts BFF with `SPRING_PROFILES_ACTIVE=local`
- `application-local.yml` assumes the BFF runs inside the compose network and resolves:
  - `authorisationservice-sox:9090`
  - `siteservice-sox:9080`
  - `workspaceaggregationservice-sox:9082`

Host-side `spring-boot:run` for BFF is intentionally not part of the supported local contract.

### Dev

Dev runtime starts the BFF with `SPRING_PROFILES_ACTIVE=dev`.

- `application-dev.yml` owns the non-secret dev topology
- the dev profile assumes the runtime network resolves:
  - `authorisationservice-sox:9090`
  - `siteservice-sox:9080`
  - `workspaceaggregationservice-sox:9082`
- public browser origin is `https://app.dev.sitionix.com`
- if the deployed dev runtime uses different service names or ports, update `application-dev.yml`; do not move topology ownership into workflow env vars
- first VM deploy expects those service names as aliases on the shared Docker network `sitionix-dev`

## Secret inventory

Only this BFF runtime secret stays external:

- `FORGE_SECURITY_DEV_JWT_SECRET`

It must be provided for both `local` and `dev`.

For the VM deploy, that secret is materialized into `/opt/sitionix/runtime/shared/dev-internal-auth.env` and should be consumed by BFF, auth-service, site-service, and workspace-service.

## Health, readiness, and smoke checks

- deployment liveness check: `GET /bffssox/actuator/health/liveness`
- deployment readiness check: `GET /bffssox/actuator/health/readiness`
- post-deploy shallow smoke check: `GET /bffssox/actuator/health`
- post-deploy functional smoke: login, list sites, and create site through `https://app.dev.sitionix.com/bffssox`

Readiness is intentionally shallow for the first dev deployment.

- it proves the BFF has booted, bound HTTP, and reached Spring's `ACCEPTING_TRAFFIC` readiness state
- it does not probe auth, site, or workspace reachability
- a deeper functional smoke should exercise a real BFF API flow once the dev environment provides a known smoke user or other stable test fixture

## Downstream timeout defaults

Shared downstream client timeout defaults live in `application.yml`.

- `api.rest.client.defaults.connect-timeout=2s`
- `api.rest.client.defaults.read-timeout=10s`

These defaults apply to the auth, site, and workspace REST clients.

## Proof and assumptions

What is currently proven:

- Spring profile loading owns runtime config selection
- local compose startup activates `local`
- local compose BFF flows work end to end for registration, login, create site, and list sites
- actuator liveness and readiness endpoints are exposed and return `UP`
- the first dev VM deploy contract is implemented in this repo with GHCR image publish, SSH release payload delivery, and a VM-local Docker runtime bound to `127.0.0.1:8080`

What is still an explicit assumption:

- `application-dev.yml` hostnames are the real dev-runtime service names
- the first dev environment will provide the shared `FORGE_SECURITY_DEV_JWT_SECRET`
- the first dev environment will provide a stable smoke user and site fixture for the deploy login/list/create-site smoke

## Later hardening

These items are intentionally not part of the first dev-deploy minimum:

- deeper readiness that probes downstream dependencies
- centralized HTTP route authorization instead of annotation-driven protection
- richer request correlation and structured startup diagnostics

## Ownership summary

### Shared in `application.yml`

- server port and context path
- refresh cookie policy
- CSRF origin-guard behavior
- shared Forge service identity
- shared non-secret internal JWT metadata (`issuer`, `ttl-seconds`)
- secret placeholder for `FORGE_SECURITY_DEV_JWT_SECRET`

### Local in `application-local.yml`

- local CORS origins
- local downstream base URLs
- local JWKS/auth base URL
- local Forge target host mapping

### Dev in `application-dev.yml`

- dev CORS origin
- dev downstream base URLs
- dev JWKS/auth base URL
- dev Forge target host mapping
