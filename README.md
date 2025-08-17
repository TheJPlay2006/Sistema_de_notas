# 📚 Sistema de Gestión de Notas

![Java](https://img.shields.io/badge/Java-JDK%2024-orange?logo=java&logoColor=white)
![NetBeans](https://img.shields.io/badge/NetBeans-IDE-blue?logo=apache-netbeans-ide&logoColor=white)
![SQL Server](https://img.shields.io/badge/SQL%20Server-LocalDB-red?logo=microsoft-sql-server&logoColor=white)
![Status](https://img.shields.io/badge/Status-Completado-success?logo=checkmarx&logoColor=white)

## 📋 Descripción

Sistema de gestión de notas académicas desarrollado en Java con interfaz gráfica (GUI) para la **Práctica Intensiva de Programación I (ITI-221)**. La aplicación permite registrar, consultar, modificar y eliminar notas académicas con persistencia en base de datos local SQL Server.

## ✨ Características Principales

| Funcionalidad | Descripción | Estado |
|---------------|-------------|--------|
| 🖥️ **Interfaz Gráfica** | GUI desarrollada en Java Swing con manejo de eventos | ✅ |
| 📊 **CRUD Completo** | Crear, Leer, Actualizar, Eliminar registros académicos | ✅ |
| 🗄️ **Base de Datos** | Persistencia con SQL Server LocalDB | ✅ |
| ✔️ **Validación de Datos** | Controles de entrada y mensajes de error claros | ✅ |
| 🏷️ **Categorización** | Clasificación por tipo (Tarea, Examen, Proyecto) | ✅ |
| 📈 **Sistema de Calificaciones** | Escala de 0.0 a 10.0 con validaciones | ✅ |
| 📄 **Exportación** | Generación de reportes en formato Markdown | ✅ |

## 🚀 Tecnologías Utilizadas

| Categoría | Tecnología | Versión | Propósito |
|-----------|------------|---------|-----------|
| **Lenguaje** | ☕ Java | JDK 24 | Desarrollo principal |
| **IDE** | 🛠️ NetBeans | Latest | Entorno de desarrollo |
| **Build Tool** | 📦 Maven/Ant | - | Gestión de dependencias |
| **Base de Datos** | 🗄️ SQL Server | LocalDB | Persistencia de datos |
| **Driver JDBC** | 🔌 Microsoft JDBC | 12.10.1 | Conectividad BD |
| **GUI Framework** | 🖼️ Java Swing | Built-in | Interfaz gráfica |
| **Control de Versiones** | 🌿 Git | - | Versionado del código |

## 📁 Estructura del Proyecto

```
Sistema_de_notas/
├── 📦 Source Packages/
│   └── 📁 Main/
│       ├── 🏠 Main.java                    # Punto de entrada de la aplicación
│       ├── 📁 dominio/                     # 🎯 Modelos y entidades de negocio
│       │   ├── 🚫 DominioException.java    # Excepciones del dominio
│       │   └── 📝 Nota.java               # Entidad principal Nota
│       ├── 📁 interfaz/                    # 🖥️ Interfaz gráfica de usuario
│       │   ├── 💬 DialogoNota.java        # Diálogo para CRUD de notas
│       │   └── 🪟 VentanaPrincipal.java   # Ventana principal de la aplicación
│       ├── 📁 persistencia/                # 💾 Capa de acceso a datos
│       │   ├── 🗃️ NotaDAO.java            # Data Access Object para Nota
│       │   ├── ⚙️ NotaDAOImpl.java        # Implementación del DAO
│       │   └── 🚫 PersistenciaException.java # Excepciones de persistencia
│       ├── 📁 servicios/                   # 🔧 Lógica de aplicación
│       │   ├── 🚫 ApplicationException.java # Excepciones de aplicación
│       │   └── ⚡ GestorNotas.java         # Servicio principal de gestión
│       └── 📁 utilidades/                  # 🛠️ Herramientas y utilidades
│           └── 📄 ExportadorMarkdown.java # Exportación a archivos .md
├── 📦 Test Packages/                       # 🧪 Pruebas unitarias
├── 📚 Libraries/                           # 📖 Dependencias externas
│   ├── mssql-jdbc-12.10.1.jre11.jar      # Driver SQL Server
│   ├── mssql-jdbc-12.10.1.jre11-javadoc.jar # Documentación del driver
│   ├── mssql-jdbc-12.10.1.jre11-sources.jar # Código fuente del driver
│   └── JDK 24 (Default)                   # Kit de desarrollo Java
└── 📄 README.md                           # Este archivo
```

## Configuración de Base de Datos

### 1. Crear la Base de Datos

Ejecute el siguiente script SQL en SQL Server Management Studio o en su cliente SQL preferido:

```sql
-- Crear base de datos
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'NotasDB')
    CREATE DATABASE NotasDB;
GO

USE NotasDB;
GO

-- Crear tabla
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Notas')
BEGIN
    CREATE TABLE Notas (
        id INT IDENTITY(1,1) PRIMARY KEY,
        titulo NVARCHAR(100) NOT NULL,
        descripcion NVARCHAR(500),
        calificacion DECIMAL(3,1) CHECK (calificacion >= 0.0 AND calificacion <= 10.0),
        categoria NVARCHAR(20) CHECK (categoria IN ('Tarea', 'Examen', 'Proyecto')),
        fecha DATE NOT NULL
    );
END
GO
```

### 2. Datos de Prueba (Opcional)

Para cargar datos de ejemplo:

```sql
USE NotasDB;
GO

-- Limpiar tabla (opcional)
DELETE FROM Notas;
DBCC CHECKIDENT (Notas, RESEED, 0);
GO

-- Insertar notas de ejemplo
INSERT INTO Notas (titulo, descripcion, calificacion, categoria, fecha) VALUES
('Examen Parcial Álgebra', 'Primer examen del semestre, temas: matrices y determinantes', 7.8, 'Examen', '2025-03-15'),
('Tarea 1 - Física', 'Ejercicios sobre cinemática y movimiento rectilíneo uniforme', 9.2, 'Tarea', '2025-03-10'),
('Proyecto Grupal - Programación', 'Desarrollo de una app de gestión de notas en Java', 8.5, 'Proyecto', '2025-04-05'),
('Quiz 2 - Química', 'Evaluación corta sobre enlaces químicos', 6.4, 'Examen', '2025-03-22'),
('Tarea 3 - Historia', 'Resumen del siglo XX en Europa', 9.0, 'Tarea', '2025-03-18'),
('Presentación Final - Inglés', 'Exposición sobre cultura británica', 8.7, 'Proyecto', '2025-04-10');

-- Verificar datos
SELECT * FROM Notas ORDER BY fecha;
```

## 🚀 Instalación y Ejecución

### 📋 Prerrequisitos

| Requisito | Versión Mínima | Recomendada | Link de Descarga |
|-----------|----------------|-------------|------------------|
| ☕ **Java JDK** | 8+ | JDK 24 | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) |
| 🛠️ **NetBeans IDE** | 12+ | Latest | [NetBeans](https://netbeans.apache.org/) |
| 🗄️ **SQL Server** | 2019+ | LocalDB | [SQL Server Express](https://www.microsoft.com/sql-server/sql-server-downloads) |
| 📦 **Maven/Ant** | - | Built-in NetBeans | - |

### ⚙️ Pasos de Instalación

#### 1️⃣ **Clonar el repositorio**
```bash
git clone https://github.com/TheJPlay2006/Sistema_de_notas.git
cd Sistema_de_notas
```

#### 2️⃣ **Configurar la base de datos**
- ✅ Ejecutar el script SQL proporcionado más abajo
- ✅ Verificar la cadena de conexión en `NotaDAOImpl.java`
- ✅ Asegurar que SQL Server LocalDB esté ejecutándose

#### 3️⃣ **Abrir en NetBeans**
- 📁 **File** → **Open Project**
- 📂 Seleccionar la carpeta `Sistema_de_notas`
- ⏳ Esperar que NetBeans resuelva las dependencias

#### 4️⃣ **Ejecutar la aplicación**
- ▶️ **Click derecho** en el proyecto → **Run**
- ⌨️ O usar **F6** para ejecutar
- 🖥️ Se abrirá la ventana principal del sistema

## 🎯 Funcionalidades

### 📝 Gestión de Notas
| Operación | Descripción | Acceso | Validaciones |
|-----------|-------------|--------|--------------|
| ➕ **Crear Nota** | Registro de nuevas calificaciones | Botón "Agregar" | Título, calificación 0.0-10.0 |
| 📋 **Listar Notas** | Visualización en tabla ordenada | Ventana principal | Formato de fecha, categorías |
| ✏️ **Editar Nota** | Modificación de registros existentes | Doble click / Botón "Editar" | Preservación de integridad |
| 🗑️ **Eliminar Nota** | Eliminación con confirmación | Botón "Eliminar" | Diálogo de confirmación |
| 🔍 **Buscar Notas** | Filtrado por criterios | Campo de búsqueda | Búsqueda en tiempo real |
| 📄 **Exportar** | Generación de reportes MD | Menú "Archivo" | Validación de ruta |

### 🛡️ Características Técnicas
| Aspecto | Implementación | Beneficio |
|---------|----------------|-----------|
| 🔐 **Validación de Entrada** | Controles en tiempo real | Prevención de errores |
| ⚠️ **Manejo de Excepciones** | Jerarquía personalizada | Recuperación robusta |
| 💾 **Persistencia** | Patrón DAO con transacciones | Integridad de datos |
| 🎨 **Interfaz Responsiva** | Swing con layout managers | Experiencia fluida |
| 📊 **Reportes** | Exportación a Markdown | Documentación externa |

## 🏗️ Arquitectura y Diseño

### 📐 Principios Aplicados

| Principio | Implementación | Beneficio |
|-----------|----------------|-----------|
| **🎯 Single Responsibility** | Cada clase tiene una única responsabilidad | Mantenibilidad |
| **🔒 Encapsulamiento** | Atributos privados con getters/setters | Seguridad de datos |
| **🔗 Bajo Acoplamiento** | Interfaces para separar capas | Flexibilidad |
| **🎪 Alta Cohesión** | Funcionalidades relacionadas agrupadas | Claridad |
| **⚠️ Manejo de Excepciones** | Jerarquía personalizada de excepciones | Robustez |

### 🏛️ Arquitectura en Capas

```
┌─────────────────────────────────────┐
│           🖥️ INTERFAZ              │ ← Presentación (Swing)
├─────────────────────────────────────┤
│          🔧 SERVICIOS               │ ← Lógica de negocio
├─────────────────────────────────────┤
│         💾 PERSISTENCIA             │ ← Acceso a datos (DAO)
├─────────────────────────────────────┤
│          🎯 DOMINIO                 │ ← Entidades y reglas
└─────────────────────────────────────┘
```

## 📊 Estructura de Datos

### 🗃️ Tabla Notas
| Campo | Tipo | Restricciones | Descripción | Ejemplo |
|-------|------|---------------|-------------|---------|
| `id` | `INT` | 🔑 PK, Identity | Identificador único | `1, 2, 3...` |
| `titulo` | `NVARCHAR(100)` | ❗ NOT NULL | Nombre de la nota | `"Examen Parcial Álgebra"` |
| `descripcion` | `NVARCHAR(500)` | ℹ️ Opcional | Descripción detallada | `"Temas: matrices y determinantes"` |
| `calificacion` | `DECIMAL(3,1)` | 📏 0.0-10.0 | Puntuación obtenida | `7.8, 9.2, 6.4` |
| `categoria` | `NVARCHAR(20)` | 🏷️ Enum | Tipo de evaluación | `Tarea/Examen/Proyecto` |
| `fecha` | `DATE` | ❗ NOT NULL | Fecha de registro | `2025-03-15` |

### 🎯 Enum Categoria
```java
public enum Categoria {
    TAREA("Tarea"),
    EXAMEN("Examen"), 
    PROYECTO("Proyecto");
}
```

## 📷 Capturas de Pantalla

> *Las siguientes capturas demuestran el funcionamiento completo del sistema*

### 🖥️ Ventana Principal
- ✅ Lista completa de notas en tabla
- ✅ Botones de acción principales
- ✅ Menú de opciones y exportación

### ➕ Diálogo de Nueva Nota  
- ✅ Formulario con validaciones en tiempo real
- ✅ Selector de categorías
- ✅ Control de calificaciones 0.0-10.0

### ⚠️ Manejo de Errores
- ✅ Mensajes de error claros y contextuales
- ✅ Validaciones de entrada
- ✅ Confirmaciones de eliminación

### 🗄️ Integración con Base de Datos
- ✅ Operaciones CRUD en tiempo real
- ✅ Consistencia de datos
- ✅ Transacciones seguras

---

## 🔗 Enlaces y Repositorio

### 📂 **GitHub Repository**
🌟 **[https://github.com/TheJPlay2006/Sistema_de_notas](https://github.com/TheJPlay2006/Sistema_de_notas)**

### 📋 **Issues y Contribuciones**
- 🐛 [Reportar Bug](https://github.com/TheJPlay2006/Sistema_de_notas/issues)
- 💡 [Solicitar Funcionalidad](https://github.com/TheJPlay2006/Sistema_de_notas/issues)
- 🤝 [Contribuir al Proyecto](https://github.com/TheJPlay2006/Sistema_de_notas/pulls)

## Entregables del Proyecto

- [x] Código fuente completo en NetBeans
- [x] Archivo JAR ejecutable
- [x] Documentación técnica (4-6 páginas)
- [x] Scripts de base de datos
- [x] Capturas de pantalla de funcionamiento
- [x] Repositorio Git con historial de commits

### 🏆 Características Destacadas

- 🎯 **Arquitectura en Capas**: Separación clara de responsabilidades
- 🔒 **Seguridad de Datos**: Validaciones en múltiples niveles  
- 🚀 **Performance**: Uso eficiente de colecciones y consultas SQL
- 🎨 **UX/UI**: Interfaz intuitiva con feedback inmediato
- 📦 **Empaquetado**: JAR ejecutable independiente
- 🧪 **Robustez**: Manejo exhaustivo de casos límite

## Autor

**TheJPlay2006**

## Licencia

Proyecto académico desarrollado para fines educativos - ITI-221 Programación I.

---
**Desarrollado con ❤️ para la práctica intensiva de Programación I**
