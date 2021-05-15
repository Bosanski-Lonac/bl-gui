package app;

import java.io.File;
import java.io.IOException;

public class ServiceController {
    private ProcessBuilder[] processBuilders;
    private Process[] processes;
    private String[] paths;

    public ServiceController() throws IOException {
        String direktorijum = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        direktorijum = direktorijum.substring(0, direktorijum.lastIndexOf("/") + 1);
        System.out.println("DIREKTORIJUM: " + direktorijum);

        paths = new String[]{"eureka.jar", "gateway.jar", "ks.jar", "szl.jar", "szak.jar"};
        int len = paths.length;
        processBuilders = new ProcessBuilder[len];
        processes = new Process[len];

        for (int i = 0; i < len; i++) {
            processBuilders[i] = new ProcessBuilder("java", "-jar", paths[i]);
            processBuilders[i].directory(new File(direktorijum));
            processes[i] = processBuilders[i].start();
            System.out.println(processes[i].toString());
        }
    }

    public void stop() {
        for (Process process : processes) {
            process.destroy();
        }
    }
}
