# Dev VM Deployment

## Scope

This repo deploys the BFF to the existing dev VM as a Docker container on the shared runtime host.

- runtime mode: Docker container on the VM
- active Spring profile: `dev`

This deployment does not introduce repo clone or `git pull` on the VM.

## Runtime contract on the VM

The deploy workflow publishes an immutable image to GHCR and then starts that exact image on the VM.

Container contract:

- container name: `bffssox-service`
- bind address: `127.0.0.1:8080`
- restart policy: `unless-stopped`
- Docker network: `sitionix-dev`
- Spring profile: `dev`

The container consumes two env files on the VM:

- shared internal-auth secret file:
  - `/opt/sitionix/runtime/shared/dev-internal-auth.env`
- BFF-only runtime file:
  - `/opt/sitionix/runtime/backendforfrontendservice-sox/shared/backendforfrontendservice-sox.dev.env`

The deploy script writes:

- `FORGE_SECURITY_DEV_JWT_SECRET` into the shared file
- `SPRING_PROFILES_ACTIVE=dev` into the BFF-only file

No non-secret topology is injected from the workflow.
The BFF still loads non-secret dev topology from `application-dev.yml`.

## Dev downstream topology contract

`application-dev.yml` remains the source of truth for non-secret dev topology:

- auth base path: `http://authorisationservice-sox:9090/authsox`
- site base path: `http://siteservice-sox:9080/stsssox`
- workspace base path: `http://workspaceaggregationservice-sox:9082/wagssox`
- Forge user-JWT auth base URL: `http://authorisationservice-sox:9090/authsox`

This deployment formalizes those names as Docker-network runtime aliases on `sitionix-dev`.

That means the dev VM runtime must provide:

- `authorisationservice-sox`
- `siteservice-sox`
- `workspaceaggregationservice-sox`

as resolvable names from inside the BFF container.

If the real dev runtime uses different names, update `application-dev.yml`.
Do not move topology ownership into workflow env vars.

## Shared internal auth contract

The deploy creates one shared VM secret source for internal service-to-service auth:

- `/opt/sitionix/runtime/shared/dev-internal-auth.env`

It contains:

- `FORGE_SECURITY_DEV_JWT_SECRET=<value from GitHub Environment secret>`

The same secret file path should be used by:

- BFF
- auth-service
- site-service
- workspace-service

for the dev runtime.

Committed non-secret internal auth values still stay in service config:

- issuer: `sitionix-internal`
- ttl: `300`

## Workflow flow

Workflow: [dev-deploy-on-push.yml](/Users/vladvinskevitch/Documents/Java/sitionix/backendforfrontendservice-sox/.github/workflows/dev-deploy-on-push.yml)

Comment workflow: [deploy-on-comment.yml](/Users/vladvinskevitch/Documents/Java/sitionix/backendforfrontendservice-sox/.github/workflows/deploy-on-comment.yml)

Composite action: [dev-deploy-run](/Users/vladvinskevitch/Documents/Java/sitionix/backendforfrontendservice-sox/.github/actions/dev-deploy-run/action.yml)

Flow:

1. build and publish immutable image to GHCR
2. create a small release payload with:
   - VM deploy script
   - release manifest
   - non-secret release env
   - runtime secret env
3. upload the payload to the VM over SSH
4. run the VM deploy script
5. wait for local actuator readiness on `127.0.0.1:8080`
6. verify private readiness and health through an SSH tunnel

The pull request comment deploy flow uses the same deploy action against the PR head branch:

```text
/deploy service --name backendforfrontendservice-sox --env dev
```

## Verification

The deploy uses private verification through an SSH tunnel, by analogy with auth-service.
After rollout it checks:

1. `GET /actuator/health/readiness`
2. `GET /actuator/health`

This proves:

- the container booted under `dev`
- the deployed BFF is reachable on the VM loopback bind
- Spring actuator health endpoints are serving correctly

## GitHub Environment contract

Expected GitHub Environment `dev` values for this repo:

Variables:
- `DEPLOY_VM_PORT`
  - optional
  - default SSH port: `22`

Repository vars:

- `MAVEN_REPOSITORY_USERNAME`

Secrets:

- `DEPLOY_VM_HOST`
- `DEPLOY_VM_USER`
- `DEPLOY_VM_SSH_PRIVATE_KEY`
- `FORGE_SECURITY_DEV_JWT_SECRET`
- `GHCR_PULL_USERNAME`
- `GHCR_PULL_TOKEN`

Repository secrets:

- `MAVEN_REPOSITORY_TOKEN`

## VM prerequisites

The VM must already provide:

- Docker installed and usable by the deploy user
- the `sitionix-dev` Docker network, or permission for the deploy user to create it
- peer backend containers attached to `sitionix-dev` with the aliases from `application-dev.yml`
