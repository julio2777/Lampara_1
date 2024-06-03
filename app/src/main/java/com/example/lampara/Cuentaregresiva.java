package com.example.lampara;
import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
public class Cuentaregresiva extends Service{
    private CountDownTimer countDownTimer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getExtras() != null) {
            int horas = intent.getIntExtra("horas", 0);
            int minutos = intent.getIntExtra("minutos", 0);
            iniciarCuentaRegresiva(horas, minutos);
        }

        return START_STICKY; // si se reinicia, se detiene
    }

    private void iniciarCuentaRegresiva(int horas, int minutos) {
        long tiempoTotal = ((horas * 60) + minutos) * 60 * 1000; //a miliseg

        countDownTimer = new CountDownTimer(tiempoTotal, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //mostrar la cuenta regresiva
                enviarBroadcastTick(millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                // al finalizar cuenta regresiva
                enviarBroadcastFin();
                stopSelf(); // Detener el Service al finalizar la cuenta regresiva
            }
        };

        countDownTimer.start();
    }

    private void enviarBroadcastTick(long segundosRestantes) {
        Intent intent = new Intent("countdown_tick");
        intent.putExtra("segundos_restantes", segundosRestantes);
        sendBroadcast(intent);
    }

    private void enviarBroadcastFin() {
        Intent intent = new Intent("countdown_finish");
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; }

}
