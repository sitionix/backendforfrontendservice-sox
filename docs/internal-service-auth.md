# Internal Service Auth Contract

## Scope

This contract covers internal service-to-service authentication for these calls:

- BFF -> auth service
- BFF -> site service
- BFF -> workspace aggregation service

It does not cover public user JWT authentication.

## Contract

Forge internal JWTs are issued by the caller and validated by the callee.

- `sub` is the caller `forge.security.service-id`
- `aud` is the target service id resolved from the BFF host mapping
- `forge.security.dev.jwt-secret` must be the same across every participating service in the same runtime
- `forge.security.dev.issuer` must be the same across every participating service in the same runtime
- `forge.security.dev.ttl-seconds` must be the same across every participating service in the same runtime

## Matrix

| Property | BFF | auth-service | site-service | workspace-service | Source of truth |
| --- | --- | --- | --- | --- | --- |
| `forge.security.service-id` | `sitionixBff` | `sitionixAuth` | `sitionixSite` | `sitionixWorkspace` | committed service config |
| `forge.security.dev.jwt-secret` | `${FORGE_SECURITY_DEV_JWT_SECRET}` | `${FORGE_SECURITY_DEV_JWT_SECRET}` | `${FORGE_SECURITY_DEV_JWT_SECRET}` | `${FORGE_SECURITY_DEV_JWT_SECRET}` | runtime secret injection |
| `forge.security.dev.issuer` | `sitionix-internal` | `sitionix-internal` | `sitionix-internal` | `sitionix-internal` | committed service config |
| `forge.security.dev.ttl-seconds` | `300` | `300` | `300` | `300` | committed service config |
| BFF target host -> audience | `authorisationservice-sox` -> `sitionixAuth` | n/a | n/a | n/a | `application-local.yml`, `application-dev.yml` |
| BFF target host -> audience | `siteservice-sox` -> `sitionixSite` | n/a | n/a | n/a | `application-local.yml`, `application-dev.yml` |
| BFF target host -> audience | `workspaceaggregationservice-sox` -> `sitionixWorkspace` | n/a | n/a | n/a | `application-local.yml`, `application-dev.yml` |
| Expected incoming `aud` | n/a | `sitionixAuth` | `sitionixSite` | `sitionixWorkspace` | derived from local `forge.security.service-id` |
| Expected incoming caller `sub` | `n/a` | `sitionixBff` for BFF calls | `sitionixBff` for BFF calls | `sitionixBff` for BFF calls | derived from BFF `forge.security.service-id` |

## Local Contract

Local runtime is root Docker Compose only.

- Compose injects `FORGE_SECURITY_DEV_JWT_SECRET=dev-internal-auth-secret` into `bff-service`, `auth-service`, `site-service`, and `workspace-service`
- BFF runs with `SPRING_PROFILES_ACTIVE=local`
- BFF resolves downstreams through compose DNS names
- site-service also requires `MONGODB_URI=mongodb://site-mongo:27017`

There is no supported host-side local BFF mode in this contract.

## Dev Contract

Dev runtime must provide one shared internal auth secret to every participating service:

- `FORGE_SECURITY_DEV_JWT_SECRET`

On the dev VM the shared secret source should be:

- `/opt/sitionix/runtime/shared/dev-internal-auth.env`

Committed non-secret values stay fixed unless the runtime topology itself changes:

- issuer: `sitionix-internal`
- ttl: `300`
- BFF service id: `sitionixBff`
- auth service id: `sitionixAuth`
- site service id: `sitionixSite`
- workspace service id: `sitionixWorkspace`

The dev runtime must also make these BFF target hosts resolvable from the BFF process:

- `authorisationservice-sox`
- `siteservice-sox`
- `workspaceaggregationservice-sox`

The first VM deployment treats those names as required aliases on the shared Docker network `sitionix-dev`.

If dev uses different hostnames, `application-dev.yml` must be updated to the real runtime names. The host mapping is not allowed to drift into workflow env vars.

## Failure Modes This Contract Prevents

- BFF signing with one internal secret while downstream services validate with another
- hidden fallback secrets in one service masking a broken runtime
- BFF sending the wrong `aud` because host-to-target mapping is missing or inconsistent
- local compose appearing up while internal calls fail with `401 Invalid internal authorization token`
