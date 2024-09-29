# Solucion del problema de Dijksta para la cena de n filosofos

## Clase Filosofo (Philosopher)

La clase Filosofo extiende la clase Thread, lo que significa que cada filósofo es un hilo separado que puede ejecutarse simultáneamente con otros filósofos. Esto es necesario porque el problema de los filósofos que comen implica que varios filósofos compiten por recursos (forks) simultáneamente.

## Variables de instancia

### tenedorIzquierdo y tenedorDerecho

Estas dos variables de instancia son objetos Semaphore, que se utilizan para sincronizar el acceso a los forks. Un semáforo es una variable que controla el acceso a un recurso común por parte de varios hilos. En este caso, cada fork está representado por un semáforo, y un filósofo debe adquirir ambos semáforos para comer.

### id

Esta variable de instancia es un entero que identifica a cada filósofo. Se utiliza para imprimir mensajes que indican qué filósofo está pensando o comiendo.

### mutex

Este es un objeto Semaphore estático que sirve como mutex (bloqueo de exclusión mutua). Se utiliza para proteger la sección crítica del código donde el filósofo adquiere y libera los semáforos de fork.

## Constructor

El constructor inicializa el ID del filósofo y los dos semáforos de bifurcación.

java

Verificar
Editar
Copiar código

```java
public Filosofo(int id, Semáforo tenedorIzquierdo, Semáforo tenedorDerecho) {
    this.id = id;
    this.tenedorIzquierdo = tenedorIzquierdo;
    this.tenedorDerecho = tenedorDerecho;
}
```

## Métodos

### pensar() (think)

Este método privado simula al filósofo pensando. Imprime un mensaje indicando que el filósofo está pensando y luego duerme durante una duración aleatoria entre 0 y 1000 milisegundos.

Verificar
Editar
Copiar código

```java
private void pensar() throws InterruptedException {
    System.out.println("Filósofo " + id + " está pensando.");
    Thread.sleep((long) (Math.random() * 1000));
}
```

La declaración throws InterruptedException indica que este método puede lanzar una InterruptedException si el hilo se interrumpe mientras duerme.

### comer() (eat)

Este método privado simula al filósofo comiendo. Imprime un mensaje indicando que el filósofo está comiendo y luego duerme durante una duración aleatoria entre 0 y 1000 milisegundos.

Verificar
Editar
Copiar código

```java
private void comer() throws InterruptedException {
    System.out.println("Filósofo " + id + " está comiendo.");
    Thread.sleep((long) (Math.random() * 1000));
}
```

Similar a pensar(), este método puede lanzar una InterruptedException si el hilo se interrumpe mientras duerme.

### Método run()

El método run() es el punto de entrada del hilo. Es donde se define el comportamiento del filósofo.

Verificar
Editar
Copiar código

```java
@Override
public void run() {
    try {
        while (true) {
            pensar();
            mutex.acquire();
            tenedorIzquierdo.acquire();
            tenedorDerecho.acquire();
            mutex.release();
            // ...
        }
    } catch (InterruptedException e) {
        // Manejar interrupción
    }
}
```

Esto es lo que sucede en el método run():

1. El filósofo piensa (pensar()).
2. El filósofo intenta adquirir el mutex (mutex.acquire()). Si el mutex ya está en poder de otro filósofo, este filósofo se bloqueará hasta que se libere el mutex.
3. Una vez adquirido el mutex, el filósofo intenta adquirir ambos semáforos de bifurcación (tenedorIzquierdo.acquire() y tenedorDerecho.acquire()). Si cualquiera de las bifurcaciones ya está en poder de otro filósofo, este filósofo se bloqueará hasta que se libere la bifurcación.
4. Si el filósofo adquiere con éxito ambos forks, libera el mutex (mutex.release()).
5. El filósofo come (comer()).
6. El filósofo libera los forks (tenedorIzquierdo.release() y tenedorDerecho.release()).
7. El filósofo repite el ciclo (pensar, adquirir mutex y forks, comer, liberar forks y mutex).
   El bloque try-catch se utiliza para manejar InterruptedExceptions que pueden ocurrir cuando el hilo se interrumpe mientras está dormido o esperando un semáforo.

#### ¿Por qué estos métodos y variables?

La clase Filosofo está diseñada para modelar el comportamiento de un filósofo en el problema de los filósofos que comen. Los métodos y variables se eligen para representar los aspectos clave de este problema:

- Los métodos pensar() y comer() simulan los comportamientos de pensamiento y alimentación del filósofo.
- Los semáforos tenedorIzquierdo y tenedorDerecho representan los forks, que son recursos compartidos a los que se debe acceder de forma sincrónica.
- El semáforo mutex garantiza que solo un filósofo pueda acceder a la sección crítica del código (adquisición y liberación de bifurcaciones) a la vez.
