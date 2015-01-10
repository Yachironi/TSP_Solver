/* This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
package Graphic;

import com.mxgraph.layout.*;
import com.mxgraph.swing.*;

import java.awt.*;

import javax.swing.*;

import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;


/**
 * A demo applet that shows how to use JGraphX to visualize JGraphT graphs.
 * Applet based on JGraphAdapterDemo.
 *
 * @since July 9, 2013
 */
public class JGraphXAdapterDemo
    extends JApplet
{
    

    private static final long serialVersionUID = 2202072534703043194L;
    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    

    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextArea jTextArea2;
    

    /**
     * An alternative starting point for this demo, to also allow running this
     * applet as an application.
     *
     * @param args ignored.
     */
  /*  public static void main(String [] args)
    {
        JGraphAdapterDemo applet = new JGraphAdapterDemo();
        applet.init("st70.xml");

        JFrame frame = new JFrame();
        JTextArea jta = new JTextArea();
        frame.getContentPane().add(applet);
        frame.getContentPane().add(jta);
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        //frame.add(jta);
        //frame.add(jButton3);
       
    }*/

    
}

//End JGraphXAdapterDemo.java
