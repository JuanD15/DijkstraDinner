package model;
import java.util.concurrent.Semaphore;

public class Filosofo extends Thread {
    private Semaphore tenedorIzquierdo;
    private Semaphore tenedorDerecho;
    private int id;
    private static Semaphore mutex = new Semaphore(1);

    public Filosofo(int id, Semaphore tenedorIzquierdo, Semaphore tenedorDerecho) {
        this.id = id;
        this.tenedorIzquierdo = tenedorIzquierdo;
        this.tenedorDerecho = tenedorDerecho;
    }

    private void pensar() throws InterruptedException {
        System.out.println("Filósofo " + id + " está pensando.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void comer() throws InterruptedException {
        System.out.println("Filósofo " + id + " está comiendo.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    @Override
    public void run() {
        try {
            while (true) {
                pensar();
                mutex.acquire();
                tenedorIzquierdo.acquire();
                tenedorDerecho.acquire();
                mutex.release();
                comer();
                tenedorIzquierdo.release();
                tenedorDerecho.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}