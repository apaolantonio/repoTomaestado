package watcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public class EstadoWatcher implements TextWatcher{



    protected TextView componente = null;
    protected boolean requerido = false;

    public EstadoWatcher(TextView componente, boolean requerido)
    {
        this.componente = componente;
        this.requerido = requerido;
    }

    @Override
    public void afterTextChanged(Editable arg0) {
        if ( validarComponente() == false )
        {
            componente.setError(getMensajeError());
        }
        else
        {
            componente.setError(null);
        }
    }

    protected boolean validarComponente()
    {
        return requerido == false || (requerido == true && componente != null && componente.getText().length() != 0);
    }

    protected String getMensajeError()
    {
        return "El estado no puede estar vacío";
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {

    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

    }

}
