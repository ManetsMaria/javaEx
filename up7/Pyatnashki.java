
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.util.Random;

public class Pyatnashki extends JFrame {
    private JPanel panelMini=new JPanel ();
    private JPanel panel = new JPanel(new GridLayout(4, 4, 2, 2));
    private int[][] numbers = new int[4][4];
    private ImageIcon[][] icons=new ImageIcon[4][4];
    JLabel label;
    ImageIcon icon ;

    public Pyatnashki() {
        super("Pyatnashki");

        setLayout(new BorderLayout());
        setBounds(600, 100, 200, 400);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createMenu();

        panel.setDoubleBuffered(true);
        add(panel, BorderLayout.CENTER);
        add(panelMini, BorderLayout.SOUTH);
        label = new JLabel();
        panelMini.add(label);
    }

    public void generate() {
        label.setIcon(icon);
        Random generator = new Random();
        int[] invariants = new int[16];

        do {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    numbers[i][j] = 0;
                    invariants[i * 4 + j] = 0;
                }
            }

            for (int i = 1; i < 16; i++) {
                int k, l;
                do {
                    k = generator.nextInt(4);
                    l = generator.nextInt(4);
                }
                while (numbers[k][l] != 0);
                numbers[k][l] = i;
                //ImageIcon buf=icons[i/4][i%4];
                //icons[i/4][i%4]=icons[k][l];
                //icons[k][l]=buf;
                invariants[k * 4 + l] = i;
            }
        }
        while (!canBeSolved(invariants));
    }

    private boolean canBeSolved(int[] invariants) {
        int sum = 0;
        for (int i = 0; i < 16; i++) {
            if (invariants[i] == 0) {
                sum += i / 4;
                continue;
            }

            for (int j = i + 1; j < 16; j++) {
                if (invariants[j] < invariants[i])
                    sum ++;
            }
        }
        System.out.println(sum % 2 == 0);
        return sum % 2 == 0;
    }

    public void repaintField() {
        panel.removeAll();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JButton button = new JButton(Integer.toString(numbers[i][j]));
                //button.setPreferredSize(new Dimension(40, 40));
                button.setIcon(icons[numbers[i][j]/4][numbers[i][j]%4]);
                button.setFocusable(false);
                panel.add(button);
                if (numbers[i][j] == 0) {
                    button.setVisible(false);
                } else
                    button.addActionListener(new ClickListener());
            }
        }

        panel.validate();
        panel.repaint();
    }

    public boolean checkWin() {
        boolean status = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 3 && j > 2)
                    break;
                if (numbers[i][j] != i * 4 + j + 1) {
                    status = false;
                }
            }
        }
        return status;
    }

    private void createMenu() {
        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        for (String fileItem : new String [] { "New", "Exit" }) {
            JMenuItem item = new JMenuItem(fileItem);
            item.setActionCommand(fileItem.toLowerCase());
            item.addActionListener(new NewMenuListener());
            fileMenu.add(item);
        }
        fileMenu.insertSeparator(1);

        menu.add(fileMenu);
        setJMenuBar(menu);
    }

    private class NewMenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if ("exit".equals(command)) {
                System.exit(0);
            }
            if ("new".equals(command)) {
                try {
                    JFileChooser fileopen = new JFileChooser();
                    int ret = fileopen.showDialog(null, "Open file");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File file= fileopen.getSelectedFile();
                        BufferedImage im=ImageIO.read(file);
                        icon = new ImageIcon (im);
                        for (int i=0; i<4; i++){
                            for (int j=0; j<4; j++){
                                icons[i][j]=new ImageIcon (im.getSubimage( i*43, j*43, 43, 43));
                                icons[i][j]=new ImageIcon(icons[i][j].getImage().getScaledInstance(62,62, icons[i][j].getImage().SCALE_DEFAULT));
                            }
                        }
                        generate();
                        repaintField();
                    }
                }
                catch(IOException i)
                {
                    JOptionPane.showMessageDialog(null, i);
                };
            }
        }
    }

    private class ClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            button.setVisible(false);
            String name = button.getText();
            change(Integer.parseInt(name));
        }
    }

    public void change(int num) {
        int i = 0, j = 0;
        for (int k = 0; k < 4; k++) {
            for (int l = 0; l < 4; l++) {
                if (numbers[k][l] == num) {
                    i = k;
                    j = l;
                }
            }
        }
        if (i > 0) {
            if (numbers[i - 1][j] == 0) {
                numbers[i - 1][j] = num;
                numbers[i][j] = 0;
            }
        }
        if (i < 3) {
            if (numbers[i + 1][j] == 0) {
                numbers[i + 1][j] = num;
                numbers[i][j] = 0;
            }
        }
        if (j > 0) {
            if (numbers[i][j - 1] == 0) {
                numbers[i][j - 1] = num;
                numbers[i][j] = 0;
            }
        }
        if (j < 3) {
            if (numbers[i][j + 1] == 0) {
                numbers[i][j + 1] = num;
                numbers[i][j] = 0;
            }
        }

        repaintField();

        if (checkWin()) {
            JOptionPane.showMessageDialog(null, "YOU WIN!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            generate();
            repaintField();
        }
    }
}