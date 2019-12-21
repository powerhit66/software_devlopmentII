import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Startstate extends JFrame {
    final int windowWidth = 800;
    final int windowHeight = 500;

    public static void main(String[] args){
        new Startstate();
    }

    public Startstate() {
        Dimension dimOfScreen =
               Toolkit.getDefaultToolkit().getScreenSize();

        setBounds(dimOfScreen.width/2 - windowWidth/2,
                  dimOfScreen.height/2 - windowHeight/2,
                  windowWidth, windowHeight);
        setResizable(false);
        setTitle("Software Development II");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        MyJPanel panel= new MyJPanel();
        Container c = getContentPane();
        c.add(panel);
        setVisible(true);
    }

    public class MyJPanel extends JPanel implements
       ActionListener, MouseListener, MouseMotionListener {
        /* 全体の設定に関する変数 */
        Dimension dimOfPanel;
        boolean start = false;
        JButton startButton;

        /* コンストラクタ（ゲーム開始時の初期化）*/
        public MyJPanel() {
            // 全体の設定
            startButton = new JButton("press to start");
            startButton.addActionListener(this);
            add(startButton);

            /**
              *　難易度の設置：プルダウンメニューを利用
              *　配列を使い、JComboBoxを宣言する場合、ArrayListみたいに型の明示が必要
            */
            Integer[] difficulty = {1, 2, 3};

            //これだとエラーが出る。
            //JComboBox petList = new JComboBox(petStrings);

            //これならOK
            JComboBox<Integer> mode_menu = new JComboBox<Integer>(difficulty);
            mode_menu.addActionListener(this);
            add(mode_menu);
            }

        /* パネル上の描画 */
        public void paintComponent(Graphics g) {
                dimOfPanel = getSize();
                super.paintComponent(g);
                g.drawString("記憶マッチング", windowWidth, windowHeight);
            }

        /* 一定時間ごとの処理（ActionListener に対する処理）*/
        public void actionPerformed(ActionEvent e) {
            repaint();
        }

        /* MouseListener に対する処理 */
        // マウスボタンをクリックする
        public void mouseClicked(MouseEvent e) {
        }
        
        // マウスボタンを押下する
        public void mousePressed(MouseEvent e) {
        }

        // マウスボタンを離す
        public void mouseReleased(MouseEvent e) {
        }

        // マウスが領域外へ出る
        public void mouseExited(MouseEvent e) {
        }

        // マウスが領域内に入る
        public void mouseEntered(MouseEvent e) {
        }

        /* MouseMotionListener に対する処理 */
        // マウスを動かす
        public void mouseMoved(MouseEvent e) {
        }

        // マウスをドラッグする
        public void mouseDragged(MouseEvent e) {
        }

        /* 画像ファイルから Image クラスへの変換 */
        public Image getImg(String filename) {
            ImageIcon icon = new ImageIcon(filename);
            Image img = icon.getImage();

            return img;
        }
    }
}