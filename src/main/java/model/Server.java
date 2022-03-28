package model;

import view.ViewSimulator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {
    public int numberOfClients;
    public int numberOfQueues;
    public int simulationInterval;
    public int currentTime = 0;
    public int minimumArrival;
    public int maximumArrival;
    public int minimumService;
    public int maximumService;
    int queue = 0;
    public int averageWaitingTime;
    public int averageClients=0;
    public int getNumberOfClients() {
        return numberOfClients;
    }

    public int getNumberOfQueues() {
        return numberOfQueues;
    }

    public int getSimulationInterval() {
        return simulationInterval;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getMinimumArrival() {
        return minimumArrival;
    }

    public int getMaximumArrival() {
        return maximumArrival;
    }

    public int getMinimumService() {
        return minimumService;
    }

    public int getMaximumService() {
        return maximumService;
    }

    public int getQueue() {
        return queue;
    }

    public int addTask(SimulationManager simulationManager, List<Queue> queues, Task c ) {
        queues.get(simulationManager.getCoadaBuna()).addNewClient(c);
        return queues.size();
    }
    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public void setNumberOfQueues(int numberOfQueues) {
        this.numberOfQueues = numberOfQueues;
    }

    public void setSimulationInterval(int simulationInterval) {
        this.simulationInterval = simulationInterval;
    }

    public void setMinimumArrival(int minimumArrival) {
        this.minimumArrival = minimumArrival;
    }

    public void setMaximumArrival(int maximumArrival) {
        this.maximumArrival = maximumArrival;
    }

    public void setMinimumService(int minimumService) {
        this.minimumService = minimumService;
    }

    public void setMaximumService(int maximumService) {
        this.maximumService = maximumService;
    }

    public static String prPrint(String x) {
        return "<html>" + x.replaceAll("\n", "<br>");
    }
public void verificari()
{
    if(minimumArrival>maximumArrival)
        System.out.println("Nu ati introdus date bune pentru timpul de Arrival");
    else if(minimumService>maximumService)
        System.out.println("Nu ati introdus date bune pentru timpul de Service");

}
    public void determinareAverageTime(SimulationManager casier) {
        int waitingCurrentTime = 0;
     int i=0;
        while(i < casier.getQueueList().size() ){
                averageClients++;
                waitingCurrentTime += casier.getQueueList().get(i).waitingTime.get();
            }
            i++;
        double result=waitingCurrentTime/averageClients;
    }
    //afisarea rezultatelor in interfata
    public String toString(List<Queue> queues, int nrOfQueues, int time, ArrayList<Task> waitingClients, int nrOfClients, FileWriter fisier) {
        String afisare= "";
        //incept sa concatenez la rezultat
        afisare = afisare + "Time: " + time + "\n";
        afisare = afisare + "Waiting clients: ";
              try {
                  fisier.write("Time: " + time + "\n");
                  fisier.write("Waiting clients: ");
              } catch (IOException e) {
                  e.printStackTrace();
                  System.out.println("Eroare:");
              }
              for(int j=0; j<waitingClients.size(); j++){
                  Task client = waitingClients.get(j);
                      try {
                          fisier.write("("+client.getId()+" "+client.getArrivingTime()+" "+client.getServingTime()+")");
                      } catch (IOException e) {
                          e.printStackTrace();
                          System.out.println("Eroare:");
                      }
            afisare = afisare + "(";
                      afisare=afisare+client.getId();
                      afisare=afisare +" "+client.getArrivingTime();
                      afisare=afisare +" "+client.getServingTime();
                      afisare=afisare +")";
        }
        afisare = afisare + "\n";
        for(int i=1;i<=nrOfQueues;i++){
                   try {fisier.write("Queue " + i + ":");}
                   catch (IOException e) {
                       e.printStackTrace();
                       System.out.println("Eroare:");}
            afisare = afisare + "Queue " + i + ":";
            if(queues.get(i-1).getSize()==0) {
                           try {fisier.write("closed");}
                           catch (IOException e) { e.printStackTrace();
                               System.out.println("Eroare:");}
                afisare = afisare + "closed";
            }
            else {
                for(Task client: queues.get(i-1).listaClienti) {
                    int servTime;
                    servTime = client.getServingTime();
                    //  servTime += 1;
                    if(servTime>0)
                                  { try {
                                      fisier.write("("+client.getId()+" "+client.getArrivingTime()+" "+ servTime +")");
                                 } catch (IOException e) {
                                     e.printStackTrace();    System.out.println("Eroare:");
                                 }
                        afisare = afisare + "(";
                                      afisare=afisare+client.getId();
                                      afisare=afisare +" "+client.getArrivingTime();
                                      afisare=afisare +" "+ servTime;
                                      afisare=afisare +")";
                             }
                    else if(servTime==0 && queues.get(i-1).getSize()==1)
                        afisare = afisare + "closed";
                }
            }
                      try {
                          fisier.write("\n");
                   } catch (IOException e) {
                        e.printStackTrace();
                          System.out.println("Eroare:");
                  }
            afisare = afisare + "\n";
        }
        return afisare;
    }

    //scrierea in fisierul de iesire/afisarea in interfata
    public synchronized void run() {
        ViewSimulator viewSimulator=new ViewSimulator();
        //trebuie sa folosec try deoarece FileWriter arunca exceptii
        try {
            FileWriter  fisier = new FileWriter("src\\main\\resources\\out_1.txt");
            SimulationManager  casier = new SimulationManager(numberOfClients, numberOfQueues, simulationInterval, minimumArrival, maximumArrival, minimumService, maximumService);
          //pentru fiecare timp de la 0->maxim
            for (currentTime=0;currentTime <= casier.getSimulationInterval();currentTime++) {
                try {
                    if  (casier.getListOfWaitingClients().size() != 0)  //daca coada nu e goala
                        //daca e timpul curent
                        if(casier.getListOfWaitingClients().get(0).getArrivingTime() == currentTime) {
                        while (casier.getListOfWaitingClients().get(0).getArrivingTime() == currentTime ) {
                            addTask(casier, casier.lista, casier.listOfWaitingClients.get(0));  //adaug un nou client
                            casier.getListOfWaitingClients().remove(0); //sterg clientul respectiv din coasa
                            if(casier.getListOfWaitingClients().size() == 0)
                                break;
                        }
                    }
                    Thread.sleep(1000);
                    //pun la "somn" thread-ul
                    viewSimulator.text.setText(prPrint(toString(casier.getQueueList(),casier.getNrOfQueues(),currentTime,casier.getListOfWaitingClients(),casier.getNrOfClients(),fisier)));
                }catch(InterruptedException e) {
                }
            }
            String rez = prPrint(toString(casier.getQueueList(),casier.getNrOfQueues(),currentTime,casier.getListOfWaitingClients(),casier.getNrOfClients(),fisier));
          //scriu in interfata grafica rezultatul
            viewSimulator.text.setText(rez);
            //inchid fisierul in care am scris
            fisier.close();
        } catch (FileNotFoundException e1) {} catch (IOException e1) {
            e1.printStackTrace();
            System.out.println("Eroare:");
        }
    }

}
