# Dev VM Infra Rollout

## 1. Final infra contract

This is the exact dev VM infrastructure foundation to build before any backend service deploy.

### Docker network

- network name: `sitionix-dev`

### Containers and aliases

- Postgres container name: `sitionix-postgres`
- Postgres network alias: `postgres`
- Kafka container name: `sitionix-kafka`
- Kafka network alias: `kafka`

### Runtime directories

- runtime root: `/opt/sitionix/runtime`
- infra bundle root: `/opt/sitionix/runtime/infra/current`
- shared runtime root: `/opt/sitionix/runtime/shared`
- infra env file: `/opt/sitionix/runtime/infra/current/env/infra-compose.env`
- shared internal auth file: `/opt/sitionix/runtime/shared/dev-internal-auth.env`

### Data directories

- Postgres data: `/opt/sitionix/data/postgres`
- Kafka data: `/opt/sitionix/data/kafka`

### Backup directories

- Postgres backups: `/opt/sitionix/backups/postgres`
- Kafka backups: `/opt/sitionix/backups/kafka`

### Postgres databases

- `AUTHS_SOX`
- `SITES_SOX`
- `WAGS_SOX`

### Postgres users

- `authssox_app`
- `stsssox_app`
- `wagssox_app`

### Later service connections

- `authorisationservice-sox` -> `jdbc:postgresql://postgres:5432/AUTHS_SOX`
- `siteservice-sox` -> `jdbc:postgresql://postgres:5432/SITES_SOX`
- `workspaceaggregationservice-sox` -> `jdbc:postgresql://postgres:5432/WAGS_SOX`
- all Kafka-using services -> `kafka:9092`
- all participating internal-auth services -> `/opt/sitionix/runtime/shared/dev-internal-auth.env`

## 2. Files and artifacts in this repo

Use these repo files as the manual rollout bundle:

- infra compose: [docker-compose.infra.yml](/Users/vladvinskevitch/Documents/Java/sitionix/backendforfrontendservice-sox/deploy/vm/infra/docker-compose.infra.yml)
- Postgres init script: [00-create-app-databases.sh](/Users/vladvinskevitch/Documents/Java/sitionix/backendforfrontendservice-sox/deploy/vm/infra/postgres/init/00-create-app-databases.sh)
- infra env template: [infra-compose.env.example](/Users/vladvinskevitch/Documents/Java/sitionix/backendforfrontendservice-sox/deploy/vm/infra/env/infra-compose.env.example)
- shared internal-auth env template: [dev-internal-auth.env.example](/Users/vladvinskevitch/Documents/Java/sitionix/backendforfrontendservice-sox/deploy/vm/infra/env/dev-internal-auth.env.example)
- Postgres verification script: [verify-postgres.sh](/Users/vladvinskevitch/Documents/Java/sitionix/backendforfrontendservice-sox/deploy/vm/infra/verify/verify-postgres.sh)
- Kafka verification script: [verify-kafka.sh](/Users/vladvinskevitch/Documents/Java/sitionix/backendforfrontendservice-sox/deploy/vm/infra/verify/verify-kafka.sh)

## 3. Operator runbook

This is the one recommended manual path.

### Step 0: Stage the bundle on the VM

Copy the contents of [deploy/vm/infra](/Users/vladvinskevitch/Documents/Java/sitionix/backendforfrontendservice-sox/deploy/vm/infra) to:

- `/opt/sitionix/runtime/infra/current`

The commands below assume that directory already contains:

- `docker-compose.infra.yml`
- `env/`
- `postgres/init/`
- `verify/`

### Step 1: Create directories

Run on the VM:

```bash
sudo mkdir -p \
  /opt/sitionix/runtime/infra/current \
  /opt/sitionix/runtime/shared \
  /opt/sitionix/data/postgres \
  /opt/sitionix/data/kafka \
  /opt/sitionix/backups/postgres \
  /opt/sitionix/backups/kafka

sudo chown -R "$(id -un)":"$(id -gn)" \
  /opt/sitionix/runtime \
  /opt/sitionix/data \
  /opt/sitionix/backups
```

Success looks like:

- directories exist
- current deploy user can write into `/opt/sitionix/runtime`, `/opt/sitionix/data`, `/opt/sitionix/backups`

If this step fails:

- stop and fix ownership before continuing
- do not start containers against root-owned unwritable paths

### Step 2: Create the shared Docker network

Run:

```bash
docker network inspect sitionix-dev >/dev/null 2>&1 || docker network create sitionix-dev
docker network inspect sitionix-dev --format '{{.Name}}'
```

Success looks like:

- command prints `sitionix-dev`

If this step fails:

- confirm Docker is installed and the operator user can access the Docker socket

### Step 3: Create the shared internal-auth secret file

Generate the secret value:

```bash
openssl rand -hex 32
```

Create the file:

```bash
cat > /opt/sitionix/runtime/shared/dev-internal-auth.env
```

Paste exactly:

```text
FORGE_SECURITY_DEV_JWT_SECRET=<paste generated hex value here>
```

Then lock permissions:

```bash
chmod 600 /opt/sitionix/runtime/shared/dev-internal-auth.env
```

Success looks like:

- file exists
- `ls -l /opt/sitionix/runtime/shared/dev-internal-auth.env` shows mode `-rw-------`

If this step fails:

- stop and fix file ownership or permissions before service deploy work begins

### Step 4: Create the infra env file

Generate four Postgres passwords:

```bash
openssl rand -hex 24
openssl rand -hex 24
openssl rand -hex 24
openssl rand -hex 24
```

Generate the Kafka KRaft cluster id:

```bash
docker run --rm confluentinc/cp-kafka:7.6.0 kafka-storage random-uuid
```

Create the env file:

```bash
cp /opt/sitionix/runtime/infra/current/env/infra-compose.env.example \
   /opt/sitionix/runtime/infra/current/env/infra-compose.env
chmod 600 /opt/sitionix/runtime/infra/current/env/infra-compose.env
```

Edit:

```bash
vi /opt/sitionix/runtime/infra/current/env/infra-compose.env
```

Set real values for:

- `POSTGRES_PASSWORD`
- `AUTHS_SOX_DB_PASSWORD`
- `SITES_SOX_DB_PASSWORD`
- `WAGS_SOX_DB_PASSWORD`
- `KAFKA_CLUSTER_ID`

Success looks like:

- file exists at the exact path above
- all five values are filled with real values

If this step fails:

- do not continue with placeholder values

### Step 5: Start infra

Run:

```bash
cd /opt/sitionix/runtime/infra/current
docker compose --env-file env/infra-compose.env -f docker-compose.infra.yml up -d
docker compose --env-file env/infra-compose.env -f docker-compose.infra.yml ps
```

Success looks like:

- `sitionix-postgres` is `running`
- `sitionix-kafka` is `running`

If this step fails:

- inspect logs with:

```bash
docker logs sitionix-postgres
docker logs sitionix-kafka
```

- fix the error before retrying
- do not move on to backend service rollout with partially started infra

### Step 6: Verify substrate

Run:

```bash
test -d /opt/sitionix/runtime/infra/current
test -d /opt/sitionix/runtime/shared
test -d /opt/sitionix/data/postgres
test -d /opt/sitionix/data/kafka
test -d /opt/sitionix/backups/postgres
test -d /opt/sitionix/backups/kafka
docker network inspect sitionix-dev --format '{{.Name}}'
```

Expected:

- all `test -d` commands succeed silently
- final command prints `sitionix-dev`

### Step 7: Verify Postgres

Run:

```bash
cd /opt/sitionix/runtime/infra/current
bash verify/verify-postgres.sh
```

Expected:

- readiness check passes
- `AUTHS_SOX`, `SITES_SOX`, `WAGS_SOX` are found
- the three application users can log in
- final line is `Postgres verification passed.`

If this step fails:

- inspect `docker logs sitionix-postgres`
- verify `/opt/sitionix/runtime/infra/current/env/infra-compose.env`
- if the data directory was initialized with bad values, stop the container, clear only `/opt/sitionix/data/postgres`, and start again

### Step 8: Verify Kafka

Run:

```bash
cd /opt/sitionix/runtime/infra/current
bash verify/verify-kafka.sh
```

Expected:

- a temporary container on `sitionix-dev` reaches `kafka:9092`
- broker API command succeeds through `kafka:9092`
- topic `sitionix.infra.smoke.v1` is described through `kafka:9092`
- final line is `Kafka verification passed through sitionix-dev to kafka:9092.`

If this step fails:

- inspect `docker logs sitionix-kafka`
- verify `KAFKA_CLUSTER_ID` exists and is not empty
- if the Kafka data directory was initialized with a wrong cluster id, stop the container, clear only `/opt/sitionix/data/kafka`, and start again

## 4. Verification checklist

### VM substrate

- `/opt/sitionix/runtime/infra/current` exists
- `/opt/sitionix/runtime/shared` exists
- `/opt/sitionix/data/postgres` exists
- `/opt/sitionix/data/kafka` exists
- `/opt/sitionix/backups/postgres` exists
- `/opt/sitionix/backups/kafka` exists
- `sitionix-dev` exists

### Postgres

- `docker inspect --format '{{.State.Status}}' sitionix-postgres` returns `running`
- `pg_isready -U postgres -d postgres` succeeds inside the container
- `AUTHS_SOX` exists
- `SITES_SOX` exists
- `WAGS_SOX` exists
- `authssox_app` can log into `AUTHS_SOX`
- `stsssox_app` can log into `SITES_SOX`
- `wagssox_app` can log into `WAGS_SOX`

### Kafka

- `docker inspect --format '{{.State.Status}}' sitionix-kafka` returns `running`
- a temporary container on `sitionix-dev` can reach `kafka:9092`
- broker API check succeeds through `kafka:9092`
- topic `sitionix.infra.smoke.v1` can be created or already exists

## 5. Legacy and stale residue not to carry over

- Do not carry `site-mongo` from the root local compose file.
- Do not carry `MONGODB_URI` for `siteservice-sox`.
- Do not carry the old mixed `site-db + site-mongo` local topology to the VM.
- Do not carry ZooKeeper from local compose. The VM infra package uses a single-node KRaft broker.
- Do not carry old shared local credentials like `postgres/postgres-pwd` into the real VM runtime.

Current `siteservice-sox` truth is Postgres-based. Old Mongo mentions are residue and must not define the VM foundation.

## 6. Next unlock

This infra rollout unlocks the first real backend service deployment:

- `authorisationservice-sox` on the `sitionix-dev` network, using:
  - `postgres:5432/AUTHS_SOX`
  - `kafka:9092`
  - `/opt/sitionix/runtime/shared/dev-internal-auth.env`
