import java.awt.*;
import java.awt.event.*;
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
        Timer timer;
        ImageIcon iconMe, iconEnemy;
        Image imgMe, imgEnemy, imgEnemy2;
        boolean start = false;
        JButton startButton;

        /* 自機に関する変数 */
        int myHeight, myWidth;
        int myX, myY, tempMyX;
        int gap = 100;
        int myMissileX, myMissileY;
        boolean isMyMissileActive, isBigActive;
        int BigMissileCD=100;
        int BigX, BigY;

        /* 敵機に関する変数*/
        Enemy[] enemy;

        /* コンストラクタ（ゲーム開始時の初期化）*/
        public MyJPanel() {
            // 全体の設定
            setLayout(null);
            setBackground(Color.black);
            addMouseListener(this);
            addMouseMotionListener(this);
            timer = new Timer(50, this);
            startButton = new JButton("press to start");
            startButton.addActionListener(this);
            startButton.setBounds(windowWidth/2-50, windowHeight-70, 100, 40);
            add(startButton);

            // 画像の取り込みと画像のサイズ
            imgMe = getImg("jiki.jpg");
            myWidth = imgMe.getWidth(this);
            myHeight = imgMe.getHeight(this);

            imgEnemy = getImg("teki.jpg");
            imgEnemy2 = getImg("teki2.jpg");
            Enemy.enemyWidth = imgEnemy.getWidth(this);
            Enemy.enemyHeight = imgEnemy.getHeight(this);

            // 自機と敵機の初期化
            initMyPlane();
            initEnemyPlane();
        }

        /* パネル上の描画 */
        public void paintComponent(Graphics g) {
                dimOfPanel = getSize();
                super.paintComponent(g);

                // 各要素の描画
                drawMyPlane(g);       // 自機
                drawMyMissile(g);     // 自機のミサイル
                drawEnemyPlane(g);    // 敵機
                drawEnemyMissile(g);  // 敵機のミサイル
                g.drawString("必殺技あと：" + Integer.toString(BigMissileCD), 10, windowHeight-30);

                // 敵機を全機撃墜した時の終了処理
                if (Enemy.numOfAlive == 0) {
                    System.exit(0);
                }
            }

        /* 一定時間ごとの処理（ActionListener に対する処理）*/
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==startButton) 
            {
                //タイマーがONなら、STOPしてボタンの文字を変える
                if(timer.isRunning())
                {
                    timer.stop();
                    startButton.setText("press to start");
                    start = false;
                }

                //タイマーがOFFならSTARTし、ボタンの文字を変える
                else
                {
                    timer.start();
                    start = true;
                    startButton.setText("press to stop");
                    for(int i=0; i<Enemy.numOfAlive; i++)
                    enemy[i].isEnemyMissileActive = true;
                }
            }
            BigMissileCD-=1;
            if(BigMissileCD<=0) BigMissileCD=0;
            repaint();
        }

        /* MouseListener に対する処理 */
        // マウスボタンをクリックする
        public void mouseClicked(MouseEvent e) {
        }
        
        // マウスボタンを押下する
        public void mousePressed(MouseEvent e) {
            if(e.getButton()==MouseEvent.BUTTON1)
            {
                if (!isMyMissileActive) {
                    myMissileX = tempMyX + myWidth/2;
                    myMissileY = myY;
                    isMyMissileActive = true;
                }
            }
            //大きい弾, 5秒に一回しか撃てない
            else
            {
                if(BigMissileCD==0)
                {
                    BigX = tempMyX + myWidth/2;
                    BigY = myY;
                    isBigActive = true;
                    BigMissileCD = 100;
                }
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
            myX = e.getX();
        }

        // マウスをドラッグする
        public void mouseDragged(MouseEvent e) {
            myX = e.getX();
        }

        /* 画像ファイルから Image クラスへの変換 */
        public Image getImg(String filename) {
            ImageIcon icon = new ImageIcon(filename);
            Image img = icon.getImage();

            return img;
        }

        /* 自機の初期化 */
        public void initMyPlane() {
            myX = windowWidth / 2;
            myY = windowHeight - 100;
            tempMyX = windowWidth / 2;
            isMyMissileActive = false;
        }

        /* 敵機の初期化 */
        public void initEnemyPlane() {
            enemy = new Enemy[Enemy.numOfEnemy];

            for (int i=0; i<7; i++) {
                enemy[i] = new Enemy();
                enemy[i].enemyX = 70*i;
                enemy[i].enemyY = 50;
            }

            for (int i=7; i<Enemy.numOfEnemy; i++) {
                enemy[i] = new Enemy();
                enemy[i].enemyX = 70*(i-6);
                enemy[i].enemyY = 100;
            }

            for (int i=0; i<Enemy.numOfEnemy; i++) {
                enemy[i].enemyHP = (int)(Math.random()*2+1);
                enemy[i].enemyMissileX = enemy[i].enemyX + Enemy.enemyWidth/2;
                enemy[i].enemyMissileY = enemy[i].enemyY;
                enemy[i].enemyMissileSpeed = 10 + (i%6);
            }
        }

        /* 自機の描画 */
        public void drawMyPlane(Graphics g) {
            if (Math.abs(tempMyX - myX) < gap) {
                if (myX < 0) {
                    myX = 0;
                } else if (myX+myWidth > dimOfPanel.width) {
                    myX = dimOfPanel.width - myWidth;
                }
                tempMyX = myX;
                g.drawImage(imgMe, tempMyX, myY, this);
            } else {
                g.drawImage(imgMe, tempMyX, myY, this);
            }
        }

        /* 自機のミサイルの描画 */
        public void drawMyMissile(Graphics g) {
            if (isMyMissileActive) {
                // ミサイルの配置
                myMissileY -= 15;
                g.setColor(Color.white);
                g.fillRect(myMissileX, myMissileY, 2, 5);

                // 自機のミサイルの敵機各機への当たり判定
                for (int i=0; i<Enemy.numOfEnemy; i++) {
                    if (enemy[i].isEnemyAlive) {
                        //左上と右下両方判定する必要がある
                        if (((myMissileX >= enemy[i].enemyX) &&
                            (myMissileX <= enemy[i].enemyX+Enemy.enemyWidth) &&
                            (myMissileY >= enemy[i].enemyY) &&
                            (myMissileY <= enemy[i].enemyY+Enemy.enemyHeight)) ||

                            ((myMissileX+2 >= enemy[i].enemyX) &&
                            (myMissileX+2 <= enemy[i].enemyX+Enemy.enemyWidth) &&
                            (myMissileY+5 >= enemy[i].enemyY) &&
                            (myMissileY+5 <= enemy[i].enemyY+Enemy.enemyHeight))) {
                                enemy[i].enemyHP -=1;
                                if(enemy[i].enemyHP==0)
                                {
                                    enemy[i].isEnemyAlive = false;
                                    Enemy.numOfAlive--;
                                }
                                isMyMissileActive = false;
                        }
                    }
                }

                // ミサイルがウィンドウ外に出たときのミサイルの再初期化
                if (myMissileY < 0) isMyMissileActive = false;
            }

            if (isBigActive) {
                // ミサイルの配置
                BigY -= 15;
                g.setColor(Color.white);
                g.fillRect(BigX, BigY, 20, 50);

                // 自機のミサイルの敵機各機への当たり判定
                for (int i=0; i<Enemy.numOfEnemy; i++) {
                    if (enemy[i].isEnemyAlive) {
                        if ((BigX >= enemy[i].enemyX) &&
                            (BigX <= enemy[i].enemyX+Enemy.enemyWidth) &&
                            (BigY >= enemy[i].enemyY) &&
                            (BigY <= enemy[i].enemyY+Enemy.enemyHeight) ||

                            (BigX+20 >= enemy[i].enemyX) &&
                            (BigX+20 <= enemy[i].enemyX+Enemy.enemyWidth) &&
                            (BigY+50 >= enemy[i].enemyY) &&
                            (BigY+50 <= enemy[i].enemyY+Enemy.enemyHeight)) {
                                enemy[i].enemyHP -=1;
                                if(enemy[i].enemyHP==0)
                                {
                                    enemy[i].isEnemyAlive = false;
                                    Enemy.numOfAlive--;
                                }
                        }
                    }
                }

                // ミサイルがウィンドウ外に出たときのミサイルの再初期化
                if (BigY < 0) isBigActive = false;
            }
        }

        /* 敵機の描画 */
        public void drawEnemyPlane(Graphics g) {
            for (int i=0; i<Enemy.numOfEnemy; i++) {
                if (enemy[i].isEnemyAlive) {
                    if (enemy[i].enemyX > dimOfPanel.width - Enemy.enemyWidth) {
                        enemy[i].enemyMove = -1;
                    } else if (enemy[i].enemyX < 0) {
                        enemy[i].enemyMove = 1;
                    }
                    enemy[i].enemyX += enemy[i].enemyMove*10;
                    
                    //敵機のHPにより描画を変える
                    if(enemy[i].enemyHP==2)
                    {
                        g.drawImage(imgEnemy2, enemy[i].enemyX, enemy[i].enemyY, this);
                    }
                    else
                    {
                        g.drawImage(imgEnemy, enemy[i].enemyX, enemy[i].enemyY, this);
                    }
                }
            }
        }

        /* 敵機のミサイルの描画 */
        public void drawEnemyMissile(Graphics g) {
            for (int i=0; i<Enemy.numOfEnemy; i++) {
                // ミサイルの配置
                if (enemy[i].isEnemyMissileActive) {
                    enemy[i].enemyMissileY += enemy[i].enemyMissileSpeed;
                    g.setColor(Color.red);
                    g.fillRect(enemy[i].enemyMissileX,
                    enemy[i].enemyMissileY, 2, 5);
                }

                // 敵機のミサイルの自機への当たり判定
                if ((enemy[i].enemyMissileX >= tempMyX) &&
                    (enemy[i].enemyMissileX <= tempMyX+myWidth) &&
                    (enemy[i].enemyMissileY+5 >= myY) &&
                    (enemy[i].enemyMissileY+5 <= myY+myHeight)) {
                    System.exit(0);
                }

                // ミサイルがウィンドウ外に出たときのミサイルの再初期化
                if (enemy[i].enemyMissileY > dimOfPanel.height) {
                    if (enemy[i].isEnemyAlive) {
                        enemy[i].enemyMissileX = enemy[i].enemyX + Enemy.enemyWidth/2;
                        enemy[i].enemyMissileY = enemy[i].enemyY + Enemy.enemyHeight;
                    } else {
                        enemy[i].isEnemyMissileActive = false;
                    }
                }
            }
        }
    }
}
//オブジェクト思考を導入 情報隠蔽も入れたいが、ちょっと複雑になるので今回は無しに
class Enemy {
    //長さなどは共通変数なので、staticで宣言
    static int enemyWidth, enemyHeight;
    static int numOfEnemy = 12;
    static int numOfAlive = numOfEnemy;
    int enemyX, enemyY, enemyMove=1;
    boolean isEnemyAlive = true, isEnemyMissileActive;
    int enemyMissileX, enemyMissileY, enemyMissileSpeed;
    int enemyHP;
}