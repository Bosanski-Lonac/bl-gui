package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class ServiceController {
    private ProcessBuilder[] processBuilders;
    private Process[] processes;

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
            File log = new File(direktorijum, "log" + i + ".txt");
            processBuilders[i].redirectOutput(log);
        }
    }

    public void start() throws IOException {
        int len = processBuilders.length;
        for(int i = 0; i < len; i++) {
            processes[i] = processBuilders[i].start();
            BufferedReader outputReader = new BufferedReader(new InputStreamReader(processes[i].getInputStream()));
            String line;
            do {
                line = outputReader.readLine();
            } while(!line.contains("Completed initialization in "))
            outputReader.close();
        }
    }

    public void stop() {
        for (Process process : processes) {
            process.destroy();
        }
    }
}
