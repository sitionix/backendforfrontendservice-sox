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
require_env "SITIONIX_RELEASE_ID"

base_url="${SITIONIX_BFF_PUBLIC_BASE_URL%/}"
readiness_url="${base_url}/bffssox/actuator/health/readiness"
health_url="${base_url}/bffssox/actuator/health"

wait_for_up_status "${readiness_url}" "Public readiness"
wait_for_up_status "${health_url}" "Public health"

printf 'BFF public readiness smoke passed for release %s\n' "${SITIONIX_RELEASE_ID}"
