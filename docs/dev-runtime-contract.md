# Dev Runtime Contract

## Startup contract

- activate the runtime with `SPRING_PROFILES_ACTIVE=dev`
- provide the shared internal auth secret with `FORGE_SECURITY_DEV_JWT_SECRET`
- let Spring load all non-secret topology from `application-dev.yml`

No workflow or shell step should assemble dev runtime config.

## Topology owned by `application-dev.yml`

The BFF currently expects these non-secret dev values:

- browser origin: `https://app.dev.sitionix.com`
- auth service base path: `http://authorisationservice-sox:9090/authsox`
- site service base path: `http://siteservice-sox:9080/stsssox`
- workspace service base path: `http://workspaceaggregationservice-sox:9082/wagssox`
- Forge user-JWT auth base URL: `http://authorisationservice-sox:9090/authsox`
- Forge target hosts:
  - `sitionixAuth -> authorisationservice-sox`
  - `sitionixSite -> siteservice-sox`
  - `sitionixWorkspace -> workspaceaggregationservice-sox`

The first VM deployment formalizes those names as required Docker-network aliases on `sitionix-dev`.

If the real dev runtime uses different service names, ports, or DNS names, update `application-dev.yml`. Do not move those values into workflow env vars.

The BFF deploy itself also now treats `sitionix-dev` as a fixed runtime contract.
It is no longer configurable through a deploy environment variable.

## Secret contract

Only this BFF runtime secret remains external:

- `FORGE_SECURITY_DEV_JWT_SECRET`

It must match the same secret used by:

- `authorisationservice-sox`
- `siteservice-sox`
- `workspaceaggregationservice-sox`

The first VM deployment writes that secret into:

- `/opt/sitionix/runtime/shared/dev-internal-auth.env`

That file is the shared dev internal-auth source that BFF, auth-service, site-service, and workspace-service should all consume on the VM.

Shared non-secret internal auth metadata stays committed:

- issuer: `sitionix-internal`
- TTL: `300`

## Health and readiness contract

The first dev deployment should use:

- liveness: `GET /bffssox/actuator/health/liveness`
- readiness: `GET /bffssox/actuator/health/readiness`

Readiness is intentionally shallow for the first deploy.

- included signals: `readinessState`, `ping`
- excluded signals: downstream auth/site/workspace reachability

This keeps deployment health stable while the dev environment contract is still being proven.

## Post-deploy verification

Use:

- `GET /bffssox/actuator/health/readiness`
- `GET /bffssox/actuator/health`

for a shallow public verification that confirms the deployed BFF is booted and serving actuator traffic.

## Downstream client timeout contract

The auth, site, and workspace REST clients all use shared defaults from `application.yml`.

- connect timeout: `2s`
- read timeout: `10s`

These are deployment-facing defaults, not per-environment topology.

## What is required now

- explicit `dev` profile activation
- the shared `FORGE_SECURITY_DEV_JWT_SECRET`
- runtime DNS/network that resolves the committed service names on `sitionix-dev`, or an intentional update to `application-dev.yml`
- deployment health checks against the actuator readiness and liveness endpoints

## Acceptable for first dev deploy

- shallow readiness instead of dependency-aware readiness
- annotation-driven protection for authenticated endpoints
- startup diagnostics focused on profile, endpoints, and downstream base paths

## Later hardening

- dependency-aware readiness checks
- stronger route-level authorization defaults
- richer structured logs and request correlation
