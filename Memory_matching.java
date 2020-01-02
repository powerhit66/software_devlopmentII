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

        //画像の個数/行
        int columns = 8;
        Dimension dimOfPanel;

        //ゲーム開始の変数
        boolean start = false;

        //難易度
        int difficulty = 3;

        //画像の数
        int numOfPic;

        //画像クラスを保存するリスト
        image[] imgList;

        //ゲーム時間
        int sec = 60;

        Timer timer;
        Timer timer2;
        Random rand;
        int rows;

        //マウスの位置
        int mouseX, mouseY;

        //クリックした画像クラスを保存するリストとcounter
        image[] clickedImage;
        int clickedCounter;

        /* コンストラクタ（ゲーム開始時の初期化）*/
        public MyJPanel() {
            // 全体の設定
            setLayout(null);

            addMouseListener(this);

            //タイマーの設定、1秒に一回
            timer = new Timer(1000, this);
            timer2 = new Timer(300, this);
            timer.start();

            //難易度により画像数が違う
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
            imgList = new image[numOfPic*2];
            clickedImage = new image[2];

            rand = new Random();

            //画像数/行
            rows = numOfPic*2/columns;

            //画像の取得
            for(int i=0; i<numOfPic; i++)
            {
                imgList[i] = new image();
                imgList[i].getImg("picture1/" + i + ".jpg");

                //ペアなので、二つ必要
                imgList[i+numOfPic] = new image();
                imgList[i+numOfPic].getImg("picture1/" + i + ".jpg");
                //画像のid
                imgList[i].id = imgList[i+numOfPic].id = i;
            }

            //array shuffle
            for(int i=0;i<numOfPic*2; i++)
            {
                int ran = rand.nextInt(numOfPic*2);
                image tmp = imgList[i];
                imgList[i] = imgList[ran];
                imgList[ran] = tmp;
            }

            //画像のx, y軸を記録
            for(int i=0;i<numOfPic*2; i++)
            {
                imgList[i].imgX = (i%columns+1)*image.imgSize;
                imgList[i].imgY = (i/columns+1)*image.imgSize;
            }
        }

        /* パネル上の描画 */
        public void paintComponent(Graphics g) {
                super.paintComponent(g);
                dimOfPanel = getSize();

                //秒数の描画
                g.drawString(Integer.toString(sec), 50, 50);

                //画像の描画
                for(int i=0; i<rows; i++)
                {
                    for(int j=0; j<columns; j++)
                    {
                        if(imgList[i*columns+j].clicked || imgList[i*columns+j].paired)
                        g.drawImage(imgList[i*columns+j].imgSource, imgList[i*columns+j].imgX, imgList[i*columns+j].imgY, image.imgSize, image.imgSize, this);
                    }
                }

                //枠線の描画
                for(int i=0; i<=rows; i++)
                {
                    for(int j=0; j<=columns; j++)
                    {
                        //枠線の描画：縦
                        g.drawLine(image.imgSize*(j+1), image.imgSize, image.imgSize*(j+1), image.imgSize*(rows+1));
                    }
                    //枠線の描画：横
                    g.drawLine(image.imgSize, image.imgSize*(i+1), image.imgSize*(columns+1), image.imgSize*(i+1));
                }

                //ペアになった画像に線を引く
                for(int i=0; i<rows; i++)
                {
                    for(int j=0; j<columns; j++)
                    {
                        if(imgList[i*columns+j].paired)
                        g.drawLine(imgList[(i)*columns+(j)].imgX, imgList[(i)*columns+(j)].imgY, imgList[(i)*columns+(j)].imgX+image.imgSize, imgList[(i)*columns+(j)].imgY+image.imgSize);
                    }
                }
            }

        /* 一定時間ごとの処理（ActionListener に対する処理）*/
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==timer)
            {
                sec --;
                if(sec==0)
                {
                    timer.stop();
                    timer2.stop();
                }
                repaint();
            }
            else if(e.getSource()==timer2)
            {
                if(clickedCounter==2)
                {
                    //タイマーの時間を利用、プレイヤーの間違ったペアを少しだけの間表示させる
                    clickedImage[0].clicked = clickedImage[1].clicked = false;
                    clickedCounter = 0;
                    timer2.stop();
                }
            }
        }

        /* MouseListener に対する処理 */
        // マウスボタンをクリックする
        public void mouseClicked(MouseEvent e) {
        }
        
        // マウスボタンを押下する
        public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            int i, j;

            //マウスの位置をキャッチし、画像群の中にあるかどうか判断
            if(mouseX >=image.imgSize && mouseX <=image.imgSize*(columns+1) 
            && mouseY >= image.imgSize && mouseY <=image.imgSize*(rows+1)
            && clickedCounter<2
            && timer.isRunning())
            {
                i = mouseX / image.imgSize;
                j = mouseY / image.imgSize;

                //falseならtrue(画像を表示させる)に、trueならfalseに
                if(imgList[columns*(j-1)+(i-1)].clicked && !(imgList[columns*(j-1)+(i-1)].paired))
                {
                    imgList[columns*(j-1)+(i-1)].clicked = false;
                    clickedCounter--;
                }
                else
                {
                    imgList[columns*(j-1)+(i-1)].clicked = true;

                    //画像クラスを保存する
                    clickedImage[clickedCounter] = imgList[columns*(j-1)+(i-1)];
                    clickedCounter++;
                }

                if(clickedCounter==2)
                {
                    if(clickedImage[0].id == clickedImage[1].id)
                    {
                        clickedImage[0].paired = clickedImage[1].paired = true;
                        clickedCounter = 0;
                    }
                }
                else
                {
                    timer2.start();
                }
                repaint();
            }
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

/*画像クラス*/
class image {

    //共通変数：画像サイズ
    static int imgSize = 80;

    //画像のx,y軸位置, 画像のid
    int imgX, imgY, id;

    //画像がクリックされたかどうか
    boolean clicked = false;

    //画像用の変数
    Image imgSource;

    //ペアが見つかったかどうかの変数
    boolean paired = false;

    /* 画像ファイルから Image クラスへの変換 */
    public void getImg(String filename) {
        ImageIcon icon = new ImageIcon(filename);
        imgSource = icon.getImage();
    }
}