package app;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import javafx.application.HostServices;

public class NotificationManager {
    private static final String LINK_ANKETE = "https://forms.gle/7wv55GstP45v6ZjS6";
    private static final SystemTray tray = SystemTray.getSystemTray();
    private TrayIcon trayIcon;
    private Timer notificationTimer = new Timer();

    public static boolean isSupported() {
        return SystemTray.isSupported();
    }

    public NotificationManager(HostServices hostServices) {
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

        trayIcon = new TrayIcon(image, "Bosanski Lonac");
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(event -> hostServices.showDocument(LINK_ANKETE));
        notificationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> displayQuestionnaire());
            }
        }, 10000); // 300000 milliseconds = 5 minutes
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            stop();
        }
    }

    public void displayQuestionnaire() {
        trayIcon.displayMessage("Anketa", "Popunite anketu i recite nam šta mislite o našoj aplikaciji!", MessageType.INFO);
    }

    public void stop() {
        notificationTimer.cancel();
        tray.remove(trayIcon);
    }
}
