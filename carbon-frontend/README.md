# Carbon Calculator Frontend

React and Vite frontend for the Carbon Calculator application.

## Render deployment

The repository-root `render.yaml` defines the static frontend service. It builds this directory with `npm ci && npm run build` and publishes `dist`.

The frontend uses `VITE_API_BASE_URL` for API requests. The Render blueprint points it at the backend service. The backend's `FRONTEND_URL` environment variable must be set to this site's public Render URL to allow browser requests.

The Spring Boot backend also needs `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` set in its Render Environment settings to connect to a publicly reachable MySQL database. Do not use `localhost` for a database hosted outside the backend container.

## Local development

Run `npm ci` and `npm run dev`. The frontend defaults to the backend at `http://localhost:8080`; override this with `VITE_API_BASE_URL` when needed.
