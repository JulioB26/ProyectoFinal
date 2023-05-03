package Package1;

import javax.swing.*;
import java.awt.*;

public class PixelArtApp extends JFrame {
    
	private Lienzo canvas;
    private SeleccionadordeColores colorPicker;
    private OpcionesArchivo fileButtons;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PixelArtApp app = new PixelArtApp();
            app.setVisible(true);
        });
    }

    public PixelArtApp() {
        setTitle("Pixel Art App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        canvas = new Lienzo();
        colorPicker = new SeleccionadordeColores(canvas);
        fileButtons = new OpcionesArchivo(canvas);

       
        JPanel canvasContainer = new JPanel(new GridBagLayout());
        canvasContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        canvasContainer.add(canvas);
        canvasContainer.setBackground(Color.DARK_GRAY);

 
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidePanel.add(colorPicker, BorderLayout.CENTER);
        sidePanel.add(fileButtons, BorderLayout.NORTH);

        add(canvasContainer, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.WEST);

        pack();
        setLocationRelativeTo(null);
    }
}

