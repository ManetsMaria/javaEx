public class Demo {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.setVisible(true);
                    mainFrame.fastTest();
                }
            });
        }
    }
}
