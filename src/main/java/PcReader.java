import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class PcReader {
    private final static String PROC = "/proc";

    public ArrayList<PcInfo> read() {
        ArrayList<PcInfo> arrayList = new ArrayList<>();
        for (File file : Objects.requireNonNull(new File(PROC).listFiles())) {
            if (file.getName().matches("\\d+")) {
                try {
                    Scanner scanner = new Scanner(new FileInputStream(file.getAbsolutePath() + "/status"));
                    String name = null, pid = null, ppid = null;
                    while (scanner.hasNextLine()) {
                        String tmp = scanner.nextLine();
                        if (tmp.matches("Name[\\s\\S]+")) {
                            name = tmp;
                        } else if (tmp.matches("Pid[\\s\\S]+")) {
                            pid = tmp;
                        } else if (tmp.matches("PPid[\\s\\S]+")) {
                            ppid = tmp;
                        }
                    }
                    if (name == null || pid == null || ppid == null) {
                        System.err.println("Error file: " + file.getAbsolutePath());
                        continue;
                    }
                    name = name.split("\\t+")[1];
                    pid = pid.split("\\t+")[1];
                    ppid = ppid.split("\\t+")[1];
                    arrayList.add(
                            new PcInfo(
                                    Integer.parseInt(pid),
                                    Integer.parseInt(ppid),
                                    name
                            )
                    );

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return arrayList;
    }
}
