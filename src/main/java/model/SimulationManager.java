package model;

import view.View;
import view.ViewSimulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;

public class SimulationManager {
    private  int numberOfClients;
    private  int numberOfQueues;
    private  int simulationInterval;
    private  int minimumArrivingTime;
    private  int maximumArrivingTime;
    private  int minimumWaitingTime;
    private  int maximumWaitingTime;
    public ArrayList<Task> listOfWaitingClients = new ArrayList<Task>();
    public List<Queue> lista = Collections.synchronizedList(new ArrayList<Queue>());

    //constructor
    public SimulationManager(int nrClients, int nrQueues,int simulationInterval, int minArrivingTime, int maxArrivingTime, int minServiceTime, int maxServiceTime){
        this.numberOfClients =nrClients;
        this.numberOfQueues =nrQueues;
        this.simulationInterval=simulationInterval;
        this.minimumArrivingTime =minArrivingTime;
        this.maximumArrivingTime =maxArrivingTime;
        this.minimumWaitingTime =minServiceTime;
        this.maximumWaitingTime =maxServiceTime;
        this.listOfWaitingClients =(ArrayList<Task>) generateClients(this.numberOfClients,this.numberOfQueues,this.minimumArrivingTime,this.maximumArrivingTime,this.minimumWaitingTime,this.maximumWaitingTime,this.listOfWaitingClients);
        initializareCozi(lista, numberOfQueues,simulationInterval);
    }
    public SimulationManager(ViewSimulator view){

    }
    //metoda de generare random a clientilor
    public List<Task> generateClients(int nrClients, int nrQueues, int minArrivingTime, int maxArrivingTime, int minWaitingTime, int maxWaitingTime, ArrayList<Task> waitingClients) {
     //creez nrClienti de clienti
       for(int i=0;i<nrClients;i++) {
           Task client = new Task();
           //am nevoie de doua variabile de random pentru timpul de servire si cel de asteptare
           Random random1 = new Random();
           Random random2 = new Random();
           client.setId(i+1);//ii setez id-ul in ordinea de la for
          int serviceTime = this.minimumWaitingTime + random2.nextInt(this.maximumWaitingTime -this.minimumWaitingTime +1);
            client.setServingTime(serviceTime);
            int arriveTime = this.minimumArrivingTime +random1.nextInt(this.maximumArrivingTime -this.minimumArrivingTime +1);
           client.setArrivingTime(arriveTime);
            this.listOfWaitingClients.add(client);
        }
        //ii sortez dupa timpul de sosire
        for(int j=0;j<this.listOfWaitingClients.size();j++)
        {
            for(int k=j+1;k<this.listOfWaitingClients.size();k++)
                if(this.listOfWaitingClients.get(j).getArrivingTime()>this.listOfWaitingClients.get(k).getArrivingTime())
                    Collections.swap(this.listOfWaitingClients,j,k);
        }
        return waitingClients;
    }

    //metoda pentru a gasi coada cea mai buna
    public synchronized Integer getCoadaBuna()
    {
        int gasit=0,timp = 0,minS = 0,j=0;
        int minQueue=this.lista.get(0).getSize(); //retin size-ul primei cozi
        //daca cumva o coada este goala,ea e cea minima si ii returnez numarul;
        while( j<this.numberOfQueues) {
            if (this.lista.get(j).getSize() == 0)
                return j;
            j++;
        }
        List<Task> clientList = new ArrayList<>(this.lista.get(0).listaClienti);
        LinkedBlockingDeque<Task> coadaMinima = this.lista.get(0).listaClienti;
        List<Task>   listOfClients2 = new ArrayList<>(this.lista.get(0).listaClienti);
        for(int k=0;k< this.lista.get(0).listaClienti.size();k++)
            minS += clientList.get(k).getServingTime();  //construiesc timpul minim de asteptare

        for(int i=0;i < this.numberOfQueues;i++) {
            //daca gasesc o coada mai goala decat prima
            if(this.lista.get(i).getSize() < minQueue)
            {
                minQueue = this.lista.get(i).getSize();    //ii retin sizeul
                coadaMinima = this.lista.get(i).listaClienti; //asta e coada minima
                for(int l=0; l< this.lista.get(i).listaClienti.size();l++)
                    minS = minS + listOfClients2.get(l).getServingTime(); //construiesc timpul minim de asteptare
                gasit = i;
            }
            //daca gasesc doua cozi cu acelasi numar de clienti deja asteptand
            if(this.lista.get(i).getSize() == minQueue)
            {timp = 0;
                for(int n=0;n < this.lista.get(i).listaClienti.size();n++)
                    timp = timp + listOfClients2.get(n).getServingTime();   //construiesc timpul minim de asteptare
            //acum verific care timp e mai bun
                if(timp < minS) {
                    minQueue = this.lista.get(i).getSize();
                    minS = timp;
                    gasit = i;}}}
        return gasit;
    }

    public void initializareCozi(List<Queue> queues, int queuesNumber, int time) {
        for(int i=0;i< queuesNumber;i++){
          Queue queue = new Queue(time);
            this.lista.add(queue);
        }
        //pentru fiecare coada noua,pornesc un thread nou
        for(int j=0;j< queuesNumber;j++)
            this.lista.get(j).startCoada();
    }

    public int getSimulationInterval() {
        return this.simulationInterval;
    }
    public int getNrOfClients() {
        return this.numberOfClients;
    }

    public ArrayList<Task> getListOfWaitingClients(){
        return this.listOfWaitingClients;
    }

    public int getNrOfQueues() {
        return this.numberOfQueues;
    }

    public List<Queue> getQueueList(){
        return this.lista;
    }

        public static void main(String[] args) throws FileNotFoundException {
            final View myFrame = new View();
        }

}