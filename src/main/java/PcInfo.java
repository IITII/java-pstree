public class PcInfo {
    private int pid, ppid;
    private String name;

    public PcInfo(int pid, int ppid, String name) {
        this.pid = pid;
        this.ppid = ppid;
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPpid() {
        return ppid;
    }

    public void setPpid(int ppid) {
        this.ppid = ppid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", name, pid);
    }
}
