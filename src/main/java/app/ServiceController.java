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
    private Thread serviceStarter;

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
        String[] servers = new String[]{"EurekaServiceApplication", "KorisnickiServisRun", "GatewayServiceApplication",  "ServisZaLetoveRun", "ServisZaAvionskeKarteRun"};
        int len = processBuilders.length;
        double increment = 1.0/len;
        serviceStarter = new Thread(() -> {
            try {
                for(int i = 0; i < len; i++) {
                    processes[i] = processBuilders[i].start();
                    BufferedReader outputReader = new BufferedReader(new InputStreamReader(processes[i].getInputStream()));
                    String line;
                    do {
                        line = outputReader.readLine();
                        if (line != null) {
                            System.out.println(line);
                            if (line.toLowerCase().contains(("application failed to start"))) {
                                Platform.runLater(progressable::fail);
                                return;
                            }
                        }
                    } while(line == null || !line.contains("Started " + servers[i] + " in"));
                    outputReader.close();
                    Platform.runLater(() -> progressable.addProgress(increment));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        serviceStarter.start();
    }

    public void stop() {
        if (serviceStarter != null) {
            serviceStarter.interrupt();
        }
        for (Process process : processes) {
            if (process != null) {
                process.destroy();
            }
        }
    }
}
