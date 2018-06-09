package com.lordneru.pruebas.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Almansa on 30/07/2016.
 */
public class Lienzo extends View {

    //Path del trazo de lineas
    private Path drawPath;

    //Paint de dibujar y paint de canvas
    private Paint drawPaint;
    private Paint canvasPaint;

    //Var del color inicializada a con color inicial
    private static int paintColor = 0xFF000000;

    //Canvas
    private Canvas drawCanvas;

    //canvas para guardar
    private Bitmap canvasBitmap;

    //indica si esta borrando
    private boolean borrado;

    //Constructor
    public Lienzo(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    /**
     *Setup drawing configuration
     */
    private void setupDrawing(){

        //Configuracion del area sobre quela que va a pintar
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(Constantes.TAM_PINCEL_MEDIANO);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    /**
     * Tamaño asignado a la vista
     * @param w anchura
     * @param h altura
     * @param oldw anchura antigua
     * @param oldh altura antigua
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    //Pinta la vista. Sera llamado desde el OnTouchEvent
    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    /**
     * Registra el onTouch del usuario
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float touchX = event.getX();
        float touchY = event.getY();


        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        //repintar
        invalidate();
        return true;

    }

    /**
     * Set color to Paint
     * @param color
     */
    public void setColor(String color){
        invalidate();
        paintColor = Color.parseColor(color);
        drawPaint.setColor(paintColor);
    }

    /**
     * Set el nuevo tamanio del punto
     * @param nuevoTam
     */
    public void setTamanioPunto(float nuevoTam){
        drawPaint.setStrokeWidth(nuevoTam);
    }

    public void setBorrado(boolean isBorrado){
        borrado = isBorrado;
        PorterDuffXfermode borrar;
        if(borrado){
            //borrar = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
            drawPaint.setColor(Color.WHITE);
        }else{
            //borrar = null;
            drawPaint.setColor(paintColor);
        }
        //drawPaint.setXfermode(borrar);
    }

    public void nuevoDibujo(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }
}
