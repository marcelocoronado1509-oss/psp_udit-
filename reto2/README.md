# Reto 2 · Pipeline de Auditoría UDITversum (Fase 1)

**Módulo:** 0490 · Programación de Servicios y Procesos
**Autor/a:** *(tu nombre)* Marcelo Gonzalez
**Tecnología:** Java + `ProcessBuilder` (procesos del sistema operativo)
**RA vinculado:** RA1 · Programación de aplicaciones compuestas por varios procesos

> 💡 **Cómo usar este README:** no es un trámite que se rellena al final. Es tu **cuaderno de pensamiento** durante el reto. Las secciones marcadas con 🧠 sirven para que pienses sobre *cómo* estás pensando. Si las rellenas de golpe en el último minuto pierden todo su valor (y se nota en la defensa oral).
>
> 🗣️ **Importante:** durante la defensa te pediré modificar tu código en directo. Todo lo que escribas aquí debe ser algo que puedas explicar sin mirar la pantalla.

---

## 📺 Qué es esta app

Un programa de consola que simula la **primera fase de una auditoría de UDITversum**. El programa:

1. Lanza **dos comprobaciones (`ping`) a la vez**, cada una en su propio proceso del sistema operativo.
2. **Espera** a que ambas terminen.
3. Lee el **código de salida** de cada una.
4. Según el resultado combinado, **toma una decisión** y abre una aplicación del sistema (Bloc de Notas o Calculadora).

```
        ┌──────────────┐
        │  Mi programa │
        │   (Java)     │
        └──────┬───────┘
               │ start()          start()
        ┌──────┴──────┐    ┌──────┴──────┐
        ▼                         ▼
  ┌───────────┐             ┌───────────┐
  │  ping A   │             │  ping B   │   ← corren a la vez
  └─────┬─────┘             └─────┬─────┘
        │ waitFor()               │ waitFor()
        └───────────┬─────────────┘
                    ▼
          ¿códigos de salida?
                    │
        ┌───────────┴───────────┐
        ▼                       ▼
   Bloc de Notas           Calculadora
```

*(Ajusta el esquema y los nombres a lo que hace realmente tu programa.)*

📸 **Sustituye esto por una captura de tu propia ejecución antes de entregar.**

---<img width="1917" height="1077" alt="image" src="https://github.com/user-attachments/assets/00b19c58-0aa6-44dd-b4e6-c0987e382edc" />


## 🧠 Antes de empezar: planifico *(5 min, sin tocar el teclado)*


Responde **antes** de escribir una sola línea de código. No importa si te equivocas: lo importante es dejar escrito qué pensabas.

**Con mis palabras, ¿qué me pide el reto?** *(sin copiar el enunciado)*
Me pide dos procesos paralelos y compararlos y si los dos son correctos se habra la calculadora y si uno sale bien y otro mal se abra la calculadora.
*(escribe aquí)*

**¿Qué parte del Reto 1 voy a reutilizar tal cual?**
*(escribe aquí)*
la creacion de los procesos

**¿Qué es nuevo respecto al Reto 1 y me da más respeto?**
*(escribe aquí)*
el if donde hay que comparar resultados.

**Mi plan en 4-5 pasos, en orden:**
1. *(escribe aquí)* crear los procesos
2. *(escribe aquí)*crear las variables con el waitfor
3. *(escribe aquí)*comparar el resultado de los procesos
4. *(escribe aquí)*mostrarlo por pantalla abrir lo correspondiente y terminar la ejecucion

**Predicciones** *(comprueba al final si acertaste)* acerte

| Escenario | ¿Qué código de salida espero en cada ping? | ¿Qué aplicación se abre? |
|---|---|---| 
| Los dos pings a `127.0.0.1` | *(escribe aquí)* | *(escribe aquí)* |0 y 0
| Un ping válido y otro a una dirección inexistente | *(escribe aquí)* | *(escribe aquí)* |0 y 1
| Los dos pings a direcciones inexistentes | *(escribe aquí)* | *(escribe aquí)* |1 y 1 

**Predicción de tiempo:** si cada ping tarda unos 3 segundos, ¿cuánto tardará mi programa en total si los lanzo en paralelo? ¿Y si los lanzara uno detrás de otro?
*(escribe aquí)*3 y si es uno detras de otro 6

---

## 🎯 Objetivo del reto

Dar el salto de **gestionar un proceso tras otro** (Reto 1) a **coordinar varios procesos simultáneos**, aplicando:

- Creación de procesos con `ProcessBuilder` y `start()`.
- **Ejecución concurrente:** lanzar ambos procesos antes de esperar a ninguno.
- Sincronización con `waitFor()` y lectura del **código de salida**.
- **Lógica condicional** (`if` con `&&` / `||`) para decidir en función de varios resultados.
- Gestión de errores con `try/catch` (`IOException`, `InterruptedException`).

---

## 🛠️ Componentes y conceptos utilizados

> Rellena la columna **"con mis palabras"** *sin mirar tus apuntes*. Después compara con ellos y corrige en otro color o con un comentario. Esa diferencia es exactamente lo que aún no tienes del todo claro.

| Componente / concepto | Para qué se usa en esta app | Con mis palabras |
|---|---|---|
| `ProcessBuilder` | Prepara la orden que se enviará al sistema operativo (ping, notepad, calc) | | manda la orden al sistema
| `start()` | Lanza de verdad el proceso y **devuelve el control enseguida**, sin esperar | | empieza el proceso 
| `Process` | Objeto con el que controlo cada proceso ya en marcha | | controla todo lo que esta en marcha
| `waitFor()` | Bloquea mi programa hasta que ese proceso termina y devuelve su código de salida | | espera para que los procesos salgan al mismo tiempo
| Código de salida (`int`) | Dice cómo terminó el proceso: `0` = éxito, distinto de `0` = fallo | | dice como termina
| `&&` (AND) | Se cumple solo si **las dos** condiciones son verdaderas | | significa Y
| `\|\|` (OR) | Se cumple si **al menos una** condición es verdadera | |siginifa o
| `try/catch` | Captura errores que Java no puede evitar (el SO no encuentra el programa, etc.) | | por si encuentra un error se pueda ejecutar igual con una solucion
| `InterruptedException` | Excepción que obliga a gestionar `waitFor()` por si el hilo es interrumpido | | obliga a interrumpir

**¿Cómo se llaman mis dos objetos `Process` y qué lanza cada uno?**
*(escribe aquí)* p1 y p2 y uno lanza 0 y el otro 1

---

## 🔀 Secuencial vs paralelo: el corazón de este reto

Lo que separa un buen Reto 2 de uno que "funciona pero no cumple" es **dónde pones los `waitFor()`**. Compara las dos líneas de tiempo (suponiendo que cada ping tarda 3 s):

**❌ Secuencial** (`waitFor()` antes del segundo `start()`):

```
t=0s   start(ping A) ──────────── waitFor() ── termina en t=3s
t=3s                              start(ping B) ──────────── waitFor() ── termina en t=6s
                                                                          TOTAL ≈ 6 s
```

**✅ Paralelo** (los dos `start()` primero, los `waitFor()` después):

```
t=0s   start(ping A) ─┐
t=0s   start(ping B) ─┤  los dos procesos corren a la vez
t=3s   waitFor(A) ✔  waitFor(B) ✔
                                  TOTAL ≈ 3 s
```

**Mi orden real de llamadas, copiado de mi código** *(pega solo las líneas relevantes)*:

```java
// (pega aquí tus llamadas a start() y waitFor() en el orden en que aparecen)
```

---

## 🔢 Tabla de verdad de mi decisión

Completa con **tu** lógica real (la del enunciado):

| Código ping A | Código ping B | ¿Ping A OK? | ¿Ping B OK? | Condición (`&&` / `\|\|`) | Aplicación que abro |
|---|---|---|---|---|---|
| 0 | 0 | | | | | bloc de  notas
| 0 | ≠ 0 | | | | | calculadora
| ≠ 0 | 0 | | | | |calculadora
| ≠ 0 | ≠ 0 | | | | |calculadora

**¿Cambiaría el resultado de alguna fila si cambiara `&&` por `||`? ¿En cuáles?**
*(escribe aquí)*

---

## 🚀 Cómo ejecutar el proyecto

1. Clonar o abrir el proyecto en IntelliJ IDEA.
2. Esperar a que indexe el proyecto.
3. Ejecutar (▶) la clase principal.

⚠️ **Dependencia del sistema operativo:** `ping` usa `-n` en Windows y `-c` en Linux/Mac, y `notepad.exe` / `calc.exe` solo existen en Windows. Indica con qué sistema lo has probado: *(escribe aquí)*

---

## 🔍 Mientras programo: mi diario de decisiones

Cada vez que te atasques, cambies de idea o algo falle, anota una entrada. Tres líneas bastan. No se trata de quedar bien: se trata de que dentro de un mes puedas reconstruir **cómo pensaste**.

| Qué intentaba | Qué pasó realmente | Qué hice / qué aprendí |
|---|---|---|
| *(ejemplo)* Que los dos pings corrieran a la vez | El programa tardaba el doble de lo esperado | Me di cuenta de que había puesto un `waitFor()` entre los dos `start()` y... | 
| | | |  abrir la calculadora o el bloc de notas
| | | |  se abrieron
| | | | a abrir el bloc de notas o una aplicación desde intelij

**Mi pregunta-brújula cuando me bloqueo:**
1. ¿Qué espero que haga esta línea?
2. ¿Qué está haciendo realmente? *(imprimo valores para comprobarlo)*
3. ¿En qué punto exacto se separan las dos respuestas?

---

## 🧠 Análisis técnico (preparación para la defensa)

> Responde de forma clara y **con tus propias palabras**. Estas preguntas serán la base de tu evaluación oral. Las pistas en cursiva son para orientarte, no para copiarlas.

### 1. Secuencial vs paralelo

**¿Qué líneas exactas garantizan que los dos pings se ejecutan a la vez? ¿Qué ocurriría físicamente si pusieras el primer `waitFor()` justo antes de lanzar el segundo `start()`?** 

*(escribe aquí)* 10-11-12-13

*Pistas: ¿qué hace `start()` con el hilo principal de mi programa? ¿Quién ejecuta el ping: mi programa o el sistema operativo? ¿Cuántos procesos existen en ese momento en cada caso? ¿Cuánto tardaría el programa completo?*

### 2. El código de salida (exit code)

**¿Qué tipo de dato devuelve `waitFor()`? ¿Qué significa en el estándar de los sistemas operativos que ese valor sea `0` o distinto de `0`?**

*(escribe aquí)* el waitfor me devuelve un int y 0 significa que la ip existe y 1 que no existe o es incorrecta

*Pistas: piensa en "0 = todo fue bien". ¿Por qué crees que el estándar eligió precisamente el 0 para el éxito y deja los demás números libres? ¿Qué información extra pueden aportar los valores distintos de 0? ¿Es lo mismo "el ping falló" que "el programa ping no pudo ejecutarse"?*

### 3. Lógica condicional

**Escribe aquí la condición `if` exacta que has programado. Explica por qué has utilizado `&&` o `||` para decidir si abrir el Bloc de Notas o la Calculadora.**

```java
  if (codigo1 == 0 && codigo2 == 0) he usado este codigo porque a continuacion he puesto que si se cumple esa condicion se abra la app correspondiente mientras que si no se cumple se abra la otra.
```

*(explica aquí tu razonamiento)*

*Pistas: ¿qué debe pasar para que se abra cada aplicación? Si usaras el operador contrario, ¿en qué fila de mi tabla de verdad cambiaría el resultado? ¿Qué pasaría si uno de los dos pings falla y el otro no?*

### 4. Gestión de excepciones

**Tu código incluye un bloque `try/catch`. Describe una situación real (un fallo del sistema o una mala configuración) que provocaría que tu programa entrase en el `catch` de `IOException`.**

*(escribe aquí)*  pues un error seria si ejecuto justo este codigo en el mac. 

*Pistas: `IOException` salta cuando Java **no consigue ni arrancar** el proceso. ¿Qué pasaría si escribo mal el nombre del ejecutable (`notepd.exe`)? ¿Y si ejecuto en un sistema operativo donde ese programa no existe? ¿En qué se diferencia esto de que el ping "falle" y devuelva un código distinto de 0?*

---

## 🛡️ Preparación para la defensa: ¿sabría hacer esto en directo?

Durante la defensa te pediré pequeñas modificaciones. Practica estas **antes** de entregar y marca las que ya sabes hacer sin ayuda:

- ☐ Cambiar la condición para que se abra la Calculadora **solo si falla uno de los dos pings**.
- ☐ Añadir un **tercer ping** en paralelo y que la decisión dependa de los tres.
- ☐ Mostrar el **PID** de cada proceso al lanzarlo.
- ☐ Medir y mostrar **cuántos milisegundos** tarda en total el programa.
- ☐ Hacer que el programa funcione en **Linux** (cambiar `-n` por `-c` y las apps a abrir).
- ☐ Provocar a propósito una `IOException` y mostrar un mensaje claro al usuario.
- ☐ Explicar qué pasaría si quito el `waitFor()`.

**¿Cuál me costó más y por qué?**
*(escribe aquí)* me costo mas añadir el tercer ping porque se me desconfiguraba todo mi codigo.

---

## 🧭 Del Reto 1 al Reto 2: cómo di el salto

El Reto 1 lanzaba procesos **uno tras otro** dentro de un bucle. El Reto 2 los lanza **a la vez**. Explica ese salto con tus palabras:

**¿Qué hacía mi Reto 1 que aquí ya no me sirve tal cual?**
*(escribe aquí)* que en el uno se trabajaba una detras de otro mientras en este trabajo paralelamente y para eso uso el waitfor en los dos procesos.

**¿Qué he tenido que cambiar para que dos procesos corran simultáneamente?**
*(escribe aquí)* he tenido que igualar la variable codigo1 y codigo 2 al waitfor

**¿Qué ventaja tiene lanzar en paralelo? ¿Y qué problema nuevo aparece cuando dependo de dos resultados a la vez?**
*(escribe aquí)* la ventaja es que tarda menos en ejecutarse y el problema puede ser que siempre me salga el mismo resultado y no alterne es decir siempre se abriría la misma aplicacion como consecuencia

**Si mañana el pipeline tuviera 50 comprobaciones en lugar de 2, ¿seguiría teniendo sentido mi estructura de código? ¿Qué cambiaría?**
*(escribe aquí)* 
si tuviera sentido porque habria que hacer lo mismo pero mas veces, lo que podria cambiar seria meter los 50 procesos en un array y usar un for para recorrerlos todos.

---

## 🧠 Qué he aprendido

> *(Completar al terminar. Redacta con tus palabras, no con las del enunciado.)*

- **`start()` vs `waitFor()`:** lanzar un proceso y esperarle son cosas distintas porque… el start empieza el proceso y el waitfor hace que esperen para que los dos procesos se den simultaneamente
- **Paralelismo real:** dos procesos corren "a la vez" porque… lo hago yo posible con el codigo y para que las condiciones que estoy poniendo se puedan cumplir
- **Código de salida:** que sea `0` o distinto de `0` significa… si es 0 significa que la ip existe es decir es correcta mientras si es distinto de 0 esto significa que la ip no existe o es incorrecta.
- **`&&` vs `||`:** elegí el operador que elegí porque… porque se me hacia mas sencillo usar el && porque significa "Y".
- **`IOException` vs ping fallido:** la diferencia entre ambos es…  ping fallido sigue corriendo el programa mientras que si el notepad.exe no funciona el programa no corre
- **Fiabilidad de mi decisión:** lo que mi programa decide se basa en… *(¿es fiable? ¿en qué casos podría equivocarse?)*
- es fiable y no podria equivocarse

---

## 🐞 Dificultades y cómo las resolví

> *(Completar antes de entregar. Reúne lo más importante de tu diario de decisiones.)*

**Dificultad 1:**
- Qué síntoma vi: como hacerlas paralelas
- Cuál era la causa real: poner un waitfor
- Cómo la encontré (¿apuntes? ¿documentación oficial? ¿depuración?): lo encontre en apuntes
- Cómo evitaré que me vuelva a pasar: no se me va a olvidar

**Dificultad 2:** *(opcional)*

---

## 🪞 Autoevaluación

Marca con honestidad, no con optimismo. Nadie te califica esta sección: es para ti y para que el profesor sepa dónde ayudarte.

| Puedo explicar a un compañero… | 🔴 No | 🟡 Más o menos | 🟢 Sí |
|---|:---:|:---:|:---:|
| Qué hace `ProcessBuilder` | ☐ | ☐ | ☐ | mas o menos
| Por qué `start()` no espera | ☐ | ☐ | ☐ | si
| Qué línea hace que mis procesos sean paralelos | ☐ | ☐ | ☐ | si
| Qué pasaría si moviera el `waitFor()` | ☐ | ☐ | ☐ | mas o menos
| Qué devuelve `waitFor()` y qué significa `0` | ☐ | ☐ | ☐ |si
| Por qué uso `&&` / `\|\|` en mi condición | ☐ | ☐ | ☐ | si
| Cuándo se entra en el `catch` de `IOException` | ☐ | ☐ | ☐ | mas o menos

**Mis predicciones del principio, ¿acerté?** *(explica por qué sí o por qué no)*
*(escribe aquí)*
si
**Lo que haría diferente si empezara de nuevo:**
*(escribe aquí)*
lo haria igual
**Lo que todavía no tengo claro y quiero preguntar en clase:**
*(escribe aquí)*
nada
---

## 🤝 Declaración de autoría y aprendizaje

Este reto **no permite herramientas de IA generativa**. Consulté únicamente: los apuntes de clase, la documentación oficial de Java e IntelliJ IDEA.

☐ Confirmo que he diseñado, programado y depurado este código aplicando mi propio razonamiento, y que puedo explicarlo línea a línea. confirmo

☐ Entiendo que durante la defensa el profesor me pedirá realizar pequeñas modificaciones sobre este código para comprobar mi comprensión del multiproceso. confirmo

---

## 📂 Estructura del proyecto

```
src/main/java/org/example/   → clase con el main (el pipeline de auditoría)
README.md                    → este documento
```

*(Ajusta la estructura a la de tu proyecto.)*

## 🔗 Enlace

<<<<<<< HEAD
GitHub: *(tu repositorio)*https://github.com/marcelocoronado1509-oss/psp_udit-/tree/main/reto2
=======
GitHub: *(tu repositorio)*https://github.com/marcelocoronado1509-oss/psp
>>>>>>> d97efac (Readme)
