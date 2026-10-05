# PawCare Web — Spring Boot + Thymeleaf + MySQL

Proyecto web para el proyecto productivo SENA ADSO PawCare.

## 1. Tecnologías
- Java 17
- Spring Boot 3.5.16
- Spring MVC + Thymeleaf
- Spring Data JPA / Hibernate
- Spring Security
- MySQL 8.4
- Maven
- Docker / Docker Compose

## 2. Qué funciona en esta primera versión
- Página pública de inicio con identidad visual PawCare.
- Registro de usuario.
- Inicio de sesión con contraseña almacenada mediante hash BCrypt.
- Sesión protegida con Spring Security.
- Dashboard privado.
- Crear y guardar mascotas asociadas al usuario.
- Base preparada para agregar salud, citas, medicamentos, recordatorios y alimentación.

## 3. Ejecutar con Docker (recomendado)
Requisitos: Docker Desktop.

```bash
docker compose up --build
```

Abrir: http://localhost:8080

Para detener:
```bash
docker compose down
```

Los datos de MySQL quedan en el volumen `pawcare_mysql`.

## 4. Ejecutar desde IntelliJ sin Docker para la app
Puedes usar Docker solo para MySQL:

```bash
docker compose up -d mysql
```

Luego abrir el proyecto en IntelliJ y ejecutar `PawCareApplication`.

## 5. Base de datos
Por defecto:
- BD: pawcare
- Usuario: pawcare
- Contraseña local: pawcare123
- Puerto: 3306

En producción NO uses estas credenciales. Configura variables de entorno.

## 6. Rutas principales
- `/` página pública
- `/registro` crear cuenta
- `/login` iniciar sesión
- `/dashboard` panel privado
- `/mascotas` guardar mascota (POST)

## 7. Próximos módulos de PawCare
1. Editar/eliminar mascota.
2. Registro de salud.
3. Medicamentos.
4. Citas veterinarias.
5. Recordatorios.
6. Plan de alimentación.
7. Actividades/paseos.
8. Perfil de veterinario.
9. Módulo para clínicas veterinarias.
10. Pruebas con Postman/Selenium.
11. Despliegue y documentación técnica/usuario.

## 8. Despliegue en Render
Render no ofrece Java como runtime nativo; para Java se recomienda desplegar mediante Docker.

Pasos:
1. Crear un repositorio GitHub y subir todo este proyecto.
2. En Render: New > Web Service.
3. Conectar el repositorio.
4. Seleccionar runtime Docker.
5. Crear/configurar la base de datos de producción y obtener su URL.
6. En Environment Variables configurar `DB_URL`, `DB_USERNAME` y `DB_PASSWORD`.
7. Deploy.

IMPORTANTE: Render soporta Docker, y los Web Services pueden desplegarse desde un repositorio Git. El servicio debe escuchar en el puerto indicado por `PORT`.

## 9. Base de datos de producción
La versión local usa MySQL. Para producción se puede mantener MySQL en un servicio externo compatible o adaptar la aplicación a PostgreSQL administrado. JPA permite que el modelo de entidades siga siendo el mismo, pero habría que cambiar el driver y la URL.

## 10. Seguridad
Nunca subas contraseñas reales al repositorio. Usa variables de entorno/secretos. Las contraseñas de los usuarios no se guardan en texto plano: se codifican con BCrypt.
