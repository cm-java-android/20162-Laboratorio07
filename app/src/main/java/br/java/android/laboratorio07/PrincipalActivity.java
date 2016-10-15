package br.java.android.laboratorio07;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


public class PrincipalActivity extends AppCompatActivity
        implements View.OnClickListener{

    // Quando a classe implementa um View.OnClickListener,
    // a própria classe se torna um ouvinte de clique

    // Campos que apresentarão o texto na tela
    private TextView operacaoEscolhida;
    private TextView estiloEscolhido;

    private TextView maximoTextView;
    private TextView minimoTextView;
    private SeekBar maximoSeekBar;
    private SeekBar minimoSeekBar;

    // Precisamos saber qual foi a operação que foi selecionada
    private int operacaoSelecionada;
    private boolean[] estiloMotoSelecionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Aqui definimos os cliques para que possam ser abertas
        // as caixas de diálogo
        findViewById(R.id.operacaoTextView).setOnClickListener(this);
        findViewById(R.id.estiloTextView).setOnClickListener(this);
        operacaoSelecionada = 0; // Para apontar para o primeiro
        estiloMotoSelecionado = new boolean[5];

        minimoTextView = (TextView) findViewById(R.id.valorMinimoTextView);
        minimoSeekBar = (SeekBar) findViewById(R.id.valorMinimoSeekBar);
        maximoTextView = (TextView) findViewById(R.id.valorMaximoTextView);
        maximoSeekBar = (SeekBar) findViewById(R.id.valorMaximoSeekBar);
        // E aqui passamos o valor selecionado para o TextView associado
        minimoTextView.setText(Integer.toString(minimoSeekBar.getProgress()));
        maximoTextView.setText(Integer.toString(maximoSeekBar.getProgress()));

        minimoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // Aqui, a cada toque, é capturado o valor do SeekBar
                // Este é ruim caso tenha que enviar os dados os dados
                // Vamos utilizar apenas para informar a alteração no valor
                minimoTextView.setText(Integer.toString(minimoSeekBar.getProgress()));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Aqui apenas quando o usuário soltar o SeekBar que o valor é capturado
                // Melhor opção caso tenha que enviar os dados para algum servidor...
                Log.d("Laboratorio 07", "Valor minimo recebido " + minimoSeekBar.getProgress());

            }
        });

        maximoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                maximoTextView.setText(Integer.toString(maximoSeekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("Laboratorio 07",  "Valor maximo recebido " + maximoSeekBar.getProgress());
            }
        });
    }

    /**
     * Qualquer toque na tela, vamos capturar quando o
     * toque for no tipo de operação ou na escolha do
     * estilo da moto
     * @param view - Componente que foi tocado
     */
    @Override
    public void onClick(View view) {
      showDialog(view.getId());

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder montador = new AlertDialog.Builder(this);

        switch (id){
            case R.id.operacaoTextView:
                CharSequence[] operacoes = new CharSequence[] {"Aluguel", "Venda"};
                montador.setTitle("Tipo da Operação")
                        .setSingleChoiceItems(operacoes, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                operacaoSelecionada = i;
                                // Ainda falta exibir qual foi a estilo selecionado

                            }
                        })
                        .setCancelable(false)
                        .setNeutralButton("Ok",null);
                break;

            case R.id.estiloTextView:
                CharSequence[] estilos = new CharSequence[] {"Street", "Trilha", "Custom"};
                montador.setTitle("Estilo da Moto")
                        .setCancelable(false)
                        .setPositiveButton("Ok",null)
                        .setMultiChoiceItems(estilos, estiloMotoSelecionado,
                                new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                estiloMotoSelecionado[i] = b;
                                // Dessa forma, em estiloMotoSelecionado, na posição i é marcado
                                // com true em b o estilo selecionado
                            }
                        });

                break;
        }

        return montador.create();
    }
}
