package com.example.mispeliculas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editPelicula;
    Button btnAgregar;
    LinearLayout layoutPeliculas;
    TextView txtContador;

    int cantidadPeliculas = 0;
    int cantidadVistas = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPelicula = findViewById(R.id.editPelicula);
        btnAgregar = findViewById(R.id.btnAgregar);
        layoutPeliculas = findViewById(R.id.layoutPeliculas);
        txtContador = findViewById(R.id.txtContador);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombrePelicula = editPelicula.getText().toString().trim();

                if (nombrePelicula.isEmpty()) {

                    Toast.makeText(
                            MainActivity.this,
                            "Escribí el nombre de una película",
                            Toast.LENGTH_SHORT
                    ).show();

                } else {

                    agregarPelicula(nombrePelicula);

                    editPelicula.setText("");
                }
            }
        });
    }

    private void agregarPelicula(String nombre) {

        // Tarjeta principal de la película
        LinearLayout tarjetaPelicula = new LinearLayout(this);
        tarjetaPelicula.setOrientation(LinearLayout.HORIZONTAL);
        tarjetaPelicula.setPadding(20, 20, 20, 20);

        LinearLayout.LayoutParams parametrosTarjeta =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parametrosTarjeta.setMargins(0, 10, 0, 10);
        tarjetaPelicula.setLayoutParams(parametrosTarjeta);

        // Imagen de la película
        ImageView imagenPelicula = new ImageView(this);

        if (nombre.equalsIgnoreCase("interestelar")) {
            imagenPelicula.setImageResource(R.drawable.interestelar);

        } else if (nombre.equalsIgnoreCase("sherk")) {
            imagenPelicula.setImageResource(R.drawable.sherk);

        } else if (nombre.equalsIgnoreCase("spiderman")) {
            imagenPelicula.setImageResource(R.drawable.spiderman);

        } else {
            imagenPelicula.setImageResource(R.drawable.ic_launcher_foreground);
        }

        int anchoPoster = getResources().getDimensionPixelSize(R.dimen.poster_ancho);
        int altoPoster = getResources().getDimensionPixelSize(R.dimen.poster_alto);
        int margenPoster = getResources().getDimensionPixelSize(R.dimen.poster_margen);

        LinearLayout.LayoutParams parametrosImagen =
                new LinearLayout.LayoutParams(anchoPoster, altoPoster);

        parametrosImagen.setMargins(0, 0, margenPoster, 0);
        imagenPelicula.setLayoutParams(parametrosImagen);
        imagenPelicula.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // Contenedor vertical para título, estado y botón
        LinearLayout datosPelicula = new LinearLayout(this);
        datosPelicula.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams parametrosDatos =
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                );

        datosPelicula.setLayoutParams(parametrosDatos);

        // Título
        TextView tituloPelicula = new TextView(this);
        tituloPelicula.setText(nombre);
        tituloPelicula.setTextSize(18);
        tituloPelicula.setTextColor(getColor(R.color.texto_principal));

        // Estado
        TextView estadoPelicula = new TextView(this);
        estadoPelicula.setText("Estado: Pendiente");
        estadoPelicula.setTextSize(14);
        estadoPelicula.setTextColor(getColor(R.color.texto_secundario));

        // Botón
        Button btnVista = new Button(this);
        btnVista.setText("Marcar como vista");

        btnVista.setBackgroundTintList(
                getColorStateList(R.color.rojo_principal)
        );
        btnVista.setTextColor(getColor(R.color.white));

        btnVista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                estadoPelicula.setText("Estado: ✓ Vista");

                btnVista.setText("Vista");
                btnVista.setEnabled(false);

                cantidadVistas++;

                actualizarContadores();
            }
        });

        Button btnEliminar = new Button(this);
        btnEliminar.setText("Eliminar");

        btnEliminar.setBackgroundTintList(
                getColorStateList(R.color.superficie_boton)
        );
        btnEliminar.setTextColor(getColor(R.color.white));

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Si la película estaba vista, bajamos también ese contador
                if (!btnVista.isEnabled()) {
                    cantidadVistas--;
                }

                layoutPeliculas.removeView(tarjetaPelicula);

                cantidadPeliculas--;

                actualizarContadores();
            }
        });

        // Armamos la parte derecha
        datosPelicula.addView(tituloPelicula);
        datosPelicula.addView(estadoPelicula);
        datosPelicula.addView(btnVista);
        datosPelicula.addView(btnEliminar);

        // Armamos la tarjeta completa
        tarjetaPelicula.addView(imagenPelicula);
        tarjetaPelicula.addView(datosPelicula);

        // Agregamos la tarjeta a "Mi lista"
        layoutPeliculas.addView(tarjetaPelicula);

        cantidadPeliculas++;

        actualizarContadores();

    }

    private void actualizarContadores() {

        txtContador.setText(
                getString(
                        R.string.contador,
                        cantidadPeliculas,
                        cantidadVistas
                )
        );
    }
}