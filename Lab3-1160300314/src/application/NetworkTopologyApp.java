package application;

import exception.InputFileAgainException;
import factory.GraphFactory;
import graph.Graph;
import factory.LoggerFactory;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.IOException;

/**
 * Network Topology GUI Application
 *
 * @author Zhu Mingyan
 */
public class NetworkTopologyApp {
    /*
   AF: Represents a GUI Application of NetworkTopologyGraph
   RI: all fields should be non-null
       instance of SubClass of GraphGUI should connected with Graph instance
   Safety for Rep Exposure:
       All fields are modified by key word private and there is no other setter function except Constructor
    */
    private Graph graph;
    private NetworkTopologyAppGUI graphGUI;
    private static final Logger LOGGER = LoggerFactory.createLogger(NetworkTopologyApp.class);

    /**
     * Constructor of NetworkTopologyApp with params file
     */
    public NetworkTopologyApp() {
        String filePath = JOptionPane.showInputDialog("Please input the file path",
                "src/txt/inputNetworkTopologyWithWirelessRouter.txt");
        while (true) {
            try {
                LOGGER.debug("Read file from " + '\"' + filePath + '\"');
                this.graph = GraphFactory.createGraph(filePath);
                this.graphGUI = new NetworkTopologyAppGUI(graph);
                break;
            } catch (IOException | InputFileAgainException e) {
                LOGGER.error(e.getMessage(), e);
                filePath = JOptionPane.showInputDialog("Please input the file path",
                        "src/txt/inputNetworkTopologyWithWirelessRouter.txt");
//                System.out.println("The path input before with errors, please input another file path!");
//                System.out.println("such as \"src/txt/inputSocialNetwork.txt\"");
//                System.out.println("Attention do not enter file path with any spaces!");
//                Scanner in = new Scanner(System.in);
//                filePath = in.next();
                LOGGER.info("Exchange file path to " + filePath);
            }
        }
    }

    /**
     * Make the GUI App visible
     */
    public void visible() {
        this.graphGUI.visible();
    }

    public static void main(String[] args) {
        NetworkTopologyApp networkTopologyApp = new NetworkTopologyApp();
        networkTopologyApp.visible();
    }
}
