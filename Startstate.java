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
        setTitle("Memory Matching");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        MyJPanel panel= new MyJPanel();
        Container c = getContentPane();
        c.add(panel);
        
        setVisible(true);
    }

    public class MyJPanel extends JPanel implements
       ActionListener, MouseListener {
        /* 全体の設定に関する変数 */
        boolean start = false;
        JButton startButton;
        String startText, modeText;
        Font romanItalic;
        int fontSize = 16;

        //別の画面にパスする配列
        String[] passable;

        //プルダウンメニュー
        JComboBox<Integer> mode_menu;

        int buttonHeight=40, buttonWidth=100;
        int modeHeight=40, modeWidth = 60;

        /* コンストラクタ（ゲーム開始時の初期化）*/
        public MyJPanel() {
            // 全体の設定
            setLayout(null);
            romanItalic = new Font("Times New Roman", Font.ITALIC, fontSize);

            /**
             * 開始ボタンの関する設定
            */
            startText = "記憶マッチング";
            startButton = new JButton("press to start");
            startButton.addActionListener(this);
            startButton.setBounds(windowWidth/2-buttonWidth, windowHeight-buttonHeight*2, 100, 40);
            add(startButton);
            
            /**
              *　難易度の設置：プルダウンメニューを利用
              *　配列を使い、JComboBoxを宣言する場合、ArrayListみたいに型の明示が必要
            */

            //難易度用の整数型配列
            Integer[] difficulty = {1, 2, 3};

            modeText = "難易度設定:";

            //これだとエラーが出る。
            //JComboBox petList = new JComboBox(petStrings);

            //これならOK
            mode_menu = new JComboBox<Integer>(difficulty);
            mode_menu.addActionListener(this);
            mode_menu.setBounds(windowWidth/2+2*modeWidth, windowHeight-2*modeHeight, modeWidth, modeHeight);
            add(mode_menu);

            //次の画面に渡す値のための配列の初期化
            passable = new String[1];
            }

        /* パネル上の描画 */
        public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setFont(romanItalic);
                g.drawString(startText, windowWidth/2-fontSize * startText.length()/2, windowHeight/2);
                g.drawString(modeText, windowWidth/2+modeWidth/2, windowHeight-5*modeHeight/4);
            }

        /* 一定時間ごとの処理（ActionListener に対する処理）*/
        public void actionPerformed(ActionEvent e) {
            //スタートボタンが押されたら、次の画面に遷移し、現在の画面を消す
            if(e.getSource()==startButton)
            {
                //難易度を次の画面に渡す
                passable[0] = mode_menu.getSelectedItem().toString();
                Memory_matching.main(passable);
                dispose();
            }
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
    }
}