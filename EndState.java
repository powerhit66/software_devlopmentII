import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EndState extends JFrame {
    final int windowWidth = 800;
    final int windowHeight = 500;

    public static void main(String[] args){
        new EndState(args);
    }

    public EndState(String[] args) {
        Dimension dimOfScreen =
               Toolkit.getDefaultToolkit().getScreenSize();

        setBounds(dimOfScreen.width/2 - windowWidth/2,
                  dimOfScreen.height/2 - windowHeight/2,
                  windowWidth, windowHeight);
        setResizable(false);
        setTitle("Software Development II");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        MyJPanel panel= new MyJPanel(args);
        Container c = getContentPane();
        c.add(panel);
        setVisible(true);
    }

    public class MyJPanel extends JPanel implements
       ActionListener, MouseListener, MouseMotionListener {
        /* 全体の設定に関する変数 */
        Dimension dimOfPanel;
        boolean start = false;
        JButton endButton, restartButton;
        Font romanItalic;
        int fontSize = 26;

        //ユーザに結果を示す文字列
        String result;

        //結果を保存する変数：1なら勝利、0なら負け
        String verdict;

        int buttonHeight=40, buttonWidth=100;

        /* コンストラクタ（ゲーム開始時の初期化）*/
        public MyJPanel(String[] args) {
            // 全体の設定
            setLayout(null);
            romanItalic = new Font("Times New Roman", Font.ITALIC, fontSize);

            verdict = args[0];
            if(verdict=="1")
            {
                result = "ペアを全部見つけた！おめでとう！";
            }
            else
            {
                result = "時間切れ！";
            }

            /**
             * 再開、終了ボタンに関する設定
            */
            restartButton = new JButton("もう一度する");
            endButton = new JButton("ゲーム終了");
            restartButton.addActionListener(this);
            restartButton.setBounds(windowWidth/2-buttonWidth*2, windowHeight-buttonHeight*2, 100, 40);
            endButton.addActionListener(this);
            endButton.setBounds(windowWidth/2+buttonWidth, windowHeight-buttonHeight*2, 100, 40);
            add(restartButton);
            add(endButton);
            }

        /* パネル上の描画 */
        public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setFont(romanItalic);
                dimOfPanel = getSize();
                g.drawString(result, windowWidth/2-fontSize * result.length()/2, windowHeight/2);
            }

        /* 一定時間ごとの処理（ActionListener に対する処理）*/
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==endButton)
            {
                System.exit(0);
            }
            else if(e.getSource()==restartButton)
            {
                Startstate.main(null);
                dispose();
            }
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
    }
}