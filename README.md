# Git Workflow & Release Pipeline

Este documento describe el flujo de trabajo de desarrollo y release basado en el diagrama de branching y CI/CD.

---

## Tabla de Contenidos

- [Convenciones de Ramas](#convenciones-de-ramas)
- [Flujo de Desarrollo](#flujo-de-desarrollo)
- [Pull Request Workflow](#pull-request-workflow)
- [WorkFlow Release](#workflow-release)

---

## Convenciones de Ramas

| Tipo | Nomenclatura | Descripción |
|------|-------------|-------------|
| Principal | `main` | Rama estable de producción |
| Corta (feature) | `feature/*` | Nueva funcionalidad |
| Corta (hotfix) | `hotfix/*` | Corrección urgente en producción |

---

## Flujo de Desarrollo

El ciclo de vida de un cambio sigue estos pasos:

```
main
 └─► Create Branch
       └─► Short Branch (feature/* o hotfix/*)
             └─► Commit Changes  (desarrollador realiza commits)
                   └─► Pull Request
                         └─► Code Review
                               └─► Merge Branch ──► main
```

### Pasos detallados

1. **Create Branch** — A partir de `main` se crea una rama corta (`feature/*` o `hotfix/*`).
2. **Commit Changes** — El desarrollador realiza uno o más commits sobre la rama corta.
3. **Pull Request** — Al completar el trabajo se abre un Pull Request hacia `main`.
4. **Code Review** — Un revisor aprueba o solicita cambios.
5. **Merge Branch** — Una vez aprobado, la rama se fusiona a `main`.

---

## Pull Request Workflow

Cuando se abre un Pull Request se ejecutan automáticamente las siguientes etapas en paralelo:

```
Pull Request
 └─► Setup
       ├─► Security    (análisis de vulnerabilidades)
       ├─► Unit Test   (pruebas unitarias)
       ├─► Quality     (análisis de calidad de código)
       └─► Coverage    (cobertura de pruebas)
```

Todas las etapas deben pasar antes de que el PR pueda ser mergeado.

---

## WorkFlow Release

Una vez que los cambios llegan a `main`, se ejecuta el pipeline de release compuesto por seis etapas secuenciales:

### Etapa 1 — Setup

- Obtener versión (`Get Version`)
- Obtener nombre (`Get Name`)
- Configurar entornos (`Config environments`)
- Validar versión TAG (`Validate version TAG`)

### Etapa 2 — Build

- Config Cache
- Build
- Immutable Artifact

### Etapa 3 — Artifact Release

- Publicar Artifact (`Publish Artifact`)
- Crear TAGs de release (`Create TAG Releases Candidate`)
- Versionar con hash (`vX.Y.Z-hash`)

### Etapa 4 — Promote DEV

- Obtener Artifact (`Get Artifact`)
- Autenticar con OIDC (`Authenticate using OIDC`)
- Desplegar (`Deploy`)

### Etapa 5 — Promote QA

- Aprobación QE (`Approval QE`)
- Obtener Artifact (`Get Artifact`)
- Autenticar con OIDC (`Authenticate using OIDC`)
- Desplegar (`Deploy`)

### Etapa 6 — Promote PROD

- Aprobación del Operador (`Approval Operator`)
- Crear TAG de release (`Create TAG Release`)
- Versión estable (`Stable vX.Y.Z`)
- Obtener Artifact (`Get Artifact`)
- Autenticar con OIDC (`Authenticate using OIDC`)
- Desplegar (`Deploy`)

### Diagrama del pipeline de release

```
Setup ──► Build ──► Artifact Release ──► Promote DEV ──► Promote QA ──► Promote PROD
```

> Las etapas **Promote QA** y **Promote PROD** requieren aprobación manual antes de continuar.

---

## Diagrama del Flujo Completo

![Diagrama del flujo de trabajo y pipeline de release](branching-model.jpeg)

---

## Notas

- El versionado sigue el esquema **SemVer** (`vX.Y.Z`).
- Los artefactos son **inmutables**: una vez construidos no se recompilan, solo se promueven entre ambientes.
- La autenticación en todos los ambientes se realiza mediante **OIDC** (sin secretos estáticos).
- El flujo de ramas cortas (`feature/*`, `hotfix/*`) garantiza que `main` siempre permanezca en estado desplegable.
 