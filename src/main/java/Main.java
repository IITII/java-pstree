import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private final static Map<Integer, PcInfo> pidMap = new HashMap<>();
    private final static Map<Integer, ArrayList<PcInfo>> ppidMap = new HashMap<>();

    public static void main(String[] args) {
        PcReader pcReader = new PcReader();
        ArrayList<PcInfo> arrayList = pcReader.read();
        for (PcInfo e : arrayList) {
            // 一个进程只会有一个 Pid
            pidMap.put(e.getPid(), e);
            // 一个 PPid 可以有多个进程
            if (ppidMap.get(e.getPpid()) == null) {
                ArrayList<PcInfo> tmp = new ArrayList<>();
                tmp.add(e);
                ppidMap.put(e.getPpid(), tmp);
            } else {
                ArrayList<PcInfo> tmp = ppidMap.get(e.getPpid());
                tmp.add(e);
                ppidMap.put(e.getPpid(), tmp);
            }
        }
        TreeNode<PcInfo> pcInfoTree = getTree(new TreeNode<>(pidMap.get(1)), ppidMap.get(1));
        for (TreeNode<PcInfo> node : pcInfoTree) {
            String indent = keepIndent(node.getLevel());
            System.out.println(indent + node.data.toString());
        }
//        try {
//            Process process = Runtime.getRuntime().exec(args[0]);
//            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line = null;
//            StringBuilder cmdout = new StringBuilder();
//                while ((line = br.readLine()) != null) {
//                    cmdout.append(line)
//                            .append("\n");
//                }
//            System.out.println(cmdout.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private static String keepIndent(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append(
                    i + 1 == depth ? "\t-" : "\t"
            );
        }
        return sb.toString();
    }

    private static TreeNode<PcInfo> getTree(
            TreeNode<PcInfo> treeNode,
            ArrayList<PcInfo> pcInfoArrayList
    ) {
        for (PcInfo pcInfo : pcInfoArrayList) {
            // 判断当前进程有无子进程
            if (ppidMap.get(pcInfo.getPid()) == null) {
                //无子进程
                treeNode.addChild(pcInfo);
            } else {
                TreeNode<PcInfo> tmp = treeNode.addChild(pcInfo);
                getTree(tmp, ppidMap.get(pcInfo.getPid()));
            }
        }
        return treeNode;
    }
}
