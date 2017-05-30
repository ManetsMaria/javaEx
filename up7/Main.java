import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.LineMetrics;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Map;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

class Task2Panel extends JPanel {
	private JTextField text = new JTextField();
	private int shadow = 0;
	private Color color = Color.GREEN;

	Task2Panel() {
		text.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				repaint();
			}
		});
		JButton shadowButton = new JButton("Change shadow");
		shadowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (shadow == 3)
					shadow = 0;
				else
					shadow++;
				repaint();
			}
		});
		JButton colorButton = new JButton("Change color");
		colorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JColorChooser colorChoose = new JColorChooser();
				color = colorChoose.showDialog(null, "Select a color", color);				
				repaint();
			}
		});
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		panel.add(new JLabel("Enter a text to show it in 3D"));
		panel.add(text);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1, 2));
		panel1.add(shadowButton);
		panel1.add(colorButton);
		add(panel, BorderLayout.NORTH);
		add(panel1, BorderLayout.SOUTH);
		add(new MyPanel(), BorderLayout.CENTER);
	}

	private class MyPanel extends JPanel {
		public MyPanel() {
		}

		@Override
		public void paintComponent(Graphics g) {
			Graphics2D gr = (Graphics2D) g;
			String str = text.getText() + " ";
			TextLayout txt = new TextLayout(str,
					new Font("Helvetica", Font.BOLD, (5 / 2) * getHeight() / (str.length() + 1)),
					gr.getFontRenderContext());
			AffineTransform tx = new AffineTransform();
			Shape sh = txt.getOutline(tx);
			for (int i = 0; i < 50 / str.length(); ++i) {
				if (shadow == 0) {
					tx.setToTranslation(200 - i, getWidth() / 3);
				} else if (shadow == 1) {
					tx.setToTranslation(200, getWidth() / 3 + i);
				} else if (shadow == 2) {
					tx.setToTranslation(200 + i, getWidth() / 3);
				} else if (shadow == 3) {
					tx.setToTranslation(200, getWidth() / 3 - i);
				}
				sh = txt.getOutline(tx);
				gr.draw(sh);
				gr.fill(sh);
			}

			for (int i = 0; i < 75 / str.length(); ++i) {				
				gr.setColor(new Color( i * color.getRed() * str.length() / 75, i * color.getGreen() * str.length() / 75, i * color.getBlue() * str.length() / 75));

				sh = txt.getOutline(tx);
				gr.draw(sh);
				gr.fill(sh);
			}
		}
	}
}
public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Mini Word Art");
		frame.add(new Task2Panel());
		frame.setSize(800, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}