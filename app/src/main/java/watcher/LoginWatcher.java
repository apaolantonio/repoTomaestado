package watcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public class LoginWatcher implements TextWatcher{



    private TextView component = null;
    private String tipo;
    private String igual;
    int tam=0;
    int tamMin;
    int tamMax;



    public LoginWatcher(TextView component, String tipo, int tamMin, int tamMax) {
        super();
        this.component = component;
        this.tipo=tipo;
        this.tamMin=tamMin;
        this.tamMax=tamMax;
    }

    public LoginWatcher(TextView component, String tipo, String igual, int tam) {
        super();
        this.component = component;
        this.igual=igual;
        this.tipo=tipo;
        this.tam=tam;
    }

    @Override
    public void afterTextChanged(Editable arg0) {
        String message = "";

        if (component.getText().toString().length() != tam && tam!=0)
        {
            message = tipo + " debe contener" + igual;
        }

        if(component.getText().toString().length() < tamMin)
        {
            message = tipo + " debe ser mayor a " + tamMin;
        }
        if(component.getText().toString().length() > tamMax && tamMax!=0)
        {
            message = tipo + " debe ser menor a " + tamMax;
        }
        if ( message.equals("") == true )
        {
            component.setError(null);
        }
        else
        {
            component.setError(message);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {

    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

    }
}
