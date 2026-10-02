# URL Shortening Service

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.0-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3-005C0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-3.4-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

Servicio web diseñado para transformar URLs largas en enlaces cortos de fácil distribución. El sistema cuenta con redirección HTTP nativa, monitoreo de visitas (clicks) y una interfaz de usuario minimalista construida directamente sobre el servidor.

## Arquitectura

El proyecto sigue un patrón MVC clásico estructurado en capas:
- **Controladores:** Manejan el tráfico web (Thymeleaf) y exponen una API REST.
- **Servicios:** Contienen la lógica de validación y generación aleatoria de códigos.
- **Repositorios:** Interfaz directa con MySQL mediante Spring Data JPA.

## Configuración y Ejecución

### Opción 1: Docker Compose (Recomendado)
El proyecto incluye todo lo necesario para ejecutarse de manera automatizada.

1. Abre tu terminal en el directorio del proyecto.
2. Construye y despliega los contenedores:
   ```bash
   docker compose up -d --build
   ```
3. La aplicación y su base de datos estarán disponibles y conectadas. Ingresa a `http://localhost:8000/`.

### Opción 2: Ejecución Manual
Si deseas correrlo desde tu IDE o terminal local:
1. Asegúrate de tener MySQL ejecutándose localmente en el puerto `3306`.
2. Crea una base de datos llamada `url_db`.
3. Compila el proyecto y córrelo:
   ```bash
   ./mvnw clean package -DskipTests
   ./mvnw spring-boot:run
   ```
*(La configuración local usará credenciales por defecto: host `localhost`, usuario `root` y contraseña `root`).*

## API REST 

Además de la interfaz gráfica, el servicio expone una API para consumir programáticamente:

| Método | Endpoint                        | Uso principal                                      |
|--------|---------------------------------|----------------------------------------------------|
| GET    | `/api/shorten`                  | Lista todas las URLs almacenadas.                  |
| POST   | `/api/shorten`                  | Acorta una nueva URL.                              |
| GET    | `/api/shorten/{shortcode}`      | Obtiene los detalles de un código corto.           |
| PUT    | `/api/shorten/{shortcode}`      | Modifica la URL original vinculada a un código.    |
| DELETE | `/api/shorten/{shortcode}`      | Borra el registro permanentemente.                 |

**Nota sobre la redirección:**
El punto final de redirección se encuentra expuesto en la ruta base `GET /{shortcode}`. Consumir este endpoint en el navegador sumará +1 al contador de visitas del registro y ejecutará un `HTTP 302` hacia el destino original.
