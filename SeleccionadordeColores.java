package Package1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class SeleccionadordeColores extends JPanel {
	
	private static final long serialVersionUID = 1L;

    private JSlider redSlider;
    private JSlider greenSlider;
    private JSlider blueSlider;
    private JPanel colorPreview;
    private JPanel defaultColorsPanel;
    private Lienzo lienzo;
    private JButton lastUsedColorButton;
    private Color lastUsedColor;
    private Set<Color> defaultColorsSet;

    public SeleccionadordeColores(Lienzo lienzo) {
        this.lienzo = lienzo;
        setLayout(new GridLayout(6, 1));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        redSlider = crearSliderColor("Rojo", Color.RED);
        greenSlider = crearSliderColor("Verde", Color.GREEN);
        blueSlider = crearSliderColor("Azul", Color.BLUE);

        add(redSlider);
        add(greenSlider);
        add(blueSlider);

        colorPreview = new JPanel();
        colorPreview.setBorder(BorderFactory.createTitledBorder("Vista previa del color"));
        colorPreview.setBackground(Color.BLACK);
        add(colorPreview);

        defaultColorsPanel = new JPanel(new GridLayout(2, 5));
        defaultColorsPanel.setBorder(BorderFactory.createTitledBorder("Colores predeterminados"));
        add(defaultColorsPanel);

        Color[] defaultColors = {
                Color.BLACK, Color.WHITE, Color.RED, Color.GREEN, Color.BLUE,
                Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.ORANGE
        };

        defaultColorsSet = new HashSet<>(Arrays.asList(defaultColors));

        for (Color color : defaultColors) {
            JButton colorButton = new JButton();
            colorButton.setBackground(color);
            colorButton.setPreferredSize(new Dimension(35, 35));
            colorButton.setBorder(BorderFactory.createEmptyBorder());
            colorButton.setOpaque(true);
            colorButton.addActionListener(e -> setColor(color));
            defaultColorsPanel.add(colorButton);
        }

        lastUsedColor = Color.BLACK;
        lastUsedColorButton = new JButton();
        lastUsedColorButton.setBackground(lastUsedColor);
        lastUsedColorButton.setPreferredSize(new Dimension(35, 35));
        lastUsedColorButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lastUsedColorButton.setOpaque(true);
        lastUsedColorButton.addActionListener(e -> setColor(lastUsedColor));
        defaultColorsPanel.add(lastUsedColorButton);
    }

    private JSlider crearSliderColor(String title, Color color) {
        JSlider slider = new JSlider(0, 255);
        slider.setBorder(BorderFactory.createTitledBorder(title));
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setForeground(color);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateColor();
            }
        });

        return slider;
    }

    private void updateColor() {
        Color newColor = new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue());
        setColor(newColor);
    }

    private void setColor(Color color) {
        if (!defaultColorsSet.contains(color)) {
            lastUsedColor = color;
            lastUsedColorButton.setBackground(color);
        }
        lienzo.establecerColorActual(color);
        colorPreview.setBackground(color);
    }
}