package controller;
import java.util.concurrent.Semaphore;
import model.Filosofo;
import view.ViewConsole;

public class CenaFilosofos {
    private ViewConsole vconsole;
    
    public CenaFilosofos() {
        vconsole = new ViewConsole();
        run();
    }

    private void run(){
        int n = vconsole.readInt("Ingrese el numero de filosofos");
        Semaphore[] tenedores = new Semaphore[n];
        Filosofo[] filosofos = new Filosofo[n];
    
        for (int i = 0; i < n; i++) {
            tenedores[i] = new Semaphore(1);
        }
    
        for (int i = 0; i < n; i++) {
            filosofos[i] = new Filosofo(i, tenedores[i], tenedores[(i + 1) % n]);
            filosofos[i].start();
        }

    }

    public static void main(String[] args) {
        new CenaFilosofos();
    }
}