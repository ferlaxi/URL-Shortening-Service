# 🔗 URL-Shortening Service

## Descripción del Proyecto
Este proyecto es un acortador de URLs que genera automáticamente enlaces únicos de 5 caracteres aleatorios a partir de URLs largas. Su diseño asegura la creación rápida y eficiente de enlaces cortos que pueden compartirse fácilmente.

## Tecnologías Utilizadas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)

## Características Principales

- Utilización de arquitectura hexagonal.
- Base de datos MySQL para almacenar las URLs.
- Manejo de excepciones centralizado.

## 🗂️ Índice

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Características Principales](#-características-principales)
- [Configuración del Entorno](#-configuración-del-entorno)
- [Descripción de Entidades](#-descripción-de-entidades)
    - [URL](#url)
- [API REST](#api-rest)
    - [Endpoints de URL](#-api-rest---endpoints-de-url)
        - [Get All URLs](#get-all-urls)
        - [Get URL by shortcode](#get-url-by-shortcode)
        - [Get stats from URL by shortcode](#get-stats-by-shortcode)
        - [Create a URL](#create-url)
        - [Update a URL by shortcode](#update-url)
        - [Delete a URL by shortcode](#delete-url)

## ⚙️ Configuración del Entorno

1. Clona el repositorio.
2. Abre el proyecto en tu IDE.
3. Configura la conexión a la base de datos MySQL en `application.properties`
````properties
spring.datasource.url=jdbc:mysql://localhost:3306/url_db?useSSL=false&serverTimezone=UTC
````
4. Ejecuta la aplicación

## Descripción de Entidades

### URL
Descripción: Representa una URL acortada en el sistema.

Atributos:

`id`: Long (ID único)
`url`: String (URL original)
`shortCode`: String (Código corto para cada url)
`createdAt`: LocalDateTime (Fecha de creación)
`updatedAt`: LocalDateTime (Fecha de actualización)
`accessCount`: Integer (Conteo de visitas a la URL)

## API REST

### 🌐 API REST - Endpoints de URL

| Método | Endpoint                          | Descripción                                  | Enlace Rápido               |
|--------|-----------------------------------|----------------------------------------------|-----------------------------|
| GET    | `api/shorten`                     | Obtiene todas las URLs.                      | [Get All URLs](#get-all-urls)|
| GET    | `api/shorten/{shortcode}`         | Obtiene una URL por Shortcode.               | [Get URL by shortcode](#get-url-by-shortcode) |
| GET    | `api/shorten/{shortcode}/stats`   | Obtiene las stats de una URL con su contador.| [Get stats from URL](#get-stats-from-url) |
| POST   | `api/shorten`                     | Crea una nueva URL.                          | [Create URL](#create-url)    |
| PUT    | `api/shorten/{shortcode}`         | Actualiza una URL existente.                 | [Update URL](#update-url)    |
| DELETE | `api/shorten/{shortcode}`         | Elimina una URL por su shortcode.            | [Delete URL](#delete-url)    |

### Get All URLs
**Endpoint:** `GET api/shorten`

**Descripción:** Recupera todas las URLs registradas en la base de datos.

**Respuesta:**
- `200 OK`: La lista de URLs se ha recuperado con éxito.

### Get URL by shortcode
**Endpoint:** `GET api/shorten/{shortcode}`

**Descripción:** Recupera una URL utilizando su shortcode..

**Parámetros:**
- `shortcode`: String (carácteres representativos de la URL)

**Respuesta:**
- `200 OK`: La URL se ha recuperado correctamente.  
- `404 Not Found`: No se encontró la URL solicitada.

### Get stats from URL by shortcode
**Endpoint:** `GET api/shorten/{shortcode}/stats`

**Descripción:** Recupera información sobre el conteo de visitas de la URL

**Parámetros:**
- `shortcode`: String (carácteres representativos de la URL)

**Respuesta:**
- `200 OK`: La URL se ha recuperado correctamente.  
- `404 Not Found`: No se encontró la URL solicitada.

### Create URL
**Endpoint:** `POST api/shorten`

**Descripción:** Crea una nueva URL mediante un RequestBody.

**Respuesta:**
- `201 Created`: La URL se ha creado con éxito.
- `400 Bad Request`: shortcode ya existente o los datos de entrada son inválidos.

**Cuerpo de la solicitud**
```json
{
    "url": "String"
}
```
### Update URL
**Endpoint:** `PUT /api/shorten/{shortcode}`

**Descripción:** Modifica una URL previamente registrada.

**Parámetros:**
- `shortcode`: String (shortcode de la URL a actualizar)
- `url`: urlEdited (Cuerpo de la solicitud que contiene los nuevos datos para actualizar la URL.)

**Respuesta:**
- `200 OK`: URL actualizada exitosamente.
- `400 Bad Request`: Los datos de entrada son inválidos.

**Cuerpo de la solicitud**
```json
{
  "url": "String"
}
```
### Delete URL
**Endpoint:** `DELETE /api/shorten/{shortcode}`

**Descripción:** Elimina una URL por su shortcode.

**Parámetros:**
- `shortcode`: String (shortcode de la URL)

**Respuesta:**
- `204 No Content`: URL eliminada exitosamente.
- `400 Bad Request`: URL no pudo ser eliminada.
