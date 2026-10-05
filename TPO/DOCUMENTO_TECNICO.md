# Batalla Naval con Trampolines — Documento técnico

Paradigma Orientado a Objetos · UADE · 2.º cuatrimestre 2026

Este documento es la referencia para programar el juego: qué clases hay, qué hace cada método, cómo se conectan y en qué orden conviene implementarlas. Las reglas para jugar están en el manual, en esta misma carpeta ([`Manual_Batalla_Naval_con_Trampolines.docx`](Manual_Batalla_Naval_con_Trampolines.docx)); acá se citan por su número.

- **Lenguaje:** Java, por consola, sin librerías externas (solo `java.util.Scanner`).
- **Clases:** 12, una por archivo `.java`, todas en el paquete por defecto.
- **Idea central:** cada casilla guarda un `ElementoMarino` (agua, barco o trampolín) y cada uno responde a la bomba a su manera. Así el tablero nunca tiene que preguntar qué hay en una casilla.

## Índice

1. [Estructura del repositorio](#1-estructura-del-repositorio)
2. [Diagrama de clases](#2-diagrama-de-clases)

---

## 1. Estructura del repositorio

Todo el TPO va en la carpeta `TPO/`, separada de las carpetas de cada clase:

```text
paradigma-grupo5/
├── README.md
├── .gitignore
├── clase-02/ … clase-04/          ejercicios de cada clase
└── TPO/
    ├── DOCUMENTO_TECNICO.md       este documento
    ├── Manual_Batalla_Naval_con_Trampolines.docx
    └── src/                       se agrega a partir de la etapa 1
        ├── Main.java
        ├── Partida.java
        ├── Consola.java
        ├── Jugador.java
        ├── Tablero.java
        ├── Casilla.java
        ├── Coordenada.java
        ├── Resultado.java
        ├── ElementoMarino.java
        ├── Agua.java
        ├── Barco.java
        └── Trampolin.java
```

## 2. Diagrama de clases

```mermaid
classDiagram
    direction TB

    class Main {
        +main(String[] args)$ void
    }
    class Partida {
        -Jugador jugador1
        -Jugador jugador2
        -Consola consola
        +Partida()
        +jugar() void
        -prepararJugador(Jugador jugador) void
        -jugarTurno(Jugador tirador, Jugador rival) void
        -rebotar(Jugador tirador, Coordenada casilla) void
    }
    class Consola {
        -Scanner scanner
        +Consola()
        +pedirTexto(String mensaje) String
        +pedirCoordenada(String mensaje) Coordenada
        +pedirOrientacion() boolean
        +mostrar(String mensaje) void
        +mostrarLadoALado(String izquierda, String derecha) void
        +limpiarPantalla() void
        +esperarEnter() void
    }
    class Jugador {
        -String nombre
        -Tablero tablero
        +Jugador(String nombre)
        +getNombre() String
        +getTablero() Tablero
        +perdio() boolean
    }
    class Tablero {
        +int TAMANIO$
        -Casilla[][] casillas
        -Barco[] flota
        -int cantidadBarcos
        +Tablero()
        +colocarBarco(Barco barco, Coordenada inicio, boolean horizontal) boolean
        +colocarTrampolin(Coordenada casilla) boolean
        -hayLugar(Coordenada inicio, int largo, boolean horizontal) boolean
        +yaRecibioBomba(Coordenada casilla) boolean
        +recibirBomba(Coordenada casilla) Resultado
        +flotaHundida() boolean
        +dibujar(boolean esPropio) String
    }
    class Coordenada {
        -int fila
        -int columna
        +Coordenada(int fila, int columna)
        +desdeTexto(String texto)$ Coordenada
        +getFila() int
        +getColumna() int
        +toString() String
    }
    class Casilla {
        -ElementoMarino elemento
        -boolean recibioBomba
        +Casilla()
        +estaLibre() boolean
        +ponerElemento(ElementoMarino elemento) void
        +yaRecibioBomba() boolean
        +recibirBomba() Resultado
        +getSimbolo(boolean esPropio) char
    }
    class ElementoMarino {
        <<abstract>>
        +recibirBomba()* Resultado
        +getSimbolo(boolean recibioBomba)* char
        +ocupaLugar() boolean
    }
    class Agua {
        +recibirBomba() Resultado
        +getSimbolo(boolean recibioBomba) char
        +ocupaLugar() boolean
    }
    class Barco {
        -String nombre
        -int largo
        -int impactos
        +Barco(String nombre, int largo)
        +getNombre() String
        +getLargo() int
        +estaHundido() boolean
        +recibirBomba() Resultado
        +getSimbolo(boolean recibioBomba) char
    }
    class Trampolin {
        +recibirBomba() Resultado
        +getSimbolo(boolean recibioBomba) char
    }

    Partida "1" *-- "2" Jugador
    Partida "1" --> "1" Consola
    Jugador "1" *-- "1" Tablero
    Tablero "1" *-- "100" Casilla
    Tablero "1" o-- "4" Barco : flota
    Tablero ..> Coordenada : usa
    Casilla "1..*" --> "1" ElementoMarino : elemento
    ElementoMarino <|-- Agua
    ElementoMarino <|-- Barco
    ElementoMarino <|-- Trampolin
```

Rombo lleno = composición (la parte no existe sin el todo). Rombo vacío = agregación. Triángulo = herencia. Flecha punteada = dependencia. `$` = `static` y `*` = abstracto.

---
