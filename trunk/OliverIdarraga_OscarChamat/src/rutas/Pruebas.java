package rutas;

import javax.swing.JFileChooser;
/**
 * Pruebas de Manejo de archivos con persistencia de rutas
 * @author Oscar Chamat Caicedo (civilian)
 */
public class Pruebas {
	public static void main(String[] args) {
		JFileChooser fChosser;
		fChosser=new JFileChooser();
		fChosser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		fChosser.showOpenDialog(null);
		
		System.out.println(fChosser.getSelectedFile());
	}
}
