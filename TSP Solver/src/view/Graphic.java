/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import ilog.concert.IloException;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Graph;
import controller.LinearProgram;
import controller.Parser;

/**
 * 
 * @author Younes
 */
public class Graphic extends javax.swing.JFrame {

	/**
	 * Creates new form Graphic
	 */
	static File file = null;

	public Graphic() {
		initComponents();

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jToggleButton1 = new javax.swing.JToggleButton();
		jPanel1 = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();
		jTextField1 = new javax.swing.JTextField();
		jButton2 = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();

		jToggleButton1.setText("jToggleButton1");

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Welcome to TSP Solver");
		setBackground(new java.awt.Color(255, 255, 255));

		jPanel1.setBackground(new java.awt.Color(255, 255, 255));

		jButton1.setText("Browse");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jTextField1.setText("FilePath");
		jTextField1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTextField1ActionPerformed(evt);
			}
		});

		jButton2.setText("Go");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jButton2ActionPerformed(evt);
				} catch (IloException | IOException e) {
					// TODO Bloc catch généré automatiquement
					e.printStackTrace();
				}
			}
		});

		jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel1.setIcon(new javax.swing.ImageIcon(
				"C:\\Users\\Younes\\Desktop\\welcome.png")); // NOI18N

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addContainerGap(
																				63,
																				Short.MAX_VALUE)
																		.addComponent(
																				jLabel1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				643,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addGap(63,
																				63,
																				63)
																		.addComponent(
																				jButton1)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jTextField1)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jButton2,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				60,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGap(70, 70, 70)));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(40, 40, 40)
										.addComponent(
												jLabel1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												286,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												47, Short.MAX_VALUE)
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jTextField1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(jButton1)
														.addComponent(jButton2))
										.addContainerGap()));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
				javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
				javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		// TODO add your handling code here:
		JFileChooser choice = new JFileChooser();
		if (file != null) {
			choice.setCurrentDirectory(file);
		}
		int tempo = choice.showOpenDialog(jPanel1);
		if (tempo == JFileChooser.APPROVE_OPTION) {
			jTextField1.setText(choice.getSelectedFile().getAbsolutePath());
		}

	}// GEN-LAST:event_jButton1ActionPerformed

	private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField1ActionPerformed
		// TODO add your handling code here:

	}// GEN-LAST:event_jTextField1ActionPerformed

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)
			throws IloException, IOException {// GEN-FIRST:event_jButton2ActionPerformed
		// TODO add your handling code here:
		file = new File(jTextField1.getText());
		// String xmlFile = file.getAbsolutePath();
		// System.out.println("XML Path :"+file);

		// JGraphXAdapterDemo gApplet = new JGraphXAdapterDemo();

		// JGraphAdapterDemo gApplet = new JGraphAdapterDemo();
		// gApplet.init(xmlFile);
		JFrame ui = new JFrame();
		ui.setSize(new Dimension(400, 400));
		JTextArea textArea =  new JTextArea(10,60);
		//textArea.setSize(new Dimension(400, 200));
		PrintStream printStream = new PrintStream(new CustomOutputStream(
				textArea));
		System.setOut(printStream);
		System.setErr(printStream);
		JPanel jp = new JPanel();
		// jp.setPreferredSize(new Dimension(400,400));
		jp.setBackground(Color.cyan);
		JScrollPane sp = new JScrollPane(textArea);
		sp.setSize(new Dimension(400, 400));
		jp.add(sp);
		ui.add(jp);
		ui.setTitle("TSP SOLVER");
		ui.setBackground(Color.BLACK);

		// ui.setVisible(true);

		
		// gApplet.luncher();
		jPanel2 = new javax.swing.JPanel();
		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);

		jTextArea2 = new javax.swing.JTextArea();
		jButton3 = new javax.swing.JButton();

		jTextArea2.setColumns(20);
		jTextArea2.setRows(5);
		jButton3.setText("jButton3");

		initSecondFrameComponents();

		// jPanel2.add(gApplet);
		// jPanel2.add(jp);
		jPanel2.add(jTextArea2);
		jPanel2.add(jButton3);
		// setContentPane(jPanel2);
		setContentPane(jp);
		// getContentPane().add(jPanel2);
		revalidate();
	
		// jTextField1.setVisible(false);
		// pack();
		setVisible(true);
		Parser p = new Parser(file.getAbsolutePath());
		Graph graph_parsed = p.parse();
		LinearProgram lp = new LinearProgram(graph_parsed);
		Graph graph_solved = lp.solve();
		// jScrollPane1 = new javax.swing.JScrollPane();

		// vérifier que c'est bien un graphe complet et orienté appeler la
		// fonction isGraph sur notre graphe
		// Dessiner le graphe, on appelle la fonction du dessein

	}// GEN-LAST:event_jButton2ActionPerformed

	private void initSecondFrameComponents() {
		jPanel1.setVisible(false);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Graphic.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Graphic.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Graphic.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Graphic.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Graphic().setVisible(true);
			}
		});
	}

	private javax.swing.JButton jButton3;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JTextArea jTextArea2;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JToggleButton jToggleButton1;
	// End of variables declaration//GEN-END:variables
}
