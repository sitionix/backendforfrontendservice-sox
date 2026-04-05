#!/usr/bin/env bash

set -euo pipefail

require_env() {
  local variable_name="$1"
  if [[ -z "${!variable_name:-}" ]]; then
    echo "Missing required environment variable: ${variable_name}" >&2
    exit 1
  fi
}

wait_for_up_status() {
  local url="$1"
  local label="$2"
  local response_file
  response_file="$(mktemp)"

  for attempt in $(seq 1 30); do
    if curl -sS -o "${response_file}" -w '%{http_code}' "${url}" | grep -qx '200'; then
      if python3 - "${response_file}" <<'PY'
import json
import sys

with open(sys.argv[1], encoding="utf-8") as handle:
    payload = json.load(handle)

if payload.get("status") != "UP":
    raise SystemExit(1)
PY
      then
        rm -f "${response_file}"
        return 0
      fi
    fi
    sleep 2
  done

  echo "${label} did not become UP at ${url}" >&2
  cat "${response_file}" >&2 || true
  rm -f "${response_file}"
  exit 1
}

require_env "SITIONIX_BFF_PUBLIC_BASE_URL"
require_env "SITIONIX_SMOKE_USER_EMAIL"
require_env "SITIONIX_SMOKE_USER_PASSWORD"
require_env "SITIONIX_SMOKE_SITE_ID"
require_env "SITIONIX_RELEASE_ID"

base_url="${SITIONIX_BFF_PUBLIC_BASE_URL%/}"
readiness_url="${base_url}/bffssox/actuator/health/readiness"
login_url="${base_url}/bffssox/api/v1/auth/login"
sites_url="${base_url}/bffssox/api/v1/sites?page=0&size=1"
create_site_url="${base_url}/bffssox/api/v1/sites"

wait_for_up_status "${readiness_url}" "Public readiness"

login_request="$(mktemp)"
login_response="$(mktemp)"
create_site_request="$(mktemp)"
create_site_response="$(mktemp)"
sites_response="$(mktemp)"
trap 'rm -f "${login_request}" "${login_response}" "${create_site_request}" "${create_site_response}" "${sites_response}"' EXIT

cat <<EOF > "${login_request}"
{
  "email": "${SITIONIX_SMOKE_USER_EMAIL}",
  "password": "${SITIONIX_SMOKE_USER_PASSWORD}",
  "siteId": "${SITIONIX_SMOKE_SITE_ID}",
  "sessionSourceId": "bff-dev-deploy-${SITIONIX_RELEASE_ID}",
  "userAgent": "bff-dev-deploy-smoke"
}
EOF

login_status="$(curl -sS -o "${login_response}" -w '%{http_code}' \
  -H 'Content-Type: application/json' \
  --data @"${login_request}" \
  "${login_url}")"

if [[ "${login_status}" != "200" ]]; then
  echo "Login smoke failed with status ${login_status}" >&2
  cat "${login_response}" >&2
  exit 1
fi

access_token="$(python3 - "${login_response}" <<'PY'
import json
import sys

with open(sys.argv[1], encoding="utf-8") as handle:
    payload = json.load(handle)

print(payload["accessToken"])
PY
)"

sites_status="$(curl -sS -o "${sites_response}" -w '%{http_code}' \
  -H "Authorization: Bearer ${access_token}" \
  "${sites_url}")"

if [[ "${sites_status}" != "200" ]]; then
  echo "Workspace smoke failed with status ${sites_status}" >&2
  cat "${sites_response}" >&2
  exit 1
fi

cat <<EOF > "${create_site_request}"
{
  "name": "BFF dev deploy smoke ${SITIONIX_RELEASE_ID}",
  "type": "PORTFOLIO",
  "description": "Automated dev deploy smoke",
  "template": "BLANK"
}
EOF

create_site_status="$(curl -sS -o "${create_site_response}" -w '%{http_code}' \
  -H 'Content-Type: application/json' \
  -H "Authorization: Bearer ${access_token}" \
  --data @"${create_site_request}" \
  "${create_site_url}")"

if [[ "${create_site_status}" != "201" ]]; then
  echo "Site-service smoke failed with status ${create_site_status}" >&2
  cat "${create_site_response}" >&2
  exit 1
fi

python3 - "${create_site_response}" <<'PY'
import json
import sys

with open(sys.argv[1], encoding="utf-8") as handle:
    payload = json.load(handle)

site_id = payload.get("siteId")
if not site_id:
    raise SystemExit("Missing siteId in create-site smoke response")

print(f"Created smoke site: {site_id}")
PY
