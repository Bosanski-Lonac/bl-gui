package app;

import gui.komponente.IProgressable;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServiceController {
    private ProcessBuilder[] processBuilders;
    private Process[] processes;
    private Thread eurekaStarter;

    public ServiceController() {
        String direktorijumPutanja = System.getProperty ("user.dir");
        File direktorijum = new File(direktorijumPutanja);

        String[] paths = new String[]{"eureka.jar", "ks.jar", "gateway.jar", "szl.jar", "szak.jar"};
        int len = paths.length;
        processBuilders = new ProcessBuilder[len];
        processes = new Process[len];

        for (int i = 0; i < len; i++) {
            processBuilders[i] = new ProcessBuilder("java", "-jar", paths[i]);
            processBuilders[i].directory(direktorijum);
        }
    }

    public void start(IProgressable progressable) {
        int len = processBuilders.length;
        double increment = 1.0 / (len * 2);
        String[] servers = new String[]{"EurekaServiceApplication", "KorisnickiServisRun", "GatewayServiceApplication",  "ServisZaLetoveRun", "ServisZaAvionskeKarteRun"};
        //String[] services = new String[] {"BL-KORISNICKI-SERVIS", "API-GATEWAY", "BL-SERVIS-ZA-LETOVE", "BL-SERVIS-ZA-AVIONSKE-KARTE"};
        Thread serviceStarter = new Thread(() -> {
            try {
                for(int i = 1; i < len; i++) {
                    processes[i] = processBuilders[i].start();
                    BufferedReader outputReader = new BufferedReader(new InputStreamReader(processes[i].getInputStream()));
                    String line;
                    while((line = outputReader.readLine()) == null ||
                            !line.contains("Started " + servers[i] + " in ")) {
                        if(line != null &&
                                line.toLowerCase().contains("application failed to start")) {
                            Platform.runLater(() -> progressable.finish(false));
                            return;
                        }
                    }
                    outputReader.close();
                    Platform.runLater(() -> progressable.addProgress(increment));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serviceStarter.setDaemon(true);
        eurekaStarter = new Thread(() -> {
            try {
                processes[0] = processBuilders[0].start();
                BufferedReader eurekaReader = new BufferedReader(new InputStreamReader(processes[0].getInputStream()));
                int registeredServices = 0;
                while(!Thread.interrupted() && registeredServices < 4) {
                    String line = eurekaReader.readLine();
                    if(line != null) {
                        if (line.toLowerCase().contains("application failed to start")) {
                            Platform.runLater(() -> progressable.finish(false));
                            return;
                        } else if(line.contains("Started EurekaServiceApplication in ")) {
                            serviceStarter.start();
                            Platform.runLater(() -> progressable.addProgress(increment * 2));
                        } else if(line.contains("Registered instance ")) {
                            registeredServices++;
                            Platform.runLater(() -> progressable.addProgress(increment));
                        }
                    }
                }
                eurekaReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        eurekaStarter.setDaemon(true);
        eurekaStarter.start();
    }

    public void stop() {
        if(eurekaStarter != null) {
            eurekaStarter.interrupt();
        }
        for (Process process : processes) {
            if (process != null) {
                process.destroy();
            }
        }
    }
}
