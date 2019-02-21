package innobi.ru.townweather;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class TheWeatherActivity extends AppCompatActivity {
    TextView textView;
    ImageView imageView;
    int iTownInfo = 8;
    String url = "http://geoman.ru/temp/weather2.txt";
    String town;
    Boolean booleanWind;
    Boolean booleanWet;
    Boolean booleanPressure;
    int temperature = 0;
    int wet = 0;
    int wind = 0;
    int pressure = 0;
    int cloudy = 0;
    int rain = 0;
    int snow = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theweather);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        final TextView status = findViewById(R.id.status);
        final TextView browse = findViewById(R.id.browse);

        town = getIntent().getExtras().getString(MainActivity.KEY_TOWN);
        booleanWind = getIntent().getExtras().getBoolean(MainActivity.KEY_WIND);
        booleanWet = getIntent().getExtras().getBoolean(MainActivity.KEY_WET);
        booleanPressure = getIntent().getExtras().getBoolean(MainActivity.KEY_PRESSURE);

        // Создаем объект класса запроса на лету делаем анонимный класс слушателя
        final RequestToWeb requestToWeb = new RequestToWeb(new RequestToWeb.OnRequestListener() {
            // Обновим прогресс
            @Override
            public void onStatusProgress(String updateProgress) {
                status.setText(updateProgress);
            }
            // По окончании загрузки страницы вызовем этот метод, который и вставит текст в WebView
            @Override
            public void onComplete(String result) {
                status.setText(String.format("%s Считали из файла: %s", town, url));
                String[] parts = result.split("\\n");
                browse.setText(result);
                for (int i = 0; i < parts.length/iTownInfo; i++) {
                    if (parts[i*iTownInfo].equals(town)){
                        temperature = Integer.parseInt(parts[i*iTownInfo+1]);
                        wet = Integer.parseInt(parts[i*iTownInfo+2]);
                        wind = Integer.parseInt(parts[i*iTownInfo+3]);
                        pressure = Integer.parseInt(parts[i*iTownInfo+4]);
                        cloudy = Integer.parseInt(parts[i*iTownInfo+5]);
                        rain = Integer.parseInt(parts[i*iTownInfo+6]);
                        snow = Integer.parseInt(parts[i*iTownInfo+7]);

                        String res;
                        if (!town.isEmpty()) {
                            res = "Погода в городе - " + town + "\n";
                            res += "Температура " + temperature+ " C°\n";
                            if (booleanWind) res += "Ветер " + wind + " м/с\n";
                            if (booleanWet) res += "Относительная влажность " + wet + "%\n";
                            if (booleanPressure) res += "Давление " + pressure + " мм рт. ст.";
                            showWeatherImage(cloudy, rain, snow);
                        } else res = "Пожалуйста укажите город";
                        textView.setText(res);

                        break;
                    }
                }
            }
        });

        requestToWeb.make(url);
    }

    private void showWeatherImage(int cloudy, int rain, int snow) {
        if (cloudy == 2) {
            if (rain==2) {
                if (snow>0) {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.oblakarainsnow2));
                } else {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.oblakarain2));
                }
            }
            if (rain==1) {
                if (snow==2) {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.oblakarainsnow2));
                } else if (snow==1) {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.oblakarainsnow));
                } else if (snow==0) {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.oblakarain));
                }
            }
            if (rain==0) {
                if (snow==2) {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.oblakasnow2));
                } else if (snow==1) {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.oblakasnow));
                } else if (snow==0) {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.oblaka));
                }
            }
        }
        if (cloudy == 1) {
            if (rain==2) {
                if (snow>0) {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.sunoblrainsnow2));
                } else {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.sunoblrain2));
                }
            }
            if (rain==1) {
                if (snow==2) {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.sunoblrainsnow2));
                } else if (snow==1) {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.sunoblrainsnow));
                } else if (snow==0) {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.sunoblrain));
                }
            }
            if (rain==0) {
                if (snow==2) {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.sunoblsnow2));
                } else if (snow==1) {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.sunoblsnow));
                } else if (snow==0) {
                    imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.sunobl));
                }
            }
        }
        if (cloudy == 0) {
            imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.sun));
        }
    }






}
