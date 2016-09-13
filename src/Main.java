import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.nio.channels.FileChannel;
import java.util.Scanner;
import java.util.prefs.Preferences;

import static java.lang.System.exit;

/**
 * Created by sanak on 25.03.2016.
 */
public class Main {
    public static final String OS_NAME = System.getProperty("os.name");
    public static final String OS_VERSION = System.getProperty("os.version");
    public static final String USER_NAME = System.getProperty("user.name");
    public static final String WIN_DIR = System.getenv("windir");
    public static final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    public static void copyFile (String nameFrom, String nameTo) throws Exception{

        FileChannel source = new FileInputStream(new File(nameFrom)).getChannel();
        FileChannel dest = new FileOutputStream(new File(nameTo)).getChannel();
        try {
            source.transferTo(0, source.size(), dest);
        } finally {
            source.close();
            dest.close();
        }
        System.out.println("Installation completed");
    }


    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        System.out.println("OS: ");
        System.out.print(OS_NAME);

        System.out.println("\n\nUser's name: ");
        System.out.print(USER_NAME);

        System.out.println(" \n\nScreen size: " + WIDTH + " px " + HEIGHT + " px");

        String hostname = "Unknown";

        InetAddress addr;
        addr = InetAddress.getLocalHost();
        hostname = addr.getHostName();

        System.out.println("\nPC's name: " + hostname);
        System.out.println("\nWindows directory: " + WIN_DIR);

        System.out.println("\nEnter secret key: ");
        String userKey = scan.next();

        String globalInfo = OS_NAME + OS_VERSION + USER_NAME + WIN_DIR + WIDTH + hostname + userKey;
        globalInfo.hashCode();
        File[] listDisk = File.listRoots();
        for (File f:listDisk) {
            System.out.println("\nPath = " + f.getPath());
            System.out.println("Volume = " + f.getTotalSpace()/1000000000 + " Gb");
        }

        Preferences userPrefs = Preferences.userRoot().node("Sanak");
        userPrefs.putInt("SIGNATURE", globalInfo.hashCode());

        System.out.print("\nEnter installation path: ");
        String directory = scan.next();
        String fileOut = directory + "/program.jar";
        String fileName = "Soft_Sec_lab_1.jar";
        if (!new File(fileName).exists()) {
            System.out.println("Installation packages are missed\nExiting...");
            exit(1);
        }
        copyFile(fileName, fileOut);
    }
}
