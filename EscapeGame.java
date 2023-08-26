/**
 * 敵から逃げてポイントを稼ぐゲームを実装するプログラム
 * 
 * @author M.Umeoka, S.Iwamoto
 * O.Honbou(Reviewer)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class EscapeGame extends JPanel implements ActionListener, KeyListener {
    private BufferedImage playerImage;
    private BufferedImage enemyImage;
    private int playerX, playerY;
    private int enemyX, enemyY;
    private int playerWidth = 150;
    private int playerHeight = 150;
    private int enemyWidth = 150;
    private int enemyHeight = 150;

    private int playerSpeed = 5;
    private int enemySpeedX = 3;
    private int enemySpeedY = 3;
    private int score = 0;
    private Timer timer;

    private Timer gameTimer; // ゲームのタイマー
    private int countdown = 30; // タイマーカウントダウン

    /**
     * プレイヤーと敵の画像を読み込む
     * 
     * ウインドウの設定
     * 
     */
    public EscapeGame() {  //コンストラクタ
        try {
            playerImage = ImageIO.read(getClass().getResource("player.png"));
            enemyImage = ImageIO.read(getClass().getResource("enemy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerX = 100;
        playerY = 200;
        enemyX = 800;
        enemyY = 200;

        setPreferredSize(new Dimension(900, 500));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(20, this);
        timer.start();

        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdown--;
                repaint();
                if (countdown <= 0) {
                    gameTimer.stop();
                    gameOver();
                }
            }
        });
        gameTimer.start();
    }

    /**
     * 画面の設定（部品になる部分を設定）
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(playerImage, playerX, playerY, playerWidth, playerHeight, null);
        g.drawImage(enemyImage, enemyX, enemyY, enemyWidth, enemyHeight, null);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Score: " + score, 20, 30);
        g.drawString("Time: " + countdown + " seconds", 20, 60);
    }

    /**
     * キャラクターの速さ、スコアの設定
     */
    public void actionPerformed(ActionEvent e) {
        playerX += playerSpeed;
        enemyX -= enemySpeedX;
        enemyY += enemySpeedY;

        if (playerX > getWidth()) {
            playerX = 0;
            score += 10;
        }

        if (enemyX < -enemyWidth) {
            enemyX = getWidth();
            enemyY = (int) (Math.random() * (getHeight() - enemyHeight));
        }

        if (enemyY <= 0 || enemyY >= getHeight() - enemyHeight) {
            enemySpeedY = -enemySpeedY;
        }

        // 当たり判定
        int playerHitBoxX = playerX + playerWidth / 4;
        int playerHitBoxY = playerY + playerHeight / 4;
        int playerHitBoxWidth = playerWidth / 2;
        int playerHitBoxHeight = playerHeight / 2;

        int enemyHitBoxX = enemyX + enemyWidth / 4;
        int enemyHitBoxY = enemyY + enemyHeight / 4;
        int enemyHitBoxWidth = enemyWidth / 2;
        int enemyHitBoxHeight = enemyHeight / 2;

        if (playerHitBoxX + playerHitBoxWidth > enemyHitBoxX && playerHitBoxX < enemyHitBoxX + enemyHitBoxWidth &&
                playerHitBoxY + playerHitBoxHeight > enemyHitBoxY && playerHitBoxY < enemyHitBoxY + enemyHitBoxHeight) {
            gameOver();
        }

        repaint();
    }

    /**
     * ゲームオーバー時の動作
     */
    public void gameOver() {
        timer.stop();
        gameTimer.stop();
        JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
        System.exit(0);
    }

    /**
     * キーを押した時の操作を表す押した時の操作を表す
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {  //キーボードの上矢印キーを押した時
            playerY -= playerSpeed;
        }
        if (key == KeyEvent.VK_DOWN) {  //キーボードの下矢印キーを押した時
            playerY += playerSpeed;
        }
    }
  
    /**
     * キーボードのキーがタイプされた状態のイベントを処理
     */
    public void keyTyped(KeyEvent e) {  //何もしない
    }

    /**
     * キーボードのキーが離された状態のイベントを処理
     */
    public void keyReleased(KeyEvent e) {  //何もしない
    }

    
}

