package view;

import model.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame{

    public JLabel numarClienti;
    public JTextField numarClientiText=new JTextField(20);;
    public JLabel numarCozi;
    public JTextField numarCoziText=new JTextField(20);;
    public JLabel intervalSimulare;
    public JTextField intervalSimulareText=new JTextField(20);;
    public JLabel minArrival;
    public JTextField minArrivalText=new JTextField(20);;
    public JLabel maxArrival;
    public JTextField maxArrivalText=new JTextField(20);;
    public JLabel minService;
    public JTextField minServiceText=new JTextField(20);;
    public JLabel maxService;
    public JTextField maxServiceText=new JTextField(20);;
    public JButton startButton;
    private JTextArea area2;

    public View() {
        this.setSize(700, 600);
        this.setTitle("QUEUES SIMULATOR");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        //setez backgoundul frame-ului
        this.getContentPane().setBackground(Color.pink);

        //label pt introducerea numarului de clienti
        numarClienti = new JLabel("Numar de clienti:");
        numarClienti.setBounds(20,10,200,150);
        this.getContentPane().add(numarClienti);
        numarClientiText.setBounds(20,100,230,30);
        this.getContentPane().add(numarClientiText);

        //label pt introducerea numarului de cozi
        numarCozi = new JLabel("Numar de cozi:");
        numarCozi.setBounds(20,80,240,150);
        this.getContentPane().add(numarCozi);
        numarCoziText.setBounds(20,170,230,30);
        this.getContentPane().add(numarCoziText);

        //label pt introducerea SIMULATION INTERVAL
        intervalSimulare = new JLabel("Interval de simulare:");
        intervalSimulare.setBounds(20,150,240,150);
        this.getContentPane().add(intervalSimulare);
        intervalSimulareText.setBounds(20,240,230,30);
        this.getContentPane().add(intervalSimulareText);

        //label pt introducerea MINIMUM ARRIVAL TIME
        minArrival = new JLabel("Minimum arrival time:");
        minArrival.setBounds(20,220,240,150);
        this.getContentPane().add(minArrival);
        minArrivalText.setBounds(20,310,230,30);
        this.getContentPane().add(minArrivalText);

        //label pt introducerea MAXIMUM ARRIVAL TIME
        maxArrival = new JLabel("Maximum arrival time:");
        maxArrival.setBounds(20,290,240,150);
        this.getContentPane().add(maxArrival);
        maxArrivalText.setBounds(20,380,230,30);
        this.getContentPane().add(maxArrivalText);

        //label pt introducerea MINIMUL SERVICE TIME
        minService = new JLabel("Minimum service time:");
        minService.setBounds(20,360,240,150);
        this.getContentPane().add(minService);
        minServiceText.setBounds(20,450,230,30);
        this.getContentPane().add(minServiceText);

        //label pt introducerea MAXIMUM SERVICE TIME
        maxService = new JLabel("Maximum service time:");
        maxService.setBounds(20,430,240,150);
        this.getContentPane().add(maxService);
        maxServiceText.setBounds(20,520,230,30);
        this.getContentPane().add(maxServiceText);

        //buton de START
        startButton=new JButton("INCEPE!");
        startButton.setBackground(Color.WHITE);
        startButton.setBounds(400,100,170,30);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == startButton){
                    Server server = new Server();
                    server.setNumberOfClients(getNrOfClientsText());
                    server.setNumberOfQueues(getNrOfQueuesText());
                    server.setSimulationInterval(getSimulationIntervalText());
                    server.setMinimumArrival(getMinArrivalText());
                    server.setMaximumArrival(getMaxArrivalText());
                    server.setMinimumService(getMinServiceText());
                    server.setMaximumService(getMaxServiceText());
                    Thread t = new Thread(server);
                    t.start();
                }
            }
        });

        this.add(startButton);

        this.setVisible(true);
    }


    public int getNrOfClientsText(){
        String s = numarClientiText.getText();
        int nr = Integer.parseInt(s);
        return nr;
    }

    public int getNrOfQueuesText(){
        String s = numarCoziText.getText();
        int nr = Integer.parseInt(s);
        return nr;
    }

    public int getSimulationIntervalText(){
        String s = intervalSimulareText.getText();
        int nr = Integer.parseInt(s);
        return nr;
    }

    public int getMinArrivalText(){
        String s = minArrivalText.getText();
        int nr = Integer.parseInt(s);
        return nr;
    }

    public int getMaxArrivalText(){
        String s = maxArrivalText.getText();
        int nr = Integer.parseInt(s);
        return nr;
    }

    public int getMinServiceText(){
        String s = minServiceText.getText();
        int nr = Integer.parseInt(s);
        return nr;
    }

    public int getMaxServiceText(){
        String s = maxServiceText.getText();
        int nr = Integer.parseInt(s);
        return nr;
    }
}