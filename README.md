# Paradigma Orientado a Objetos — Grupo 5 "Chiqui Mafia"

Repositorio grupal de la materia **Paradigma Orientado a Objetos** (UADE).
Acá subimos los ejercicios y trabajos que vamos resolviendo clase a clase.

---

## Sobre el grupo

**Grupo 5 — Chiqui Mafia**

| Integrante |
|---|
| Santiago Terminiello |
| Nahuel Mioni |
| Emanuel Paoloni |
| Matias Llanos |
| Joaquin Valdettaro |

## Datos de la cursada

- **Materia:** Paradigma Orientado a Objetos
- **Universidad:** UADE
- **Cursada:** lunes a la tarde
- **Lenguaje:** Java

## Organización del repositorio

Los ejercicios se organizan **por clase**. Cada clase tiene su propia carpeta:

```
.
├── clase-01/
│   └── src/
├── clase-02/
│   └── src/
├── clase-03/
│   └── src/
└── README.md
```

Dentro de cada carpeta van los `.java` correspondientes a los ejercicios de esa
clase. Si un ejercicio es grande o tiene varias clases, se le puede armar una
subcarpeta propia:

```
clase-04/
├── ejercicio-1/
└── ejercicio-2/
```

## Cómo trabajar en el repo

1. Traer los últimos cambios antes de empezar:

   ```bash
   git pull origin main
   ```

2. Crear la carpeta de la clase si todavía no existe y agregar los archivos.

3. Subir los cambios:

   ```bash
   git add .
   git commit -m "clase 03: ejercicio de herencia"
   git push origin main
   ```

### Convención de commits

Que se entienda qué se subió y de qué clase es:

```
clase-02: ejercicios de constructores
clase-05: TP encapsulamiento - punto 1 y 2
fix: corrección en clase-03/Persona.java
```

## Cómo ejecutar los ejercicios

Desde la carpeta del ejercicio:

```bash
javac *.java
java Main
```

O directamente abriendo la carpeta desde IntelliJ IDEA / Eclipse / VS Code.
