package natalia.tictactoe;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import static natalia.tictactoe.R.id.board;

public class MainActivity extends AppCompatActivity {

    private BoardView boardView;

    private GameEngine gameEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Tic Tac Toe");
        boardView = findViewById(board);
        gameEngine = new GameEngine();
        boardView.setGameEngine(gameEngine);
        boardView.setMainActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_game) {
            newGame();
        }
        return super.onOptionsItemSelected(item);
    }

    public void gameEnded(String winner) {
        String msg = winner.isEmpty() ? "Game Ended. Tie" :
                (winner.equals("X") ? "You win!" : "You lost!");
        new AlertDialog.Builder(this).setTitle("Tic Tac Toe").
                setMessage(msg).
                setOnDismissListener(dialogInterface -> newGame()).show();
    }

    private void newGame() {
        gameEngine.newGame();
        boardView.invalidate();
    }

}
