# Taxai Core API - Getting Started

Este documento describe cómo levantar el entorno local para el proyecto Taxai Core API.

## Requisitos Previos

* [Docker Desktop](https://www.docker.com/) o Docker Engine instalado y ejecutándose en tu máquina.

## Levantar los Servicios Locales

Actualmente, el ecosistema se basa en contenedores Docker para proveer la infraestructura base (una base de datos PostgreSQL 17 configurada para multi-tenant y particionado temporal).

Para arrancar el entorno, sigue estos pasos:

1. Abre una terminal y asegúrate de estar en el directorio raíz de la API (`Taxai/Core/api`).
2. Ejecuta el siguiente comando para iniciar el contenedor en segundo plano:

```bash
docker compose up -d
```

Una vez ejecutado, el contenedor `girapi-db` estará escuchando en el puerto `5432`. Los datos se guardarán de forma persistente dentro de la carpeta local `.local_data/postgres`.

### Comandos de Utilidad (Docker Compose)

* **Detener los contenedores** sin perder los datos:
  ```bash
  docker compose down
  ```

* **Ver los registros (logs)** en tiempo real:
  ```bash
  docker compose logs -f
  ```

* **Eliminar todo (incluyendo los volúmenes de datos)**. ¡Cuidado, esto borrará la base de datos por completo!:
  ```bash
  docker compose down -v
  ```
