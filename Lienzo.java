package Package1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

class Lienzo extends JPanel {

	 private Color currentColor;
	    private BufferedImage image;
	    private static final int WIDTH = 800;
	    private static final int HEIGHT = 800;
	    private static final int GRID_SIZE = 20;

	    private List<Rectangle> historial;
	    
	    public Lienzo() {
	        currentColor = Color.BLACK;
	        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	        setOpaque(true);
	        setMinimumSize(new Dimension(WIDTH, HEIGHT));
	        setPreferredSize(new Dimension(WIDTH, HEIGHT));
	        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        
	        historial = new ArrayList<>();

	        
	        MouseAdapter mouseAdapter = new MouseAdapter() {
	        	
	            public void mouseClicked(MouseEvent e) {
	                pintarPixel(e.getX(), e.getY());
	            }

	            public void mouseDragged(MouseEvent e) {
	                pintarPixel(e.getX(), e.getY());
	            }
	        };

	        addMouseListener(mouseAdapter);
	        addMouseMotionListener(mouseAdapter);

	        Graphics2D g2d = image.createGraphics();
	        g2d.setColor(Color.WHITE);
	        g2d.fillRect(0, 0, WIDTH, HEIGHT);
	        g2d.dispose();
	    }

	    private void pintarPixel(int x, int y) {
	        x = (x / GRID_SIZE) * GRID_SIZE;
	        y = (y / GRID_SIZE) * GRID_SIZE;

	        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
	            Graphics g = image.getGraphics();
	            g.setColor(currentColor);
	            g.fillRect(x, y, GRID_SIZE, GRID_SIZE);
	            repaint();

	            historial.add(new Rectangle(x, y, GRID_SIZE, GRID_SIZE));
	        }
	    }

	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, this);

	        g.setColor(Color.GRAY);
	        for (int x = 0; x < getWidth(); x += GRID_SIZE) {
	            g.drawLine(x, 0, x, getHeight());
	        }
	        for (int y = 0; y < getHeight(); y += GRID_SIZE) {
	            g.drawLine(0, y, getWidth(), y);
	        }
	    }

	    public void establecerColorActual(Color color) {
	        currentColor = color;
	    }

	    public void guardarImagen(String format, File file) throws IOException {
	        ImageIO.write(image, format, file);
	    }

	    public void cargarImagen(File file) throws IOException {
	        image = ImageIO.read(file);
	        repaint();
	    }

	    public void borrarLienzo() {
	        Graphics2D g2d = image.createGraphics();
	        g2d.setColor(Color.WHITE);
	        g2d.fillRect(0, 0, getWidth(), getHeight());
	        g2d.dispose();
	        repaint();
	    }
	    
	    public void cambiarTamañoPixel(int newWidth, int newHeight) {
	        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2d = resizedImage.createGraphics();
	        g2d.setColor(Color.WHITE);
	        g2d.fillRect(0, 0, newWidth, newHeight);

	        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
	        g2d.drawImage(image, 0, 0, newWidth, newHeight, null);
	        g2d.dispose();

	        image = resizedImage;
	        setMinimumSize(new Dimension(newWidth, newHeight));
	        setPreferredSize(new Dimension(newWidth, newHeight));
	        setSize(new Dimension(newWidth, newHeight));

	        revalidate();
	        repaint();
	    }
	    
	    public void deshacer() {
	        if (!historial.isEmpty()) {
	            Rectangle lastAction = historial.remove(historial.size() - 1);
	            Graphics g = image.getGraphics();
	            g.setColor(Color.WHITE);
	            g.fillRect(lastAction.x, lastAction.y, GRID_SIZE, GRID_SIZE);
	            g.dispose();
	            repaint();
	        }
	    }
	}
	