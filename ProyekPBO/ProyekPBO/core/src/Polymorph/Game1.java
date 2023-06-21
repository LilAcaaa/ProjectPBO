//package Polymorph;
//
//import com.mygdx.game.Alien;
//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.utils.Array;
//
//public class Game1 extends ApplicationAdapter {
//    private SpriteBatch batch;
//    private BitmapFont font;
//    private OrthographicCamera camera;
//
//    private Player player;
//    private Array<Alien> aliens;
//    private Array<Projectile> projectiles;
//    private boolean gameOver;
//    private boolean gameStarted = false;
//
//    // ...
//
//    @Override
//    public void create() {
//        // ...
//        batch = new SpriteBatch();
//        font = new BitmapFont();
//        font.getData().setScale(2);
//
//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, 800, 600);
//
//        player = new Player(new Texture("player.png"), 800 / 2 - 64 / 2, 20, 64, 64);
//        aliens = new Array<Alien>();
//        projectiles = new Array<Projectile>();
//    }
//
//    // ...
//
//    @Override
//    public void render() {
//        // ...
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//
//        if (!gameStarted) {
//            // ...
//        } else if (!gameOver) {
//            // ...
//            player.render(batch);
//
//            for (Alien alien : aliens) {
//                alien.render(batch);
//            }
//
//            for (Projectile projectile : projectiles) {
//                projectile.render(batch);
//            }
//
//            // ...
//        } else {
//            // ...
//        }
//
//        // ...
//        batch.end();
//    }
//
//    // ...
//}
