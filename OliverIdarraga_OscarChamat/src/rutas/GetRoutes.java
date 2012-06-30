package rutas;

import java.io.BufferedReader;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
/**
 * Manejo de archivos con persistencia de rutas
 * @author Oscar Chamat Caicedo (civilian)
 */
public class GetRoutes {
	
	public static String escogerRutaDirectorio() throws IOException {
		JFileChooser fChosser=new JFileChooser(ultRuta());
		fChosser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int seleccion=fChosser.showOpenDialog(null);
		if(seleccion==JFileChooser.APPROVE_OPTION){
			guadarUltRuta(fChosser.getSelectedFile().getParentFile().getPath());
			return fChosser.getSelectedFile().getPath();
		}
		else {
			return "Not Selected";
		}		
	}
	
	public static String escogerRutaArchivo() throws IOException {
		JFileChooser fChosser=new JFileChooser(ultRuta());
		int seleccion=fChosser.showOpenDialog(null);
		if(seleccion==JFileChooser.APPROVE_OPTION){
			guadarUltRuta(fChosser.getSelectedFile().getParentFile().getPath());
			return fChosser.getSelectedFile().getPath();
		}else
		{
			return "Not Selected";
		}
	}
	
	public static String escogerRutaArchivoGuardar() throws IOException {
		JFileChooser fChosser=new JFileChooser(ultRuta());
		fChosser.showSaveDialog(null);
		guadarUltRuta(fChosser.getSelectedFile().getParentFile().getPath());
		return fChosser.getSelectedFile().getPath();
		// TODO Auto-generated method stub

	}
	
	private static String ultRuta() throws IOException {
		//saco la ultima ruta utilizada
		String ultRuta="";
		try {
			BufferedReader in=new BufferedReader(new FileReader("ultRuta"));
			ultRuta=in.readLine();
		} catch (Exception e) {
			System.err.println("Archivo ultRuta no encontrado");
		}
//		System.out.println(ultRuta);
		return ultRuta;
	}
	
	private static void guadarUltRuta(String ruta) throws IOException {
		// TODO Auto-generated method stub
//		System.out.println(ruta);
		FileWriter out=new FileWriter("ultRuta");
		out.write(ruta);
		out.close();
	}
}
