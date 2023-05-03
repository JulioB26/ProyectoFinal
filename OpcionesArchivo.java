package Package1;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class OpcionesArchivo extends JPanel {
 
	  private Lienzo canvas;
	  private JButton botonGuardado, botonCarga, botonBorrarLienzo, botonDeshacerUltimaAccion, botonCambiarTamanoPixel;

	    public OpcionesArchivo(Lienzo canvas) {
	        this.canvas = canvas;
	        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
	        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	        botonGuardado = new JButton("Guardar");
	        botonGuardado.addActionListener(e -> guardarImagen());
	        add(botonGuardado);

	        botonCarga = new JButton("Cargar");
	        botonCarga.addActionListener(e -> cargarImagen());
	        add(botonCarga);

	        botonBorrarLienzo = new JButton("Borrar lienzo");
	        botonBorrarLienzo.addActionListener(e -> canvas.borrarLienzo());
	        add(botonBorrarLienzo);

	        botonCambiarTamanoPixel = new JButton("Cambiar tamaño de píxel");
	        botonCambiarTamanoPixel.addActionListener(e -> cambiarTamanoPixel());
	        add(botonCambiarTamanoPixel);
	        
	        botonDeshacerUltimaAccion = new JButton("Deshacer última acción");
	        botonDeshacerUltimaAccion.addActionListener(e -> canvas.deshacer());
	        add(botonDeshacerUltimaAccion);
	    }

	private void guardarImagen() {
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Guardar imagen");
	    fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes (JPG, GIF, PNG)", "jpg", "gif", "png"));

	    int result = fileChooser.showSaveDialog(this);

	    if (result == JFileChooser.APPROVE_OPTION) {
	        File file = fileChooser.getSelectedFile();
	        String fileName = file.getName().toLowerCase();

	        String format;
	        if (fileName.endsWith(".jpg")) {
	            format = "jpg";
	        } else if (fileName.endsWith(".gif")) {
	            format = "gif";
	        } else {
	            format = "png";
	            if (!fileName.endsWith(".png")) {
	                file = new File(file.getAbsolutePath() + ".png");
	            }
	        }

	        try {
	            canvas.guardarImagen(format, file);
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(this, "Error al guardar la imagen: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}

	private void cargarImagen() {
		
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Cargar imagen");
	    fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes (JPG, GIF, PNG)", "jpg", "gif", "png"));

	    int result = fileChooser.showOpenDialog(this);

	    if (result == JFileChooser.APPROVE_OPTION) {
	        File file = fileChooser.getSelectedFile();
	        try {
	            canvas.cargarImagen(file);
	            
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	    
	  }

	private void cambiarTamanoPixel() {
        String input = JOptionPane.showInputDialog(this, "Ingresa el nuevo tamaño de píxel (WIDTH x HEIGHT):");
        if (input != null) {
            String[] parts = input.split("x");
            if (parts.length == 2) {
                try {
                    int width = Integer.parseInt(parts[0].trim());
                    int height = Integer.parseInt(parts[1].trim());
                    canvas.cambiarTamañoPixel(width, height);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Entrada inválida. Introduce el tamaño en el formato WIDTH x HEIGHT", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Entrada inválida. Introduce el tamaño en el formato WIDTH x HEIGHT", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
