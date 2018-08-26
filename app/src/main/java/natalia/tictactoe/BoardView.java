package natalia.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BoardView extends View {

    private static final int LINE_THICK = 5;
    private static final int SPACE_MARGIN = 20;
    private static final int SPACE_STROKE_WIDTH = 15;
    private int width, height, spaceWidth, spaceHeight;
    private Paint gridPaint, oPaint, xPaint;
    private GameEngine gameEngine;
    private MainActivity mainActivity;

    public BoardView(Context context) {
        super(context);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gridPaint = new Paint();
        oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setColor(Color.RED);
        oPaint.setStyle(Paint.Style.STROKE);
        oPaint.setStrokeWidth(SPACE_STROKE_WIDTH);
        xPaint = new Paint(oPaint);
        xPaint.setColor(Color.BLUE);
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        height = View.MeasureSpec.getSize(heightMeasureSpec);
        width = View.MeasureSpec.getSize(widthMeasureSpec);
        spaceWidth = (width - LINE_THICK) / 3;
        spaceHeight = (height - LINE_THICK) / 3;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawGrid(canvas);
        drawBoard(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!gameEngine.isFinished()  &&  event.getAction() == MotionEvent.ACTION_DOWN) {
            int column = (int) (event.getX() / spaceWidth);
            int line = (int) (event.getY() / spaceHeight);
            boolean finished = gameEngine.makePlayerMove(line, column);
            String winner = gameEngine.getWinner();
            invalidate();
            if (finished) {
                mainActivity.gameEnded(winner);
            } else {
                finished = gameEngine.makeComputerMove();
                invalidate();
                if (finished) {
                    mainActivity.gameEnded(winner);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void drawBoard(Canvas canvas) {
        for (int line = 0; line < 3; line++) {
            for (int column = 0; column < 3; column++) {
                drawSpace(canvas, gameEngine.getSpaceValue(line, column), column, line);
            }
        }
    }

    private void drawGrid(Canvas canvas) {
        for (int i = 0; i < 2; i++) {
            // vertical lines
            float left = spaceWidth * (i + 1);
            float right = left + LINE_THICK;
            float top = 0;
            float bottom = height;
            canvas.drawRect(left, top, right, bottom, gridPaint);
            // horizontal lines
            float left2 = 0;
            float right2 = width;
            float top2 = spaceHeight * (i + 1);
            float bottom2 = top2 + LINE_THICK;
            canvas.drawRect(left2, top2, right2, bottom2, gridPaint);
        }
    }

    private void drawSpace(Canvas canvas, String value, int line, int column) {
        if (value.equals(GameEngine.COMPUTER_SYMBOL)) {
            float cx = (spaceWidth * line) + spaceWidth / 2;
            float cy = (spaceHeight * column) + spaceHeight / 2;
            canvas.drawCircle(cx, cy, Math.min(spaceWidth, spaceHeight) / 2 - SPACE_MARGIN * 2, oPaint);
        } else if (value.equals(GameEngine.PLAYER_SYMBOL)) {
            float startX = (spaceWidth * line) + SPACE_MARGIN;
            float startY = (spaceHeight * column) + SPACE_MARGIN;
            float endX = startX + spaceWidth - SPACE_MARGIN * 2;
            float endY = startY + spaceHeight - SPACE_MARGIN;
            canvas.drawLine(startX, startY, endX, endY, xPaint);
            float startX2 = (spaceWidth * (line + 1)) - SPACE_MARGIN;
            float startY2 = (spaceHeight * column) + SPACE_MARGIN;
            float endX2 = startX2 - spaceWidth + SPACE_MARGIN * 2;
            float endY2 = startY2 + spaceHeight - SPACE_MARGIN;
            canvas.drawLine(startX2, startY2, endX2, endY2, xPaint);
        }
    }

}