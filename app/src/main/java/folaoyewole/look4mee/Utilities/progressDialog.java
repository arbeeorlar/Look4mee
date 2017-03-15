package folaoyewole.look4mee.Utilities;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by sp_developer on 11/2/16.
 */
public class progressDialog {
    Context mContext;
    AlertDialog.Builder builder;

    // Empty constructor for accessing class
    public progressDialog(){

    }

    public progressDialog(Context context,String title, String msg){
        mContext = context;
        builder =  new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("OK",null);
        builder.show();
    }

    public void error_Dialog(Context context, String msg, String errorString){
        builder =  new AlertDialog.Builder(context);
        builder.setTitle(errorString);
        builder.setMessage(msg);
        //  builder.setIcon(R.mipmap.fail);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

}
