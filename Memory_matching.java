import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Memory_matching extends JFrame {
    final int windowWidth = 800;
    final int windowHeight = 500;

    public static void main(String[] args){
        new Memory_matching();
    }

    public Memory_matching() {
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
        String startText, modeText;
        Font romanItalic;
        int fontSize = 16;
        int difficulty = 1;
        int numOfPic;
        ArrayList<Image> imgList;
        int[] imgPosList;

        /* コンストラクタ（ゲーム開始時の初期化）*/
        public MyJPanel() {
            // 全体の設定
            setLayout(null);

            imgList = new ArrayList<Image>();

            switch(difficulty){
                case 1:
                    numOfPic = 8;
                    break;
                case 2:
                    numOfPic = 12;
                    break;
                case 3:
                    numOfPic = 16;
                    break;
            }

            //画像の取得
            for(int i=1; i<=numOfPic; i++)
            {
                Image img = getImg("picture1/" + i + ".jpg");
                imgList.add(img);
            }

            //画像順番を記録する配列
            imgPosList = new int[numOfPic*2];
            for(int i=0, j=0; i<numOfPic*2; i+=2,j++)
            {
                imgPosList[i] = j;
                imgPosList[i+1] = j;
            }

            Random rand = new Random();
            //array shuffle
            for(int i=0;i<numOfPic*2; i++)
            {
                int ran = rand.nextInt(numOfPic*2);
                int tmp = imgPosList[i];
                imgPosList[i] = imgPosList[ran];
                imgPosList[ran] = tmp;
            }
        }

        /* パネル上の描画 */
        public void paintComponent(Graphics g) {
                super.paintComponent(g);
                dimOfPanel = getSize();

                int numPerRow = 8;
                int rows = numOfPic*2/numPerRow;
                int imgX=0, imgY=80, imgSize = 80;
                for(int i=1; i<=rows; i++)
                {
                    for(int j=0; j<numPerRow; j++)
                    {
                        g.drawImage(imgList.get(imgPosList[j+(i-1)*8]), (imgX+=80), imgY, imgSize, imgSize,this);
                    }
                    imgY += imgSize;
                    imgX = 0;
                }
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