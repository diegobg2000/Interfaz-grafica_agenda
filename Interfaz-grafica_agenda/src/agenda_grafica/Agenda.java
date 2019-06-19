package agenda_grafica;

	import java.io.*;
	import java.util.*;

	public class Agenda extends TreeMap<String, String> {

		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unused")
		
		
		private String ruta = null;
	

		public String ejecutar(String cmd) {
			String nombre = null;
			String token = null;
			String telefono = null;
			String respuesta = null;

			Scanner s = new Scanner(cmd);
			int estado = 0;
			while (estado != 5) {
				switch (estado) {
				case 0:
					try {
						token = s.skip("fin|buscar:|borrar:|\\p{L}+(\\s+\\p{L}+)*").match().group();
						if (token.equals("fin")) {
							estado = 5;
						} else if (token.equals("borrar:"))
							estado = 3;
						else if (token.equals("buscar:"))
							estado = 4;
						else {
							nombre = token;
							estado = 1;
						}
					} catch (NoSuchElementException e) {
						respuesta = "Se esperaba un nombre o 'buscar:' - 'guardar:' - 'borrar:' - 'cargar:' o 'fin'";
						estado = 5;
					}
					break;

				case 1:
					try {
						s.skip("-");
						estado = 2;
					} catch (NoSuchElementException e) {
						respuesta = "Se esperaba '-'";

						estado = 5;
					}
					break;
				case 2:
					try {
						telefono = s.skip("\\d+").match().group();
						if (!containsKey(nombre)) {
							put(nombre, telefono);
							respuesta = "Se insertó el contacto: " + nombre + "->" + telefono;
						} else {
							put(nombre, telefono);
							respuesta = "Se actualizó el contacto: " + nombre + "->" + telefono;
						}
						estado = 5;
					} catch (NoSuchElementException e) {
						respuesta = "Se esperaba un número de telefono";
						estado = 5;
					}
					break;

				case 3:
					try {
						nombre = s.skip("\\p{L}+").match().group();
						if (containsKey(nombre)) {
							remove(nombre);
							respuesta =  "Se eliminó el contacto " + "'" + nombre + "' de la agenda.";
						} else {
							respuesta = "No existe '" + nombre + "' en la agenda.";
						}
						estado = 5;
					} catch (NoSuchElementException e) {
						respuesta = "Se esperaba un nombre para borrar";
						estado = 5;
					}
					break;

				case 4:
					try {
						nombre = s.skip("\\p{L}+").match().group();
						if (containsKey(nombre)) {
							telefono = get(nombre);
							respuesta = nombre + "->" + telefono;
						} else {
							respuesta = "'" + nombre + "' no está en la agenda.";
						}
						estado = 5;
					} catch (NoSuchElementException e) {
						respuesta = "Se esperaba un nombre";
						estado = 5;
					}
					break;
				}
			}
			s.close();
			return respuesta;
		}

		public void load(String ruta) {
			File f = new File(ruta);
			try {
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				String linea;
				while ((linea = br.readLine()) != null) {
					String[] datos = linea.split("-");
					put(datos[0], datos[1]);
				}
				br.close();
			} catch (FileNotFoundException e) {
				System.out.println("Error al leer el fichero");
			} catch (IOException e) {
				System.out.println("Error al cargar el fichero");
			}
		}

		public void save(String ruta) {
			FileWriter fw;
			try {
				fw = new FileWriter(ruta);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				Set<String> conjuntoClaves = keySet();
				Iterator<String> it = conjuntoClaves.iterator();

				while (it.hasNext()) {
					String clave = (String) it.next();
					String tf = get(clave);
					pw.println(clave + "-" + tf);
				}
				pw.close();
			} catch (IOException e) {
				System.out.println("Error al guardar el fichero");
			}

		}

	}

