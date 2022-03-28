package model;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue extends Thread{
    public AtomicInteger waitingTime;
    public int numberOfClients;
    public int timpOprire;
    public int t= 0;
    public LinkedBlockingDeque<Task> listaClienti = new LinkedBlockingDeque<Task>(2000);

    public AtomicInteger getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(AtomicInteger waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public int getTimpOprire() {
        return timpOprire;
    }

    public void setTimpOprire(int timpOprire) {
        this.timpOprire = timpOprire;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public LinkedBlockingDeque<Task> getListaClienti() {
        return listaClienti;
    }

    public void setListaClienti(LinkedBlockingDeque<Task> listaClienti) {
        this.listaClienti = listaClienti;
    }

    public Queue(Integer oprire) {
        this.timpOprire = oprire;
        waitingTime = new AtomicInteger(0);
    }

    //stergerea unui client din coada
    public synchronized void stergereClient() {
        this.listaClienti.remove(0);  //il sterg din lista
        this.numberOfClients--;  //scad numarul de clienti ai cozii
        notifyAll();
    }
    public void startCoada() {
     Thread thread = new Thread(this);  //initializez un nou thread
        thread.start(); //ii dau start
    }
    //adaugarea unui client in coada
    public void addNewClient(Task client) {
        this.numberOfClients++;  //cresc numarul de clienti ai cozii respective
        if(this.numberOfClients > 1)
            client.setServingTime(client.getServingTime()-1);
        this.listaClienti.add(client);  //adaug clientul nou
        waitingTime.addAndGet(client.getServingTime());  //modific waitingTime-ul global
    }

    public int getSize() {
        return this.listaClienti.size();
    }

    public synchronized void run()  {
        while( (t < timpOprire) || (listaClienti.size() != 0) ) {
            try {
                Thread.sleep(1500);
                if(this.listaClienti.size() != 0) {
                    //pentru ficare client modific timpul de servire
                    for(Task client: this.listaClienti)
                    {int i=0; int  s = client.getServingTime();
                    while(i <= s){
                        client.setServingTime(client.getServingTime()-1);//decrementez timpul de asteptare la coada
                        Thread.sleep(1000);  //opresc acest thread timp de o secunda
                        t++;  i++;
                        this.waitingTime.decrementAndGet();
                    }
                        this.stergereClient();} //am terminat cu el
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Eroare:");
            }
        }
    }
}