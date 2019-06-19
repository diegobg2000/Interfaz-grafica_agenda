package agenda_grafica;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.ImageIcon;



public class interfaz extends JFrame implements ActionListener, KeyListener, WindowListener {

		private static final long serialVersionUID = 1L;

		private Agenda agenda = new Agenda();
		private JTextField cmd;
		private static JTextArea textArea;
		private JFileChooser fileChooser = new JFileChooser();
		private String ruta = null;

		public interfaz() throws IOException, FontFormatException {
			setTitle("Agenda");//titulo
			setIconImage(Toolkit.getDefaultToolkit().getImage("res/img/Play.png"));//Es el icono de la ventana
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setLocationRelativeTo(null);
			setLayout(new BorderLayout());//crea la caja con las regiones
			JToolBar toolBar = new JToolBar();
			

			JButton load = new JButton(new ImageIcon("res/img/Open file.png"));
			load.setActionCommand("LOAD");
			load.addActionListener(this);
			toolBar.add(load);

			JButton saveAs = new JButton(new ImageIcon("res/img/Save as.png"));
			saveAs.setActionCommand("SAVE AS");
			saveAs.addActionListener(this);
			toolBar.add(saveAs);

			JButton save = new JButton(new ImageIcon("res/img/Save.png"));
			save.setActionCommand("SAVE");//Establece el nombre del comando para el evento de acción activado por este botón.
			save.addActionListener(this);
			toolBar.add(save);

			add(toolBar, BorderLayout.NORTH);

			
			textArea = new JTextArea(20, 80);
			textArea.setEditable(false);
			textArea.setFocusable(false);

			add(textArea, BorderLayout.CENTER);

			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			


			cmd = new JTextField();
			cmd.addKeyListener(this);
			panel.add(cmd, BorderLayout.CENTER);
			cmd.setText("");


			JButton exec = new JButton(new ImageIcon("res/img/Play.png"));
			exec.setActionCommand("EXEC");
			exec.addActionListener(this);//Pulsamos y se ejecuta
			panel.add(exec, BorderLayout.EAST);

			add(panel, BorderLayout.SOUTH);

			pack();
			setLocationRelativeTo(null);
			addWindowListener(this);
		}

		private void exec() throws IOException {//se ejecuta
			
			
			String result = agenda.ejecutar(cmd.getText());
			if (result != null) {
				textArea.append(result + "\n");
			}
			cmd.setText("");
			
		}

		public static void main(String[] args) {
			SwingUtilities.invokeLater(() -> {
				try {
					new interfaz().setVisible(true);//

				} catch (IOException | FontFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}

		@Override
		public void windowActivated(WindowEvent arg0) {
			cmd.requestFocus();
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			int respuesta = JOptionPane.showConfirmDialog(interfaz.this, "¿Está seguro de que quiere cerara la agenda?", "CERRAR",
					JOptionPane.YES_NO_OPTION, 3, new ImageIcon("res/img/Agenda/agenda.png"));//cambiar parte del codigo como lo tiene el
			if (respuesta == JOptionPane.YES_OPTION)
				System.exit(0);
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				String mensaje = agenda.ejecutar(cmd.getText());
				cmd.setText("");
				textArea.append(mensaje + System.lineSeparator());
				
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
		}	

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("LOAD")) {
				load();
			} else if (e.getActionCommand().equals("SAVE AS")) {
				saveAs();
				
			} else if (e.getActionCommand().equals("SAVE")) {
				save();
				
			} else if (e.getActionCommand().equals("EXEC")) {
				try {
					exec();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		private void load() {
			fileChooser.showOpenDialog(null);
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			File fichero = fileChooser.getSelectedFile();
			try {
				ruta = fichero.getCanonicalPath();
				agenda.load(ruta);
				textArea.setText("Agenda cargada con éxito."+ "\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
		}

		private void saveAs() {
			fileChooser.showSaveDialog(null);
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			File fichero = fileChooser.getSelectedFile();

			try {
				ruta = fichero.getCanonicalPath();
				textArea.setText("Agenda guardada con éxito."+ "\n");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			agenda.save(ruta);
			
		}
		
		private void save(){
			agenda.save(ruta);
		}

		

	}

