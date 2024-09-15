package com.example.farmerapplication.common;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.farmerapplication.R;

//BrodCastReciever:system and app communication
public class NetworkChangeListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      if(!NetworkDetails.isconnectedtointernet(context))//Arguments Must provided,if network not connected
      {
          AlertDialog.Builder ad=new AlertDialog.Builder(context);//To build The AlertDialog
          View layout_dialog=LayoutInflater.from(context).inflate(R.layout.internetconnectivitydialogbox,null);//View Store the layout and views
          //LayoutInfalter:Call layout in java class
          //from(context):code in java class so use from method
          //inflate:call layout and menu
          ad.setView(layout_dialog);
          AlertDialog alertDialog=ad.create();
          alertDialog.show();
          alertDialog.setCanceledOnTouchOutside(false);
          AppCompatButton btnRetry=layout_dialog.findViewById(R.id.btnInternetConnectivity);
          btnRetry.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  alertDialog.dismiss();
                  onReceive(context,intent);
              }
          });
      }
      else {
          Toast.makeText(context,"You Are Connected to Internet",Toast.LENGTH_SHORT).show();
      }
    }
}
